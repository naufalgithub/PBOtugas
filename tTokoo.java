package tokoo;

import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TestTokoo extends JFrame {
    private String[] judul = {"kode_barang", "nama_barang", "harga_barang", "stok_barang"};
    DefaultTableModel df;
    JTable tab = new JTable();
    JScrollPane scp = new JScrollPane();
    JPanel pnl = new JPanel();
    JLabel lblkode_barang = new JLabel("Kode Barang");
    JTextField txkode_barang = new JTextField(20);
    JLabel lblnama_barang = new JLabel("Nama Barang");
    JTextField txnama_barang = new JTextField(20);
    JLabel lblharga_barang = new JLabel("Harga Barang");
    JTextField txharga_barang = new JTextField(20);
    JLabel lblstok_barang = new JLabel("Stok Barang");
    JTextField txstok_barang = new JTextField(20);
    JButton btadd = new JButton("Simpan");
    JButton btnew = new JButton("Baru");
    JButton btdel = new JButton("Hapus");
    JButton btedit = new JButton("Ubah");

    TestTokoo() {
        super("Data Barang");
        setSize(460, 300);
        pnl.setLayout(null);

        pnl.add(lblkode_barang);
        lblkode_barang.setBounds(20, 10, 80, 20);
        pnl.add(txkode_barang);
        txkode_barang.setBounds(105, 10, 100, 20);

        pnl.add(lblnama_barang);
        lblnama_barang.setBounds(20, 33, 80, 20);
        pnl.add(txnama_barang);
        txnama_barang.setBounds(105, 33, 175, 20);

        pnl.add(lblharga_barang);
        lblharga_barang.setBounds(20, 56, 80, 20);
        pnl.add(txharga_barang);
        txharga_barang.setBounds(105, 56, 100, 20);

        pnl.add(lblstok_barang);
        lblstok_barang.setBounds(20, 79, 80, 20);
        pnl.add(txstok_barang);
        txstok_barang.setBounds(105, 79, 100, 20);

        pnl.add(btnew);
        btnew.setBounds(300, 10, 125, 20);
        btnew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnewAksi(e);
            }
        });

        pnl.add(btadd);
        btadd.setBounds(300, 33, 125, 20);
        btadd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btaddAksi(e);
            }
        });

        pnl.add(btedit);
        btedit.setBounds(300, 56, 125, 20);
        btedit.setEnabled(false);
        btedit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bteditAksi(e);
            }
        });

        pnl.add(btdel);
        btdel.setBounds(300, 79, 125, 20);
        btdel.setEnabled(false);
        btdel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btdelAksi(e);
            }
        });

        df = new DefaultTableModel(null, judul);
        tab.setModel(df);
        scp.getViewport().add(tab);
        tab.setEnabled(true);
        tab.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tabMouseClicked(evt);
            }
        });

        scp.setBounds(20, 110, 405, 130);
        pnl.add(scp);
        getContentPane().add(pnl);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    void loadData() {
        try {
            Connection cn = new ConnectDB().getConnect();
            Statement st = cn.createStatement();
            String sql = "SELECT * FROM tbl_barang";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String kode_barang, nama_barang, harga_barang, stok_barang;
                kode_barang = rs.getString("kode_barang");
                nama_barang = rs.getString("nama_barang");
                harga_barang = rs.getString("harga_barang");
                stok_barang = rs.getString("stok_barang");
                String[] data = {kode_barang, nama_barang, harga_barang, stok_barang};
                df.addRow(data);
            }
            rs.close();
            cn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void clearTable() {
        int numRow = df.getRowCount();
        for (int i = 0; i < numRow; i++) {
            df.removeRow(0);
        }
    }

    void clearTextField() {
        txkode_barang.setText(null);
        txnama_barang.setText(null);
        txharga_barang.setText(null);
        txstok_barang.setText(null);
    }

    void simpanData(Tokoo M) {
        try {
            Connection cn = new ConnectDB().getConnect();
            Statement st = cn.createStatement();
            String sql = "INSERT INTO tbl_barang (kode_barang, nama_barang, harga_barang, stok_barang) " +
                    "VALUES ('" + M.getkode_barang() + "', '" + M.getnama_barang() + "', '" + M.getharga_barang() + "', '" + M.getstok_barang() + "')";
            int result = st.executeUpdate(sql);
            cn.close();
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan",
                    "Info Proses", JOptionPane.INFORMATION_MESSAGE);
            String[] data = {M.getkode_barang(), M.getnama_barang(), M.getharga_barang(), M.getstok_barang()};
            df.addRow(data);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void hapusData(String kode_barang) {
        try {
            Connection cn = new ConnectDB().getConnect();
            Statement st = cn.createStatement();
            String sql = "DELETE FROM tbl_barang WHERE kode_barang = '" + kode_barang + "'";
            int result = st.executeUpdate(sql);
            cn.close();
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus", "Info Proses",
                    JOptionPane.INFORMATION_MESSAGE);
            df.removeRow(tab.getSelectedRow());
            clearTextField();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void ubahData(Tokoo M, String kode_barang) {
        try {
            Connection cn = new ConnectDB().getConnect();
            Statement st = cn.createStatement();
            String sql = "UPDATE tbl_barang SET kode_barang='" + M.getkode_barang() + "', nama_barang='" + M.getnama_barang() + "', harga_barang='" + M.getharga_barang() + "', stok_barang='" + M.getstok_barang() + "' WHERE kode_barang='" + kode_barang + "'";
            int result = st.executeUpdate(sql);
            cn.close();
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah", "Info Proses",
                    JOptionPane.INFORMATION_MESSAGE);
            clearTable();
            loadData();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void btnewAksi(ActionEvent evt) {
        clearTextField();
        btedit.setEnabled(false);
        btdel.setEnabled(false);
        btadd.setEnabled(true);
    }

    private void btaddAksi(ActionEvent evt) {
        Tokoo M = new Tokoo();
        M.setkode_barang(txkode_barang.getText());
        M.setnama_barang(txnama_barang.getText());
        M.setharga_barang(txharga_barang.getText());
        M.setstok_barang(txstok_barang.getText());
        simpanData(M);
    }

    private void btdelAksi(ActionEvent evt) {
        int status;
        status = JOptionPane.showConfirmDialog(null, "Yakin data akan dihapus?",
                "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if (status == 0) {
            hapusData(txkode_barang.getText());
        }
    }

    private void bteditAksi(ActionEvent evt) {
        Tokoo M = new Tokoo();
        M.setkode_barang(txkode_barang.getText());
        M.setnama_barang(txnama_barang.getText());
        M.setharga_barang(txharga_barang.getText());
        M.setstok_barang(txstok_barang.getText());
        ubahData(M, tab.getValueAt(tab.getSelectedRow(), 0).toString());
    }

    private void tabMouseClicked(MouseEvent evt) {
        btedit.setEnabled(true);
        btdel.setEnabled(true);
        btadd.setEnabled(false);
        String kode_barang, nama_barang, harga_barang, stok_barang;
        kode_barang = tab.getValueAt(tab.getSelectedRow(), 0).toString();
        nama_barang = tab.getValueAt(tab.getSelectedRow(), 1).toString();
        harga_barang = tab.getValueAt(tab.getSelectedRow(), 2).toString();
        stok_barang = tab.getValueAt(tab.getSelectedRow(), 3).toString();
        txkode_barang.setText(kode_barang);
        txnama_barang.setText(nama_barang);
        txharga_barang.setText(harga_barang);
        txstok_barang.setText(stok_barang);
    }

    public static void main(String[] args) {
        new TestTokoo().loadData();
    }
}