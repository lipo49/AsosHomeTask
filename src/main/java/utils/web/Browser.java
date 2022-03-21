package utils.web;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.xml.sax.SAXException;
import testCases.BaseTest;
import utils.configuration.ConfigRun;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static testCases.BaseTest.browser;
import static testCases.BaseTest.report;

public class Browser {

    // Extension to the driver functionality
    private WebDriver driver;
    private WebDriverWait wait;
    private BaseTest baseTest;
    private String deviceIndex;
    private WebDriverFactory webDriverFactory;
    private int counter;

    public static ArrayList<String> steps = new ArrayList<String>();

    public Browser(WebDriver driver) throws ParserConfigurationException, SAXException, IOException {
        this.driver = driver;
        wait = new WebDriverWait(this.getDriver(), 10);
        baseTest = new BaseTest();
        webDriverFactory = new WebDriverFactory();
        counter = 0;

    }


    public WebDriver getDriver() {
        return driver;
    }

    public void tap(WebElement element, String tapDiscription) throws IOException, SAXException, ParserConfigurationException {
        TouchAction action;
        wait = new WebDriverWait(browser.get().getDriver(), 5);
        if (isAndroid()) {
            action = new TouchAction((AndroidDriver) driver);
        } else {
            action = new TouchAction((IOSDriver) driver);
        }
        try {
            steps.add("Click on: " + tapDiscription);
            tapItem(element, action);
            report.info("Tap on: " + tapDiscription);

        } catch (Exception e) {
            try {

                System.out.println("Second try to tap on " + tapDiscription);
                steps.add("Click on: " + tapDiscription);
                tapItem(element, action);
                report.info("Tap on: " + tapDiscription);
            } catch (Exception e2) {
                report.info("failed to tap on: " + tapDiscription);
                report.screenShot();
                report.fail(e.getMessage());
            }
        }
    }

    private void tapItem(WebElement element, TouchAction action) {
        int startX = ((MobileElement) element).getCenter().getX();
        int startY = ((MobileElement) element).getCenter().getY();
        action.tap(new PointOption().withCoordinates(startX, startY)).perform();
    }

    public void click(WebElement element, String clickDescription) throws IOException, SAXException, ParserConfigurationException {
        try {
            wait = new WebDriverWait(browser.get().getDriver(), 10);
            if (isAndroid()) {
                wait.until(ExpectedConditions.elementToBeClickable(element));
            } else {
                wait.until(ExpectedConditions.elementToBeClickable(element));
            }
            steps.add("Click on: " + clickDescription);
            if (isAndroid()) {
                element.click();
                report.info("Click on: " + clickDescription);
            } else {
                browser.get().tap(element, clickDescription);
            }
        } catch (Exception e) {
            try {
                System.out.println("Second try to click on " + clickDescription);
                System.out.println(e.getMessage());
                steps.add("Click on: " + clickDescription);
                if (isAndroid()) {
                    element.click();
                    report.info("Click on: " + clickDescription);
                } else {
                    browser.get().tap(element, clickDescription);
                }
            } catch (Exception e2) {
                report.info("failed to click on: " + clickDescription);
                System.out.println(e2.getMessage());
                report.screenShot();
                report.fail(e.getMessage());
            }
        }
    }

    public boolean isAndroid() throws IOException, SAXException, ParserConfigurationException {
        if (ConfigRun.get("PLATFORM").equals("android")) {
            return true;
        } else {
            return false;
        }
    }

    public void type(WebElement element, String value, String description) throws ParserConfigurationException, SAXException, IOException {
        String s = value;
        try {
            report.info("Type '" + value + "' to " + description + " field.");
            element.sendKeys(s);
        } catch (Exception e) {
            report.fail(e.getMessage());
            this.fail(e.getMessage());
        }
    }

    public String getText(WebElement element) throws ParserConfigurationException, SAXException, IOException {
        String text = "";
        try {
            if (isElementVisible(element)) {
                report.info("Getting text");
                text = element.getText();
                report.info("Text is: " + text);
            }
            return text;
        } catch (Exception e) {
            fail(e.getMessage());
            return "";
        }
    }

