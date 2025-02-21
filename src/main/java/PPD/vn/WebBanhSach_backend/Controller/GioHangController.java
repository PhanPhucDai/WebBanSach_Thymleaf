package PPD.vn.WebBanhSach_backend.Controller;

import PPD.vn.WebBanhSach_backend.DTO.ChiTietGioHangDTO;
import PPD.vn.WebBanhSach_backend.Entity.*;
import PPD.vn.WebBanhSach_backend.Rest.*;
import PPD.vn.WebBanhSach_backend.Service.GioHangService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
public class GioHangController {
    @Autowired
    private HinhAnhRespository hinhAnhRespository;
    @Autowired
    private GioHangService gioHangService;
    @Autowired
    private ChiTietGioHangRepository chiTietGioHangRepository;
    @Autowired
    private NguoiDungRespository nguoiDungRespository;
    @Autowired
    private GioHangRespository gioHangRespository;
    @Autowired
    private SachRespository sachRespository;
    @Autowired
    private HttpServletRequest httpRequest;

    @GetMapping("/gio-hang")
    public String gioHang(Model model, @ModelAttribute("message")String mesage){
        //Lấy tên đăng nhập đã lưu vào session
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String tenDangNhap=authentication.getName();

        NguoiDung nguoiDung = nguoiDungRespository.findByTenDangNhap(tenDangNhap);
        GioHang maGioHang = gioHangRespository.findIDGioHangByNguoiDung(nguoiDung.getMaNguoiDung());

        List<ChiTietGioHang> chiTietGioHangList = chiTietGioHangRepository.findByGioHang(maGioHang);
        List<ChiTietGioHangDTO> chiTietGioHangDTOS= new ArrayList<>();
        double tongTien=0;
        for (ChiTietGioHang ctgh : chiTietGioHangList) {
            // Xử lý từng phần tử ctgh trong danh sách
            ChiTietGioHangDTO chiTietGioHangDTO = new ChiTietGioHangDTO();
            Optional<Sach> sach= sachRespository.findById(ctgh.getSach().getMaSach());
            HinhAnh hinhAnh =  hinhAnhRespository.findBySachAndLaIcons(ctgh.getSach(), true);
            chiTietGioHangDTO.setMaChiTietGioHang(ctgh.getMaChiTietGioHang());
            chiTietGioHangDTO.setSoluong(ctgh.getSoluong());
            chiTietGioHangDTO.setIsSelected(ctgh.getIsSelected());
            chiTietGioHangDTO.setSachAll(sach.get());
            chiTietGioHangDTO.setGioHang(ctgh.getGioHang().getMaGioHang());
            chiTietGioHangDTO.setDuLieuAnh(hinhAnh.getDuLieuAnh());
            chiTietGioHangDTOS.add(chiTietGioHangDTO);
            if(ctgh.getIsSelected() == 1){
                tongTien+=ctgh.getSach().getGiaBan()*ctgh.getSoluong();
            }
        }

        model.addAttribute("tongTien",tongTien);
        model.addAttribute("message",mesage);
        model.addAttribute("chiTietGioHangs",chiTietGioHangDTOS);
       return "User/GioHang";
    }

    @PostMapping("/chon-san-pham-thanh-toan")
    public String isSelected(@RequestParam("soLuong") int soLuong
                            ,@RequestParam("maSach") int maSach
                            ,@RequestParam("maGioHang") int maGioHang
                            ,RedirectAttributes redirectAttributes){
         ChiTietGioHangDTO chiTietGioHangDTO = new ChiTietGioHangDTO(maSach, maGioHang, soLuong);
        int rs= gioHangService.isSelected(chiTietGioHangDTO);
        if(rs == 0 ){
            redirectAttributes.addFlashAttribute("message","Mặt hàng này đã hết. Vui lòng xoá ra khỏi cửa hàng");
            return "redirect:/gio-hang";
        }else if(rs == 2){
            redirectAttributes.addFlashAttribute("message","Số lượng sản phẩm không đủ chúng tôi đã giảm số lượng");
            return "redirect:/gio-hang";
        }
        redirectAttributes.addFlashAttribute("message","Sản phẩm đã chọn");
        return "redirect:/gio-hang";

    }

    @PostMapping("/them-so-luong")
    public String themSoLuong(@RequestParam("soLuong") int soLuong
                                        ,@RequestParam("maSach") int maSach
                                        ,@RequestParam("maGioHang") int maGioHang
                                        ,@RequestParam("maChiTietGioHang") int maChiTietGioHang
                                        ,RedirectAttributes redirectAttributes){
        ChiTietGioHangDTO chiTietGioHangDTO =  new ChiTietGioHangDTO(maChiTietGioHang,maSach,maGioHang,soLuong);
        if(gioHangService.addItemInCart(chiTietGioHangDTO)==1){
            return "redirect:/gio-hang";
       }
        redirectAttributes.addFlashAttribute("message","Số lượng sản phẩm không đủ");
        return "redirect:/gio-hang";
    }

    @PostMapping("/xoa-so-luong")
    public String xoaSoLuong(@RequestParam("soLuong") int soLuong
            ,@RequestParam("maSach") int maSach
            ,@RequestParam("maGioHang") int maGioHang
            ,@RequestParam("maChiTietGioHang") int maChiTietGioHang
            ,RedirectAttributes redirectAttributes){
        ChiTietGioHangDTO chiTietGioHangDTO =  new ChiTietGioHangDTO(maChiTietGioHang,maSach,maGioHang,soLuong);
        if(gioHangService.removeItemInCart(chiTietGioHangDTO)==1){
            return "redirect:/gio-hang";
        }
         redirectAttributes.addFlashAttribute("message","Số lượng đã đạt giới hạn");
        return "redirect:/gio-hang";
    }

