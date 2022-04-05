package pageObjects;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.web.Browser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;


public class recipePage extends BasePage {

    // Recipe Page Locators

    @AndroidFindBy(id = "pictureView")
    public WebElement innerArticleImage;

    @AndroidFindBy(id = "titleView")
    public WebElement innerRecipeTitle;

    @AndroidFindBy(id = "ingredientsLabel")
    public WebElement ingredientsLabel;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.CHAIN)
    @AndroidFindBy(id = "ingredientsWrapper")
    @AndroidFindBy(className = "android.widget.LinearLayout")
    public List<WebElement> ingredientsItems;

    @AndroidFindBy(id = "instructionsLabel")
    public WebElement instructionsLabel;

    @AndroidFindBy(id = "instructionsWrapper")
    public WebElement instructionsSection;

    @AndroidFindBy(className = "android.widget.ImageButton")
    public WebElement backButton;

    // Constructor
    public recipePage(Browser browser) {
        super(browser);
        PageFactory.initElements(new AppiumFieldDecorator(browser.getDriver()),this);
    }

    private boolean exist = true;


    private boolean isElementExist(WebElement element, String desc) {
        if (!(browser.isElementVisible(element))) {
            exist = false;
            report.info(desc + " is not visible");
        }
        report.info(desc + " is visible");
        return true;
    }

    // Function verifies existence of elements on screen
    public boolean areAllRecipePageElementsDisplayed() throws Exception {
        isElementExist(backButton,"Back button");
        isElementExist(innerArticleImage,"Recipe Image");
        isElementExist(innerRecipeTitle,"Recipe Title");
        isElementExist(ingredientsLabel,"Ingredients Label");
        isElementExist(instructionsLabel,"Instructions Label");
        isElementExist(instructionsSection,"Instructions section");
        String ingredientsNumber = ingredientsLabel.getText();
        int ingrItems = ingredientsItems.size() -1 ;
        String ingredientsNumberString = String.valueOf(ingrItems);
        if(!ingredientsNumber.contains(ingredientsNumberString)){
            return false;
        }
        return exist;
    }
    // Getting recipe page title for assertion
    public String getInnerRecipeTitle() throws IOException, ParserConfigurationException, org.xml.sax.SAXException {
        report.info("Recipe title is: " + browser.getText(innerRecipeTitle));
        return browser.getText(innerRecipeTitle);
    }



}
