package PPD.vn.WebBanhSach_backend.DTO;

public class SachDTO {
    private int maSach;
    private String tenSach;
    private Double giaBan;
    private Double giaNiemYet;
    private String duLieuAnh;
    private String tenTacGia;
    private int maTheLoai;
    private String theLoai;


    public SachDTO(int maSach, String tenSach, Double giaNiemYet, Double giaBan, String duLieuAnh
            , String tenTacGia
            , int maTheLoai
            , String theLoai) {
        this.maSach = maSach;
        this.duLieuAnh = duLieuAnh;
        this.giaNiemYet = giaNiemYet;
        this.giaBan = giaBan;
        this.tenSach = tenSach;
        this.tenTacGia = tenTacGia;
        this.maTheLoai = maTheLoai;
        this.theLoai = theLoai;
    }

    public SachDTO(int maSach, String tenSach, Double giaNiemYet, Double giaBan, String duLieuAnh
            , String tenTacGia) {
        this.maSach = maSach;
        this.duLieuAnh = duLieuAnh;
        this.giaNiemYet = giaNiemYet;
        this.giaBan = giaBan;
        this.tenSach = tenSach;
        this.tenTacGia = tenTacGia;
    }


    public int getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(int maTheLoai) {
        this.maTheLoai = maTheLoai;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public String getTenTacGia() {
        return tenTacGia;
    }

    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getDuLieuAnh() {
        return duLieuAnh;
    }

    public void setDuLieuAnh(String duLieuAnh) {
        this.duLieuAnh = duLieuAnh;
    }

    public Double getGiaNiemYet() {
        return giaNiemYet;
    }

    public void setGiaNiemYet(Double giaNiemYet) {
        this.giaNiemYet = giaNiemYet;
    }

    public Double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(Double giaBan) {
        this.giaBan = giaBan;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }
}