    @DeleteMapping("/xoa-so-luong")
    public ResponseEntity<?> xoaChiTietGioHang(@Validated @RequestBody ChiTietGioHangDTO chiTietGioHang){
        System.out.println("ma sach"+chiTietGioHang);
        if(gioHangService.removeItemInCart(chiTietGioHang)==1){
            return ResponseEntity.ok().body("");
        }
        return ResponseEntity.badRequest().body("Số lượng đã đạt giới hạn");
    }

    @GetMapping("/xoa-san-pham-gio-hang/{maChiTietGioHang}")
    public String xoaSanPhamGioHang(@PathVariable("maChiTietGioHang") int maChiTietGioHang,RedirectAttributes redirectAttributes){
        System.out.println("Chi tiết giỏ hàng nhận được: " + maChiTietGioHang);
        Optional<ChiTietGioHang> chiTietGioHang = chiTietGioHangRepository.findById(maChiTietGioHang);
        if(chiTietGioHang.isPresent()){
            if( gioHangService.xoaSanPhamGioHang(chiTietGioHang.get())==1){
                redirectAttributes.addFlashAttribute("message","Đã xóa thành công");
                return "redirect:/gio-hang";
            }
        }
        redirectAttributes.addFlashAttribute("message","Không thể xóa sản phẩm này");
        return "redirect:/gio-hang";
    }

    @GetMapping("/them-san-pham-gio-hang/{maSach}")
    public String themSanPhamGioHang(Model model
            , @PathVariable("maSach")int maSach
            , RedirectAttributes redirectAttributes){
        System.out.println(maSach);
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String tenDangNhap=authentication.getName();
        System.out.println("Tên đăng nhập"+ tenDangNhap);
        NguoiDung nguoiDung = nguoiDungRespository.findByTenDangNhap(tenDangNhap);
          GioHang maGioHang = gioHangRespository.findIDGioHangByNguoiDung(nguoiDung.getMaNguoiDung());

        ChiTietGioHangDTO chiTietGioHang = new ChiTietGioHangDTO(maSach, maGioHang.getMaGioHang() , 1 );
        if(gioHangService.themSanPhamGioHang(chiTietGioHang)==1){
            System.out.println("Thành công");
            //Chuyển về trang trước đó
            redirectAttributes.addAttribute("message", "Đã thêm thành công");
            String referer = httpRequest.getHeader("Referer");
            return "redirect:" + (referer != null ? referer : "/");

        }
        redirectAttributes.addAttribute("message", "Đã thêm không thành công");
        String referer = httpRequest.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/");
    }

    @PostMapping("/San-pham-gio-hang")
    public ResponseEntity<?> laySanPhamGioHang(@Validated @RequestBody Map<String, Integer> data){
        int maNguoiDung = data.get("maNguoiDung");
        Optional<NguoiDung> nguoiDung= nguoiDungRespository.findById(maNguoiDung);
        GioHang gioHang = nguoiDung.get().getGioHang();
        List<ChiTietGioHang> chiTietGioHang = chiTietGioHangRepository.findByGioHang(gioHang);
        try {
            List<ChiTietGioHangDTO> listChiTietGioHangDTO = new ArrayList<>();
            for(ChiTietGioHang chiTietGioHang1: chiTietGioHang) {
                ChiTietGioHangDTO chiTietGioHangDTO
                        = new ChiTietGioHangDTO(
                        chiTietGioHang1.getMaChiTietGioHang()
                        , chiTietGioHang1.getSach().getMaSach()
                        , chiTietGioHang1.getGioHang().getMaGioHang()
                        , chiTietGioHang1.getSoluong()
                        , chiTietGioHang1.getIsSelected()
                );
                listChiTietGioHangDTO.add(chiTietGioHangDTO);

            }

            return ResponseEntity.ok(listChiTietGioHangDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body("Không thể thêm sản phẩm này");
    }



//    @GetMapping("/Chi-tiet-gio-hang/isSelected")
//    public ResponseEntity<?> getItemIsSelected(@Validated @RequestParam("maNguoiDung")  int maNguoiDung){
//
//        Optional<NguoiDung> nguoiDung= nguoiDungRespository.findById(maNguoiDung);
//        GioHang gioHang = nguoiDung.get().getGioHang();
//        List<ChiTietGioHang> chiTietGioHang = chiTietGioHangRepository.findByGioHang(gioHang);
//        try {
//            List<ChiTietGioHangDTO> listChiTietGioHangDTO = new ArrayList<>();
//            for(ChiTietGioHang chiTietGioHang1: chiTietGioHang) {
//                if(chiTietGioHang1.getIsSelected() == 1){
//                    ChiTietGioHangDTO chiTietGioHangDTO
//                            = new ChiTietGioHangDTO(
//                            chiTietGioHang1.getMaChiTietGioHang()
//                            , chiTietGioHang1.getSach().getMaSach()
//                            , chiTietGioHang1.getGioHang().getMaGioHang()
//                            , chiTietGioHang1.getSoluong()
//                            , chiTietGioHang1.getIsSelected()
//                    );
//                    listChiTietGioHangDTO.add(chiTietGioHangDTO);
//                }
//            }
//            return ResponseEntity.ok(listChiTietGioHangDTO);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.badRequest().body("Không thể thêm sản phẩm này");
//    }
}
