package PPD.vn.WebBanhSach_backend.DTO;

import PPD.vn.WebBanhSach_backend.Entity.Sach;
import jakarta.persistence.Column;
import lombok.*;


@NoArgsConstructor
public class ChiTietGioHangDTO {
    private int maChiTietGioHang;
    private int soluong;
    private int isSelected;
    private Integer gioHang;
    private int sach;
    private String duLieuAnh;
    private Sach sachAll;

    public ChiTietGioHangDTO(int soluong, int isSelected, String duLieuAnh, Sach sach) {
        this.soluong = soluong;
        this.isSelected = isSelected;
        this.duLieuAnh = duLieuAnh;
        this.sachAll = sach;
    }

    public ChiTietGioHangDTO(int maChiTietGioHang, int sach, int gioHang, int soluong, int isSelected) {
        this.maChiTietGioHang = maChiTietGioHang;
        this.sach = sach;
        this.gioHang = gioHang;
        this.soluong = soluong;
        this.isSelected= isSelected;
    }

    public ChiTietGioHangDTO(int sach, int gioHang, int soluong) {
        this.soluong = soluong;
        this.sach = sach;
        this.gioHang = gioHang;

     }

    public String getDuLieuAnh() {
        return duLieuAnh;
    }

    public void setDuLieuAnh(String duLieuAnh) {
        this.duLieuAnh = duLieuAnh;
    }

    public Sach getSachAll() {
        return sachAll;
    }

    public void setSachAll(Sach sachAll) {
        this.sachAll = sachAll;
    }

    public int getMaChiTietGioHang() {
        return maChiTietGioHang;
    }

    public void setMaChiTietGioHang(int maChiTietGioHang) {
        this.maChiTietGioHang = maChiTietGioHang;
    }

    public int getSach() {
        return sach;
    }

    public void setSach(int sach) {
        this.sach = sach;
    }

    public Integer getGioHang() {
        return gioHang;
    }

    public void setGioHang(Integer gioHang) {
        this.gioHang = gioHang;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
