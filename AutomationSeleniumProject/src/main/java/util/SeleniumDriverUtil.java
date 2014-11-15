package util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by bs211w on 7/7/2014.
 */
public class SeleniumDriverUtil {
    public WebDriver driver;

    //Constructor
    public SeleniumDriverUtil(String browserType){

        if (browserType == "FireFox"){
            FirefoxProfile fp = new FirefoxProfile();
            fp.setEnableNativeEvents(true);
            driver = new FirefoxDriver(fp);
            assert driver != null;
        }
        else if ((browserType == "InternetExplorer")){
            DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            String fileName = System.getProperty("user.dir");
            fileName += "/src/main/resources/IEDriverServer.exe";
            File file = new File(fileName);
            System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
            driver = new InternetExplorerDriver(capabilities);
            assert driver != null;
        }
        else{
            FirefoxProfile fp = new FirefoxProfile();
            fp.setEnableNativeEvents(true);
            driver = new FirefoxDriver(fp);
            assert driver != null;
        }
    }


    //Takes screenShot if needed
    public String getScreenShot() throws IOException {
        File screenShot;
        if (driver instanceof TakesScreenshot){
            screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        }
        else {
            // for Selenium Grid
            WebDriver augmentedDriver = new Augmenter().augment(driver);
            screenShot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
        }
        String fileName = UUID.randomUUID().toString().substring(0,18).toUpperCase()+".png";
        String path=System.getProperty("output.directory")+"/images/"+fileName;

        // Needs Commons IO library
        FileUtils.copyFile(screenShot, new File(path));

        return "images/"+fileName;
    }

    //Kills the IE Driver
    public void killDriver(String browserType) {
        final String KILL = "taskkill /F /IM ";
        if (browserType == "FireFox") {
            try {
                driver.close();
                Runtime.getRuntime().exec(KILL + "firefox.exe");
                System.out.println("Driver Closed");
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        else {
            try {
                Runtime.getRuntime().exec(KILL + "iexplore.exe");
                Runtime.getRuntime().exec(KILL + "IEDriverServer.exe");
            } catch (Exception e) {
                System.out.println(e);
                //throw e;
            }
        }
    }
}
