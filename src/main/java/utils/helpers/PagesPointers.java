package utils.helpers;

import org.xml.sax.SAXException;
import pageObjects.ArticlePage;
import pageObjects.HomePage;
import testCases.BaseTest;
import utils.configuration.Page;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class PagesPointers extends BaseTest {

    // This Class contain the "Pointer" object for the pages on the app, this usage helps to manage different classes
    // on both platforms, for example when the iOS has a different functionality we will point to different Page

    private String page;
    private Object object;

    public PagesPointers(String page) {
        this.page = page;
    }

    public Object getPage() throws ParserConfigurationException, SAXException, IOException {
        switch (page) {
            case Page.HOME_PAGE:
                object = browser.get().isAndroid() ? new HomePage(browser.get()) : new HomePage(browser.get());
                break;

            case Page.ARTICLE_PAGE:
                object = browser.get().isAndroid() ? new ArticlePage(browser.get()) : new ArticlePage(browser.get());
                break;

        }
        return object;
    }
}