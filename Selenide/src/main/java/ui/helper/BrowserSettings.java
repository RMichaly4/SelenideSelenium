package ui.helper;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.close;

public class BrowserSettings {

    public static DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("ddMMYYHHmmss");

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeClass
    public void setUp() throws Exception {
        FileInputStream file = new FileInputStream("target/classes/config.properties");
        Properties prop = new Properties();
        prop.load(file);

        com.codeborne.selenide.Configuration.timeout = Integer.valueOf(prop.getProperty("wait.time"));

//        cap.setCapability("apm_id", "Test Application");
//        cap.setCapability("project", "TestApp");
//        cap.setCapability("user", prop.getProperty("user.login"));
//        cap.setCapability("password", prop.getProperty("user.password"));


        if (prop.getProperty("browser.type").equalsIgnoreCase("chrome")) {
            DesiredCapabilities cap2 = DesiredCapabilities.chrome();
            System.setProperty("webdriver.gecko.driver", prop.getProperty("chrome.driver.path"));
            cap2.setCapability("screen-resolution", "1920x1080");
        }
        if (prop.getProperty("browser.type").equalsIgnoreCase("firefox")) {
            DesiredCapabilities cap = DesiredCapabilities.firefox();
            System.setProperty("webdriver.gecko.driver", prop.getProperty("ffdriver.path"));
            //System.setProperty("webdriver.firefox.bin", "C:\\customFolder\\FF\\firefox.exe");  //comment path if FF in ProgrammFiles

            FirefoxProfile profile = new FirefoxProfile(new File(prop.getProperty("profile.path")));
            cap.setCapability(FirefoxDriver.PROFILE, profile);
            cap.setCapability("screen-resolution", "1920x1080");

            driver = new FirefoxDriver(cap);
            WebDriverRunner.setWebDriver(driver);
        }
        if (prop.getProperty("browser.type").equalsIgnoreCase("grid")) {
            DesiredCapabilities cap = DesiredCapabilities.firefox();
            System.setProperty("webdriver.gecko.driver", prop.getProperty("ffdriver.path"));
            FirefoxProfile profile = new FirefoxProfile(new File(prop.getProperty("profile.path")));
            cap.setCapability(FirefoxDriver.PROFILE, profile);

            RemoteWebDriver driver;
            driver = new RemoteWebDriver(new URL(prop.getProperty("hub.url")), cap);
            driver.setFileDetector(new LocalFileDetector());
            String sessionId = driver.getSessionId().toString();
            String videoURL = "http://selenium-hub:8080/grid/resources/remote?session=" + sessionId;
            System.out.println("live, then video recording can be viewed @ " + videoURL);
        }

        WebDriverRunner.getWebDriver().manage().window().maximize();
        //open("http://the-internet.herokuapp.com/");
    }

    @AfterClass
    protected void tearDown() {
        if (driver != null) {
            close();
        }
    }

}
