package PPD.vn.WebBanhSach_backend.DTO;

import jakarta.persistence.Column;

public class SachDTO {
    private int maSach;
    private String tenSach;
    private Double giaBan;
    private Double giaNiemYet;
    private String duLieuAnh;
    private int maHinhAnh;
    private String tenTacGia;
    private int maTheLoai;
    private String theLoai;
     private String ISBN;
     private String moTa;
     private String moTaChiTiet;
     private int soLuong;
     private Double trungBinhXepHang;

    public SachDTO() {
    }


    public SachDTO(int maSach, String tenSach, Double giaBan, Double giaNiemYet, String duLieuAnh, String tenTacGia, int maTheLoai, String theLoai, String ISBN, String moTa, String moTaChiTiet, int soLuong, Double trungBinhXepHang) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.giaBan = giaBan;
        this.giaNiemYet = giaNiemYet;
        this.duLieuAnh = duLieuAnh;
        this.tenTacGia = tenTacGia;
        this.maTheLoai = maTheLoai;
        this.theLoai = theLoai;
        this.ISBN = ISBN;
        this.moTa = moTa;
        this.moTaChiTiet = moTaChiTiet;
        this.soLuong = soLuong;
        this.trungBinhXepHang = trungBinhXepHang;
    }

    public SachDTO(int maSach, String tenSach, Double giaNiemYet, Double giaBan, String duLieuAnh
            , String tenTacGia
            , int maTheLoai
            , String theLoai) {
        this.maSach = maSach;
        this.duLieuAnh = duLieuAnh;
        this.giaNiemYet = giaNiemYet;
        this.giaBan = giaBan;
        this.tenSach = tenSach;
        this.tenTacGia = tenTacGia;
        this.maTheLoai = maTheLoai;
        this.theLoai = theLoai;
    }

    public SachDTO(int maSach, String tenSach, Double giaNiemYet, Double giaBan, String duLieuAnh
            , String tenTacGia) {
        this.maSach = maSach;
        this.duLieuAnh = duLieuAnh;
        this.giaNiemYet = giaNiemYet;
        this.giaBan = giaBan;
        this.tenSach = tenSach;
        this.tenTacGia = tenTacGia;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Double getTrungBinhXepHang() {
        return trungBinhXepHang;
    }

    public void setTrungBinhXepHang(Double trungBinhXepHang) {
        this.trungBinhXepHang = trungBinhXepHang;
    }

    public String getMoTaChiTiet() {
        return moTaChiTiet;
    }

    public void setMoTaChiTiet(String moTaChiTiet) {
        this.moTaChiTiet = moTaChiTiet;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public int getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(int maTheLoai) {
        this.maTheLoai = maTheLoai;
    }

    public String getTenTacGia() {
        return tenTacGia;
    }

    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }

    public String getDuLieuAnh() {
        return duLieuAnh;
    }

    public void setDuLieuAnh(String duLieuAnh) {
        this.duLieuAnh = duLieuAnh;
    }

    public Double getGiaNiemYet() {
        return giaNiemYet;
    }

    public void setGiaNiemYet(Double giaNiemYet) {
        this.giaNiemYet = giaNiemYet;
    }

    public Double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(Double giaBan) {
        this.giaBan = giaBan;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getMaHinhAnh() {
        return maHinhAnh;
    }

    public void setMaHinhAnh(int maHinhAnh) {
        this.maHinhAnh = maHinhAnh;
    }
}
