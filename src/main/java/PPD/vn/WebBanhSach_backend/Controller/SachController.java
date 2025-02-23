package PPD.vn.WebBanhSach_backend.Controller;

import PPD.vn.WebBanhSach_backend.DTO.SachDTO;
import PPD.vn.WebBanhSach_backend.Entity.Sach;
import PPD.vn.WebBanhSach_backend.Entity.TheLoai;
import PPD.vn.WebBanhSach_backend.Rest.SachRespository;
import PPD.vn.WebBanhSach_backend.Rest.TheLoaiRespository;
import PPD.vn.WebBanhSach_backend.Service.SachServide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("sach")
public class SachController {
    @Autowired
    private SachServide sachServide;
    @Autowired
    private SachRespository sachRespository;
    @Autowired
    private TheLoaiRespository theLoaiRespository;

    @GetMapping("/sach-all")
    public String listSach(@RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "8") int size,
                           Model model){
        Pageable pageable = PageRequest.of(page, size);
        model.addAttribute("phanTrang","/sach/sach-all");
        model.addAttribute("listTheLoai", theLoaiRespository.findAll());
        model.addAttribute("sachList",   sachRespository.findAllSachWithIconImages(pageable));
        model.addAttribute("currentPage", page);
        return "User/Home";
    }

    @GetMapping("/the-loai/{maTheLoai}")
    public String listSachByTheLoai(
                           @PathVariable("maTheLoai") int maTheLoai,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "8") int size,
                           Model model){
        Pageable pageable = PageRequest.of(page, size);
        model.addAttribute("phanTrang","/sach/the-loai/"+maTheLoai);
        model.addAttribute("listTheLoai", theLoaiRespository.findAll());
        model.addAttribute("sachList",   sachRespository.findSachWithTheLoai(maTheLoai,pageable));
        model.addAttribute("currentPage", page);
        return "User/Home";
    }

    @GetMapping("/sach-content")
    public String sachByContent(
            @RequestParam("content") String content,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "8") int size,
            Model model){
        System.out.println(content+ " content");
        Pageable pageable = PageRequest.of(page, size);
        model.addAttribute("phanTrang","/sach/sach-content?content="+content);
        model.addAttribute("listTheLoai", theLoaiRespository.findAll());
        model.addAttribute("sachList",   sachRespository.findSachByContent( content,pageable ));
        model.addAttribute("currentPage", page);
        return "User/Home";
    }

    @GetMapping("/sach-admin")
    public String sachAdmin(
            @RequestParam(value = "maSach", required = false)Integer maSach,
            Model model){
        if(maSach != null){
            SachDTO sach = sachRespository.findSachFullWithTheLoaiAndHinhAnh(maSach);
            model.addAttribute("sach",sach);
        }else{
            SachDTO sach = new SachDTO();
            model.addAttribute("sach",sach);
        }
        model.addAttribute("theLoais", theLoaiRespository.findAll());
        model.addAttribute("sachs",sachRespository.findAll());
     return "Admin/Admin_Sach";
    }

    @GetMapping("/sach-admin/tim-kiem")
    public String findSach(Model model, @RequestParam("timKiem")String timKiem){
            SachDTO sach = new SachDTO();
            model.addAttribute("sach",sach);
            model.addAttribute("theLoais", theLoaiRespository.findAll());
            if(timKiem.trim().isEmpty()){
                model.addAttribute("sachs",sachRespository.findAll());

            }else{
                model.addAttribute("sachs",sachRespository.findByTenSach(timKiem));
            }
        return "Admin/Admin_Sach";
    }


    @PostMapping("/them-sach")
    public String crudSach(@RequestParam(value = "action", required = false) String action,
                           @RequestParam(value = "maSach", required = false) int maSach,
                           @RequestParam("tenSach")String tenSach,
                           @RequestParam("giaBan")String giaBan,
                           @RequestParam("giaNiemYet")String giaNiemYet,
                           @RequestParam("tenTacGia")String tenTacGia,
                           @RequestParam("maTheLoai")String maTheLoai,
                           @RequestParam("ISBN")String ISBN,
                           @RequestParam("moTa")String moTa,
                           @RequestParam("soLuong")String soLuong,
                           @RequestParam("trungBinhXepHang")String trungBinhXepHang,
                           @RequestParam("duLieuAnh") MultipartFile duLieuAnh
                            ,Model model){
        if ("create".equals(action)) {
             // Xử lý tạo sách
            System.out.println(duLieuAnh);

            Sach  sach = new Sach(tenSach,tenTacGia,ISBN,moTa,"",Double.parseDouble(giaNiemYet),Double.parseDouble(giaBan),Integer.parseInt(soLuong),Double.parseDouble(trungBinhXepHang));
            if(!sachServide.themSach(sach, duLieuAnh, Integer.parseInt(maTheLoai))){
                         model.addAttribute("Không thể thêm Sách mới");
                         System.out.println("Lỗi khi thêm sách");
                    }
                     model.addAttribute("Đã thêm Sách mới");
                    System.out.println("ĐÃ thêm sách");
        }
        else if ("update".equals(action)) {
            System.out.println("Người dùng nhấn nút Update");
            System.out.println(duLieuAnh);

            Sach  sach = new Sach(tenSach,tenTacGia,ISBN,moTa,"",Double.parseDouble(giaNiemYet),Double.parseDouble(giaBan),Integer.parseInt(soLuong),Double.parseDouble(trungBinhXepHang));
            if(!sachServide.update(maSach, sach, duLieuAnh, Integer.parseInt(maTheLoai))){
                model.addAttribute("Không thể thêm Sách mới");
                System.out.println(duLieuAnh);
            }
            model.addAttribute("Đã thêm Sách mới");
            System.out.println("ĐÃ sửa sách");
            // Xử lý cập nhật sách
        } else if ("delete".equals(action)) {
            System.out.println("Người dùng nhấn nút Delete");
            sachServide.delete(maSach);
            model.addAttribute("Đã Xóa Sách  ");
            // Xử lý xóa sách
        } else if ("reset".equals(action)) {
            System.out.println("Người dùng nhấn nút Reset");
            return "redirect:/sach/sach-admin";
            // Xử lý reset form
        }
        return "redirect:/sach/sach-admin"; // Điều hướng sau khi xử lý xong
     }

}


