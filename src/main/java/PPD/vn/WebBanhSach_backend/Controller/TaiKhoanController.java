package PPD.vn.WebBanhSach_backend.Controller;

import PPD.vn.WebBanhSach_backend.Entity.DiaChiGiaoHang;
import PPD.vn.WebBanhSach_backend.Entity.NguoiDung;
import PPD.vn.WebBanhSach_backend.Rest.NguoiDungRespository;
import PPD.vn.WebBanhSach_backend.Service.NguoiDungService;
import PPD.vn.WebBanhSach_backend.Service.TaiKhoanService;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("tai-khoan")
public class TaiKhoanController {
    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private NguoiDungRespository nguoiDungRespository;

    @GetMapping("/nguoi-dung")
    public String getThongTin(Model model, @ModelAttribute("message")String message){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String tenNguoiDung = authentication.getName();

        NguoiDung nguoiDung = nguoiDungService.findByUsername(tenNguoiDung);
        model.addAttribute("message", message);
        model.addAttribute("nguoiDung", nguoiDung);
        return "User/ThongTinCaNhan";
    }
    @GetMapping("/danh-sach-dia-chi")
    public String getDiaChi(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String tenNguoiDung = authentication.getName();
        NguoiDung nguoiDung= nguoiDungService.findByUsername(tenNguoiDung);
        List<DiaChiGiaoHang> diaChiGiaoHang = nguoiDungRespository.diaChiGiaoHangs(nguoiDung.getMaNguoiDung());
        model.addAttribute("diaChiGiaoHangs", diaChiGiaoHang);
        return "User/DiaChiCaNhan";
    }

    @GetMapping("/doi-mat-khau")
    public String getDoiMatKhau(@ModelAttribute("message")String message,Model model){
       model.addAttribute("message", message);
        return "User/DoiMatKhau";
     }
    @PostMapping("/doi-mat-khau")
    public String getDoiMatKhau(RedirectAttributes redirectAttributes
            ,@RequestParam("matKhauCu") String matKhauCu
            ,@RequestParam("matKhauMoi") String matKhauMoi
            ,@RequestParam("matKhauNhapLai")  String matKhauNhapLai){


        int rs = taiKhoanService.doiMatKhau(matKhauCu, matKhauMoi, matKhauNhapLai);
        if(rs == 1){
            redirectAttributes.addFlashAttribute("message","Thay đổi thành công");
            return "redirect:/tai-khoan/doi-mat-khau";
        }else if(rs == 0){
            redirectAttributes.addFlashAttribute("message","Mật khẩu nhập lại không đúng");
            return "redirect:/tai-khoan/doi-mat-khau";
        }else if(rs == -1){
            redirectAttributes.addFlashAttribute("message","Mật khẩu củ không chính xác");
            return "redirect:/tai-khoan/doi-mat-khau";
        }
        return "redirect:/tai-khoan/doi-mat-khau";
    }


    @PostMapping("/dang-ki")
    public String dangKiTaiKhoan(@ModelAttribute NguoiDung nguoiDung
            ,@RequestParam("confirmPassword")String xacNhanMatKhau, Model model){

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

    @PostMapping("/sua-thong-tin-ca-nhan")
    public String suaThongTinCaNhan(
            @RequestParam("soDienThoai") String soDienThoai,
            @RequestParam("email") String email,
            RedirectAttributes redirectAttributes,Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        NguoiDung nguoiDung = nguoiDungRespository.findByTenDangNhap(authentication.getName());
        nguoiDung.setEmail(email);
        nguoiDung.setSoDienThoai(soDienThoai);
        try {
            nguoiDungRespository.save(nguoiDung);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Không thể chỉnh sửa");
            return "redirect:/tai-khoan/nguoi-dung";

        }
        redirectAttributes.addFlashAttribute("message", "Đã chĩnh sữa thành công");
        return "redirect:/tai-khoan/nguoi-dung";
   }




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
