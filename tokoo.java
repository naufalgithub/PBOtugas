package tokoo;

public class Tokoo {
    private String kode_barang, nama_barang, harga_barang, stok_barang;

    public String getkode_barang(){
        return kode_barang ;
    }

    public void setkode_barang(String kode_barang){
        this.kode_barang = kode_barang;
    }

    public String getnama_barang(){
        return nama_barang;
    }
    public void setnama_barang(String nama_barang){
        this.nama_barang = nama_barang;
    }

    public String getharga_barang(){
        return harga_barang;
    }
    public void setharga_barang(String harga_barang){
        this.harga_barang = harga_barang;
    }

    public String getstok_barang(){
        return stok_barang;
    }
    public void setstok_barang(String stok_barang){
        this.stok_barang = stok_barang;
    }
}

