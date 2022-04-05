package utils.helpers;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.xml.sax.SAXException;
import testCases.BaseTest;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class ExtentManager extends BaseTest {
    private static ExtentReports extent;
    private static String reportFileName = "Report.html";
    private static String macPath = System.getProperty("user.dir")+ "/AsosAppReport" + "/" + getTimeStamp() + "/";
    private static String windowsPath = System.getProperty("user.dir")+ "\\AsosAppReport" + "\\" + getTimeStamp() + "\\";
    private static String macReportFileLoc = macPath + "/" + reportFileName;
    private static String winReportFileLoc = windowsPath + "\\" + reportFileName;
    public static ExtentReports getInstance() throws ParserConfigurationException, SAXException, IOException {
        if (extent == null)
            createInstance();
        return extent;
    }
    //Create an extent report instance
    public static ExtentReports createInstance() throws IOException, SAXException, ParserConfigurationException {
        String fileName = getReportFileLocation();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(fileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(fileName);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        return extent;
    }
    //Select the extent report file location based on platform
    private static String getReportFileLocation () {
        String reportFileLocation = null;
                reportFileLocation = macReportFileLoc;
                createReportPath(macPath);
        report.info("ExtentReport Path for MAC: " + macPath + "\n");
        return reportFileLocation;
    }
    //Create the report path if it does not exist
    private static void createReportPath (String path) {
        File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdir()) {
                report.info("Directory: " + path + " is created!" );
            } else {
                report.info("Failed to create directory: " + path);
            }
        } else {
            report.info("Directory already exists: " + path);
        }
    }
}
