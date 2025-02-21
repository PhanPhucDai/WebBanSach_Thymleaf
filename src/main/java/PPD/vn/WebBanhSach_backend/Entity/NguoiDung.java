package PPD.vn.WebBanhSach_backend.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "nguoi_dung")
public class NguoiDung {
    @Id
    @GeneratedValue( strategy =  GenerationType.IDENTITY)
    @Column(name = "ma_nguoi_dung")
    private Integer maNguoiDung;
    @Column(name = "ho_dem", length = 255)
    private String hoDem;
    @Column(name = "ten", length = 255)
    private String ten;
    @Column(name = "ten_dang_nhap", length = 255)
    private String tenDangNhap;
    @Column(name = "mat_khau",length = 512)
    private String matKhau;
    @Column(name = "gioi_tinh", length = 1)
    private String gioiTinh;
    @Column(name = "email")
    private String email;
    @Column(name = "so_dien_thoai")
    private String soDienThoai;
    @Column(name = "isKichHoat")
    private Boolean isKichHoat;
    @Column(name = "maKichHoat")
    private String maKichHoat;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "nguoiDung",
            cascade = {  CascadeType.DETACH
                    , CascadeType.MERGE
                    , CascadeType.PERSIST
                    , CascadeType.REFRESH
            }
    )

    private List<SuDanhGia> danhSachSuDanhGia;
    @OneToOne(
            mappedBy = "nguoiDung",
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH
                    , CascadeType.MERGE
                    , CascadeType.PERSIST
                    , CascadeType.REFRESH
            }
    )
     private GioHang gioHang;
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "nguoiDung",
            cascade = {
                    CascadeType.DETACH
                    , CascadeType.MERGE
                    , CascadeType.PERSIST
                    , CascadeType.REFRESH
            }
    )
    private List<SachYeuThich> danhSachSachYeuThich;
    @ManyToMany(
            fetch=FetchType.EAGER
            ,cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH
    })
    @JoinTable(
            name = "nguoidung_quyen",
            joinColumns = @JoinColumn(name = "ma_nguoi_dung"),
            inverseJoinColumns = @JoinColumn(name = "ma_quyen")

    )
    private List<Quyen> danhSachQuyen;
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "nguoiDung",
            cascade = {
                    CascadeType.DETACH
                    , CascadeType.MERGE
                    , CascadeType.PERSIST
                    , CascadeType.REFRESH
            }
    )
    private List<DonHang> danhSachDonHang;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "nguoiDung",
            cascade = {CascadeType.ALL}
    )
    private List<DiaChiGiaoHang> diaChiGiaoHang;


    public NguoiDung(String hoDem, String ten, String tenDangNhap,
                         String matKhau, String gioiTinh, String email,
                         String soDienThoai, Boolean isKichHoat, String maKichHoat) {
            this.hoDem = hoDem;
            this.ten = ten;
            this.tenDangNhap = tenDangNhap;
            this.matKhau = matKhau;
            this.gioiTinh = gioiTinh;
            this.email = email;
            this.soDienThoai = soDienThoai;
            this.isKichHoat = isKichHoat;
            this.maKichHoat = maKichHoat;
        }


}
