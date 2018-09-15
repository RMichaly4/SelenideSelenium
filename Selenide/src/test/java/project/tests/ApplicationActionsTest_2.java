package project.tests;

import org.testng.annotations.Test;
import ui.helper.BrowserSettings;
import ui.pageObjects.ApplicationActions;

import static com.codeborne.selenide.Condition.selected;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ui.pageObjects.ApplicationActions.TemplateType.user2;
import static ui.pageObjects.ApplicationActions.drpdwnListValues_1.*;

public class ApplicationActionsTest_2 extends BrowserSettings {

    private ApplicationActions applicationActions = new ApplicationActions();

    @Test
    public void verifyDropdownListValues() throws Exception {
        applicationActions.openPage("Dropdown")
            .openDropdownList()
            .selectDpdwnListValue(O2);
        assertTrue(applicationActions.inputFieldForFilter(O2).is(selected));
        assertFalse(applicationActions.inputFieldForFilter(O1).is(selected));
        assertFalse(applicationActions.inputFieldForFilter(PSAO).is(selected));
    }

    @Test
    public void verifyDropdownListValues_2() throws Exception {
        applicationActions.openPage("Dropdown")
                .openDropdownList()
                .selectDpdwnListValue_2("Option 2");
        assertTrue(applicationActions.inputFieldForFilter_2("Option 2").is(selected));
        assertFalse(applicationActions.inputFieldForFilter_2("Option 1").is(selected));
        assertFalse(applicationActions.inputFieldForFilter_2("Please select an option").is(selected));
    }

    @Test
    public void scrollToElement() throws Exception {
        applicationActions.scrollToElement("Powered by")
            .scrollToElement("A/B Testing");
    }

    @Test
    public void dndElements() throws Exception {
        applicationActions.openPageDrgAndDrop()
            .dndElement();
        assertTrue(applicationActions.textInBigCircle("You did great!").isDisplayed());
    }

    @Test
    public void setTimeStampToTextField() throws Exception {
        applicationActions.openYARUPage()
            .fieldYARUSearch().clear();
        applicationActions.fieldYARUSearch().sendKeys(applicationActions.setText());
        applicationActions.btnSearch().click();
        assertTrue(applicationActions.fieldYARUSearch().getAttribute("value").equals(applicationActions.getText()));

    }

    @Test
    public void switchToBrowserTab() throws Exception {
        applicationActions.openNewWindowPage()
                .linkForOpenNewWindow().click();
        applicationActions.switchToBrowserTab();
        assertTrue(applicationActions.pageText().isDisplayed());

    }

    @Test
    public void verifyImg() throws Exception {
        applicationActions.openImgPage()
                .compareElementWithOriginal(applicationActions.imgElement(), "hplogo");
    }

    @Test
    public void selectUserProfile() throws Exception {
        applicationActions.selectProfile(user2);
    }

    @Test
    public void test_If_Else_ForUserProfile3() throws Exception {
        applicationActions.openUserProfile3();
    }

    @Test
    public void verifyElementColor() throws Exception {
        applicationActions.openJavascripAlertsPage();
        applicationActions.verifyElementColor(applicationActions.buttonClickForJSAlert().getCssValue("background-color"), "#2ba6cb");
    }

//    @AfterClass
//    @BeforeClass
//    public void cleanupDB () {
//        System.out.print("Executing cleanup for Database...");
//        Jdbc.getInstance().cleanupDB();
//        ImageImpl.cleanDirectory(new File("src/test/screenshots/"));
//    }


}
