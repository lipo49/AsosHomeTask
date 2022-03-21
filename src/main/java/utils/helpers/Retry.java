package utils.helpers;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import testCases.BaseTest;

public class Retry extends BaseTest implements IRetryAnalyzer {

     int counter = 0;
     int retryLimit = 3;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (counter < retryLimit){
            counter++;
            return true;
        }
        return false;
    }
}
