package PPD.vn.WebBanhSach_backend.Rest;

import PPD.vn.WebBanhSach_backend.DTO.SachDTO;
import PPD.vn.WebBanhSach_backend.Entity.Quyen;
import PPD.vn.WebBanhSach_backend.Entity.Sach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RepositoryRestResource(path = "sach")
public interface SachRespository extends JpaRepository<Sach, Integer> {
    //Lấy danh sách quyển sách với ảnh
    @Query(value = "SELECT new PPD.vn.WebBanhSach_backend.DTO.SachDTO(s.maSach, s.tenSach, s.giaBan, s.giaNiemYet, h.duLieuAnh, s.tenTacGia) " +
            "FROM Sach s JOIN s.danhSachHinhAnh h WHERE h.laIcons = true")
    Page<SachDTO> findAllSachWithIconImages(Pageable pageable);

    //Lấy sách bằng ma sach
    @Query("SELECT new PPD.vn.WebBanhSach_backend.DTO.SachDTO( " +
            "s.maSach, s.tenSach, s.giaBan, s.giaNiemYet,ha.duLieuAnh, s.tenTacGia, " +
            " tl.maTheLoai, tl.tenTheLoai ) " +
            "FROM Sach s " +
            "JOIN s.danhSachTheLoai tl " +
            "JOIN s.danhSachHinhAnh ha " +
            "WHERE s.maSach = :maSach AND ha.laIcons = true")
    SachDTO findSachWithTheLoaiAndHinhAnh(@Param("maSach") int maSach);

    //lấy theo thể loại
    @Query("SELECT DISTINCT new PPD.vn.WebBanhSach_backend.DTO.SachDTO( " +
            "s.maSach, s.tenSach, s.giaBan, s.giaNiemYet, h.duLieuAnh, s.tenTacGia, " +
            "tl.maTheLoai, tl.tenTheLoai) " +
            "FROM Sach s " +
            "JOIN s.danhSachHinhAnh h " +
            "JOIN s.danhSachTheLoai tl " +
            "WHERE h.laIcons = true AND tl.maTheLoai = :maTheLoai")
    List<SachDTO> findSachWithTheLoai(@Param("maTheLoai") int maTheLoai);

}
