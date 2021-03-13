package homepageTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class Test {
private static	WebDriver driver;
	public static void main(String[] args) throws Exception {
		 driver = new ChromeDriver();
		driver.get("https://in.bookmyshow.com/");
		driver.manage().window().maximize();
		Thread.sleep(10000);
//		driver.findElement(By.id("wzrk-cancel")).click();
		handlePersonalisedUpdatesAlert();
	}

	
	
	public static void handlePersonalisedUpdatesAlert() {
		//common.logInfo("Checking if the \"Personalised Updates\" alert is present");
		System.out.println("Checking if the \"Personalised Updates\" alert is present");
		if(driver.findElement(By.xpath("//div[@class='wzrk-alert-heading']")).isDisplayed()) {
			System.out.println("\"Personalised Updates\"alert is present");
			driver.findElement(By.xpath("//button[@id='wzrk-cancel']")).click();
			System.out.println("Clicking on Not Now button");
			return;
			
		}
		System.out.println("\"Personalised Updates\"alert is not present");
	}
}
