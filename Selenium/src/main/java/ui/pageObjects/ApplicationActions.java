package ui.pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ui.helper.BrowserSettings;
import ui.helper.ImageImpl;
import java.io.File;
import java.util.Calendar;

import static org.testng.Assert.assertEquals;

public class ApplicationActions extends BrowserSettings{
    private WebDriverWait wait;
    private WebDriver driver;

    public ApplicationActions(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, WAITTIME);
    }

    private String name;

    public enum drpdwnListValues_1 {
        PSAO("Please select an option"),
        O1("Option 1"),
        O2("Option 2");

        private final String drpdwnListVal;

        drpdwnListValues_1(String drpdwnListVal) {
            this.drpdwnListVal = drpdwnListVal;
        }

        @Override
        public String toString() {
            return String.valueOf(drpdwnListVal);
        }
    }

    @FindBy(xpath = "//select[@id='dropdown']")
    public WebElement drpdwnList;
    @FindBy(xpath = "//a[@href='/windows/new']")
    public WebElement linkForOpenNewWindow;
    @FindBy(xpath = "//button[@type='submit']")
    public WebElement btnSearch;
    @FindBy(xpath = "//h3[text()='New Window']")
    public WebElement pageText;
    @FindBy(xpath = "//input[@name='text']")
    public WebElement fieldYARUSearch;
    @FindBy(xpath = "//*[contains(text(),'Powered by')]")
    public WebElement linkPoweredBy;
    @FindBy(xpath = "//*[contains(text(),'A/B Testing')]")
    public WebElement linkPoweredByABTesting;
    @FindBy(xpath = "//button[text()='Click for JS Alert']")
    public WebElement buttonClickForJSAlert;

    public WebElement  inputFieldForFilter(drpdwnListValues_1 value) {return driver.findElement(By.xpath("//option[contains(text(),'" + value + "')]"));}
    public WebElement inputFieldForFilter_2(String value) {return driver.findElement(By.xpath("//option[contains(text(),'" + value + "')]"));}
    public WebElement linkPage (String value) {return driver.findElement(By.xpath("//a[contains(text(),'" + value + "')]"));}
    public WebElement pageTitle (String title) {return driver.findElement(By.xpath("//h3[contains(text(),'" + title + "')]"));}
    public WebElement drpdwnListValue (String value) {return driver.findElement(By.xpath("//option[contains(text(),'" + value + "')]"));}
    public WebElement textInBigCircle(String text) {return driver.findElement(By.xpath("//div[text()='" + text + "']"));}
    public WebElement imgElement() {return driver.findElement(By.xpath("//div[@id='hplogo']"));}

    public ApplicationActions openPage(String link) throws Exception {
        driver.get("http://the-internet.herokuapp.com");
        linkPage(link).click();
        pageTitle(link).isDisplayed();
        return this;
    }

    public ApplicationActions openPageDrgAndDrop() throws Exception {
        driver.get("https://demos.telerik.com/kendo-ui/dragdrop/index");
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//div[@id='draggable']"))));
        return this;
    }

    public ApplicationActions openYARUPage() throws Exception {
        driver.get("https://yandex.ru/search/?text=test");
        return this;
    }

    public ApplicationActions openNewWindowPage() throws Exception {
        driver.get("http://the-internet.herokuapp.com/windows");
        return this;
    }

    public ApplicationActions openDropdownList() throws Exception {
        String[] arrDrpdwnListValue = {"Please select an option", "Option 1", "Option 2"};
        drpdwnList.click();
        for (int i = 0; i < arrDrpdwnListValue.length; i++)
            Assert.assertTrue(drpdwnListValue(arrDrpdwnListValue[i]).isDisplayed(), arrDrpdwnListValue[i]);
        return this;
    }

    public String getText() {
        return this.name;
    }

    public ApplicationActions selectDpdwnListValue(drpdwnListValues_1 value) throws Exception {
        Actions action = new Actions(driver);

        action.moveToElement(inputFieldForFilter(value)).build().perform();
        Thread.sleep(500);
        inputFieldForFilter(value).click();
        return this;
    }

    public ApplicationActions selectDpdwnListValue_2(String value) throws Exception {
        Actions action = new Actions(driver);

        action.moveToElement(inputFieldForFilter_2(value)).build().perform();
        Thread.sleep(500);
        inputFieldForFilter_2(value).click();
        return this;
    }

    public ApplicationActions scrollToElement(WebDriver driver, WebElement element) throws InterruptedException {
        driver.get("http://the-internet.herokuapp.com");
        scrollUPDown(driver, element);
        return this;
    }

    public static Object scrollUPDown (WebDriver driver, WebElement element) throws InterruptedException {
        return ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    public ApplicationActions dndElement() {
        WebElement From =  driver.findElement(By.xpath("//div[@id='draggable']"));
        WebElement To =  driver.findElement(By.xpath("//div[@id='droptarget']"));

        Actions builder = new Actions(driver);
        Action dragAndDrop = builder.clickAndHold(From)
                .moveToElement(To)
                .release(To)
                .build();
        dragAndDrop.perform();
        return this;
    }

    public String setText() {
        this.name = DEFAULT_DATE_FORMAT.format(Calendar.getInstance().getTime()) + "_test";
        return name;
    }

    public ApplicationActions openImgPage() throws Exception {
        driver.get("https://images.google.com");
        return this;
    }

    public ApplicationActions compareElementWithOriginal(WebElement element, String original) throws Exception {
        ImageImpl imageImpl = new ImageImpl(driver);
        String elementId = element.getAttribute("id");
        imageImpl.makeScreenshot(element, elementId);
        Assert.assertTrue(imageImpl.processImage(elementId, original));
        imageImpl.cleanDirectory(new File("src/test/screenshots/"));
        return this;
    }

    public ApplicationActions switchToBrowserTab() throws InterruptedException{
        Thread.sleep(2000);
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);}
        Thread.sleep(2000);
        return this;
    }

    public enum TemplateType {
            user1,
            user2,
            user3
        }

    public ApplicationActions selectProfile(TemplateType templateType) {
        Actions action = new Actions(driver);

        driver.get("http://the-internet.herokuapp.com/hovers");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Hovers']")));
        switch (templateType) {
            case user1:
                action.moveToElement(driver.findElement(By.xpath("//div[@class='example']/div[1]"))).build().perform();
                driver.findElement(By.xpath("//div[@class='example']/div[1]//a[text()='View profile']")).click();
                assertEquals(driver.getCurrentUrl(), "http://the-internet.herokuapp.com/users/1" );
                System.out.print("Profile for user 1 selected");
                break;
            case user2:
                action.moveToElement(driver.findElement(By.xpath("//div[@class='example']/div[2]"))).build().perform();
                driver.findElement(By.xpath("//div[@class='example']/div[2]//a[text()='View profile']")).click();
                assertEquals(driver.getCurrentUrl(), "http://the-internet.herokuapp.com/users/2" );
                System.out.print("Profile for user 2 selected");
                break;
            case user3:
                action.moveToElement(driver.findElement(By.xpath("//div[@class='example']/div[3]"))).build().perform();
                driver.findElement(By.xpath("//div[@class='example']/div[3]//a[text()='View profile']")).click();
                assertEquals(driver.getCurrentUrl(), "http://the-internet.herokuapp.com/users/3" );
                System.out.print("Profile for user 3 selected");
                break;
        }
        return this;
    }

    public ApplicationActions openUserProfile3() {
        Actions action = new Actions(driver);

        driver.get("http://ya.ru");

        boolean present;
        try {
            driver.findElement(By.xpath("//*[text()='Hovers']")).isDisplayed();
            present = true;
        } catch (NoSuchElementException e) {
            present = false;
        }

        if (present) {
            return this;
        } else {
            driver.get("http://the-internet.herokuapp.com/hovers");
            driver.findElement(By.xpath("//*[text()='Hovers']")).isDisplayed();
            action.moveToElement(driver.findElement(By.xpath("//div[@class='example']/div[3]"))).build().perform();
            driver.findElement(By.xpath("//div[@class='example']/div[3]//a[text()='View profile']")).click();
            return this;
        }
    }

    public ApplicationActions openJavascripAlertsPage() {
        driver.get("http://the-internet.herokuapp.com/javascript_alerts");
        return this;
    }

    public ApplicationActions verifyElementColor(String element, String color) throws InterruptedException {
        String elementColor1 = element;
        String hex1 = Color.fromString(elementColor1).asHex();
        Assert.assertEquals(color, hex1);
        return this;
    }


}
