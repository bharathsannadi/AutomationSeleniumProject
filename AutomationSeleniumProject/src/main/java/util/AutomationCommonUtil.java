package util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by bs211w on 7/7/2014.
 */
public class AutomationCommonUtil {

    WebDriver driver;
    Actions action ;

    private final String strFailed = " : Failed -- Actual : ";
    private final String strPassed = " : Passed -- Actual : ";
    private final String strExpected = " Expected :";

    public AutomationCommonUtil(WebDriver driver){
        this.driver = driver;
        action = new Actions(driver);
    }

    //launch URL
    public void launchPageURL(String url){
       try{
           driver.get(url);
       }
       catch(Exception e){
           HtmlLogUtil.writeToLog(1, "Exception occurred : - " + e.getMessage());
       }
    }

    //Set text on the input box
    public void setText(By byElement , String textString) {
        try{
            waitForElement(byElement).sendKeys(textString);
            HtmlLogUtil.writeToLog(0,"The Text :" + textString + "is set in the input box");
        }
        catch(Exception e){
            HtmlLogUtil.writeToLog(1, "Exception occurred : - " + e.getMessage());
        }
    }

    //wait For Element
    public WebElement waitForElement(By byElement) {
        try{
            WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.presenceOfElementLocated(byElement));
            return myDynamicElement;
        }
        catch(Exception e){
            HtmlLogUtil.writeToLog(1, "Exception Element Not found : - " + e.getMessage());
            return null;
        }
    }

    //Click on any element
    public void clickElement(By byElement) {
        try{
            waitForElement(byElement).click();
            HtmlLogUtil.writeToLog(0,"Element Clicked");
        }
        catch(Exception e){
            HtmlLogUtil.writeToLog(1, "Exception occurred : - " + e.getMessage());
        }
    }

    //validate the text
    public void ValidateText(String textToValidate) {
        try {
            List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'" + textToValidate + "')]"));
            if (list.size() > 0) {
                HtmlLogUtil.writeToLog(0, textToValidate + " is present on the page", true);
            } else {
                HtmlLogUtil.writeToLog(1, textToValidate + " is not present on the page", true);
            }
        }catch(Exception e){
            HtmlLogUtil.writeToLog(1, "Exception occurred : - " + e.getMessage());
        }
    }
}
