package PPD.vn.WebBanhSach_backend.Service;

import PPD.vn.WebBanhSach_backend.Entity.NguoiDung;

public interface  NguoiDungService  {
    public NguoiDung findByUsername(String tenDangNhap);
}
