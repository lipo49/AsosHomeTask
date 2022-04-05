package pageObjects;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.SAXException;
import utils.web.Browser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import static utils.configuration.AppStrings.SEARCH_VALUE;


public class HomePage extends BasePage {

    // Home page Locators

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.CHAIN)
    @AndroidFindBy(id = "searchInput")
    public WebElement searchTextField;

    @AndroidFindBy(id = "difficultySpinner")
    public WebElement difficultySpinner;

    @AndroidFindBy(id = "preparationTimeSpinner")
    public WebElement perapareTimeSpinner;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.CHAIN)
    @AndroidFindBy(id = "recipesRecycler")
    @AndroidFindBy(className = "android.widget.FrameLayout")
    public List<WebElement> recipieElement;

    @AndroidFindBy(id = "pictureView")
    public List<WebElement> imageView;

    @AndroidFindBy(id = "titleView")
    public List<WebElement> recipeTitles;

    @AndroidFindBy(id = "titleView")
    public WebElement recipeTitle;

    @AndroidFindBy(id = "ingredientsView")
    public List<WebElement> recipeIngredients;

    @AndroidFindBy(id = "minutesView")
    public List<WebElement> recipePrepareTime;

    @AndroidFindBy(uiAutomator = "text(\"Hard\")")
    public WebElement hardDif;


    // Constructor
    public HomePage(Browser browser) throws ParserConfigurationException, IOException, SAXException {
        super(browser);
        PageFactory.initElements(new AppiumFieldDecorator(browser.getDriver()),this);
    }

    private boolean exist = true;
    private int index;
    private String title;

    // Function verifies existence of elements on screen
    public boolean homePageElementsVisibility() throws Exception {
        int rand = new Random().nextInt(recipeTitles.size()-1);
        isElementExist(imageView.get(rand), "Image element");
        isElementExist(recipeTitles.get(rand), "Title Element");
        isElementExist(recipeIngredients.get(rand), "Ingredients Element");
        isElementExist(recipePrepareTime.get(rand), "Prepare Time Element");
        isElementExist(searchTextField, "Search Text Field");
        isElementExist(difficultySpinner, "Difficulties");
        isElementExist(perapareTimeSpinner, "Prepare Time");
        return exist;
    }

    private boolean isElementExist(WebElement element, String desc) {
        if (!(browser.isElementVisible(element))) {
            exist = false;
            report.info(desc + " is not visible");
        }
        report.info(desc + " is visible");
        return true;
    }

    // Function returns true only if No. of recipes before search IS MORE than after search
    public boolean searchResultsFunctionality() throws ParserConfigurationException, IOException, SAXException {
        int recipesBeforeSearch = recipieElement.size();
        browser.type(searchTextField,SEARCH_VALUE,"Search for a recipe");
        int recipesAfterSearch = recipieElement.size();
        title = recipeTitle.getText();
        if(recipesBeforeSearch <= recipesAfterSearch) {
            return false;
        }
        return true;
    }

    public void clickSelectedRecipe() throws IOException, ParserConfigurationException, SAXException {
        browser.click(recipeTitle,"Recipe Title");
    }

    // Function returns true only if No. of recipes before filtering IS MORE than after filtering
    public boolean difficultyFilterFunctionality() throws ParserConfigurationException, IOException, SAXException {
        int recipesBeforeFilterApply = recipieElement.size();
        browser.click(difficultySpinner,"Difficulty Filter");
        browser.click(hardDif,"Hard difficulty");
        int recipesAfterFilterApply = recipieElement.size();
        if(recipesBeforeFilterApply <= recipesAfterFilterApply) {
            return false;
        }
        return true;
    }

    // Click on random recipe on screen
    public void clickRandomRecipe() throws IOException, ParserConfigurationException, SAXException, InterruptedException {
            wait = new WebDriverWait(browser.getDriver(), 10);
            wait.until(ExpectedConditions.visibilityOf(recipeTitles.get(0)));
            index = new Random().nextInt(recipeTitles.size());
            title = recipeTitles.get(index).getText();
            browser.click(recipeTitles.get(index), "Random article");
    }


}
