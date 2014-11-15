package util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by bs211w on 7/21/2014.
 */
public class HtmlLogUtil {

    //Declaration
    private static String strMsg = new String();
    private static String strLogPath;
    private static String logSummaryTitle;
    private static String logSummaryFolderName;
    private static String logFolderName;
    private static String logSummaryPath;
    private static String currDir;
    private static String strExecutionSummary;
    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static BufferedWriter output;
    public static BufferedWriter summaryOutput;

    private static int intIncrementer;
    private static int index = 0;

    private static File summaryLogFile;
    private static File logFile;

    public static String testResult = "Pass";
    public static boolean bolTestResult = true;
    private static WebDriver driver;


    //Creating a HTML log
    public HtmlLogUtil(WebDriver driver) throws IOException {
        this.driver = driver;
        Properties prop = new ReadPropertiesFileUtil().readPropertiesFile("config.properties");
        logSummaryTitle = prop.getProperty("SummaryReportName");
        logSummaryFolderName = prop.getProperty("SummaryFolderName");
        logFolderName = prop.getProperty("logFolderName");

    }

    //Create a HTMl Summary Log
    public void createSummaryHtmlLogFile(int totalTestsToExecute) throws Exception {
        try {

            logSummaryPath = createFolder("/" + logSummaryFolderName)  + "\\" + logSummaryTitle + "-" + getCurrentDateTime().replaceAll("\\-", "") + ".htm";
            summaryLogFile = new File(logSummaryPath);
            summaryLogFile.createNewFile();

            summaryOutput = new BufferedWriter(new FileWriter(summaryLogFile));
            summaryOutput.flush();
            summaryOutput.write("<HTML>");
            summaryOutput.write("<BODY>");
            summaryOutput.write("<TABLE style=font-family:calibri;color:black;font-size:15px; border=\"5\" width=\"90%\" cellpadding=\"4\" cellspacing=\"3\">");
            summaryOutput.write("<tr><td colspan=\"6\">");
            summaryOutput.write("<H2><FONT style=font-family:cambria;color:blue><Center><U>" + logSummaryTitle + "</H2></U></Center></FONT></td></tr>");
            summaryOutput.write("<tr colspan=\"6\" style=font-family:cambria;color:blue>" +
                    "<td WIDTH=\"15%\"><B><H4> EXECUTION START TIME </H4></B></FONT></td>"+
                    "<td WIDTH=\"15%\"><B><H4>" + getCurrentTime() + "</H4></B></td>" );
            summaryOutput.write( "<td WIDTH=\"15%\"> <B><H4> EXECUTION END TIME  </H4></B> </td>" +
                    "<td WIDTH=\"15%\"><B><H4>"+ "" +"</H4></B></td></tr>");
            summaryOutput.write("<tr colspan=\"6\" style=font-family:cambria;color:blue>" +
                    "<td WIDTH=\"10%\"><B><H4> TOTAL TESTS </H4></B></FONT></td>"+
                    "<td WIDTH=\"10%\"><B><H4>" + totalTestsToExecute + "</H4></B></td>" );
            strExecutionSummary = "<td WIDTH=\"10%\"> <B><H4> TEST EXECUTED </H4></B> </td>" +
                    "<td WIDTH=\"10%\"><B><H4>"+ "" +"</H4></B></td></tr>" +
                    "<tr colspan=\"6\" style=font-family:cambria;color:blue>" +
                    "<td WIDTH=\"10%\"><B><H4> TESTS PASSED </H4></B></FONT></td>"+
                    "<td WIDTH=\"10%\"><B><H4>" + "" + "</H4></B></td>" +
                    "<td WIDTH=\"10%\"> <B><H4> TESTS FAILED </H4></B> </td>" +
                    "<td WIDTH=\"10%\"><B><H4>"+ "" +"</H4></B></td></tr>";
            summaryOutput.write(strExecutionSummary);
            summaryOutput.write("<tr style=font-family:cambria;color:brown>" +
                    "<td WIDTH=\"10%\"><B><H4> INDEX </H4></B></FONT></td>"+
                    "<td WIDTH=\"10%\"><B><H4> LOG TIME </H4></B></td>" +
                    "<td WIDTH=\"10%\"> <B><H4> TEST ID </H4></B> </td>" +
                    "<td WIDTH=\"10%\"><B><H4> TEST NAME </H4></B></td>"+
                    "<td WIDTH=\"30%\"><B><H4> REPORT LINK</H4></B></td>" +
                    "<td WIDTH=\"10%\"><B><H4> TEST RESULT </H4></B></td></tr>");
            summaryOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //Create a HTMl Log
    public String createHtmlLogFile(String testName) throws Exception {
        try {

            strLogPath = createFolder("/" + logSummaryFolderName + "/" + logFolderName) + "\\" + testName + "-" + getCurrentDateTime().replaceAll("\\-", "") + ".htm";
            logFile = new File(strLogPath);
            logFile.createNewFile();

            //Generating the Header of the Log
            output = new BufferedWriter(new FileWriter(logFile));
            output.write("<HTML>");
            output.write("<BODY>");
            output.write("<FONT style=font-family:cambria;color:blue;font-size:15px;>");
            output.write("<H1><U>" + testName + "</U></H1>");
            output.write("</FONT>");
            output.close();
            intIncrementer = 1;

            return strLogPath;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    //Dynamically writing to Summary HTML log
    public void writeToSummaryLog(String testID, String testName ,String logLink)
    {
        try
        {
            //Creating a writer to write the output log
            summaryOutput = new BufferedWriter(new FileWriter(summaryLogFile,true));
            index += 1;
            String strUpdatedSummary = "<td WIDTH=\"10%\"> <B><H4> TEST EXECUTED </H4></B> </td>" +
                    "<td WIDTH=\"10%\"><B><H4>"+ index +"</H4></B></td></tr>" +
                    "<tr colspan=\"6\" style=font-family:cambria;color:blue>" +
                    "<td WIDTH=\"10%\"><B><H4> TESTS PASSED </H4></B></FONT></td>"+
                    "<td WIDTH=\"10%\"><B><H4>" + testsPassed + "</H4></B></td>" +
                    "<td WIDTH=\"10%\"> <B><H4> TESTS FAILED </H4></B> </td>" +
                    "<td WIDTH=\"10%\"><B><H4>"+ testsFailed +"</H4></B></td></tr>";
            replaceSummaryContent(strExecutionSummary,strUpdatedSummary);
            strExecutionSummary = strUpdatedSummary;
            summaryOutput.write("<tr style=font-family:cambria>" +
                    "<td WIDTH=\"10%\"><B><H4>" + index + "</H4></B></FONT></td>" +
                    "<td WIDTH=\"10%\"><B><H4>" + getCurrentTime() + "</H4></B></td>" +
                    "<td WIDTH=\"10%\"> <B><H4>" + testID + "</H4></B> </td>" +
                    "<td WIDTH=\"10%\"><B><H4>" + testName + "</H4></B></td>" +
                    "<td WIDTH=\"30%\"><B><H4><a href=" + logLink + ">" + testName + "-LogLink" +"</a></H4></B></td>");
            if(testResult == "passed") {
                summaryOutput.write("<td WIDTH=\"10%\"><B><H4><font style=color:green>" + testResult + "<font></H4></B></td></tr>");
            } else {
                summaryOutput.write("<td WIDTH=\"10%\"><B><H4><font style=color:red>" + testResult + "<font></H4></B></td></tr>");
            }
            summaryOutput.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    //Dynamically writing to HTML log without screen shot
    public static void writeToLog(int intReportCode,String strLogInfo){
        writeToLog(intReportCode,strLogInfo, false);
    }

    //Dynamically writing to HTML log
    public static void writeToLog(int intReportCode,String strLogInfo, boolean takeScreenShot)
    {
        try
        {
            //Taking a Screen-Shot using SELENIUM driver
            output = new BufferedWriter(new FileWriter(logFile,true));
            String strScreenShotPath = new String();
            File FolderName = createFolder("/" + logSummaryFolderName + "/" + "ScreenShot");

            if (takeScreenShot == true) {
                strScreenShotPath = FolderName.toString() + "\\ScreenShot-" + getCurrentDateTime().replaceAll("\\-", "") + ".png";
                //Taking ScreenShot
                File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screenShot, new File(strScreenShotPath));
            }

            //dynamically creating the according to the input
            if (intReportCode == 0 )
            {
                bolTestResult &= true;
                strMsg = "<DIV><FONT style=font-family:calibri;color:black;font-size:15px;><B>"+ intIncrementer +". &nbsp;&nbsp;</B></FONT>"
                        + " <FONT style=font-family:calibri;color:green;font-size:15px;>[MSG_SUCCESS] [" + getCurrentTime() + "]- ";
                strMsg += "  " + strLogInfo;
                if (takeScreenShot == true) {
                    strMsg += "   Verify Screenshot -";
                    strMsg += "<a href=" + strScreenShotPath + ">ScreenShot</a>";
                }
                strMsg += "</FONT><BR></DIV>";
            }
            else if (intReportCode == 1)
            {
                bolTestResult &= false;
                strMsg = "<DIV><FONT style=font-family:calibri;color:black;font-size:15px;><B>"+ intIncrementer +". &nbsp;&nbsp;</B></FONT>"
                        + " <FONT style=font-family:calibri;color:red;font-size:15px;>[MSG_FAILURE] [" + getCurrentTime() +"]- ";
                strMsg += "  " + strLogInfo;
                if (takeScreenShot == true) {
                    strMsg += "   Verify Screenshot -";
                    strMsg += "<a href=" + strScreenShotPath + ">ScreenShot</a>";
                }
                strMsg += "</FONT><BR></DIV>";
            }
            else if (intReportCode == 2)
            {
                bolTestResult &= true;
                strMsg = "<DIV><FONT style=font-family:calibri;color:black;font-size:15px;><B>"+ intIncrementer +". &nbsp;&nbsp;</B></FONT>"
                        + " <FONT style=font-family:calibri;color:orange;font-size:15px;>[MSG_INFO] [" + getCurrentTime() + "]- ";
                strMsg += "  " + strLogInfo;
                if (takeScreenShot == true) {
                    strMsg += "   Verify Screenshot -";
                    strMsg += "<a href=" + strScreenShotPath + ">ScreenShot</a>";
                }
                strMsg += "</FONT><BR></DIV>";
            }
            if (intIncrementer==1)
            {
                strMsg = strMsg.replace("null", "");
            }
            intIncrementer = intIncrementer + 1;
            output.write(strMsg);
            output.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    //Close Summary Log
    public void closeSummaryHtmlLogFile()
    {
        try
        {
            summaryOutput = new BufferedWriter(new FileWriter(summaryLogFile,true));
            summaryOutput.write("</BODY>");
            summaryOutput.write("</HTML>");
            summaryOutput.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    //Close individual summary Log
    public void closeHtmlLogFile()
    {
        try
        {
            output = new BufferedWriter(new FileWriter(logFile,true));
            if (bolTestResult) {
                testsPassed += 1;
                bolTestResult = true;
                testResult = "passed";
            }
            else{
                testsFailed += 1;
                bolTestResult = true;
                testResult = "Failed";
            }
            output.write("</BODY>");
            output.write("</HTML>");
            output.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    //Get Current time
    private static String getCurrentTime(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = sdf.format(date.getTime());
        return formattedTime;
    }

    //Get Current time
    private static String getCurrentDateTime(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy-h-mm-ss-a");
        String formattedTime = sdf.format(date.getTime());
        return formattedTime;
    }
    
    //to create a folder
    private static File createFolder(String strPath) throws Exception {
       try {
           currDir = System.getProperty("user.dir");
           File dirMDMComparison = new File(currDir, strPath);
           if (!dirMDMComparison.exists()) {
               boolean result = dirMDMComparison.mkdir();
               if (result) {
                   System.out.println("DIR not created");
               }
           }
           return dirMDMComparison;
       }
       catch(Exception e){
           System.out.println(e.getMessage());
           throw e;
         }
    }

    //to replace a string in a file and update the same file.
    private void replaceSummaryContent(String strToString, String strUpdatedSummary){
        try {
            String content = FileUtils.readFileToString(new File(logSummaryPath));
            content = content.replaceAll(strToString,strUpdatedSummary);
            FileUtils.writeStringToFile(new File(logSummaryPath), content, "UTF-8");
        } catch (IOException e) {
            //Simple exception handling, replace with what's necessary for your use case!
            throw new RuntimeException("Generating file failed", e);
        }
    }
}
