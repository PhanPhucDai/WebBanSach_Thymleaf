package PPD.vn.WebBanhSach_backend.Controller;

import org.springframework.boot.web.server.Cookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class DangNhap {
    @GetMapping(value = "/dang-nhap")
    public String dangNhap(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "success", required = false) String success,
                           Model model) {
        if (error != null) {
            model.addAttribute("error", "Đăng kí thất bại. Vui lòng thử lại.");}
        if (success != null) {

            model.addAttribute("success", "Đăng ký thành công. Vui lòng đăng nhập.");}
        return "User/DangNhap";}

}
