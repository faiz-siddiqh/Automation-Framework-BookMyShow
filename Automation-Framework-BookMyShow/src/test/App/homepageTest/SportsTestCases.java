package homepageTest;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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

	@Test(priority = 0)
	public void checkIfCityElementIsActive_20001(Method method) {
		homepage.launchAndLogin(method.getName());
		Assert.assertTrue(
				BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("homepage-CityList")).isDisplayed());
	}

	@Test(priority = 1)
	public void selectCityFromTheList_20002(Method method) {
		homepage.launchAndLoginWithCity(method.getName());

	}

	@Test(priority = 2)
	public void testSportsLinkButton_20003(Method method) {
		homepage.launchAndLoginWithCity(method.getName());

		Assert.assertTrue(
				BaseUtils.isElementPresentAndClickable(
						BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("homepage-Sports-Button"))),
				"Sports button is not present or enabled");

	}

//	@Test(priority = 3)
//	public void testIfUserIsUnableToClickOnSportsButton_20004(Method method) {
//		homepage.launchAndLoginWithCity(method.getName());
//
//		Assert.assertTrue(BaseUtils.isElementPresentAndClickable(
//				BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("homepage-Sports-Button1"))));
//
//	}

	@Test(priority = 4)
	public void testThisWeekendFunctionality_20005(Method method) {
		homepage.launchAndLoginWithCity(method.getName());
		sportspage.navigateToSportsPage();

		Assert.assertTrue(
				BaseUtils.isElementPresentAndClickable(
						BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("Sports-ThisWeekened-Btn"))),
				"Cannot find This Weekend Button");

	}

	@Test
	public void verifyIfTheListOfEventsAreDisplayed_20007(Method method) {
		homepage.launchAndLoginWithCity(method.getName());
		sportspage.filterAllTheEventsForTheWeekend();

		Assert.assertTrue(
				BaseUtils.isElementPresentAndClickable(
						BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("Sports-ThisWeekened-Activated"))),
				"Cannot find This Weekend Sports Activities");

	}

	@Test
	public void testScrollDownFunctionality_20009(Method method) {
		homepage.launchAndLoginWithCity(method.getName());
		sportspage.filterAllTheEventsForTheWeekend();
		BaseUtils.scrollToView(200);

		Assert.assertTrue(BaseUtils.isElementPresentAndClickable(
				BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("Sports-ScrollTo-Element"))));

	}

//	public void getTheListOfAllSportsActivity_20008(Method method) {
//		homepage.launchAndLoginWithCity(method.getName());
//		sportspage.filterAllTheEventsForTheWeekend();
//		
//		
//
//	}

	@AfterMethod
	public void cleanUp(ITestResult testresult) throws Exception {
		if (testresult.getStatus() == ITestResult.FAILURE) {
			BaseUtils.common.cleanUpOnFailure();
		} else if (testresult.getStatus() == ITestResult.SUCCESS)
			BaseUtils.common.cleanUpOnSuccess();

	}

}
