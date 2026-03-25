package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ISuiteListener, ITestListener{

//	public ExtentSparkReporter spark; //Sets UI Properties
//	public ExtentReports extent; //Populates Common info on reports
//	public ExtentTest test; //Creates test entries and update test result
	
	 // ThreadLocal to hold separate report instances for each parallel <test>
//	private static ThreadLocal<ExtentSparkReporter> sparkLocal = new ThreadLocal<>();
//    private static ThreadLocal<ExtentReports> extentLocal = new ThreadLocal<>();
	private static ExtentSparkReporter spark; 
	private static ExtentReports extent; 
    private static ThreadLocal<ExtentTest> testLocal = new ThreadLocal<>();
	
	public static String repoName;
	 
    public static ExtentTest getTest() {
        return testLocal.get();
    }
    
    public static void createNewTest(String methodName) {
        // This now uses the thread's unique 'extent' engine
        ExtentTest test = extent.createTest(methodName);
        testLocal.set(test);
    }
    
    public static void initReport(ISuite suite) {
//    	SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
//		Date dt = new Date();
//		String formatedStamp = timeStamp.format(dt);
		
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); //Generating Time Stamp string
		
		repoName = suite.getName() + "-" + timeStamp + ".html"; //Generating name of the report
		
        //Create a NEW engine and reporter for THIS specific thread/test-tag
		spark = new ExtentSparkReporter(".\\reports\\" + repoName);
		
		extent = new ExtentReports();
		extent.attachReporter(spark);
    }
    
    public static void flushReport() {
		 try {
	            if (extent != null) {
	                extent.flush();
	            }
	        } finally {
	            // Always remove to prevent memory leaks in parallel execution
	            testLocal.remove();
	        }

		//Below code will automatically open the generated report
		String path = ".\\reports\\" + repoName;
		File finalRepo = new File(path);
		try {
			Desktop.getDesktop().browse(finalRepo.toURI());;
		}
		catch(IOException e) {
			e.printStackTrace();
		}
    }
	
	public void onStart(ISuite suite) {
		
//		SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
//		Date dt = new Date();
//		String formatedStamp = timeStamp.format(dt);
		
//		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); //Generating Time Stamp string
//		
//		repoName = context.getName() + "-" + timeStamp + ".html"; //Generating name of the report
//		
//        //Create a NEW engine and reporter for THIS specific thread/test-tag
//		ExtentSparkReporter spark = new ExtentSparkReporter(".\\reports\\" + repoName);
//		
//		ExtentReports extent = new ExtentReports();
//		extent.attachReporter(spark);
//		
//		sparkLocal.set(spark);
//		extentLocal.set(extent);
		
		initReport(suite);
		
		spark.config().setDocumentTitle("API Test Report");
		spark.config().setReportName("REST Assured Report");
		spark.config().setTheme(Theme.DARK);
				
		extent.setSystemInfo("Application Name", "Pet Store");
		extent.setSystemInfo("Module", "User Management");
		extent.setSystemInfo("Sub Module", "Customer");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Enviornment", "QA");
		extent.setSystemInfo("OS", suite.getXmlSuite().getParameter("os")); //Reading Parameter "os" from testng xml
	}
	
	public void onStart(ITestContext context) {
		createNewTest(context.getName()); //Create new Test entry in report with Test Name = Class name in XML
	}
	
	public void onTestSuccess(ITestResult result) {
		ExtentTest node = getTest().createNode(result.getMethod().getMethodName());
		getTest().assignCategory(result.getMethod().getGroups()); //Get group name of test method
		node.log(Status.PASS, result.getName() + " is Executed Successfully!"); //Get method name
	}
	
	public void onTestFailure(ITestResult result) {
		ExtentTest node = getTest().createNode(result.getMethod().getMethodName());
		getTest().assignCategory(result.getMethod().getGroups());
		
		node.log(Status.FAIL, result.getName() + " is Failed");
		node.log(Status.INFO, result.getThrowable().getMessage());
		
//		try {
//			getTest().addScreenCaptureFromPath(new BaseTestCase().takeScreenshot(result.getName()));
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
	}
	
	public void onTestSkipped(ITestResult result) {
		ExtentTest node = getTest().createNode(result.getMethod().getMethodName());
		getTest().assignCategory(result.getMethod().getGroups());
		node.log(Status.SKIP, result.getName() + " is Skipped");
		node.log(Status.INFO, result.getThrowable().getMessage());
	}
	
	public void onFinish(ISuite suite) {
		flushReport();
	}
}
