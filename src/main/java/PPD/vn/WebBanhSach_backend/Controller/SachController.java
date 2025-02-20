package PPD.vn.WebBanhSach_backend.Controller;

import PPD.vn.WebBanhSach_backend.Entity.Sach;
import PPD.vn.WebBanhSach_backend.Entity.TheLoai;
import PPD.vn.WebBanhSach_backend.Rest.SachRespository;
import PPD.vn.WebBanhSach_backend.Rest.TheLoaiRespository;
import PPD.vn.WebBanhSach_backend.Service.SachServide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String listSach(@RequestParam(name = "page", defaultValue = "1") int page,
                           @RequestParam(name = "size", defaultValue = "8") int size,
                           Model model){
        Pageable pageable = PageRequest.of(page, size);
        model.addAttribute("listTheLoai", theLoaiRespository.findAll());
        model.addAttribute("sachList",   sachRespository.findAllSachWithIconImages(pageable));
        model.addAttribute("currentPage", page);
        return "User/Home";
    }


    @PostMapping("/them-sach")
    public String themSach(@Validated @RequestBody Sach sach, Model model){
        if(!sachServide.themSach(sach)){
             model.addAttribute("Không thể thêm Sách mới");
        }
         model.addAttribute("Đã thêm Sách mới");
        return "Home";
    }

}
