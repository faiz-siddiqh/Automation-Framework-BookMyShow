package homepageTest;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.TestNG;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.reporters.jq.TestNgXmlPanel;

import core.BaseUtils;
import core.HomePage_Util;
import core.Sports_Util;

public class SportsTestCases {

	public static HomePage_Util homepage = new HomePage_Util();
	public static Sports_Util sportspage = new Sports_Util();

	@BeforeClass
	public void setUp() throws Exception {
		homepage.setUp("Sports");
	}

	/**
	 * Testcase ID=20001 
       TestCase Description:To verify if the user is able to click the city on the
	 * homepage of bookmyshow.com
	 * 
	 * @param method
	 */
	@Test(priority = 0)
	public void checkIfCityElementIsActive_20001(Method method) {
		homepage.launchAndLogin(method.getName());
		Assert.assertTrue(
				BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("homepage-CityList")).isDisplayed());
	}

	/**
	 * Testcase ID=20002 
       TestCase Description: To verify if the user is able to select the required city
	 * from the cities in the dropdown city field.
	 * 
	 * @param method
	 */
	@Test(priority = 1)
	public void selectCityFromTheList_20002(Method method) {
		homepage.launchAndLoginWithCity(method.getName());

	}

	/**
	 * Testcase ID=20003 
	   TestCase Description:To verify if the user is able to click the sports button
	 * on the homepage of bookmyshow.com
	 * 
	 * @param method
	 */
	@Test(priority = 2)
	public void testSportsLinkButton_20003(Method method) {
		homepage.launchAndLoginWithCity(method.getName());

		Assert.assertTrue(
				BaseUtils.isElementPresentAndClickable(
						BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("homepage-Sports-Button"))),
				"Sports button is not present or enabled");

	}

	/**
	 * Testcase ID=20004 
	   TestCase Description: To verify if the user is able to change the city after
	 * navigating to sports page on bookmyshow.com
	 * 
	 * @param method
	 */
	@Test(priority = 3)
	public void testIfUserIsAbleToChangeTheCity_20004(Method method) {
		homepage.launchAndLoginWithCity(method.getName());
		sportspage.navigateToSportsPage();
		BaseUtils.clickAndWait(BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("homepage-City-Btn")));

		Assert.assertTrue(BaseUtils.isElementPresent(BaseUtils.locators.getLocator("homepage-CityList"),
				"List of popular cities are displayed"));
	}

	/**
	 * Testcase ID=20005 
       TestCase Description: To check whether user is able to click on This Weekend
	 * option in the filter section on the sports field.
	 * 
	 * @param method
	 */
	@Test(priority = 4)
	public void testThisWeekendFunctionality_20005(Method method) {
		homepage.launchAndLoginWithCity(method.getName());
		sportspage.navigateToSportsPage();

		Assert.assertTrue(
				BaseUtils.isElementPresentAndClickable(
						BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("Sports-ThisWeekened-Btn"))),
				"Cannot find This Weekend Button");

	}

	/**
	 * Testcase ID=20006 
       TestCase Description: To check if user is able view upcoming sports events after clicking on This weekened in Filters Section
	 * 
	 * @param method
	 */
	@Test
	public void verifyIfTheListOfEventsAreDisplayed_20006(Method method) {
		homepage.launchAndLoginWithCity(method.getName());
		sportspage.filterAllTheEventsForTheWeekend();

		Assert.assertTrue(
				BaseUtils.isElementPresentAndClickable(
						BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("Sports-ThisWeekened-Activated"))),
				"Cannot find This Weekend Sports Activities");

	}

	/**
	 * Testcase ID=20008 
       TestCase Description: To verify if the user is able to scroll down in the sports section to  check all the sports activities.
	 * 
	 * @param method
	 */
	@Test
	public void testScrollDownFunctionality_20008(Method method) {
		homepage.launchAndLoginWithCity(method.getName());
		sportspage.filterAllTheEventsForTheWeekend();
		BaseUtils.scrollToView(200);

		Assert.assertTrue(BaseUtils.isElementPresentAndClickable(
				BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("Sports-ScrollTo-Element"))));

	}

	/**
	 * Testcase ID=20007 
	   TestCase Description: Print the sports activities with lowest charge on top.
	 * 
	 * @param method
	 */
	@Test
	public void getTheListOfAllSportsActivity_20007(Method method) {
		homepage.launchAndLoginWithCity(method.getName());
		sportspage.filterAllTheEventsForTheWeekend();
		sportspage.getAllWeekendEvents();

	}

	@AfterMethod
	public void cleanUp(ITestResult testresult) throws Exception {
		if (testresult.getStatus() == ITestResult.FAILURE) {
			BaseUtils.common.cleanUpOnFailure();
		} else if (testresult.getStatus() == ITestResult.SUCCESS)
			BaseUtils.common.cleanUpOnSuccess();

	}

}
