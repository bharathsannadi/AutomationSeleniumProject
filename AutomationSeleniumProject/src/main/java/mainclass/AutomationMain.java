package mainclass;

import util.SeleniumDriverUtil;
import util.ReadPropertiesFileUtil;
import util.TestDriver;
import org.openqa.selenium.WebDriver;
import java.util.ArrayList;
import java.util.Properties;

import util.ExcelUtil;

/**
 * Created by Sagari on 8/16/2014.
 */
public class AutomationMain {
    static WebDriver driver;

    public static void main(String[] args) throws Exception {
        //Read the properties
        ArrayList excelRead = ExcelUtil.ExcelTestDataRead();

        Properties prop = new ReadPropertiesFileUtil().readPropertiesFile("config.properties");
        String browserType =  prop.getProperty("BrowserType");
        SeleniumDriverUtil dUtil = new SeleniumDriverUtil(browserType);
        driver = dUtil.driver;

        TestDriver testDriver = new TestDriver(driver);
        testDriver.ExecuteTests(excelRead);

        dUtil.killDriver(browserType);
    }
}
