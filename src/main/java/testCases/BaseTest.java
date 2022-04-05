package testCases;

import io.appium.java_client.AppiumDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import utils.configuration.ConfigRun;
import utils.helpers.Report;
import utils.web.Browser;
import utils.web.BrowserType;
import utils.web.WebDriverFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class BaseTest {

    public static String timeStamp = new SimpleDateFormat("dd-MM-yyy_HH-mm-ss")
            .format(Calendar.getInstance().getTime());
    public static String path;
    public static Report report = new Report();
    public static ThreadLocal<Browser> browser = new ThreadLocal<>();
    public AppiumDriver webDriver;
    public SoftAssert softAssert = new SoftAssert();
    public WebDriverFactory webDriverFactory;
    public static String getTimeStamp() {
        return timeStamp;
    }
    public static boolean failed = false;

    ///////////////////// Suite ///////////////////
    @BeforeSuite(alwaysRun = true)
    public void init() throws IOException, SAXException, ParserConfigurationException{
        System.out.println("BeforeSuite");
        path = getData("ReportFilePath") + "/" + timeStamp + "/" + "report" + ".html";
    }

    @AfterSuite(alwaysRun = true)
    public void shutDown()  {
    }

    // Driver building, chooses which driver to run, IOS or Android according to what set on ConfigRun.properties file
    @BeforeTest(alwaysRun = true)
    public void start() throws Exception {

        webDriverFactory = new WebDriverFactory();
        System.out.println("BeforeTest");
        if (ConfigRun.get("PLATFORM").equals("android")) {
            try {
                webDriver = (AppiumDriver) webDriverFactory.init(BrowserType.ANDROID);
                browser.set(new Browser(webDriver));
            } catch (Exception e) {
            }
        } else {
            try {
                webDriver = (AppiumDriver) webDriverFactory.init(BrowserType.IOS);
                browser.set(new Browser(webDriver));
            } catch (Exception e) {
            }
        }

    }
    // Driver quiting after each test
    @AfterTest(alwaysRun = true)
    public void close() {
        try {
            browser.get().getDriver().quit();
        } catch (Exception e) {
        }
    }

    ///////////////////// Class ///////////////////
    @BeforeClass(alwaysRun = true)
    public void setUp(ITestContext context) {
        System.out.println("BeforeClass");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
    }


    ///////////////////// Method ///////////////////
    @BeforeMethod(alwaysRun = true) //worked
    public void doBeforeTest(Method method){
        System.out.println("BeforeMethod: " + testNameChanging(method.getName()));
    }

    @AfterMethod(alwaysRun = true)
    public void doAfterTest(){
    }

    // Change test name string for the report
    private String testNameChanging(String oldName) {
        String s = "";
        char ch;
        char[] chars = oldName.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        s += chars[0];
        for (int i = 1; i < chars.length; i++) {
            if (Character.isUpperCase(chars[i])) {
                ch = Character.toLowerCase(chars[i]);
                s += " ";
                s += ch;
            } else {
                s += chars[i];
            }
        }
        return s;
    }

    // Random for screen shot file name
    public static int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(999999) + 1000;
    }

    // Returning string from xml file
    public static String getData(String nodeName) throws ParserConfigurationException, IOException, SAXException {
        File xmlFile = new File("params.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName(nodeName).item(0).getTextContent();
    }
}
