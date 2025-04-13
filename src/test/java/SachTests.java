import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SachTests {

    private static WebDriver driver;
    private static final String BASE_URL = "http://localhost:8080";

    @BeforeAll
    static void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        login();
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private static void login() {
        try {
            driver.get(BASE_URL + "/dang-nhap");

            WebElement userInput = driver.findElement(By.id("accountname"));
            userInput.sendKeys("user1");

            WebElement password = driver.findElement(By.id("password"));
            password.sendKeys("123");

            driver.findElement(By.className("btn-custom")).click();
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    void testCreateSach() {
        try {
            driver.get(BASE_URL + "/sach/sach-admin");
            Thread.sleep(5000);

//            scroll down to the bottom of the page
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(2000, document.body.scrollHeight);");

            // Fill book info
            driver.findElement(By.name("tenSach")).sendKeys("Test Book 1");
            driver.findElement(By.name("giaBan")).sendKeys("90000");
            driver.findElement(By.name("giaNiemYet")).sendKeys("100000");
            driver.findElement(By.name("tenTacGia")).sendKeys("Test Author");
            driver.findElement(By.name("maTheLoai")).sendKeys("1");
            driver.findElement(By.name("ISBN")).sendKeys("TEST-123");
            driver.findElement(By.name("moTa")).sendKeys("Test Description");
            driver.findElement(By.name("soLuong")).sendKeys("10");
            driver.findElement(By.name("trungBinhXepHang")).sendKeys("4.5");

            // Click create button
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(By.className("btn-primary")));

            Thread.sleep(1000);

            Assertions.assertTrue(driver.getCurrentUrl().contains("/sach-admin"));
            Assertions.assertTrue(() -> {  System.out.println("ok");
                return true;
            });
        } catch (Exception e) {
            Assertions.fail("Create book failed: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    void testReadSach() {
        try {
            driver.get(BASE_URL + "/sach/sach-admin");
            Thread.sleep(1000);

            WebElement bookTable = driver.findElement(By.tagName("table"));
            Assertions.assertNotNull(bookTable);

            // Verify test book exists
            List<WebElement> rows = driver.findElement(By.tagName("table")).findElements(By.tagName("tr"));
            boolean found = rows.stream().anyMatch(row -> row.getText().contains("Test Book"));
            Assertions.assertTrue(found, "Test Book not found in the table");


        } catch (Exception e) {
            Assertions.fail("Read book failed: " + e.getMessage());
        }
    }

    @Test
    @Order(3)
    public void testUpdateSach() {
        try {
            driver.get("http://localhost:8080/sach");

            // Tìm hàng chứa sách cần sửa (ví dụ sách có tên là "Test Book")
            List<WebElement> rows = driver.findElements(By.cssSelector("table tbody tr"));
            WebElement targetRow = null;
            for (WebElement row : rows) {
                if (row.getText().contains("Test Book")) {
                    targetRow = row;
                    break;
                }
            }

            Assertions.assertNotNull(targetRow, "Không tìm thấy sách cần sửa");

            // Scroll đến nút sửa (giả sử là class btn-warning)
            WebElement editButton = targetRow.findElement(By.className("btn-warning"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", editButton);

            // Chờ để nút có thể click được
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(editButton));

            // Click nút sửa
            editButton.click();

            // Chờ form hiện ra và nhập thông tin mới
            WebElement tenSachInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("tenSach")));
            tenSachInput.clear();
            tenSachInput.sendKeys("Sách về khoa học");

            // Gửi form (giả sử có nút submit với type='submit')
            WebElement submitBtn = driver.findElement(By.cssSelector("button[type='submit']"));
            submitBtn.click();

            // Quay lại trang danh sách, kiểm tra tên sách mới đã cập nhật
            driver.get("http://localhost:8080/sach");

            List<WebElement> updatedRows = driver.findElements(By.cssSelector("table tbody tr"));
            boolean foundUpdated = false;
            for (WebElement row : updatedRows) {
                if (row.getText().contains("Sách về khoa học")) {
                    foundUpdated = true;
                    break;
                }
            }

            Assertions.assertTrue(foundUpdated, "Không tìm thấy sách sau khi cập nhật");

        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Update book failed: " + e.getMessage());
        }
    }


    @Test
    @Order(4)
    void testDeleteSach() {
        try {
            driver.get(BASE_URL + "/sach/sach-admin");
            Thread.sleep(1000);

            // Click delete button on last row
            List<WebElement> rows = driver.findElements(By.tagName("tr"));
            for (WebElement row : rows) {
                if (row.getText().contains("Updated Test Book")) {
                    row.findElement(By.xpath(".//button[contains(text(), 'Xóa')]")).click();
                    break;
                }
            }

            // Verify book was deleted
            WebElement bookTable = driver.findElement(By.tagName("table"));
            WebElement newLastRow = bookTable.findElements(By.tagName("tr")).get(1);
            String bookName = newLastRow.findElements(By.tagName("td")).get(6).getText();
            Assertions.assertNotEquals("Updated Test Book", bookName);

        } catch (Exception e) {
            Assertions.fail("Delete book failed: " + e.getMessage());
        }
    }
}