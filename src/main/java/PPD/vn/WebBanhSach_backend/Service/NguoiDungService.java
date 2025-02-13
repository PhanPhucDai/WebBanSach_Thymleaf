package PPD.vn.WebBanhSach_backend.Service;

import PPD.vn.WebBanhSach_backend.Entity.NguoiDung;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface  NguoiDungService  {
    public NguoiDung findByUsername(String tenDangNhap);
}
