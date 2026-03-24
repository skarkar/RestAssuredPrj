package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener{

//	public ExtentSparkReporter spark; //Sets UI Properties
//	public ExtentReports extent; //Populates Common info on reports
//	public ExtentTest test; //Creates test entries and update test result
	
	 // ThreadLocal to hold separate report instances for each parallel <test>
	private static ThreadLocal<ExtentSparkReporter> sparkLocal = new ThreadLocal<>();
    private static ThreadLocal<ExtentReports> extentLocal = new ThreadLocal<>();
    private static ThreadLocal<ExtentTest> testLocal = new ThreadLocal<>();
	
//	public String repoName;
	
    public static ExtentSparkReporter getSpark() {
        return sparkLocal.get();
    }
    
    public static ExtentReports getExtent() {
        return extentLocal.get();
    }
    
    public static ExtentTest getTest() {
        return testLocal.get();
    }
    
    public static void createNewTest(String methodName) {
        // This now uses the thread's unique 'extent' engine
        ExtentTest test = extentLocal.get().createTest(methodName);
        testLocal.set(test);
    }
    
    public static void initReport(ITestContext context) {
//    	SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
//		Date dt = new Date();
//		String formatedStamp = timeStamp.format(dt);
		
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); //Generating Time Stamp string
		
		String repoName = context.getName() + "-" + timeStamp + ".html"; //Generating name of the report
		
        //Create a NEW engine and reporter for THIS specific thread/test-tag
		ExtentSparkReporter spark = new ExtentSparkReporter(".\\reports\\" + repoName);
		
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(spark);
		
		sparkLocal.set(spark);
		extentLocal.set(extent);
    }
    
    public static void flushReport() {
		 try {
	            if (getExtent() != null) {
	                getExtent().flush();
	            }
	        } finally {
	            // Always remove to prevent memory leaks in parallel execution
	            extentLocal.remove();
	            sparkLocal.remove();
	            testLocal.remove();
	        }

		//Below code will automatically open the generated report
//		String path = ".\\reports\\" + repoName;
//		File finalRepo = new File(path);
//		try {
//			Desktop.getDesktop().browse(finalRepo.toURI());;
//		}
//		catch(IOException e) {
//			e.printStackTrace();
//		}
    }
	
	public void onStart(ITestContext context) {
		
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
		
		initReport(context);
		
		getSpark().config().setDocumentTitle("API Test Report");
		getSpark().config().setReportName("REST Assured Report");
		getSpark().config().setTheme(Theme.DARK);
				
		getExtent().setSystemInfo("Application Name", "Pet Store");
		getExtent().setSystemInfo("Module", "User Management");
		getExtent().setSystemInfo("Sub Module", "Customer");
		getExtent().setSystemInfo("User Name", System.getProperty("user.name"));
		getExtent().setSystemInfo("Enviornment", "QA");
		getExtent().setSystemInfo("OS", context.getCurrentXmlTest().getParameter("os")); //Reading Parameter "os" from testng xml
		getExtent().setSystemInfo("Browser", context.getCurrentXmlTest().getParameter("browser")); //Reading Parameter "browser" from testng xml
		List<String> groups = context.getCurrentXmlTest().getIncludedGroups(); //Reading included groups from testng xml
		if(!groups.isEmpty())
			getExtent().setSystemInfo("Groups", groups.toString()); //Displaying included groups from testng xml
		System.out.println("In onStart() for " + context.getCurrentXmlTest().getParameter("browser"));
	}
	
	public void onTestSuccess(ITestResult result) {
		createNewTest(result.getTestClass().getName()); //Create new Test entry in report with Test Name = Class name in XML
		getTest().assignCategory(result.getMethod().getGroups()); //Get group name of test method
		getTest().log(Status.PASS, result.getName() + " is Executed Successfully!"); //Get method name
	}
	
	public void onTestFailure(ITestResult result) {
		createNewTest(result.getTestClass().getName()); //Create new Test entry in report
		getTest().assignCategory(result.getMethod().getGroups());
		
		getTest().log(Status.FAIL, result.getName() + " is Failed");
		getTest().log(Status.INFO, result.getThrowable().getMessage());
		
//		try {
//			getTest().addScreenCaptureFromPath(new BaseTestCase().takeScreenshot(result.getName()));
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
	}
	
	public void onTestSkipped(ITestResult result) {
		createNewTest(result.getTestClass().getName());; //Create new Test entry in report
		getTest().assignCategory(result.getMethod().getGroups());
		getTest().log(Status.SKIP, result.getName() + " is Skipped");
		getTest().log(Status.INFO, result.getThrowable().getMessage());
	}
	
	public void onFinish(ITestContext context) {
		flushReport();
	}
}
