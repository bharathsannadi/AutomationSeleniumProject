package util;

import org.openqa.selenium.WebDriver;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by bs211w on 7/23/2014.
 */
public class TestDriver {


    WebDriver driver;

    //Constructor
    public TestDriver(WebDriver driver){
        this.driver = driver;
    }

    //Run all the tests that has indicator "Y"
    public void ExecuteTests(ArrayList<ArrayList<String>> excelRead) throws Exception {

        HtmlLogUtil htmlLogUtil = new HtmlLogUtil(driver);
        try {
            int testNumber;
            Class noParams[] = {};
            String testID = new String();
            String testName= new String();
            String logPath = new String();

              ArrayList<ArrayList<ArrayList<String>>> testList = testsToExecute(excelRead);
              htmlLogUtil.createSummaryHtmlLogFile(testList.size());


              for (ArrayList<ArrayList<String>> tests : testList) {

                  //Get the testID , testName , testLogPath
                  testID  = tests.get(0).get(0).toString();
                  testName  = tests.get(0).get(1).toString();

                  logPath = htmlLogUtil.createHtmlLogFile(testName);

                  for (ArrayList<String> currentTest : tests) {
                      //Get the class name and function name
                      String className = currentTest.get(4);
                      String functionName = currentTest.get(5);


                      //Read the class name and function name using java reflection
                      Class<?> RefClass = Class.forName("seleniumautomation." + className);
                      Class[] argTypes = {WebDriver.class};
                      Constructor<?> cons = RefClass.getDeclaredConstructor(argTypes);
                      Object[] arguments = {driver};
                      Object obj = cons.newInstance(arguments);
                      Method method = RefClass.getDeclaredMethod(functionName, noParams);

                      //invoke the method within the mentioned class
                      method.invoke(obj, null);
                  }
                  htmlLogUtil.closeHtmlLogFile();
                  htmlLogUtil.writeToSummaryLog(testID,testName,logPath);
          }
            htmlLogUtil.closeSummaryHtmlLogFile();
        }
        catch(Exception exp){
          throw exp;
        }
    }

    //segregates all the tests in the excel
    //Splits each test case in the test case by using the test steps
    public ArrayList testSegregation(ArrayList<ArrayList<String>> excelRead) throws ArrayIndexOutOfBoundsException{

    int i = 1;
    ArrayList testList = new ArrayList();
    ArrayList ExcelTestList = new ArrayList();

    for(ArrayList<String> stringArrayList:excelRead){
        if(stringArrayList.get(3) != null ){
            if (stringArrayList.get(3).contentEquals("Step 1")){
                if(i != 1){
                    testList.add(ExcelTestList);
                }
                i = 1;
                ExcelTestList = new ArrayList();
            }
            if( stringArrayList.get(3).contentEquals("Step " + i)){
                ExcelTestList.add(stringArrayList);
                i++;
            }
        }
    }
      testList.add(ExcelTestList);
      return testList;
  }

    //select the tests that has Execute indicator "Y"
    public ArrayList testsToExecute(ArrayList<ArrayList<String>> excelRead) {

        //Collect test cases that has Execute flag = "Y"
        ArrayList testToExecute = new ArrayList();

        ArrayList<ArrayList<ArrayList<String>>> testList = testSegregation(excelRead);
        for(ArrayList<ArrayList<String>> tests:testList){
            if (tests.get(0).get(2).toString().contentEquals("Y")) {
                testToExecute.add(tests);
            }
        }
        return testToExecute;
    }
}
