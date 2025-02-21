package PPD.vn.WebBanhSach_backend.DTO;

import PPD.vn.WebBanhSach_backend.Entity.Sach;
import lombok.*;


@NoArgsConstructor
@Data
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
    public ChiTietGioHangDTO(int maChiTietGioHang, int sach, int gioHang, int soluong) {
        this.maChiTietGioHang = maChiTietGioHang;
        this.sach = sach;
        this.gioHang = gioHang;
        this.soluong = soluong;
    }

}
