package PPD.vn.WebBanhSach_backend.Controller;

import PPD.vn.WebBanhSach_backend.Entity.NguoiDung;
import PPD.vn.WebBanhSach_backend.Service.NguoiDungService;
import PPD.vn.WebBanhSach_backend.Service.TaiKhoanService;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("tai-khoan")
public class TaiKhoanController {
    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    private NguoiDungService nguoiDungService;

    @PostMapping("/dang-ki")
    public String dangKiTaiKhoan(@ModelAttribute NguoiDung nguoiDung,@RequestParam("confirmPassword")String xacNhanMatKhau, Model model){

        String hoDem = nguoiDung.getHoDem();;
        String ten = nguoiDung.getTen();
        String tenDangNhap = nguoiDung.getTenDangNhap();
        String matKhau = nguoiDung.getMatKhau();
        String gioiTinh = nguoiDung.getGioiTinh();
        String email = nguoiDung.getEmail();
        String soDienThoai = nguoiDung.getSoDienThoai();
        String patternSDT = "^(0|\\\\+84)[3-9][0-9]{8}$";
        String patternEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    System.out.println("gioi tinh"+gioiTinh);
        if(!matKhau.equals(xacNhanMatKhau)){
            addUserAttributes(model, nguoiDung, "error", "Mật khẩu nhập lại không chính xác");
            return  "User/DangKy";
        }else if(!soDienThoai.matches(patternSDT)){
            addUserAttributes(model, nguoiDung, "error_soDienThoai", "Số điện thoại không phù hợp");
            model.addAttribute("matKhau",matKhau);
            model.addAttribute("xacNhanMatKhau",xacNhanMatKhau);
            return  "User/DangKy";
        }else if(!email.matches(patternEmail)){
            addUserAttributes(model, nguoiDung, "error_Email", "Email không phù hợp");
            model.addAttribute("matKhau",matKhau);
            model.addAttribute("xacNhanMatKhau",xacNhanMatKhau);
            model.addAttribute("error_Email","Email không phù hợp");
            return  "User/DangKy";
        }
        if(taiKhoanService.dangKiNguoiDung(new NguoiDung(hoDem, ten, tenDangNhap, "{noop}"+matKhau, gioiTinh, email, soDienThoai, false, "0"))== -2)
    {
        addUserAttributes(model, nguoiDung, "error", "Tên đăng nhập đã tồn tại");
        model.addAttribute("tenDangNhap", "");
        model.addAttribute("matKhau",matKhau);
        model.addAttribute("xacNhanMatKhau",xacNhanMatKhau);
        return  "User/DangKy";
        }else if(taiKhoanService.dangKiNguoiDung(new NguoiDung(hoDem, ten, tenDangNhap, "{noop}"+matKhau, gioiTinh, email, soDienThoai, false, "0"))== -3){
            addUserAttributes(model, nguoiDung, "error", "Email đã tồn tại");
            model.addAttribute("email", "");
            model.addAttribute("matKhau",matKhau);
            model.addAttribute("xacNhanMatKhau",xacNhanMatKhau);
            return  "User/DangKy";
        }
         return  "User/DangNhap";
    }

//    @GetMapping("/kich-hoat")
//    public ResponseEntity<?> kichHoatTaiKHoan(@RequestParam  String email, @RequestParam String maKichHoat){
//        ResponseEntity<?> response = taiKhoanService.kichHoatTaiKhoan(email, maKichHoat);
//        return  response;
//    }

    private void addUserAttributes(Model model, NguoiDung nguoiDung, String errorField, String errorMessage) {
        model.addAttribute("hoDem", nguoiDung.getHoDem());
        model.addAttribute("ten", nguoiDung.getTen());
        model.addAttribute("tenDangNhap", nguoiDung.getTenDangNhap());
        model.addAttribute("gioiTinh", nguoiDung.getGioiTinh());
        model.addAttribute("email", nguoiDung.getEmail());
        model.addAttribute("soDienThoai", nguoiDung.getSoDienThoai());
        model.addAttribute(errorField, errorMessage);
    }



}
