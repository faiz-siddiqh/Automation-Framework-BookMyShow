package core;

import java.io.FileOutputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;

public class Sports_Util {

	public void navigateToSportsPage() {

		BaseUtils.clickAndWait(BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("homepage-Sports-Button")),
				"Click And Wait on Sports Link-Btn");
		BaseUtils.waitForThePageToLoad();

	}

	public void filterAllTheEventsForTheWeekend() {
		navigateToSportsPage();
		BaseUtils.clickAndWait(BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("Sports-ThisWeekened-Btn")),
				"Click on this weekend filter btn");
	}

	public void getAllWeekendEvents() {
		List<WebElement> list = null;
		XSSFWorkbook excelBook = BaseUtils.testData.ExcelWBook;
		XSSFSheet sheet = excelBook.getSheet("Results");
		int count = 1;
		list = BaseUtils.getElements(BaseUtils.locators.getLocator("Sports-AllWeekendEvents"), "xpath");

		// Implement clicking and getting values in list
		Map<String, Integer> listOfEvents = new TreeMap<String, Integer>();
		System.out.println("Name of the Event             Date of the Event               Price of the Event");
		for (int i = 0; i < list.size(); i++) {
			BaseUtils.scrollToView(200);
			BaseUtils.waitForTheElementToBeClickable(25, list.get(i));
			BaseUtils.clickAndWait(list.get(i));

			BaseUtils.waitForThePageToLoad();

			String eventName = BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("Sports-NameOfTheEvent"))
					.getText();
			System.out.println(eventName);
			String dateOfTheEvent = BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("Sports-DateOfTheEvent"))
					.getText();
			System.out.println(dateOfTheEvent);

			String key = eventName + "#" + dateOfTheEvent;
			String priceOfTheEvent = BaseUtils
					.getElementByXpath(BaseUtils.locators.getLocator("Sports-PriceOfTheEvent")).getText()
					.replaceAll("₹ ", "");
			Integer price = Integer.parseInt(priceOfTheEvent);

			listOfEvents.put(key, price);
			System.out.println(price);

			BaseUtils.common.getDriver().navigate().back();
			BaseUtils.waitForThePageToLoad();

		}

		Map<String, Integer> sortedList = entriesSortedByPrice(listOfEvents);

		for (Map.Entry<String, Integer> pair : sortedList.entrySet()) {
			String[] name = pair.getKey().split("#");
			XSSFRow row = sheet.createRow(count);
			String nameOfTheEvent = name[0];
			String dateOfTheEvent = name[0];
			Integer priceOfTheEvent = pair.getValue();
			row.createCell(0).setCellValue(nameOfTheEvent);
			row.createCell(1).setCellValue(dateOfTheEvent);
			row.createCell(3).setCellValue(priceOfTheEvent);
			System.out.println(nameOfTheEvent + "            " + dateOfTheEvent + "         " + priceOfTheEvent);
			count++;

		}

		try {
			FileOutputStream fos = new FileOutputStream(BaseUtils.testData.filePath);
			excelBook.write(fos);
			fos.flush();
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
			BaseUtils.common.logInfo("Unable to find Testdata file");
		}

	}

	public <K, V extends Comparable<V>> Map<K, V> entriesSortedByPrice(final Map<K, V> map) {
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

}
