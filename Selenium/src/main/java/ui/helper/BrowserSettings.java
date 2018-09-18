package ui.helper;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import ui.helper.wd.ConfigReader;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class BrowserSettings {

    public int WAITTIME = Integer.parseInt(ConfigReader.getValueByKey("wait.time"));
    public boolean isSeleniumGrid = Boolean.valueOf(ConfigReader.getValueByKey("selenium.grid.enabled"));
    public static DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("ddMMYYHHmmss");


    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeClass
    public void setUp() throws Exception{
        System.setProperty("webdriver.gecko.driver", ConfigReader.getValueByKey("ffdriver.path"));
        DesiredCapabilities cap = DesiredCapabilities.firefox();

        //---Set FF profile
        File profilePath = new File(ConfigReader.getValueByKey("profile.path"));
        FirefoxProfile profile = new FirefoxProfile(profilePath);
        cap.setCapability(FirefoxDriver.PROFILE, profile);

        cap.setCapability("video", true); //Videorecording on: http://selenium-hub:8080/grid/resources/remote?session=SELENIUMSESSIONID
        cap.setCapability("project", "Test Project");
        cap.setCapability("apm_id", "TestProject");
        //cap.setCapability("user", ConfigReader.getValueByKey("user.login")); //TechUser login
        //cap.setCapability("password", ConfigReader.getValueByKey("user.password")); //TechUser pass
        if (isSeleniumGrid) {
            driver = new RemoteWebDriver(new URL(ConfigReader.getValueByKey("hub.url")), cap);
            String sessionId = ((RemoteWebDriver)driver).getSessionId().toString();
            String videoURL = "http://selenium-hub:8080/grid/resources/remote?session=" + sessionId;
            System.out.println("live, then video recording can be viewed @ " + videoURL);}
        else {driver = new FirefoxDriver();}

        wait = new WebDriverWait(driver, WAITTIME);
        driver.manage().window().maximize();
        //driver.get(ConfigReader.getValueByKey("app.url"));
        Thread.sleep(2000);
        initialize();
    }

    @AfterClass
    protected void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void initialize() {
    }


}