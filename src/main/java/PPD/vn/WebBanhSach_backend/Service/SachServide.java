package PPD.vn.WebBanhSach_backend.Service;

import PPD.vn.WebBanhSach_backend.Entity.HinhAnh;
import PPD.vn.WebBanhSach_backend.Entity.Sach;
import PPD.vn.WebBanhSach_backend.Entity.TheLoai;
import PPD.vn.WebBanhSach_backend.Rest.HinhAnhRespository;
import PPD.vn.WebBanhSach_backend.Rest.SachRespository;
import PPD.vn.WebBanhSach_backend.Rest.TheLoaiRespository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SachServide {
    @Autowired
    private SachRespository sachRespository;
    @Autowired
    private HinhAnhRespository hinhAnhRespository;
    @Autowired
    private TheLoaiRespository theLoaiRespository;

    public boolean delete(int maSach){
            Optional<Sach> sachOptional = sachRespository.findById(maSach);
            if(!sachOptional.isPresent()){
                return false;
            }
            Sach sach = sachOptional.get();
            sachRespository.delete(sach);
        return true;
    }

    public boolean themSach(Sach sach, MultipartFile duLieuAnh, int maTheLoai){
        try {
            if (sach.getDanhSachTheLoai() == null) {
                sach.setDanhSachTheLoai(new ArrayList<>());
            }
            TheLoai theLoai= theLoaiRespository.findById(maTheLoai).orElseThrow(()-> new RuntimeException("Không thìm thấy thể loại"));
            sach.getDanhSachTheLoai().add(theLoai);
            sachRespository.save(sach);
            HinhAnh hinhAnh=new HinhAnh(sach.getTenSach(),true,"",sach,"data:image/webp;base64,"+hashPicture(duLieuAnh));

            hinhAnhRespository.save(hinhAnh);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(int maSach, Sach sachUpdate, MultipartFile duLieuAnh, int maTheLoai){
        try {


            Optional<Sach> sach = sachRespository.findById(maSach);
            if(!sach.isPresent()){
                return false;
            }
            sach.get().setTenSach(sachUpdate.getTenSach());
            sach.get().setISBN(sachUpdate.getISBN());
            sach.get().setSoLuong(sachUpdate.getSoLuong());
            sach.get().setGiaBan(sachUpdate.getGiaBan());
            sach.get().setGiaNiemYet(sachUpdate.getGiaNiemYet());
            sach.get().setMoTa(sachUpdate.getMoTa());
            sach.get().setTrungBinhXepHang(sachUpdate.getTrungBinhXepHang());
            sach.get().setTenTacGia(sachUpdate.getTenTacGia());

            Optional<TheLoai> theLoais = theLoaiRespository.findById(maTheLoai);

            if(theLoais.isPresent()){
                List<TheLoai> theLoai = new ArrayList<>();
                theLoai.add(theLoais.get());
                sach.get().setDanhSachTheLoai(theLoai);
            }
            sachRespository.save(sach.get());
             if(!duLieuAnh.isEmpty()){
                HinhAnh hinhAnh = hinhAnhRespository.findBySachAndLaIcons(sach.get(), true);
                hinhAnh.setDuLieuAnh("data:image/webp;base64,"+hashPicture(duLieuAnh));
                hinhAnhRespository.save(hinhAnh);
            }

            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String hashPicture(MultipartFile path){
        try {
            byte[] fileBytes = path.getBytes();
            return Base64.getEncoder().encodeToString(fileBytes);
        } catch (IOException  e) {
            throw new RuntimeException(e);
        }
    }
}
