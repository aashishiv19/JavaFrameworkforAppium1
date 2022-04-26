package practise.AppiumFramework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class capability {
	
	protected static String apppackage;
	protected static String appactivity;
	protected static String deviceName;
	protected static String platformName;
	protected static String chromeexcutable;
	public AppiumDriverLocalService service;
	public AppiumDriverLocalService startServer()
	{
		boolean flag = checkifserverisRunning(4723);
		if(!flag)
		{
//	service = AppiumDriverLocalService.buildDefaultService();
//	service.start();
			
		service = AppiumDriverLocalService//starting appium

					.buildService(new AppiumServiceBuilder()

				.usingDriverExecutable(new File("C://Program Files//nodejs//node.exe"))

					.withAppiumJS(new File("C://Users//HP//AppData//Roaming//npm//node_modules//appium//build//lib//main.js"))

			.withIPAddress("127.0.0.1").usingPort(4723));

				service.start();
		}
		return service;
	}
//	
	public static boolean checkifserverisRunning(int port)// created  A method appium is running plz go and close(new cocket programming)
	{
		boolean isServerRunning=false;
		ServerSocket serversocket;
		try{
			serversocket = new ServerSocket(port);
			serversocket.close();
		}
		catch(IOException e)
		{
			isServerRunning = true;
		}
		finally {
			serversocket=null;
		}
		return isServerRunning;
	}
//	
	public static void startEmulator() throws IOException, InterruptedException
	{
		Runtime.getRuntime().exec(System.getProperty("user.dir")+"//src//main//resources//emulator.bat");
		Thread.sleep(14000);
	}
//simply creted main method or capability
	public static AndroidDriver<AndroidElement> HybridCapability(String apppackage, String appactivity, String deviceName, String platformName,String chromeexcutable) throws IOException, InterruptedException {
		
		FileReader fis =new FileReader(System.getProperty("user.dir")+"//src//main//java//global.properties");
		Properties pro = new Properties();
		pro.load(fis);//it will load global propery
		 apppackage = pro.getProperty("apppackage");// we are calling and storing variables
		appactivity = pro.getProperty("appactivity");
		platformName = pro.getProperty("platformName");
		deviceName = pro.getProperty("deviceName");
		//this is something which i can pass at the run time 
		//String device = System.getProperty("deviceName");
if(deviceName.contains("Shivani1"))
	{
startEmulator();
	}
		chromeexcutable = pro.getProperty("chromeexcutable");
		DesiredCapabilities cap = new DesiredCapabilities();
		//if you are using emulator--> provide devica name as emulator
		//real device then you can provide your name
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName );
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
		cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, apppackage);
		cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appactivity);
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE, chromeexcutable );
		AndroidDriver<AndroidElement>driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"),cap);
		return driver;
		

	}

}
