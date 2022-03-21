package pageObjects;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.SAXException;
import utils.configuration.Page;
import utils.helpers.PagesPointers;
import utils.web.Browser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.Random;


public class HomePage extends BasePage {

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.CHAIN)
    @AndroidFindBy(id = "toolbar")
    @AndroidFindBy(className = "android.widget.TextView")
    public WebElement pageTitle;

    @AndroidFindBy(id = "articleTextView")
    public List<WebElement> articleTextViews;

    @AndroidFindBy(id = "articleTextView")
    public WebElement articleTextView;

    @AndroidFindBy(id = "articleImageView")
    public List<WebElement> articleImage;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.CHAIN)
    @AndroidFindBy(id = "articleListRecyclerView")
    @AndroidFindBy(className = "android.widget.LinearLayout")
    public List<WebElement> articleItems;

    public HomePage(Browser browser) throws ParserConfigurationException, IOException, SAXException {
        super(browser);
        PageFactory.initElements(new AppiumFieldDecorator(browser.getDriver()),this);
        articlePage = (ArticlePage) new PagesPointers(Page.ARTICLE_PAGE).getPage();
    }

    private int index;
    private String title;
    private ArticlePage articlePage;

    public String isArticlesTitleCorrect() throws ParserConfigurationException, IOException, SAXException {
        return browser.getText(pageTitle);
    }

    private boolean isElementExist(WebElement element, String desc) throws Exception {
        return browser.isElementVisible(element, desc);
    }

    public boolean areAllItemElementsDisplayed() throws Exception {
        if (!isElementExist(pageTitle,"Articles title"))
            return false;
        for (WebElement article: articleItems) {
            if (!isElementExist(article.findElement(By.id("articleTextView")),"Article text"))
                return false;
            if (!isElementExist(article.findElement(By.id("articleImageView")),"Article Image"))
                return false;
        }
        return true;
    }

    public void clickRandomArticle() throws IOException, ParserConfigurationException, SAXException, InterruptedException {
            wait = new WebDriverWait(browser.getDriver(), 10);
            wait.until(ExpectedConditions.visibilityOf(articleTextViews.get(0)));
            index = new Random().nextInt(articleTextViews.size());
            title = articleTextViews.get(index).getText();
            browser.click(articleTextViews.get(index), "Random article");
    }

    public String getRandomArticleTitle() {
        return title;
    }

    public boolean verifyArticleTitleInLists() throws IOException, ParserConfigurationException, SAXException, InterruptedException {
        for (int i = 0; i <5; i++) {
            browser.swipeDown(2000);
            clickRandomArticle();
            String innerArticleTitle = articlePage.innerArticleTitle.getText();
            if (innerArticleTitle.equals(title)) {
                report.info("Inner Article's title equals to Home page article's title");
                browser.goBack();
            } else {
                report.info("Inner Article's title NOT equals to Home page article's title");
                return false;
            }
        }
        return true;
    }



    public boolean verifyCrash() throws IOException, ParserConfigurationException, SAXException, InterruptedException {
        try {
            while (browser.isElementVisible(articleTextViews.get(index))) {
                browser.swipeDown(2000);
                clickRandomArticle();
                browser.goBack();
            }
        } catch (Exception e) {
            browser.fail(e.getMessage());
        }
        return true;
    }






}
