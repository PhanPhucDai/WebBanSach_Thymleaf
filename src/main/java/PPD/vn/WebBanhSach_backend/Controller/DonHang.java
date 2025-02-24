package PPD.vn.WebBanhSach_backend.Controller;

import PPD.vn.WebBanhSach_backend.Entity.NguoiDung;
import PPD.vn.WebBanhSach_backend.Rest.NguoiDungRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DonHang {

    @Autowired
    private NguoiDungRespository nguoiDungRespository;

    @GetMapping("/don-hang")
    public String donHang(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        NguoiDung nguoiDung = nguoiDungRespository.findByTenDangNhap(name);
        model.addAttribute("nguoiDung",nguoiDung);
        return "User/HoaDon";
    }

    @PostMapping("/thanh-toan")
    public String thanhToan(Model model){

        return "User/HoaDon";}


    @PostMapping("/thuc-hien/thanh-toan")
    public String thucHienThanhToan(
            @RequestParam("tinh")String tinh,
            @RequestParam("huyen")String huyen,
            @RequestParam("xa")String xa,
            @RequestParam("diaChiNhanHang") String diaChiNhanHang,
            @RequestParam("ptvc") String phuongThucVanChuyen,
            @RequestParam("paymentMethod") String phuongThucThanhToan,
            Model model){
        return "User/HoaDon";
    }
}
