package practise.AppiumFramework;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static java.time.Duration.ofSeconds;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import practise.AppiumFramework.capability;

public class TC_01 extends capability{
	 
	@BeforeTest
	public void killAllProcess() throws IOException, InterruptedException
	{
		//i am ensuring before any of my test starts if the appium server is up and running
		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Thread.sleep(5000);
	}
	
	
	
	@Test
	public void tc1() throws InterruptedException, IOException
	{
		
		//This is starting my appium server
		service = startServer();
		//what is the benifit of using implicity wait
		AndroidDriver<AndroidElement> driver = HybridCapability(apppackage, appactivity, deviceName, chromeexcutable);
		//is to implement implicitwait 
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
				//Edit field to pass username
				driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Niharika");
				//can i use this//this is my radiobutton
				driver.findElement(By.xpath("//*[@text='Female']")).click();	
				//i have to select the country
				driver.findElement(By.id("android:id/text1")).click();
				driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Australia\"))");
				driver.findElement(By.xpath("//*[@text='Australia']")).click();
				String CountryName = driver.findElement(By.xpath("//*[@text='Australia']")).getText();
				//i am using asserts to verify it has selected india//if assert pass then click on let's shop element 
				Assert.assertEquals(CountryName, "Australia");
				driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
				//i want to click on add to cart for the first two products 
				driver.findElements(By.xpath("//*[@text='ADD TO CART']")).get(0).click();
				driver.findElements(By.xpath("//*[@text='ADD TO CART']")).get(0).click();
				driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
				//i want to check the added product name is same as the checkout names(assignment) 
				Thread.sleep(3000);
				String amount1 = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice")).get(0).getText();
				amount1 = amount1.substring(1);
				Double amount1value = Double.parseDouble(amount1);
				
				String amount2 = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice")).get(1).getText();
				amount2 = amount2.substring(1);
				Double amount2value = Double.parseDouble(amount2);
				
				Double Totalamount = amount1value + amount2value;
				System.out.println(Totalamount);
				
				String Total = driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();
				Total = Total.substring(1);
				Double Totalfinalvalue = Double.parseDouble(Total);
				
				
				Assert.assertEquals(Totalfinalvalue, Totalamount);
				//on the checkbox i want you to use tap option(touch action)
				//i want to longpress on the element called terms and condition
				//i wan to cick on visit to the website
				WebElement checkbox = driver.findElement(By.className("android.widget.CheckBox"));

				TouchAction t = new TouchAction(driver);

				 t.tap(tapOptions().withElement(element(checkbox))).perform();

				 AndroidElement ele = driver.findElement(By.xpath("//*[@text='Please read our terms of conditions']"));

				t.longPress(longPressOptions().withElement(element(ele)).withDuration(ofSeconds(3))).release().perform();	 

				System.out.println(driver.findElement(By.id("android:id/message")).getText());	 

				driver.findElement(By.xpath("//*[@text='CLOSE']")).click();	 

				 driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();
				 Thread.sleep(8000);
				 
				 // i am now in the web page through my Native app
				 
				 Set<String> contextNames = driver.getContextHandles();
				 for (String contextName : contextNames) {
				     System.out.println(contextName); //prints out something like NATIVE_APP \n WEBVIEW_1
				 }
				 driver.context("WEBVIEW_com.androidsample.generalstore");
				 Thread.sleep(5000);
				 driver.findElement(By.xpath("//*[@name='q']")).sendKeys("swiggy");
				 driver.findElement(By.xpath("//*[@name='q']")).sendKeys(Keys.RETURN);
				 driver.pressKey(new KeyEvent(AndroidKey.BACK));
				 //or 
				// driver.navigate().back();
				 //switch back to my Native app
				 driver.context("NATIVE_APP");
				 //this is stooping my appium server
				service.stop();
	}



}
