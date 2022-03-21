package utils.helpers;


import com.aventstack.extentreports.Status;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.xml.sax.SAXException;
import testCases.BaseTest;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Report extends BaseTest {

    // Extension to the ExtentReports class

    public Report(){
    }

    public void info(String description) {
        try {
            TestListener.test.get().info(description);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void fail(String description) throws IOException, SAXException, ParserConfigurationException {
      try {
          System.out.println("Fail: " + description);
          report.screenShot();
      }catch (Exception e){
          System.out.println(e.getMessage());
      }
    }
    public void warning(String description){
        TestListener.test.get().log(Status.WARNING, description);
    }

    public void screenShot() throws ParserConfigurationException, IOException, SAXException {
        try {
            TestListener.test.get().addScreenCaptureFromPath(getScreenshot(browser.get().getDriver()));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
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
