package utils.web;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import testCases.BaseTest;

import java.net.URL;
import java.util.concurrent.TimeUnit;


public class WebDriverFactory extends BaseTest{

    private DesiredCapabilities capabilities;
    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public WebDriver init(BrowserType browserType) throws Exception {
        switch (browserType) {
            case ANDROID:
                return initAndroid(true);
            case IOS:
                return initIOS(true);
            default:
                throw new Exception("Not implement yet");
        }
    }

    public WebDriver initAndroid(boolean isCacheReset) {
        capabilities = new DesiredCapabilities();
        if (!isCacheReset) {
            capabilities.setCapability("noReset", "true");
        }
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, TimeUnit.MINUTES.toMillis(10)); // Will wait 10 min to next command, helps on debug mode
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("autoGrantPermissions", true);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.asos.recipes.debug");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.asos.recipes.presentation.ui.splash.SplashActivity");
        capabilities.setCapability("appium:setWebContentsDebuggingEnabled", "true"); // must when working with webViews
        try {
                // ------------------------- Local init --------------------
                driver.set(new AndroidDriver(new URL("http://localhost:4723/wd/hub"), capabilities));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }

        driver.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver.get();
    }

    public WebDriver initIOS(boolean isCacheReset) {
        capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Iphone");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        capabilities.setCapability("xcodeOrgId", ""); // constant from apple developer
        capabilities.setCapability("xcodeSigningId", "iPhone Developer"); // constant
        capabilities.setCapability("automationName", "XCUITest");
        capabilities.setCapability("restart", "true");
        capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "");
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, TimeUnit.MINUTES.toMillis(5));
        capabilities.setCapability("autoAcceptAlerts", true);
        capabilities.setCapability("browserstack.appiumLogs", "true");
        capabilities.setCapability("appium:setWebContentsDebuggingEnabled", "true");

        try {
                // ------------------------- Local init --------------------
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "15"); // Need real version from the device
                capabilities.setCapability("udid", ""); // Need the real udid from the device
                IOSDriver driverInternal = new IOSDriver(new URL("http://localhost:4723/wd/hub"), capabilities);
                driver.set(driverInternal);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        driver.get().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return driver.get();
    }
}
