package PPD.vn.WebBanhSach_backend.Rest;

import PPD.vn.WebBanhSach_backend.DTO.SachDTO;
import PPD.vn.WebBanhSach_backend.Entity.DiaChiGiaoHang;
import PPD.vn.WebBanhSach_backend.Entity.HinhThucThanhToan;
import PPD.vn.WebBanhSach_backend.Entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource(path = "nguoi-dung")
public interface NguoiDungRespository extends JpaRepository<NguoiDung, Integer> {
    boolean existsByTenDangNhap(String tenDangNhap);
    boolean existsByEmail(String email);
    public NguoiDung findByTenDangNhap(String tenDangnhap);
    public NguoiDung findByEmail(String email);

    @Query("SELECT dc FROM DiaChiGiaoHang dc WHERE dc.nguoiDung.maNguoiDung = :maNguoiDung")
    List<DiaChiGiaoHang> diaChiGiaoHangs(@Param("maNguoiDung") int maNguoiDung);



 }
