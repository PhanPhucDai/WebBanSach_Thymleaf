package PPD.vn.WebBanhSach_backend.Service;


import PPD.vn.WebBanhSach_backend.Entity.GioHang;
import PPD.vn.WebBanhSach_backend.Entity.NguoiDung;
import PPD.vn.WebBanhSach_backend.Entity.ThongBaoLoi;
import PPD.vn.WebBanhSach_backend.Rest.GioHangRespository;
import PPD.vn.WebBanhSach_backend.Rest.NguoiDungRespository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaiKhoanService {
    @Autowired
    private NguoiDungRespository nguoiDungRespository;
    @Autowired
    private EmailServiceImp emailServiceImp;
    @Autowired
    private GioHangRespository gioHangRespository;

    public int doiMatKhau(String matKhauCu,String matKhauMoi, String matKhauNhapLai){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        NguoiDung nguoiDung= nguoiDungRespository.findByTenDangNhap(name);

         if(!matKhauMoi.equals(matKhauNhapLai)){
             System.out.println("matKhau nhap lai khong dung");

             return 0;
         }
         if(!nguoiDung.getMatKhau().equals("{noop}"+matKhauCu)){
             System.out.println("mật khẩu cũ không đúng ");

             return -1;
         }
         nguoiDung.setMatKhau("{noop}"+matKhauMoi);
        try {
            nguoiDungRespository.save(nguoiDung);
            System.out.println("thành công ");

        } catch (Exception e) {
            return 0;
        }
        return  1;
    }

    public int dangKiNguoiDung(NguoiDung nguoiDung){
        if(nguoiDungRespository.existsByTenDangNhap(nguoiDung.getTenDangNhap())){
            return -2;
        }
        //kiểm tra email tồn tại
        if(nguoiDungRespository.existsByEmail(nguoiDung.getEmail())){
            return -3;
        }

        nguoiDung.setMaKichHoat(taoMaKichHoat());
        nguoiDung.setIsKichHoat(false);
        //lưu người dùng vào cơ sở dũ liệu
        nguoiDungRespository.save(nguoiDung);
        gioHangRespository.save(new GioHang(nguoiDung));

        //Gửi email cho người dùng kích hoạt
        guiEmailKichHoat(nguoiDung.getEmail(), nguoiDung.getMaKichHoat());
        return  1;
    }

    public ResponseEntity<?> kichHoatTaiKhoan(String  emai, String maKichHoat){
        NguoiDung nguoiDung= nguoiDungRespository.findByEmail(emai);
        if(nguoiDung == null){
            return ResponseEntity.badRequest().body(new ThongBaoLoi("Nguoi dùng không tồn tại"));
        }
        if(nguoiDung.getIsKichHoat()){
            return ResponseEntity.badRequest().body(new ThongBaoLoi("Tài khoản đã đuwocj kích hoạt"));
        }
        System.out.println("nguoi dung"+ nguoiDung.getTen());
        if(maKichHoat.equals(maKichHoat)){
            nguoiDung.setIsKichHoat(true);
            nguoiDungRespository.save(nguoiDung);
            return ResponseEntity.ok().body("Đã kích hoạt thành công");
        }else{
            return ResponseEntity.badRequest().body("kích hoạt tài khoản thành công");
        }
    }

    private String taoMaKichHoat(){
        //tạo mã ngẫu nhiên
        return UUID.randomUUID().toString();
    }

    private void guiEmailKichHoat(String email, String maKichHoat){
        String subject="Kích hoạt tài khoản của bạn tại WEB Bán sách";
        String text = " Vui lòng sử dụng mã sau để kích hoạt cho tài khoản <"
                +email+">: </br><h1>"+maKichHoat+"</h1>";
                text+="</br>Click vào đường link để kích hoạt";
                String url = "http://localhost:3000/kich-hoat/"+email+"/"+maKichHoat;
                text+="</br><a href="+url+">"+url+"</a>";
        emailServiceImp.sendMessage("dai582005@gmail.com", email, subject, text);
    }
}
