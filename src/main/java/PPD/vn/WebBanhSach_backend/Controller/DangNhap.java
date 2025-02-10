package PPD.vn.WebBanhSach_backend.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class DangNhap {
    @GetMapping(value = "/dang-nhap")
    public String dangNhap(){
        return "DangNhap";
    }
}
