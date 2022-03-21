package pageObjects;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.xml.sax.SAXException;
import utils.web.Browser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

import static utils.configuration.AppStrings.EMAIL_ADDRESS;


public class ArticlePage extends BasePage {

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.CHAIN)
    @AndroidFindBy(id = "toolbar")
    @AndroidFindBy(className = "android.widget.TextView")
    public WebElement articlesPageTitle;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.CHAIN)
    @AndroidFindBy(id = "toolbar")
    @AndroidFindBy(className = "android.widget.ImageButton")
    public WebElement backButton;

    @AndroidFindBy(id = "share")
    public WebElement shareButton;

    @AndroidFindBy(id = "articleImageView")
    public WebElement innerArticleImage;

    @AndroidFindBy(id = "articleDetailHeadlineTextView")
    public WebElement innerArticleTitle;

    @AndroidFindBy(id = "articleDetailBodyTextView")
    public WebElement innerArticleBody;

    // Sharing

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Gmail\")")
    public WebElement gmailButton;

    @AndroidFindBy(className = "android.widget.EditText")
    public List<WebElement> emailToTextField;

    @AndroidFindBy(id = "peoplekit_listview_flattened_row")
    public WebElement gmailAutoComplete;

    @AndroidFindBy(className = "android.widget.EditText")
    public List<WebElement> emailText;

    @AndroidFindBy(id = "send")
    public WebElement sendEmailButton;






    public ArticlePage(Browser browser) {
        super(browser);
        PageFactory.initElements(new AppiumFieldDecorator(browser.getDriver()),this);
    }

    private String articleSharedText;

    private boolean isElementExist(WebElement element, String desc) throws Exception {
        return browser.isElementVisible(element, desc);
    }

    public boolean isAllInnerArticleNecessaryElementsDisplayed() throws Exception {
        boolean elementExist = true;
        isElementExist(articlesPageTitle,"Articles title");
        isElementExist(backButton,"Back button");
        isElementExist(shareButton,"Share button");
        isElementExist(innerArticleImage,"Inner Article Image");
        isElementExist(innerArticleTitle,"Inner Articles title");
        isElementExist(innerArticleBody,"Inner Articles text");
        return elementExist;
    }

    public String getInnerArticleTitle() throws IOException, SAXException, ParserConfigurationException {
        report.info("Article title is: " + browser.getText(innerArticleTitle));
        return browser.getText(innerArticleTitle);
    }

    public void clickOnShareButton() throws IOException, ParserConfigurationException, SAXException {
        browser.click(shareButton,"Share button");
    }

    public void clickGmailButton() throws IOException, ParserConfigurationException, SAXException {
        browser.click(gmailButton,"Gmail button");
    }

    public void typeEmailAddress() throws ParserConfigurationException, IOException, SAXException {
        browser.type(emailToTextField.get(0),EMAIL_ADDRESS,"type Email address");
        browser.click(gmailAutoComplete,"First Autocomplete result");
    }

    public String getActualSharedTitle(){
        return articleSharedText = emailText.get(2).getText();
    }

    public String sharedEmailText() {
        return articleSharedText;
    }

    public String getArticlesPageTitle(){
        return articlesPageTitle.getText();
    }

    public void clickGmailSendButton() throws IOException, ParserConfigurationException, SAXException {
        browser.click(sendEmailButton,"Send button");
    }

    public void goBackToApp() throws ParserConfigurationException, IOException, InterruptedException, SAXException {
        browser.goBack();
        browser.goBack();
    }

}
