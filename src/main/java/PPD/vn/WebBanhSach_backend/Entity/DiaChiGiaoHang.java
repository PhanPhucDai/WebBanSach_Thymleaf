package PPD.vn.WebBanhSach_backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "dia_chi_giao_hang")
public class DiaChiGiaoHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maDiaChiGiaoHang;
    @Column(name="chi_tiet_diachi")
    private String  chiTtietDiaChi;
    @Column(name="ten_huyen")
    private String  tenHuyen;
    @Column(name="ten_tinh")
    private String  tenTinh;
    @Column(name="ten_xa")
    private String  tenXa;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    private NguoiDung nguoiDung;
}
