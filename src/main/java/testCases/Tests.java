package testCases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import pageObjects.recipePage;
import pageObjects.HomePage;
import utils.configuration.Page;
import utils.helpers.ExtendedAssert;
import utils.helpers.PagesPointers;
import utils.helpers.Retry;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static utils.configuration.AppStrings.SEARCH_VALUE;

public class Tests extends BaseTest {

    private HomePage homePage;
    private recipePage recipePage;
    private ExtendedAssert extendedAssert;

    @BeforeMethod(alwaysRun = true)
    public void set() throws IOException, SAXException, ParserConfigurationException {
        homePage = (HomePage) new PagesPointers(Page.HOME_PAGE).getPage();
        recipePage = (recipePage) new PagesPointers(Page.RECIPE_PAGE).getPage();
        extendedAssert = new ExtendedAssert();
    }

    @AfterMethod(alwaysRun = true)
    public void reset() {
        browser.get().reopenApp(true);
    }


    @Test(retryAnalyzer = Retry.class)
    public void test_01_verifyHomePageEssentialElementsVisibility() throws Exception {
        extendedAssert.assertTrue(homePage.homePageElementsVisibility());
        extendedAssert.softAssertAll();
    }

    @Test(retryAnalyzer = Retry.class)
    public void test_02_verifyRecipePageEssentialElementsVisibility() throws Exception {
        homePage.clickRandomRecipe();
        extendedAssert.assertTrue(recipePage.areAllRecipePageElementsDisplayed());
        extendedAssert.softAssertAll();
    }


    @Test(retryAnalyzer = Retry.class) // save your searched text and assert it appeared
    public void test_03_verifySearchFunctionality() throws Exception {
        extendedAssert.softAssertTrue((homePage.searchResultsFunctionality()));
        homePage.clickSelectedRecipe();
        extendedAssert.softAssertEquals(SEARCH_VALUE,recipePage.getInnerRecipeTitle());
        extendedAssert.softAssertAll();
    }

    @Test(retryAnalyzer = Retry.class)
    public void test_04_verifyDifficultyFilter() throws Exception {
        extendedAssert.assertTrue(homePage.difficultyFilterFunctionality());
        extendedAssert.softAssertAll();
    }

}
