package LiveProject3;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class LiveProject3 {

    WebDriver driver;

    @BeforeClass
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    void userLoginOrangeHRM() {

        /**TC_Ml_MIM_01 First time user login-information display check*/
        // Navigate to the login page
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        // Enter the username
        WebElement username = driver.findElement(By.xpath("//input[@name='username']"));
        username.sendKeys("Admin");

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        // Enter the password
        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys("admin123");
        // Click the login button
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
        loginButton.click();


        // Check if the personal information page is displayed
   //     if (driver.getCurrentUrl().equals("https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index")) {
        if (driver.getCurrentUrl().equals("https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewPersonalDetails/empNumber/7")) {

            WebElement myInfoClick = driver.findElement(By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > aside:nth-child(1) > nav:nth-child(1) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(6) > a:nth-child(1) > span:nth-child(2)"));
            myInfoClick.click();

            // Check if the fields are disabled for entry in Personal Details
            WebElement employeeId = driver.findElement(By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > form:nth-child(3) > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > input:nth-child(1)"));
            WebElement ssnNo = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/div/div[2]/div[1]/form/div[2]/div[3]/div[1]/div/div[2]/input"));
            WebElement sinNo = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/div/div[2]/div[1]/form/div[2]/div[3]/div[2]/div/div[2]/input"));
            WebElement driverLicense = driver.findElement(By.xpath("(//input[@class='oxd-input oxd-input--active'])[5]"));
            WebElement dob = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/div/div[2]/div[1]/form/div[3]/div[2]/div[1]/div/div[2]/div/div/input"));

            if (employeeId.getAttribute("readonly").equals("true") &&
                    ssnNo.getAttribute("readonly").equals("true") &&
                    sinNo.getAttribute("readonly").equals("true") &&
                    driverLicense.getAttribute("readonly").equals("true") &&
                    dob.getAttribute("readonly").equals("true")) {

                System.out.println("All fields are disabled for entry in Personal Details");

            } else {

                System.out.println("Some fields are enabled for entry in Personal Details");
            }
        }
//        System.out.println("Employee ID readonly attribute: " + employeeId.getAttribute("readonly"));
//        System.out.println("SSN No readonly attribute: " + ssnNo.getAttribute("readonly"));
//        System.out.println("SIN No readonly attribute: " + sinNo.getAttribute("readonly"));
//        System.out.println("Driver License No readonly attribute: " + driverLicense.getAttribute("readonly"));
//        System.out.println("Date of Birth readonly attribute: " + dob.getAttribute("readonly"));
    }

    // Test Case ID: TC_MI_02
    // Assignee: Eray Sahin
    // Test Case Description: Error message on unsuccessful Employee login
    @Test
    void errorOnUnsuccessfulEmpLogin() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        WebElement username = driver.findElement(By.xpath("//input[@name='username']"));
        // Enter a "valid" username
        username.sendKeys("Admin");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        // Enter an "invalid" password
        password.sendKeys("abc123");
        // Click the login button
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
        loginButton.click();
        // Check if the error message is displayed
        String errorMessageXpath = "//*[@id=\"app\"]/div[1]/div/div[1]/div/div[2]/div[2]/div/div[1]/div[1]/p";
        WebElement errorMessage = driver.findElement(By.xpath(errorMessageXpath));
        Assert.assertEquals(errorMessage.getText(), "Invalid credentials");
    }

}