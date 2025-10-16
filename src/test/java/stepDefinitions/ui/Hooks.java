// package stepDefinitions.ui;

// import java.io.File;
// import java.io.IOException;
// import java.sql.Timestamp;
// import java.util.Date;
// import java.util.concurrent.TimeUnit;

// import org.apache.commons.io.FileUtils;
// import org.openqa.selenium.NoSuchSessionException;
// import org.openqa.selenium.OutputType;
// import org.openqa.selenium.TakesScreenshot;

// import io.cucumber.java.After;
// import io.cucumber.java.Before;
// import io.cucumber.java.Scenario;
// import mission.BaseTest;
// import mission.BrowserSetup;
// import mission.LoadProp;
// import mission.LoginPage;
// // import mission.iniClass;

// public class Hooks extends BaseTest {
//     public LoginPage loginPage;

//     private static final int WAIT_SEC = 20;

//     @Before("@UI")
//     public void initializeTest() {
//         driver = BrowserSetup.selectBrowser();
//         driver.manage().deleteAllCookies();
//         driver.manage().timeouts().pageLoadTimeout(WAIT_SEC, TimeUnit.SECONDS);
//         driver.manage().timeouts().implicitlyWait(WAIT_SEC, TimeUnit.SECONDS);
//         driver.manage().timeouts().setScriptTimeout(WAIT_SEC, TimeUnit.SECONDS);
//         // new iniClass();
//     }

//     /**
//      * Executed after each UI tagged scenario
//      */
//     @After("@UI")
//     public void screenshot(Scenario scenario) {
//         String screenShotFilename = scenario.getName().replace(" ", "")
//                 + new Timestamp(new Date().getTime()).toString().replaceAll("[^a-zA-Z0-9]", "")
//                 + "_" + LoadProp.getProperty("Browser") + ".jpg";
//         File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//         try {
//             FileUtils.copyFile(scrFile, new File(LoadProp.getProperty("ScreenshotLocation") + screenShotFilename));
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//         driver.close();
//         //Handling the NoSuchSessionException with Firefox browser after close
//         try {
//         driver.quit();
//         } catch (NoSuchSessionException ex) {
//         }
//     }
// }


package stepDefinitions.ui;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.sl.In;
import mission.BasePage;
import mission.BaseTest;
import mission.BrowserSetup;
import mission.CheckoutStepOnePage;
import mission.CheckoutStepTwoPage;
import mission.InventoryPage;
import mission.LoginPage;
import mission.ShoppingCartPage;

public class Hooks extends BaseTest {

    public BasePage basePage;
    public LoginPage loginPage; // Shared page object
    public InventoryPage inventoryPage;
    public CheckoutStepOnePage checkoutStepOnePage;
    public CheckoutStepTwoPage checkoutStepTwoPage;
    public ShoppingCartPage shoppingCartPage;

    @Before
    public void beforeScenario() {
        // Initialize driver
        driver = BrowserSetup.selectBrowser();

        // Initialize page objects AFTER driver exists
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        checkoutStepOnePage = new CheckoutStepOnePage(driver);
        checkoutStepTwoPage = new CheckoutStepTwoPage(driver);
        shoppingCartPage = new ShoppingCartPage(driver);
   }

    @After
    public void afterScenario() {
        if (driver != null) {
            driver.quit();
        }
    }
}
