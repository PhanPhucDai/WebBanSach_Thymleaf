import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestLogin {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        String[][] duLieuDangNhap = {
                // Sai mật khẩu
                {"user1", "sai123"},
                // Không tồn tại tài khoản
                {"khongCoUser", "123456"},
                // Thiếu tên đăng nhập
                {"", "123456"},
                // Thiếu mật khẩu
                {"user1", ""},
                // ✅ Đúng thông tin
                {"user1", "123"}
        };

        for (int i = 0; i < duLieuDangNhap.length; i++) {
            System.out.println("▶️ Kiểm thử lần " + (i + 1) + " với: " + duLieuDangNhap[i][0] + " / " + duLieuDangNhap[i][1]);

            driver.get("http://localhost:8080/dang-nhap");

            WebElement userInput = driver.findElement(By.id("accountname"));
            WebElement passInput = driver.findElement(By.id("password"));

            userInput.clear();
            userInput.sendKeys(duLieuDangNhap[i][0]);

            passInput.clear();
            passInput.sendKeys(duLieuDangNhap[i][1]);

            driver.findElement(By.className("btn-custom")).click();
            Thread.sleep(1500); // Chờ trang xử lý, hiển thị thông báo lỗi nếu có
        }

    }
}
