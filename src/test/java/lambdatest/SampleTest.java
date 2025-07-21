package lambdatest;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SampleTest {

    private RemoteWebDriver driver;
    private WebDriverWait wait;
    private String Status = "failed";

    @BeforeEach
    public void setup(TestInfo testInfo) throws Exception {
        String username = "ashwinkoshy";
        String authkey = "LT_eesHIc2A3kLNR2AKxhut6J4AFOa86jwNjvMAdUCyuFxhorn";

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browserName", "MicrosoftEdge");
        caps.setCapability("browserVersion", "latest");

        // ✅ W3C-compliant LambdaTest options
        HashMap<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("platformName", "Windows 11");
        ltOptions.put("build", "JUnit Sample_Test Build");
        ltOptions.put("name", testInfo.getDisplayName());
        ltOptions.put("plugin", "git-junit");
        ltOptions.put("selenium_version", "4.21.0");

        caps.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(
                new URL("https://" + username + ":" + authkey + "@hub.lambdatest.com/wd/hub"), caps);

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("runRemoteTest")
    public void runRemoteTest() {
        try {
            driver.get("https://practicetestautomation.com/practice-test-login/");
            driver.findElement(By.id("username")).sendKeys("student");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
            driver.findElement(By.id("password")).sendKeys("Password123");
            wait.until(ExpectedConditions.elementToBeClickable(By.id("submit")));
            driver.findElement(By.id("submit")).click();

            String title = driver.getTitle();
            System.out.println("Title: " + title);
            Assertions.assertTrue(title.contains("Logged In"), "Login failed");
            Status = "passed";
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Assertions.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.executeScript("lambda-status=" + Status);
            driver.quit();
        }
    }
}