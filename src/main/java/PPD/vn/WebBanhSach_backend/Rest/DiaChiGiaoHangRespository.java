package PPD.vn.WebBanhSach_backend.Rest;

import PPD.vn.WebBanhSach_backend.Entity.DiaChiGiaoHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DiaChiGiaoHangRespository extends JpaRepository<DiaChiGiaoHang, Integer> {
}
