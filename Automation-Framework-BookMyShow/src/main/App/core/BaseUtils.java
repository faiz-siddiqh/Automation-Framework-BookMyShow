package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseUtils {

	/**
	 * @author Faiz Ahmed Siddiqh
	 */

	private static Properties properties = new Properties();
	private static WebDriver driver;
	private static ExtentReports extentreport;
	private static ExtentTest test;
	public static String className;
	public static String methodName;

	public static class ProjectProperties {

		public static void loadPropertiesFile(String filePath) {
			try {
				properties = new Properties();
				properties.load(new FileReader(System.getProperty("user.dir") + filePath));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		public static String readProjectVariables(String propertyName) {
			properties = new Properties();
			loadPropertiesFile("\\ExecutionFiles\\HomePage\\module.properties");
			return properties.getProperty(propertyName);

		}

		public static String readFromGlobalConfigFile(String propertyName) {
			loadPropertiesFile("//CommonFiles//config.properties");
			return properties.getProperty(propertyName);

		}

	}

	public static class common {

		public static void appLogin() {
			try {
				String url = ProjectProperties.readFromGlobalConfigFile("URL");
				logInfo("Fetching URl");
				launch(url);
			} catch (Exception e) {
				logInfo(e.getMessage());
				cleanUp();
			}

		}

		public static void launch(String url) {
			try {
				if (driver == null) {
					BaseUtils.setUpDriver();
				}

				driver.get(url);
				logInfo("Navigating to " + url);
				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				waitForThePageToLoad();

			} catch (Exception e) {
				logInfo(e.getMessage());
				cleanUp();
			}
		}

		public static void setClassName(String className) {
			BaseUtils.className = className;
		}

		public static void setMethodName(String methodName) {
			BaseUtils.methodName = methodName;
		}

		public static void getExtentReportInstance() {

			String path = System.getProperty("user.dir") + "//TestResults//" + className;
			File resultsFile = new File(path);
			if (resultsFile.exists() && resultsFile.isDirectory()) {
				resultsFile.delete();
			}
			resultsFile.mkdir();
			extentreport = new ExtentReports(path + "//" + "ExtentReport.html", false);
			extentreport.addSystemInfo("Selenium Version", "3.141.59").addSystemInfo("Platform", "Windows");
			// extent.addSystemInfo("Selenium Version",
			// "3.141.59").addSystemInfo("Platform", System.getProperty("os.name"));

		}

		public static void cleanUpOnFailure() {
			common.logInfo("This Test Step failed, Capturing Screenshot.");
			String screenshotPath = BaseUtils.Screenshot.takeScreenshot();

			test.log(LogStatus.FAIL, "Test Failed", screenshotPath);
			driver.quit();
			BaseUtils.common.getExtentReport().endTest(test);
			BaseUtils.common.getExtentReport().flush();

		}

		public static void cleanUp() {
			common.logInfo("This Test Step failed, Capturing Screenshot.");
			String screenshotPath = BaseUtils.Screenshot.takeScreenshot();

			test.log(LogStatus.FAIL, "Test Failed", screenshotPath);
			driver.quit();
			BaseUtils.common.getExtentReport().endTest(test);
			BaseUtils.common.getExtentReport().flush();

		}

		public static void cleanUpOnSuccess() {
			String screenshotPath = BaseUtils.Screenshot.takeScreenshot();
			driver.quit();
			test.log(LogStatus.PASS, "Test Passed", screenshotPath);
			BaseUtils.common.getExtentReport().endTest(test);
			BaseUtils.common.getExtentReport().flush();
		}

		public static void cleanUpOnSkip() {
			String screenshotPath = BaseUtils.Screenshot.takeScreenshot();
			BaseUtils.common.getDriver().quit();
			test.log(LogStatus.SKIP, "Test Skipped", screenshotPath);
			BaseUtils.common.getExtentReport().endTest(test);
			BaseUtils.common.getExtentReport().flush();
		}

		public static ExtentReports getExtentReport() {
			return extentreport;
		}

		public static WebDriver getDriver() {
			return driver;
		}

		public static void setExtentTest(String testName) {
			test = extentreport.startTest(testName);
			test.log(LogStatus.INFO, "Setting log report");
			test.log(LogStatus.INFO, "Starting Test-" + testName);

		}

		public static ExtentTest getExtentTest() {
			return test;
		}

		public static void logInfo(String log) {
			test.log(LogStatus.INFO, log);
		}

	}

	public static class locators {
		private static Document doc;
		private static XPath xpath;
		private static XPathExpression expr;

		public static void setUpLocatorsFile(String moduleLocatorFileName) {

			String locatorsFileLocation = ProjectProperties.readProjectVariables(moduleLocatorFileName);
			File file = new File(System.getProperty("user.dir") + locatorsFileLocation);

			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			try {
				DocumentBuilder builder = documentFactory.newDocumentBuilder();
				doc = builder.parse(file);
				XPathFactory xpathFactory = XPathFactory.newInstance();
				xpath = xpathFactory.newXPath();
			} catch (ParserConfigurationException e) {
				System.out.println("Xml file parsing failed");
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		public static String getLocator(String locatorname) {
			String locator = null;
			try {
				expr = xpath.compile("//element[@name='" + locatorname + "']/@*");
				NodeList result = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
				common.logInfo("Get Locator for " + locatorname);
				Attr attr = (Attr) result.item(0);
				locator = attr.getNodeValue();
				common.logInfo("Get Locator successful- " + locator);
				// return attr.getTextContent();

			} catch (XPathExpressionException e) {
				// System.out.println("check the locatorname input value");
				common.logInfo("Get Locator unsuccessful- " + locator);
				// common.logInfo(e.getMessage());
				BaseUtils.common.cleanUp();
				e.printStackTrace();
			}

			return locator;
		}

	}

	public static class testData {
		private static XSSFWorkbook ExcelWBook;
		private static XSSFSheet ExcelWSheet;

		public static void setTestFile(String fileName) {
			try {
				// Open the Excel file
				FileInputStream ExcelFile = new FileInputStream(
						System.getProperty("user.dir") + "//ExecutionFiles//Run//" + fileName + ".xlsx");

				// Access the excel data sheet
				ExcelWBook = new XSSFWorkbook(ExcelFile);
				ExcelWSheet = ExcelWBook.getSheet("TestData");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public static String getTestData(String testVariable) {
			try {
				for (org.apache.poi.ss.usermodel.Row eachRow : ExcelWSheet) {

					XSSFCell Cell = (XSSFCell) eachRow.getCell(3);
					XSSFCell variableCell = (XSSFCell) eachRow.getCell(4);
					XSSFCell variableValueCell = (XSSFCell) eachRow.getCell(5);

					if (Cell.getStringCellValue().equals(methodName)
							&& variableCell.getStringCellValue().equals(testVariable)) {
						common.logInfo("LookUp for testdata -" + testVariable);

						if (variableValueCell.getCellType() == CellType.STRING) {
							common.logInfo("LookUp for testdata " + testVariable + " successful.value = "
									+ variableValueCell.getStringCellValue());

							return variableValueCell.getStringCellValue();

						} else if (variableValueCell.getCellType() == CellType.NUMERIC) {

							common.logInfo("LookUp for testdata " + testVariable + " successful.value = "
									+ String.valueOf(variableValueCell.getNumericCellValue()));
							return String.valueOf(variableValueCell.getNumericCellValue());
						}
					}

				}
				common.logInfo("LookUp for testdata failed.Testdata not found");

			} catch (Exception e) {
				common.logInfo("LookUp for testdata failed.Testdata not found");
				common.logInfo(e.getMessage());
				common.cleanUp();
			}
			return null;

		}

	}

	public static class Screenshot {

		public static String takeScreenshot() {

			String path = System.getProperty("user.dir") + "//Screenshots";
			String fullPath = path + "//" + new SimpleDateFormat("yyyy-MM-dd hh-mm-ss'.tsv'").format(new Date())
					+ ".png";
			File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			test.log(LogStatus.INFO, "capturing Screenshot");
			try {
				FileUtils.copyFile(sourceFile, new File(fullPath));
			} catch (Exception e) {
				test.log(LogStatus.WARNING, e.getMessage());
			}
			return test.addScreenCapture(fullPath);

		}

		public static String getRandomString(int length) {
			StringBuilder sb = new StringBuilder();
			String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

			for (int i = 0; i < str.length(); i++) {
				int index = (int) (Math.random() * str.length());
				sb.append(str.charAt(index));
			}

			return sb.toString();
		}
	}

	public static void setUp() throws SAXException, IOException {

		// setting up an extent report
		common.getExtentReportInstance();
		/*
		 * Setting up Locators File
		 */
		locators.setUpLocatorsFile("locatorsfile");

	}

	public static void setUpDriver() {
		// Load the properties file using this method which contains baseURL and
		// WebDriverType
		String driverLocation;
		common.logInfo("Setting Up WebDriver");
		String driverName = ProjectProperties.readFromGlobalConfigFile("driver");
		// String baseURL = projectDetails.getProperty("baseURL");
		common.logInfo("WebDriver chosen =" + driverName);

		if (driverName.equalsIgnoreCase("ChromeDriver")) {
			// Set System Property to instantiate ChromeDriver with the path of
			// chromedriver.

			driverLocation = ProjectProperties.readFromGlobalConfigFile("chromedriver");
			if (System.getProperty("os.name").startsWith("Windows")) {// the path varies for windows and Mac
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + driverLocation);
			} else {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + driverLocation.replaceAll(".exe", ""));
			}
			// Set Options using for chrome using the below commented line

			// ChromeOptions options = new ChromeOptions();
			driver = new ChromeDriver();
			common.logInfo("Launching Chrome");

		} else if (driverName.equalsIgnoreCase("FireFox")) {
			// Set System Property to instantiate ChromeDriver with the path of
			// firefoxdriver.
			driverLocation = ProjectProperties.readFromGlobalConfigFile("firefoxdriver");

			if (System.getProperty("os.name").startsWith("Windows")) {// the path varies for windows and Mac
				System.setProperty("webdriver.firefox.driver", System.getProperty("user.dir") + driverLocation);
			} else {
				System.setProperty("webdriver.firefox.driver",
						System.getProperty("user.dir") + driverLocation.replaceAll(".exe", ""));
			}

			// Set Options using for Firefox
			FirefoxProfile profile = new FirefoxProfile();
			FirefoxOptions options = new FirefoxOptions();
			options.setProfile(profile);

			driver = new FirefoxDriver();
			common.logInfo("Launching Firefox");
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		common.logInfo("Maximizing the window");

	}

	public static void waitForThePageToLoad() {
		try {
//		WebDriverWait wait = new WebDriverWait(driver, 25);
//
//		wait.until(new ExpectedCondition<Boolean>() {
//			public Boolean apply(WebDriver driver) {
//				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
//			}
//		});
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			common.logInfo("WebPage took more time to Load.");
			common.cleanUp();
		}
	}

	public static void handlePersonalisedUpdatesAlert() {
		try {
			common.logInfo("Checking if the \"Personalised Updates\" alert is present");
			WebElement alertELement = getElementByXpath(locators.getLocator("homepage-personalisedUpdates-alert"));
			waitForTheElementToBePresent(12, alertELement, "Waiting for the Personalised Updates to Pop-Up");
			if (alertELement.isDisplayed()) {
				common.logInfo("\"Personalised Updates\"alert is present");
				clickAndWait(getElementByXpath(locators.getLocator("homepage-NotNow-button")),
						"Click on Not Now Button");
			}

		} catch (Exception e) {
			common.logInfo("\"Personalised Updates\"alert is not present");
			common.cleanUp();

		}
	}

	public static void selectCity() {
		try {
			common.logInfo("Selecting City from the displayed List of Popular Cities");
			String ourCity = ProjectProperties.readProjectVariables("City");
			common.logInfo("Our city is -" + ourCity);
			common.logInfo("Selecting City from the displayed List of Popular Cities");
			String locator = locators.getLocator("homepage-SelectCity").replaceAll("CITY", ourCity);

			clickAndWait(getElementByXpath(locator), "Click on " + ourCity);

		} catch (Exception e) {
			common.logInfo("City is not present");
			common.cleanUp();
		}

	}

	public static void captureScreenshot() {
		String screenshotPath = BaseUtils.Screenshot.takeScreenshot();
		test.log(LogStatus.INFO, "Capturing ScreenShot", screenshotPath);

	}

	public static WebElement getElement(String locator, String type) {
		WebElement element = null;
		type = type.toLowerCase();
		common.logInfo("Lookup for Element-" + locator);
		try {
			if (type.equals("id")) {
				element = driver.findElement(By.id(locator));
				common.logInfo("Lookup for Element successful");
			} else if (type.equals("xpath")) {
				element = driver.findElement(By.xpath(locator));
				common.logInfo("Lookup for Element successful");
			} else if (type.equals("cssselector")) {
				element = driver.findElement(By.cssSelector(locator));
				common.logInfo("Lookup for Element successful");
			} else if (type.equals("name")) {
				element = driver.findElement(By.name(locator));
				common.logInfo("Lookup for Element successful");
			} else if (type.equals("classname")) {
				element = driver.findElement(By.className(locator));
				common.logInfo("Lookup for Element successful");
			} else if (type.equals("tagname")) {
				element = driver.findElement(By.tagName(locator));
				common.logInfo("Lookup for Element successful");
			} else if (type.equals("linktext")) {
				element = driver.findElement(By.linkText(locator));
				common.logInfo("Element found -" + element);
			}
		} catch (Exception e) {
			common.logInfo("Element not found -" + locator);
			common.logInfo("Locator not supported or check type");
			// common.logInfo(e.getMessage());
			common.cleanUp();
		}
		common.logInfo("Lookup for Element successful");
		return element;
	}

	public static WebElement getElementByXpath(String locator) {
		WebElement element = null;
		try {
			common.logInfo("Lookup for Element-" + locator);
			element = driver.findElement(By.xpath(locator));
			common.logInfo("Lookup for Element successful");

		} catch (Exception e) {
			common.logInfo("Element not found -" + locator);
			common.logInfo(e.getMessage());
			common.cleanUp();
		}

		return element;
	}

	public static boolean isElementPresent(String locator, String message) {
		try {
			if (getElementByXpath(locator).isDisplayed()) {
				common.logInfo(message);
				return true;
			}
		} catch (Exception e) {
			common.logInfo(e.getMessage());
			common.cleanUp();
		}

		return false;
	}

	public static boolean isElementEnabled(String locator) {
		try {
			if (getElementByXpath(locator).isEnabled()) {
				common.logInfo("Element is Enabled-" + locator);
				return true;
			}
		} catch (Exception e) {
			common.logInfo(e.getMessage());
			common.cleanUp();
		}

		return false;
	}

	public static void waitForTheElementToBePresent(long timeOutInSeconds, WebElement element, String message) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			wait.until(ExpectedConditions.visibilityOf(element));
			common.logInfo(message);
		} catch (Exception e) {
			common.logInfo("Element is not present");
			common.logInfo(e.getMessage());
			common.cleanUp();
		}
	}

	public static void waitForTheElementToBePresent(long timeOutInSeconds, String locator, String message) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locators.getLocator(locator))));
			common.logInfo(message);
		} catch (Exception e) {
			common.logInfo("Element is not present");
			common.logInfo(e.getMessage());
			common.cleanUp();
		}
	}

	public static void waitForTheElementToBeClickable(long timeOutInSeconds, WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			common.logInfo("Waiting for the element to be clickable");
		} catch (Exception e) {
			common.logInfo("Element not clickable OR available");
			common.logInfo(e.getMessage());
			// common.cleanUp();
		}
	}

	public static List<WebElement> getElementsByTagname(WebElement element, String tagname) {
		try {
			common.logInfo("Lookup for Elements by tagName -" + tagname);
			return element.findElements(By.tagName(tagname));
		} catch (Exception e) {
			common.logInfo("Elements not found -check locator and type");
			// common.logInfo(e.getMessage());
			common.cleanUp();
		}

		return null;

	}

	public static List<WebElement> getElements(String locator, String type) {

		type = type.toLowerCase();
		List<WebElement> list = new ArrayList<WebElement>();
		try {
			if (type.equals("id")) {
				list = driver.findElements(By.id(locator));

			} else if (type.equals("xpath")) {
				list = driver.findElements(By.xpath(locator));
			} else if (type.equals("cssselector")) {
				list = driver.findElements(By.cssSelector(locator));
			} else if (type.equals("name")) {
				list = driver.findElements(By.name(locator));
			} else if (type.equals("classname")) {
				list = driver.findElements(By.className(locator));
			} else if (type.equals("tagname")) {
				list = driver.findElements(By.tagName(locator));
			} else if (type.equals("linktext")) {
				list = driver.findElements(By.linkText(locator));
			}
		} catch (Exception e) {
			common.logInfo("Locator not supported or check type");
			common.logInfo(e.getMessage());
			common.cleanUp();
		}

		common.logInfo("Lookup for Elements successful");
		return list;
	}

	public static void findElementAndClick(List<WebElement> list, String requiredText) {
		try {
			for (WebElement eachElement : list) {
				if (eachElement.getText().contains(requiredText)) {
					clickAndWait(eachElement);
					common.logInfo("clicked on " + requiredText);
					break;
				}
			}
		} catch (Exception e) {
			common.logInfo(e.getMessage());
//			driver.quit();
//			extentreport.endTest(test);
//			extentreport.flush();
			common.cleanUp();
		}

	}

	public static void clickAndWait(WebElement element, String message) {
		try {
			waitForTheElementToBeClickable(10, element);
			element.click();
			Thread.sleep(4000);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			common.logInfo(message);

		} catch (Exception e) {
			common.logInfo("Element not clickable");
			common.logInfo(e.getMessage());
			common.cleanUp();
		}

	}

	public static void clickAndWait(WebElement element) {
		try {
			element.click();
			Thread.sleep(3000);
			common.logInfo("Click and Wait");
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			common.logInfo(e.getMessage());
//			driver.quit();
//			extentreport.endTest(test);
//			extentreport.flush();
			common.cleanUp();
		}

	}

	public static void clickAndTypeAndWait(WebElement element, String keysToSend, String message) {
		try {
			clickAndWait(element);
			element.sendKeys(keysToSend);
			common.logInfo(message);
			Thread.sleep(3000);
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		} catch (Exception e) {
			common.logInfo(e.getMessage());
			common.cleanUp();
		}
	}

	public static void dragAndDrop(WebElement fromElement, WebElement toElement) {
		try {
			Actions action = new Actions(driver);
			// 1)
			action.dragAndDrop(fromElement, toElement).build().perform();
		} catch (Exception e) {
			common.logInfo(e.getMessage());
			common.cleanUp();
		}

		/*
		 * 2)
		 * action.clickAndHold(fromElement).moveToElement(toElement).build().perform();
		 */
	}

	public static void slider(WebElement sliderElement, int xOffset, int yOffset) {
		try {
			Actions action = new Actions(driver);

			action.dragAndDropBy(sliderElement, xOffset, yOffset).perform();

		} catch (Exception e) {
			common.logInfo(e.getMessage());
			common.cleanUp();
		}
	}

	public static void selectFromDropdown(WebElement element, String textToBeSelected) {
		try {
			Select select = new Select(element);
			select.selectByVisibleText(textToBeSelected);
			common.logInfo(textToBeSelected + " selected");

		} catch (Exception e) {
			common.logInfo(e.getMessage());
			common.cleanUp();
		}

	}

	public static void switchToHandle() {

		String parentHandle = driver.getWindowHandle();

		// Get all Handles

		Set<String> handles = driver.getWindowHandles();

		// Switching between handles

		for (String handle : handles) {

			if (!handle.equals(parentHandle)) {
				common.logInfo("Switching to another window");
				driver.switchTo().window(handle);
				break;
			}
		}

	}

	public static List<WebElement> clickableLinks() {

		List<WebElement> linksToClick = new ArrayList<WebElement>();
		List<WebElement> elements = driver.findElements(By.tagName("a"));
		elements.addAll(driver.findElements(By.tagName("img")));

		for (WebElement e : elements) {
			if (e.getAttribute("href") != null) {
				linksToClick.add(e);
			}
		}

		return linksToClick;
	}

	public static void returnToParentHandle() {
		String currentHandle = driver.getWindowHandle();
		String parentHandle = null;
		// Get all Handles

		Set<String> handles = driver.getWindowHandles();

		// Switching between handles

		for (String handle : handles) {

			if (handle.equals(currentHandle)) {
				common.logInfo("Closing the child window");
				driver.switchTo().window(handle).close();
			} else {
				parentHandle = handle;
			}
		}

		driver.switchTo().window(parentHandle);
		common.logInfo("Switching to parent window");

	}

}