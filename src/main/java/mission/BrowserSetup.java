package mission;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserSetup {

    public static WebDriver selectBrowser() {
        String browser = LoadProp.getProperty("Browser");
        WebDriver driver;

        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions options = new ChromeOptions();
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("profile.password_manager_leak_detection", false);
                options.setExperimentalOption("prefs", prefs);
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(options);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            case "chromeheadless":
                ChromeOptions headless = new ChromeOptions();
                headless.addArguments("--headless");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(headless);
                break;

            default:
                Assert.fail(MessageFormat.format("Wrong Browser: {0}", browser));
                return null;
        }

        driver.manage().window().maximize();
        return driver;
    }
}
