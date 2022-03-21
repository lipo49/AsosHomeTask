package pageObjects;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.SAXException;
import testCases.BaseTest;
import utils.configuration.ConfigRun;
import utils.web.Browser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public abstract class BasePage extends BaseTest {

    public Browser browser;
    public WebDriverWait wait;


    public BasePage(Browser browser) {
        this.browser = browser;
        wait = new WebDriverWait(browser.getDriver(), 10);
    }

    public boolean isAndroid() throws IOException, SAXException, ParserConfigurationException {
        return ConfigRun.get("PLATFORM").equals("android");
    }
    public Browser getBrowser() {
        return this.browser;
    }
}
