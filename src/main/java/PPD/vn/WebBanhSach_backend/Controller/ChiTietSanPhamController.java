package PPD.vn.WebBanhSach_backend.Controller;

import PPD.vn.WebBanhSach_backend.DTO.SachDTO;
import PPD.vn.WebBanhSach_backend.Rest.SachRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChiTietSanPhamController {

    @Autowired
    private SachRespository  sachRespository;

    @GetMapping("/chi-tiet-san-pham/{maSach}")
    public String getSachByID(@PathVariable("maSach")String maSach, Model model){
        int maSachTranfer= Integer.valueOf(maSach);
        SachDTO sachDTO = sachRespository.findSachWithTheLoaiAndHinhAnh(maSachTranfer);
        model.addAttribute("listSachByTheLoai",sachRespository.findSachWithTheLoai(sachDTO.getMaTheLoai()) );
        model.addAttribute("sach",sachDTO );
         return "User/ChiTietSanPham";
    }

}
