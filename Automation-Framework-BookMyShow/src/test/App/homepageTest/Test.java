package homepageTest;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import core.BaseUtils;

public class Test {
//private static	WebDriver driver;
//	public static void main(String[] args) throws Exception {
//		 driver = new ChromeDriver();
//		driver.get("https://in.bookmyshow.com/");
//		driver.manage().window().maximize();
//		Thread.sleep(10000);
////		driver.findElement(By.id("wzrk-cancel")).click();
//		handlePersonalisedUpdatesAlert();
//	}
//
//	
//	
//	public static void handlePersonalisedUpdatesAlert() {
//		//common.logInfo("Checking if the \"Personalised Updates\" alert is present");
//		System.out.println("Checking if the \"Personalised Updates\" alert is present");
//		if(driver.findElement(By.xpath("//div[@class='wzrk-alert-heading']")).isDisplayed()) {
//			System.out.println("\"Personalised Updates\"alert is present");
//			driver.findElement(By.xpath("//button[@id='wzrk-cancel']")).click();
//			System.out.println("Clicking on Not Now button");
//			return;
//			
//		}
//		System.out.println("\"Personalised Updates\"alert is not present");
//	}

	public static void main(String[] args) throws Exception {

//		TreeMap<String, Integer> listOfEvents = new TreeMap<String, Integer>();
//
//		listOfEvents.put("One # five", 10);
//		listOfEvents.put("two #adnjada", 1);
//		listOfEvents.put("three#adavv", 30);
//		listOfEvents.put("four#adadad", 2);
//		listOfEvents.put("fivea#adjadja", 5);
//		listOfEvents.put("six#kldskddj", 5);
//
//		Map<String,Integer> sorted=entriesSortedByPrice(listOfEvents);
//		
//		for(Map.Entry<String, Integer> pair :sorted.entrySet()) {
//			String[] name =pair.getKey().split("#");
//			System.out.println(name[0]);
//			System.out.println(name[1]);
//			System.out.println(pair.getValue());
//			
//		}

		WebDriver driver = new FirefoxDriver();
		driver.get("https://in.bookmyshow.com/explore/sports-bengaluru?daygroups=this-weekend");
		Thread.sleep(20000);
		List<WebElement> list = null;
		list = driver.findElements(By.xpath("//div[@class='commonStyles__HorizontalFlexBox-sc-133848s-3 style__CardBody-dv5ht7-1 eFGyIh']"));
		System.out.println(list.size());
		for (WebElement eachElement : list) {
			System.out.println(eachElement);
			
			
			eachElement.click();
			driver.navigate().back();
			Thread.sleep(7000);
		}
	}

	public static <K, V extends Comparable<V>> Map<K, V> entriesSortedByPrice(final Map<K, V> map) {
		// Static Method with return type Map and
		// extending comparator class which compares values
		// associated with two keys
		Comparator<K> valueComparator = new Comparator<K>() {

			// return comparison results of values of
			// two keys
			public int compare(K k1, K k2) {
				int comp = map.get(k1).compareTo(map.get(k2));
				if (comp == 0)
					return 1;
				else
					return comp;
			}

		};

		// SortedMap created using the comparator
		Map<K, V> sorted = new TreeMap<K, V>(valueComparator);

		sorted.putAll(map);

		return sorted;
	}

//	public static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> SortedByPrice(Map<K, V> map) {
//		SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<Map.Entry<K, V>>(new Comparator<Map.Entry<K, V>>() {
//			@Override
//			public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
//				int res = e1.getValue().compareTo(e2.getValue());
//				return res != 0 ? res : 1;
//			}
//		});
//		sortedEntries.addAll(map.entrySet());
//		return sortedEntries;
//	}
}
