package PPD.vn.WebBanhSach_backend.Service;

import PPD.vn.WebBanhSach_backend.Entity.NguoiDung;
import PPD.vn.WebBanhSach_backend.Entity.Quyen;
import PPD.vn.WebBanhSach_backend.Rest.NguoiDungRespository;
import PPD.vn.WebBanhSach_backend.Rest.QuyenRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
 import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.stream.Collectors;


@Service
public class NguoiDungImplement implements NguoiDungService{

    private NguoiDungRespository nguoiDungRespository;

    private QuyenRespository quyenRespository;
    @Autowired
    public NguoiDungImplement(NguoiDungRespository nguoiDungRespository, QuyenRespository quyenRespository) {
        this.nguoiDungRespository = nguoiDungRespository;
        this.quyenRespository = quyenRespository;
    }

    @Override
    public NguoiDung findByUsername(String tenDangNhap) {
        return nguoiDungRespository.findByTenDangNhap(tenDangNhap);
    }


    private Collection<? extends GrantedAuthority> rolesToAuthorities(Collection<Quyen> quyens ) {
        return quyens.stream().map(quyen->new SimpleGrantedAuthority(quyen.getTenQuyen())).collect(Collectors.toList());

    }

}
