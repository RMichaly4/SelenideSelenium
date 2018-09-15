package ui.pageObjects;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import ui.helper.BrowserSettings;
import ui.helper.ImageImpl;

import java.io.File;
import java.util.Calendar;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.testng.Assert.assertTrue;

public class ApplicationActions extends BrowserSettings {

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

    private SelenideElement drpdwnList = $(By.xpath("//select[@id='dropdown']"));
    public SelenideElement inputFieldForFilter(drpdwnListValues_1 value) {return $(By.xpath("//option[contains(text(),'" + value + "')]"));}
    public SelenideElement inputFieldForFilter_2(String value) {return $(By.xpath("//option[contains(text(),'" + value + "')]"));}
    public SelenideElement linkPage (String value) {return $(By.xpath("//a[contains(text(),'" + value + "')]"));}
    public SelenideElement pageTitle (String title) {return $(By.xpath("//h3[contains(text(),'" + title + "')]"));}
    public SelenideElement drpdwnListValue (String value) {return $(By.xpath("//option[contains(text(),'" + value + "')]"));}
    private SelenideElement pageElement (String value) {return $(By.xpath("//*[contains(text(),'" + value + "')]"));}
    public SelenideElement fieldYARUSearch() {return $(By.xpath("//input[@name='text']"));}
    public SelenideElement textInBigCircle(String text) {
        return $(By.xpath("//div[text()='" + text + "']"));
    }
    public SelenideElement pageText() {return $(By.xpath("//h3[text()='New Window']"));}
    public SelenideElement btnSearch() {return $(By.xpath("//button[@type='submit']"));}
    public SelenideElement linkForOpenNewWindow() {return $(By.xpath("//a[@href='/windows/new']"));}
    public SelenideElement buttonClickForJSAlert() {return $(By.xpath("//button[text()='Click for JS Alert']"));}

    public ApplicationActions openPage(String link) throws Exception {
        open("http://the-internet.herokuapp.com");
        linkPage(link).click();
        pageTitle(link).waitUntil(visible, 10000);
        return this;
    }

    public ApplicationActions openPageDrgAndDrop() throws Exception {
        open("https://demos.telerik.com/kendo-ui/dragdrop/index");
        $(By.xpath("//div[@id='draggable']")).waitUntil(visible, 10000);
        return this;
    }

    public ApplicationActions openYARUPage() throws Exception {
        open("https://yandex.ru/search/?text=test");
        return this;
    }

    public ApplicationActions openNewWindowPage() throws Exception {
        open("http://the-internet.herokuapp.com/windows");
        return this;
    }

    public ApplicationActions openDropdownList() throws Exception {
        String[] arrDrpdwnListValue = {"Please select an option", "Option 1", "Option 2"};
        drpdwnList.click();
        for (int i = 0; i < arrDrpdwnListValue.length; i++)
            assertTrue(drpdwnListValue(arrDrpdwnListValue[i]).isDisplayed(), arrDrpdwnListValue[i]);
        return this;
    }

    public ApplicationActions selectDpdwnListValue(drpdwnListValues_1 value) throws Exception {
        inputFieldForFilter(value).hover();
        Thread.sleep(500);
        inputFieldForFilter(value).click();
        return this;
    }

    public ApplicationActions selectDpdwnListValue_2(String value) throws Exception {
        inputFieldForFilter_2(value).hover();
        Thread.sleep(500);
        inputFieldForFilter_2(value).click();
        return this;
    }

    public ApplicationActions scrollToElement(String text) {
        open("http://the-internet.herokuapp.com");
        Selenide.executeJavaScript("arguments[0].scrollIntoView(true);", pageElement(text));
        return this;
    }

    public ApplicationActions dndElement() {
        SelenideElement From = $(By.xpath("//div[@id='draggable']"));
        SelenideElement To = $(By.xpath("//div[@id='droptarget']"));
        From.dragAndDropTo(To);
        return this;
    }

    public String setText() {
        this.name = DEFAULT_DATE_FORMAT.format(Calendar.getInstance().getTime()) + "_test";
        return name;
    }

    public String getText() {
        return this.name;
    }

    public SelenideElement imgElement() {return $(By.xpath("//div[@id='hplogo']"));}

    public ApplicationActions openImgPage() throws Exception {
        open("https://images.google.com");
        return this;
    }

    public ApplicationActions compareElementWithOriginal(SelenideElement element, String original) throws Exception {
        ImageImpl imageImpl = new ImageImpl(driver);
        String elementId = element.getAttribute("id");
        imageImpl.makeScreenshot(element, elementId);
        assertTrue(imageImpl.processImage(elementId, original));
        imageImpl.cleanDirectory(new File("src/test/screenshots/"));
        return this;
    }

    public ApplicationActions switchToBrowserTab() throws InterruptedException {
    Thread.sleep(2000);
    switchTo().window(0).close();
    switchTo().window(0);
    Thread.sleep(2000);
    return this;
    }

    public enum TemplateType {
        user1,
        user2,
        user3
    }

    public ApplicationActions selectProfile(TemplateType templateType) {
        open("http://the-internet.herokuapp.com/hovers");
        $(By.xpath("//*[text()='Hovers']")).isDisplayed();
        switch (templateType) {
            case user1:
                $(By.xpath("//div[@class='example']/div[1]")).hover();
                $(By.xpath("//div[@class='example']/div[1]//a[text()='View profile']")).isDisplayed();
                $(By.xpath("//div[@class='example']/div[1]//a[text()='View profile']")).click();
                assertTrue(url().contains("http://the-internet.herokuapp.com/users/1"));
                System.out.print("Profile for user 1 selected");
                break;
            case user2:
                $(By.xpath("//div[@class='example']/div[2]")).hover();
                $(By.xpath("//div[@class='example']/div[2]//a[text()='View profile']")).click();
                assertTrue(url().contains("http://the-internet.herokuapp.com/users/2"));
                System.out.print("Profile for user 2 selected");
                break;
            case user3:
                $(By.xpath("//div[@class='example']/div[3]")).hover();
                $(By.xpath("//div[@class='example']/div[3]//a[text()='View profile']")).click();
                assertTrue(url().contains("http://the-internet.herokuapp.com/users/3"));
                System.out.print("Profile for user 3 selected");
                break;
        }
        return this;
    }

    public ApplicationActions openUserProfile3() {
        open("http://ya.ru");
        if ($(By.xpath("//div[@class='example']/div[3]//a[text()='View profile']")).isDisplayed()) {
            return this;
        } else {
            open("http://the-internet.herokuapp.com/hovers");
            $(By.xpath("//*[text()='Hovers']")).isDisplayed();
            $(By.xpath("//div[@class='example']/div[3]")).hover();
            $(By.xpath("//div[@class='example']/div[3]//a[text()='View profile']")).click();
            return this;
        }
    }

    public ApplicationActions openJavascripAlertsPage() {
        open("http://the-internet.herokuapp.com/javascript_alerts");
        return this;
    }

    public ApplicationActions verifyElementColor(String element, String color) throws InterruptedException {
        String elementColor1 = element;
        String hex1 = Color.fromString(elementColor1).asHex();
        Assert.assertEquals(color, hex1);
        return this;
    }
}
