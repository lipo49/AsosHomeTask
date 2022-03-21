package utils.helpers;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.xml.sax.SAXException;
import testCases.BaseTest;
import utils.web.Browser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class TestListener extends BaseTest implements ITestListener {
    //Extent Report Declarations
    private static ExtentReports extent;
    private Report report;
    public static String testName;

    public TestListener(){
        report = new Report();
    }

    static {
        try {
            extent = ExtentManager.createInstance();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    @Override
    public synchronized void onStart(ITestContext context) {
        System.out.println("Test Suite started!");
    }
    @Override
    public synchronized void onFinish(ITestContext context) {
        System.out.println(("Test Suite is ending!"));
        extent.flush();
    }
    @Override
    public synchronized void onTestStart(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " started!"));
        try {
            ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),result.getMethod().getDescription());
            test.set(extentTest);
        }catch (Exception e){

        }
    }
    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " passed!"));
        test.get().pass("Test passed");
        try {
            Browser.steps.clear();
            extent.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public synchronized void onTestFailure(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " failed!"));
        testName = result.getMethod().getQualifiedName();
        System.out.println("+++++++++++++++++ Test failed on: " + result.getMethod().getQualifiedName() + " +++++++++++++++++");
        System.out.println("+++++++++++++++++ Test failed with : " + result.getThrowable().getMessage() + " +++++++++++++++++");
        failed = true;
        test.get().fail(result.getThrowable());
        try {
            extent.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " skipped!"));
        test.get().skip(result.getThrowable());
        System.out.println("+++++++++++++++++ Test skipped on: " + result.getMethod().getQualifiedName() + " +++++++++++++++++");
        System.out.println("+++++++++++++++++ Test skipped with : " + result.getThrowable().getMessage() + " +++++++++++++++++");
        try {
            extent.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
    }

    public String getScreenshot(WebDriver driver) throws IOException, ParserConfigurationException, SAXException {

        String fileName = "screenshot_" + getRandomNumber() + ".png";
        String sSpath =  getData("ReportFilePath") + "/" + getTimeStamp() + "/"  +  fileName;
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destination = sSpath;
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return fileName;
    }
}
