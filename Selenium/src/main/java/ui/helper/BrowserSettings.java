package ui.helper;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
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

    public static final String APPLICATION_URL = ConfigReader.getValueByKey("app.url");
    public int WAITTIME = Integer.parseInt(ConfigReader.getValueByKey("wait.time"));
    public static final String USERNAME = ConfigReader.getValueByKey("user.login");
    public static final String PASSWORD = ConfigReader.getValueByKey("user.password");
    public static DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("ddMMYYHHmmss");
    public static final String BROWSER_PROFILE_PATH = ConfigReader.getValueByKey("profile.path");

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeClass
    public void setUp() throws Exception{

        //---Set FF profile
        File profilePath = new File(BROWSER_PROFILE_PATH);
        FirefoxProfile profile = new FirefoxProfile(profilePath);

        if (ConfigReader.getValueByKey("browser.type").equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.gecko.driver", ConfigReader.getValueByKey("chrome.driver.path"));
            DesiredCapabilities cap = DesiredCapabilities.chrome();
            //WebDriverRunner.setWebDriver(driver);
            cap.setCapability("screen-resolution", "1920x1080");
            driver = new FirefoxDriver();
        }
        if (ConfigReader.getValueByKey("browser.type").equalsIgnoreCase("firefox")) {
            DesiredCapabilities cap = DesiredCapabilities.firefox();
            System.setProperty("webdriver.gecko.driver", ConfigReader.getValueByKey("ffdriver.path"));
//            System.setProperty("webdriver.firefox.bin", "C:\\customFolder\\FF\\firefox.exe");  //comment path if FF in ProgrammFiles
            cap.setCapability(FirefoxDriver.PROFILE, profile);
            cap.setCapability("screen-resolution", "1920x1080");

            driver = new FirefoxDriver(cap);
        }
        if (ConfigReader.getValueByKey("browser.type").equalsIgnoreCase("grid")) {
            DesiredCapabilities cap = DesiredCapabilities.firefox();
            System.setProperty("webdriver.gecko.driver", ConfigReader.getValueByKey("ffdriver.path"));
            cap.setCapability(FirefoxDriver.PROFILE, profile);

            RemoteWebDriver driver;
            driver = new RemoteWebDriver(new URL(ConfigReader.getValueByKey("hub.url")), cap);
            String sessionId = driver.getSessionId().toString();
            String videoURL = "http://selenium-hub:8080/grid/resources/remote?session=" + sessionId;
            System.out.println("live, then video recording can be viewed @ " + videoURL);
        }

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