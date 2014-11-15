package seleniumautomation;

import databuilder.AccountInfo;
import databuilder.AccountInfoBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import util.AutomationCommonUtil;

/**
 * Created by Sagari on 8/16/2014.
 */
public class GMailLogin {

    AutomationCommonUtil seleniumUtil;
    WebDriver driver;
    AccountInfo accountInfo;

    private final By byFirstName = By.id("FirstName");
    private final By byLastName = By.id("LastName");
    private final By bySubmit = By.id("submitbutton");

    public GMailLogin(WebDriver driver){
        this.driver = driver;
        seleniumUtil = new AutomationCommonUtil(driver);
        accountInfo = new AccountInfo("RAM","Lakshman");

    }

    public void launchUrl(){
        seleniumUtil.launchPageURL("https://accounts.google.com/SignUp?continue=https%3A%2F%2Faccounts.google.com%2FManageAccount");
    }

    public void doLogin(){
        seleniumUtil.setText(byFirstName,accountInfo.getFirstName());
        seleniumUtil.setText(byLastName, accountInfo.getLastName());
        seleniumUtil.clickElement(bySubmit);
        seleniumUtil.ValidateText("Account Created Successfully");
     }
}
