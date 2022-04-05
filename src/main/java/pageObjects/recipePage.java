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


public class ArticlePage extends BasePage {

//    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.CHAIN)
//    @AndroidFindBy(id = "toolbar")
//    @AndroidFindBy(className = "android.widget.TextView")
//    public WebElement articlesPageTitle;
//
//    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.CHAIN)
//    @AndroidFindBy(id = "toolbar")
//    @AndroidFindBy(className = "android.widget.ImageButton")
//    public WebElement backButton;


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


    public ArticlePage(Browser browser) {
        super(browser);
        PageFactory.initElements(new AppiumFieldDecorator(browser.getDriver()),this);
    }


    private boolean isElementExist(WebElement element, String desc) throws Exception {
//        System.out.println(desc);
        return browser.isElementVisible(element, desc);
    }

    public boolean isAllInnerArticleNecessaryElementsDisplayed() throws Exception {
        boolean elementExist = true;
        isElementExist(backButton,"Back button");
        isElementExist(innerArticleImage,"Recipe Image");
        isElementExist(innerRecipeTitle,"Recipe Title");
        isElementExist(ingredientsLabel,"Ingredients Label");
        isElementExist(instructionsLabel,"Instructions Label");
        isElementExist(instructionsSection,"Instructions section");
        String ingredientsNumber = ingredientsLabel.getText();
        System.out.println(ingredientsNumber);
        int ingrItems = ingredientsItems.size() -1 ;
        String ingredientsNumberString = String.valueOf(ingrItems);
        System.out.println(ingredientsNumberString);
        if(!ingredientsNumber.contains(ingredientsNumberString)){
            return false;
        }
        return elementExist;
    }

    public String getInnerRecipeTitle() throws IOException, ParserConfigurationException, org.xml.sax.SAXException {
        report.info("Recipe title is: " + browser.getText(innerRecipeTitle));
        System.out.println(innerRecipeTitle.getText());
        return browser.getText(innerRecipeTitle);
    }


//
//    public String sharedEmailText() {
//        return articleSharedText;
//    }
//

//    public void clickGmailSendButton() throws IOException, ParserConfigurationException, SAXException {
//        browser.click(sendEmailButton,"Send button");
//    }
//
//    public void goBackToApp() throws ParserConfigurationException, IOException, InterruptedException, SAXException {
//        browser.goBack();
//        browser.goBack();
//    }

}
