package PPD.vn.WebBanhSach_backend.Rest;

import PPD.vn.WebBanhSach_backend.Entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path = "gio-hang")
 public interface GioHangRespository extends JpaRepository<GioHang, String> {

  @Query("SELECT g FROM GioHang g JOIN g.nguoiDung n WHERE n.maNguoiDung = :maNguoiDung")
  GioHang findIDGioHangByNguoiDung(@Param("maNguoiDung")int maNguoiDung);
 }

