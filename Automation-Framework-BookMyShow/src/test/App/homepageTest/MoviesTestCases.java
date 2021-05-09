package homepageTest;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import core.BaseUtils;
import core.HomePage_Util;
import core.Movies_Util;

public class MoviesTestCases {
	public static HomePage_Util homepage = new HomePage_Util();
	public static Movies_Util moviespage = new Movies_Util();
	
	@BeforeSuite
	public void InitiateDocker() {
		BaseUtils.GlobalLibrary.triggerDocker("StartUp");
		BaseUtils.GlobalLibrary.scaleUpBrowserInstances();
		
	}
	
	@BeforeClass
	public void setUp() throws Exception {
		homepage.setUp("Movies");
	}

	/**
	 * Testcase ID=20009 
	 * TestCase Description: To verify if user is able to click on the movies from the
	 * bookmyshow website.
	 * 
	 * @param method
	 */
	@Test(priority = 0)
	public void testIsMoviesButtonPresent_20009(Method method) {
		homepage.launchAndLoginWithCity(method.getName());

		Assert.assertTrue(
				BaseUtils.isElementPresentAndClickable(
						BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("homepage-Movies-Button"))),
				"Movies button is not present or enabled");

	}

	/**
	 * Testcase ID=20010 
	   TestCase Description: To verify if user is able to clear the language in filter
	 * section
	 * 
	 * @param method
	 */
	@Test(priority = 1)
	public void checkIfClearButtonIsActive_20010(Method method) {
		homepage.launchAndLoginWithCity(method.getName());

		String languageToBeFiltered = BaseUtils.testData.getTestData("language");
		moviespage.NavigateToMoviesPage();
		moviespage.filterLanguage(languageToBeFiltered);

		BaseUtils.clickAndWait(BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("Movies-filterClear")),
				"Click on Clear button");

	}

	/**
	 * Testcase ID=20011 
	 * TestCase Description:To verify if user is able to click on the languages button
	 * in the filters Section .
	 * 
	 * @param method
	 */
	@Test(priority = 2)
	public void testIfLanguagesButtonIsActive_20011(Method method) {
		homepage.launchAndLoginWithCity(method.getName());
		moviespage.NavigateToMoviesPage();

		Assert.assertTrue(
				BaseUtils.isElementPresentAndClickable(
						BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("Movies-Languages-FilterButton"))),
				"Languages Filter button is not present");

	}

	/**
	 * Testcase ID=20012 
	 * TestCase Description:To Check if all the languages of particular city are
	 * displayed
	 * 
	 * @param method
	 */
	@Test(priority = 3)
	public void testIfLanguagesAreDisplayed_20012(Method method) {
		homepage.launchAndLoginWithCity(method.getName());
		moviespage.NavigateToMoviesPage();

		Assert.assertTrue(BaseUtils.isElementPresent(BaseUtils.locators.getLocator("Movies-AllLanguages"),
				"Languages are not displayed"));

	}

	/**
	 * Testcase ID=20013 
	 * TestCase Description:To print List of all the languages displayed for a
	 * particular city
	 * 
	 * @param method
	 */
	@Test
	public void printListOfAllTheLanguages_20013(Method method) {
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
	
	@AfterSuite
	public void shutdownDocker() {
		BaseUtils.GlobalLibrary.triggerDocker("ShutDown");
	}
	

}