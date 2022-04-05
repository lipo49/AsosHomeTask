package testCases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import pageObjects.ArticlePage;
import pageObjects.HomePage;
import utils.configuration.Page;
import utils.helpers.ExtendedAssert;
import utils.helpers.PagesPointers;
import utils.helpers.Retry;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class MonzoTests extends BaseTest {

    private HomePage homePage;
    private ArticlePage articlePage;
    private ExtendedAssert extendedAssert;

    @BeforeMethod(alwaysRun = true)
    public void set() throws IOException, SAXException, ParserConfigurationException {
        homePage = (HomePage) new PagesPointers(Page.HOME_PAGE).getPage();
        articlePage = (ArticlePage) new PagesPointers(Page.ARTICLE_PAGE).getPage();
        extendedAssert = new ExtendedAssert();
    }

    @AfterMethod(alwaysRun = true)
    public void reset() {
        browser.get().reopenApp(true);
    }

    @Test(retryAnalyzer = Retry.class)
    public void test_01_verifyTopBarTitleText() throws ParserConfigurationException, IOException, SAXException {
        extendedAssert.softAssertEquals(homePage.isArticlesTitleCorrect(), "Articles");
        extendedAssert.softAssertAll();
    }

    @Test(retryAnalyzer = Retry.class) // Pass
    public void test_02_verifyHomePageEssentialElementsVisibility() throws Exception {
        extendedAssert.assertTrue((homePage.areAllItemElementsDisplayed()));
        extendedAssert.softAssertAll();
    }

    @Test(retryAnalyzer = Retry.class) // Pass
    public void test_03_verifyArticlesPageEssentialElementsVisibility() throws Exception {
        homePage.clickRandomArticle();
        extendedAssert.assertTrue((articlePage.isAllInnerArticleNecessaryElementsDisplayed()));
        extendedAssert.softAssertAll();
    }


    @Test(retryAnalyzer = Retry.class) // Test fails coz shared text = "http://www.test.com" instead of an Article's title
    public void test_04_verifySharedArticlesTitle() throws Exception {
        homePage.clickRandomArticle();
        articlePage.clickOnShareButton();
        articlePage.clickGmailButton();
        extendedAssert.softAssertEquals(homePage.getRandomArticleTitle(), articlePage.getActualSharedTitle());
        articlePage.goBackToApp();
        extendedAssert.softAssertAll();
    }

    @Test(retryAnalyzer = Retry.class) // Running through articles verifying their title and inner-article title
    public void test_05_runningThroughArticlesVerifyingTitles() throws IOException, ParserConfigurationException, SAXException, InterruptedException {
        extendedAssert.assertTrue(homePage.verifyArticleTitleInLists());
        extendedAssert.softAssertAll();
    }

    @Test(retryAnalyzer = Retry.class) // Test fails due to a crash after navigating to 11 articles
    public void test_06_crashingTheApps() throws IOException, ParserConfigurationException, SAXException, InterruptedException {
        extendedAssert.assertTrue(homePage.verifyCrash());
        extendedAssert.softAssertAll();

    }

    @Test(retryAnalyzer = Retry.class) // Test fails coz Articles page title is "Deetails" instead of "Details"
    public void test_07_verifyTopBarTitleText() throws IOException, ParserConfigurationException, InterruptedException, SAXException {
        homePage.clickRandomArticle();
        extendedAssert.softAssertEquals(articlePage.getArticlesPageTitle(), "Details");
        extendedAssert.softAssertAll();


    }
}
