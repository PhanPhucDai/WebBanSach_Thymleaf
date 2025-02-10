package PPD.vn.WebBanhSach_backend.Controller;

import PPD.vn.WebBanhSach_backend.Entity.NguoiDung;
import PPD.vn.WebBanhSach_backend.Service.NguoiDungService;
import PPD.vn.WebBanhSach_backend.Service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("tai-khoan")
public class TaiKhoanController {
    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private NguoiDungService nguoiDungService;



    @PostMapping("/dang-ki")
    public ResponseEntity<?> dangKiTaiKhoan(@Validated @RequestBody  NguoiDung nguoiDung){
        ResponseEntity<?> response = taiKhoanService.dangKiNguoiDung(nguoiDung);
        return  response;
    }

    @GetMapping("/kich-hoat")
    public ResponseEntity<?> kichHoatTaiKHoan(@RequestParam  String email, @RequestParam String maKichHoat){
        ResponseEntity<?> response = taiKhoanService.kichHoatTaiKhoan(email, maKichHoat);
        return  response;
    }


}
