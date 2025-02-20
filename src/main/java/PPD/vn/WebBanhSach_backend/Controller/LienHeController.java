package PPD.vn.WebBanhSach_backend.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LienHeController {
    @GetMapping("lien-he")
    public String getLienHe(){
        return "User/LienHe";
    }
}
