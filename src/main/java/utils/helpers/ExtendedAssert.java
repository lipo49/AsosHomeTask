package utils.helpers;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.xml.sax.SAXException;
import testCases.BaseTest;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class ExtendedAssert extends BaseTest {

    // Extension for the assertTrue TestNG function
    public void assertTrue(boolean condition) throws IOException, SAXException, ParserConfigurationException {
        try {
            Assert.assertTrue(condition);
        } catch (Exception e) {
            report.fail("Test FAIL: " + e.getMessage());
            browser.get().fail(e.getMessage());
        } catch (AssertionError error) {
            report.fail("Assert FAIL: " + error.getMessage());
            browser.get().fail(error.getMessage());
        }
    }

    // Extension for the assertEqual TestNG function
    public void assertStringEqual(String expected, String real) throws ParserConfigurationException, SAXException, IOException, InterruptedException {
        report.info("Compare values ");
        real = real.replace("\n", "").replace("\r", "");
        expected = expected.replace("\n", "").replace("\r", "");
        try {
            Assert.assertEquals(real, expected);
        } catch (Exception e) {
            report.fail("Test FAIL: " + e.getMessage());
            report.screenShot();
            browser.get().fail(e.getMessage());
        } catch (AssertionError error) {
            report.fail("Assert FAIL: " + error.getMessage());
            browser.get().fail(error.getMessage());
        }
    }

    // Helping function for the stringContain function
    public void assertStringContain(String expected, String real) throws ParserConfigurationException, SAXException, IOException, InterruptedException {
        expected = expected.toLowerCase();
        real = real.toLowerCase();
        try {
            if (expected.contains(real)){
            }else {
                throw new AssertionError("The text does not contain value: " + real);
            }
        } catch (Exception e) {
            report.fail("Test FAIL: " + e.getMessage());
            report.screenShot();
            browser.get().fail(e.getMessage());
        } catch (AssertionError error) {
            report.fail("Assert FAIL: " + error.getMessage());
            browser.get().fail(error.getMessage());
        }
    }

    public void softAssertEquals(String expected, String real) throws ParserConfigurationException, SAXException, IOException {
        report.info("Compare values ");
        real = real.replace("\n", " ").replace("\r", " ");
        expected = expected.replace("\n", " ").replace("\r", " ");
        try {
            softAssert.assertEquals(real, expected);
            report.info( "Expected for text: " + expected + " The real text is: " + real);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void softAssertNotEquals(String expected, String real) throws ParserConfigurationException, SAXException, IOException {
        report.info("Compare values ");
        real = real.replace("\n", " ").replace("\r", " ");
        expected = expected.replace("\n", " ").replace("\r", " ");
        try {
            softAssert.assertNotEquals(real, expected);
            report.info( "Expected for text: " + expected + " The real text is: " + real);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void softAssertTrue(boolean condition) throws ParserConfigurationException, SAXException, IOException {
        try {
                softAssert.assertTrue(condition);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void softStringContain(String expected, String real) throws ParserConfigurationException, SAXException, IOException, InterruptedException {
        expected = expected.toLowerCase();
        real = real.toLowerCase();
        try {
            if (expected.contains(real)) {
                softAssertTrue(true);
            }else {
                softAssertTrue(false);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void softAssertAll() throws ParserConfigurationException, SAXException, IOException {
        try {
            softAssert.assertAll();
        } catch (AssertionError e) {
            softAssert = new SoftAssert();
            report.fail("Test FAIL: " + e.getMessage());
            browser.get().fail(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
