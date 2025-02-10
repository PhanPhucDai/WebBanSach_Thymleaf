package PPD.vn.WebBanhSach_backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="hinh_anh")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HinhAnh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_hinh_anh")
    private int maHinhAnh;
    @Column(name = "ten_tinh_anh")
    private String tenHinhAnh;
    @Column(name = "la_icons")
    private boolean laIcons;
    @Column(name = "duong_dan")
    private String duongDan;
    @Column(name = "du_Lieu_Anh" )
    @Lob
    private String duLieuAnh;
    @ManyToOne(
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH})
    //nullable là hình ảnh tồn tại thì bắt buộc nó phải thuộc về hình ảnh nào
    @JoinColumn(name = "ma_sach", nullable = false)
    private Sach sach;

    public HinhAnh(String tenHinhAnh, boolean laIcons, String duongDan, Sach sach, String duLieuAnh) {
        this.tenHinhAnh = tenHinhAnh;
        this.laIcons = laIcons;
        this.duongDan = duongDan;
        this.sach = sach;
        this.duLieuAnh = duLieuAnh;
    }
}