    public void fail(String error) throws ParserConfigurationException, SAXException, IOException {
        report.fail(error);
        Assert.fail(error);
    }

    // Function will take the dimension of the screen and swipe right the screen
    // milisec parameter is how fast the swipe will be done for example 300 it is a fast swipe, and 2000 it is a slow one
    public void swipeRight(int milisec) {
        try {
            TouchAction action;
            Dimension size = driver.manage().window().getSize();
            int starty = size.height / 2;
            int startx = (int) (size.width * 0.85);
            int endy = size.height / 2;
            int endx = (int) (size.width * 0.15);
            if (isAndroid()) {
                action = new TouchAction((AndroidDriver) driver);
            } else {
                action = new TouchAction((IOSDriver) driver);
            }
            action.press(new PointOption().withCoordinates(startx, endy));
            action.waitAction(waitOptions(Duration.ofMillis(milisec)));
            action.moveTo(new PointOption().withCoordinates(endx, starty));
            action.release();
            action.perform();
            report.info("Swipe right");
        } catch (Exception e) {

        }

    }

    public void swipeDown(int milisec) {
        try {
            if (!isAndroid()) {
                milisec = milisec + 1000;
            }
            TouchAction action;
            Dimension size = driver.manage().window().getSize();
            int startx = size.width / 2;
            int starty = (int) (size.height * 0.75);
            int endx = size.width / 2;
            int endy = (int) (size.height * 0.4);
            if (ConfigRun.get("PLATFORM").equals("android")) {
                action = new TouchAction((AndroidDriver) driver);
            } else {
                action = new TouchAction((IOSDriver) driver);
            }
            action.press(new PointOption().withCoordinates(startx, starty));
            action.waitAction(waitOptions(Duration.ofMillis(milisec)));
            action.moveTo(new PointOption().withCoordinates(endx, endy));
            action.release();
            action.perform();
            report.info("Swipe down");
        } catch (Exception e) {

        }
    }

    public void swipeDown(int milisec, boolean smallSwipe) {
        try {
            if (!isAndroid()) {
                milisec = milisec + 1000;
            }
            TouchAction action;
            Dimension size = driver.manage().window().getSize();
            int startx = size.width / 2;
            int starty = (int) (size.height * 0.6);
            int endx = size.width / 2;
            int endy = (int) (size.height * 0.4);
            if (ConfigRun.get("PLATFORM").equals("android")) {
                action = new TouchAction((AndroidDriver) driver);
            } else {
                action = new TouchAction((IOSDriver) driver);
            }
            action.press(new PointOption().withCoordinates(startx, starty));
            action.waitAction(waitOptions(Duration.ofMillis(milisec)));
            action.moveTo(new PointOption().withCoordinates(endx, endy));
            action.release();
            action.perform();
            report.info("Swipe down");
        } catch (Exception e) {

        }
    }

    public void swipeUp(int milisec) {
        try {
            TouchAction action;
            Dimension size = driver.manage().window().getSize();
            int startx = size.width / 2;
            int starty = (int) (size.height * 0.75);
            int endx = size.width / 2;
            int endy = (int) (size.height * 0.4);
            if (ConfigRun.get("PLATFORM").equals("android")) {
                action = new TouchAction((AndroidDriver) driver);
            } else {
                action = new TouchAction((IOSDriver) driver);
            }
            action.press(new PointOption().withCoordinates(startx, endy));
            action.waitAction(waitOptions(Duration.ofMillis(milisec)));
            action.moveTo(new PointOption().withCoordinates(endx, starty));
            action.release();
            action.perform();
            report.info("Swipe up");
        } catch (Exception e) {

        }
    }

