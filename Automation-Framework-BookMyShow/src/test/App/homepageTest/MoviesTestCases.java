package homepageTest;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import core.BaseUtils;
import core.HomePage_Util;
import core.Movies_Util;

public class MoviesTestCases {
	public static HomePage_Util homepage = new HomePage_Util();
	public static Movies_Util moviespage = new Movies_Util();

	@BeforeClass
	public void setUp() throws Exception {
		homepage.setUp("Movies");
	}

	@Test(priority = 0)
	public void testIsMoviesButtonPresent_20010(Method method) {
		homepage.launchAndLoginWithCity(method.getName());

		Assert.assertTrue(
				BaseUtils.isElementPresentAndClickable(
						BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("homepage-Movies-Button"))),
				"Movies button is not present or enabled");

	}

	@Test(priority = 1)
	public void checkIfMoviesButtonIsActive_20011(Method method) {
		homepage.launchAndLoginWithCity(method.getName());
		moviespage.NavigateToMoviesPage();

		Assert.assertTrue(BaseUtils.isElementPresent(BaseUtils.locators.getLocator("Movies-Verifiable-Element"),
				"User is not on Movies Page"));

	}

	@Test(priority = 1)
	public void testIfLanguagesButtonIsActive_20012(Method method) {
		homepage.launchAndLoginWithCity(method.getName());
		moviespage.NavigateToMoviesPage();

		Assert.assertTrue(
				BaseUtils.isElementPresentAndClickable(
						BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("Movies-Languages-FilterButton"))),
				"Languages Filter button is not present");

	}

	@Test(priority = 2)
	public void testIfLanguagesAreDisplayed_20014(Method method) {
		homepage.launchAndLoginWithCity(method.getName());
		moviespage.NavigateToMoviesPage();

		Assert.assertTrue(BaseUtils.isElementPresent(BaseUtils.locators.getLocator("Movies-AllLanguages"),
				"Languages are not displayed"));

	}

	@Test(priority = 3)
	public void printListOfAllTheLanguages_20015(Method method) {
		homepage.launchAndLoginWithCity(method.getName());
		moviespage.printAllTheLanguages();

	}

	@AfterMethod
	public void cleanUp(ITestResult testresult) throws Exception {
		if (testresult.getStatus() == ITestResult.FAILURE) {
			BaseUtils.common.cleanUpOnFailure();
		} else if (testresult.getStatus() == ITestResult.SUCCESS)
			BaseUtils.common.cleanUpOnSuccess();

	}

}