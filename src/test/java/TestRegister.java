import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestRegister {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Các bộ dữ liệu kiểm thử: thiếu thông tin và 1 bộ đúng
        String[][] duLieuDangKy = {
                // Họ đệm thiếu
                {"", "An", "user1", "user1@email.com", "123456", "123456", "0912345678", "Nam"},
                // Tên thiếu
                {"Nguyen", "", "user2", "user2@email.com", "abcdef", "abcdef", "0987654321", "Nữ"},
                // Tên đăng nhập thiếu
                {"Le", "Binh", "", "user3@email.com", "111111", "111111", "0909090909", "Khác"},
                // Email sai định dạng
                {"Tran", "Huy", "user4", "saiemail", "222222", "222222", "0911111111", "Nam"},
                // Mật khẩu không khớp
                {"Pham", "Ha", "user5", "user5@email.com", "abc123", "abc321", "0922222222", "Nữ"},
                // Số điện thoại thiếu
                {"Vu", "Kien", "user6", "user6@email.com", "aaaaaa", "aaaaaa", "", "Nam"},
                // ✅ Trường hợp đúng
                {"Nguyen", "Cuong", "user7", "user7@email.com", "123456", "123456", "0909090909", "Nam"}
        };

        for (int i = 0; i < duLieuDangKy.length; i++) {
            System.out.println("▶️ Đang kiểm thử bộ dữ liệu thứ " + (i + 1));

            driver.get("http://localhost:8080/dang-ky");

            driver.findElement(By.id("hoDem")).sendKeys(duLieuDangKy[i][0]);
            driver.findElement(By.id("ten")).sendKeys(duLieuDangKy[i][1]);
            driver.findElement(By.id("tenDangNhap")).sendKeys(duLieuDangKy[i][2]);
            driver.findElement(By.id("email")).sendKeys(duLieuDangKy[i][3]);
            driver.findElement(By.id("matKhau")).sendKeys(duLieuDangKy[i][4]);
            driver.findElement(By.id("confirmPassword")).sendKeys(duLieuDangKy[i][5]);
            driver.findElement(By.id("soDienThoai")).sendKeys(duLieuDangKy[i][6]);

            WebElement gioiTinh = driver.findElement(By.id("gioiTinh"));
            gioiTinh.sendKeys(duLieuDangKy[i][7]);

            driver.findElement(By.className("btn-custom")).click();

            Thread.sleep(2000);
        }

    }
}