    public void findElementAndSwipe(WebElement element, int duration, Integer... maxSwipes) throws InterruptedException {
        boolean isFound = false;
        Integer swipes = maxSwipes.length > 0 ? maxSwipes[0] : 15;
        int counter = 0;
        while (!isFound) {
            try {
                if (isAndroid()) {
                    if (isElementVisible(element)) {
                        isFound = true;
                    } else {
                        throw new Exception();
                    }
                } else {
                    if (element.getAttribute("visible").equals("true") || !(element.getText().equals(""))) {
                        isFound = true;
                    } else {
                        Thread.sleep(3000);
                        throw new Exception();
                    }
                }
            } catch (Exception e) {
                swipeDown(duration);
            }
            if (!isFound) {
                counter++;
            }
            if (counter == swipes) {
                try {
                    browser.get().fail("Cant find element");
                    report.info("Can't find element");
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // Using a lot on before method
    // isCacheReset will reset the app with cache reset, simulate new installation (work only with android)
    // !isCacheReset will reopen the app
    public void reopenApp(Boolean isCacheReset, Integer... clearIOSData) {
        try {
            if (!isCacheReset) {
                ((AppiumDriver) this.getDriver()).runAppInBackground(Duration.ofMillis(100));
            }
            if (!isAndroid() && clearIOSData.length > 0) {
                ((AppiumDriver) driver).resetApp();
            } else {
                ((AppiumDriver) driver).resetApp();
            }
        } catch (Exception e) {
            // retry to reset with new driver
            try {
                reopenApp(true, true);
            } catch (Exception e2) {
                System.out.println("************ Failed on reset app ***********");
            }
        }
    }

    public void reopenApp(Boolean isCacheReset, boolean reopenDriver) throws ParserConfigurationException, SAXException, IOException {
        try {
            if (counter == 10) {
                counter = 0;
                browser.get().fail("************************************ Restart driver is on loop ************************************");
            }
            if (isAndroid()) {
                if (reopenDriver) {
                    System.out.println("************************************ restart driver ************************************");
                    browser.get().reopenApp(true);
                    counter++;
                }
            } else {
                if (reopenDriver) {
                    System.out.println("************************************ restart driver ************************************");
                    ((IOSDriver) this.getDriver()).terminateApp("com.test.qainterviewapp");
                    ((IOSDriver) this.getDriver()).launchApp();
                    counter++;
                }
            }
        } catch (Exception e) {
            System.out.println("************ Failed on reset app driver *********** " + e.getMessage());
        }
    }


    public boolean isElementVisible(WebElement element, Integer... waitTimes) {
        int counter = 0;
        Integer initialWaitTime = waitTimes.length > 0 ? waitTimes[0] : 10;
        Integer afterWaitTime = waitTimes.length > 1 ? waitTimes[1] : 10;
        try {
            if (isAndroid()){
                if (element.isDisplayed()) {
//                    System.out.println(element + " is visible");
                    return true;
                }
            }else {
                if (element.isDisplayed() || (!element.getText().isEmpty())){
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
//            report.info("Element is not visible: " + element);
            return false;
        }
    }

    // with fail description
    public boolean isElementVisible(WebElement element, String description){
        WebDriverWait wait = new WebDriverWait(this.driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        description = "<b style=\"margin: 0px 10px 0px 10px;color:blue;font-size:15px;\">" + description + "</b>";
        if (isElementVisible(element)){
            //report.info(description + " element is visible");
            return true;
        }
        return false;
    }

    public void goBack() throws InterruptedException, ParserConfigurationException, SAXException, IOException {
        report.info("Press back");
        Thread.sleep(1500);
        if (isAndroid()) {
            driver.navigate().back();

        } else {
            try {
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(MobileBy.iOSNsPredicateString("type == 'XCUIElementTypeButton' and name = 'back_button'")));
            } catch (Exception e) {
            }
            this.click(this.getDriver().findElement(MobileBy.iOSNsPredicateString("type == 'XCUIElementTypeButton' and name = 'back_button'")), "Back");
        }
    }

    public void setImplicitWaitTime(long seconds) throws ParserConfigurationException, SAXException, IOException {
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void hideKeyboard() {
        try {
            if (isAndroid()) {
                ((AppiumDriver) this.getDriver()).hideKeyboard();
            } else {
                this.swipeUp(500);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean isKeyboardShown() {
        try {
            if (isAndroid()) {
                return ((AndroidDriver) this.getDriver()).isKeyboardShown();
            } else {
                return ((IOSDriver) this.getDriver()).isKeyboardShown();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void findElementAndLocateToMiddle(WebElement elem) throws ParserConfigurationException, SAXException, IOException {
        int counter = 0;
        int milisec = 2000;
        if (!isAndroid()) {
            while (!isElementVisible(elem) && counter < 3) {
                PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
                swipeDown(300);
                counter++;
            }
        } else {
            while (!isElementVisible(elem) && counter < 7) {
                PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
                swipeDown(400);
                counter++;
            }
            try {
                PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
                findElementAndSwipe(elem, milisec);
                Point location = elem.getLocation();
                TouchAction action;
                Dimension size = getDriver().manage().window().getSize();
                int startx = size.width / 2;
                int starty = (location.getY()) + (elem.getSize().getHeight()) / 2;
                int endx = size.width / 2;
                int endy = (int) (size.height * 0.40);

                action = new TouchAction((AndroidDriver) getDriver());
                action.press(new PointOption().withCoordinates(startx, starty));
                action.waitAction(waitOptions(Duration.ofMillis(milisec)));
                action.moveTo(new PointOption().withCoordinates(endx, endy));
                action.release();
                action.perform();
            } catch (Exception e) {
            }
        }

    }

    public void swipeScreen(Direction dir) {
        System.out.println("swipeScreen(): dir: '" + dir + "'"); // always log your actions

        // Animation default time:
        //  - Android: 300 ms
        //  - iOS: 200 ms
        // final value depends on your app and could be greater
        final int ANIMATION_TIME = 200; // ms

        final int PRESS_TIME = 200; // ms

        int edgeBorder = 10; // better avoid edges
        PointOption pointOptionStart, pointOptionEnd;

        // init screen variables
        Dimension dims = driver.manage().window().getSize();

        // init start point = center of screen
        pointOptionStart = PointOption.point(dims.width / 2, dims.height / 2);

        switch (dir) {
            case DOWN: // center of footer
                pointOptionEnd = PointOption.point(dims.width / 2, dims.height - edgeBorder);
                break;
            case UP: // center of header
                pointOptionEnd = PointOption.point(dims.width / 2, edgeBorder);
                break;
            case LEFT: // center of left side
                pointOptionEnd = PointOption.point(edgeBorder, dims.height / 2);
                break;
            case RIGHT: // center of right side
                pointOptionEnd = PointOption.point(dims.width - edgeBorder, dims.height / 2);
                break;
            default:
                throw new IllegalArgumentException("swipeScreen(): dir: '" + dir + "' NOT supported");
        }

        // execute swipe using TouchAction
        try {
            TouchAction action;
            if (isAndroid()) {
                action = new TouchAction(((AndroidDriver) this.getDriver()));
            } else {
                action = new TouchAction(((IOSDriver) this.getDriver()));
            }
            //       new TouchAction(((AndroidDriver) driver))
            action.press(pointOptionStart)
                    // a bit more reliable when we add small wait
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
                    .moveTo(pointOptionEnd)
                    .release().perform();
        } catch (Exception e) {
            System.err.println("swipeScreen(): TouchAction FAILED\n" + e.getMessage());
            return;
        }

        // always allow swipe action to complete
        try {
            Thread.sleep(ANIMATION_TIME);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT;
    }

    public void doubleTap(WebElement element) throws Exception {
        TouchActions action;
        if (isAndroid()) {
            action = new TouchActions(((AndroidDriver) this.getDriver()));

        } else {
            action = new TouchActions(((IOSDriver) this.getDriver()));
        }
        action.doubleTap(element);
    }

    public void moveMapRight(int milisec) {
        try {
            TouchAction action;
            Dimension size = driver.manage().window().getSize();
            int starty = (int) (size.height * 0.25);
            int startx = (int) (size.width * 0.85);
            int endy = (int) (size.height * 0.25);
            int endx = (int) (size.width * 0.15);
            if (isAndroid()) {
                action = new TouchAction((AndroidDriver) driver);
            } else {
                action = new TouchAction((IOSDriver) driver);
            }
            action.press(new PointOption().withCoordinates(startx, endy));
            action.waitAction(waitOptions(Duration.ofMillis(milisec)));
            action.moveTo(new PointOption().withCoordinates(endx, starty));
            action.release();
            action.perform();
        } catch (Exception e) {

        }

    }
}
