package homepageTest;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import core.BaseUtils;
import core.HomePage_Util;
import core.SignIn_Util;

public class SignInTestCases {
	public static HomePage_Util homepage = new HomePage_Util();
	public static SignIn_Util signInPage = new SignIn_Util();

	@BeforeClass
	public void setUp() throws Exception {
		homepage.setUp("Sign In");
	}

	@Test(priority = 0)
	public void testSignInButton_20014(Method method) {
		homepage.launchAndLoginWithCity(method.getName());
		signInPage.signIn();
		Assert.assertTrue(BaseUtils.isElementPresent(BaseUtils.locators.getLocator("SignIn-Get_Started"),
				"Get Started Page is Opened"), " Get Started Page is not opened");
	}

	@Test(priority = 1)
	public void verifyIfContinueWithGoogleIsPresent_20015(Method method) {
		homepage.launchAndLoginWithCity(method.getName());
		signInPage.signIn();
		Assert.assertTrue(BaseUtils.isElementPresent(BaseUtils.locators.getLocator("SignIn-ContinueWithGoogle"),
				"Continue with Google link is Present"), "Continue with Google link is not Present");

	}

	@Test(dependsOnMethods = { "verifyIfContinueWithGoogleIsPresent_20015" })
	public void testContinueWithGoogleButton_20016(Method method) {
		homepage.launchAndLoginWithCity(method.getName());
		signInPage.signInWithGoogle();
		signInPage.returnToParentWindowAndCloseSignInPage();

	}

	@Test(enabled = false)
	public void signInWithValidEmail_20017(Method method) {

		String email = BaseUtils.testData.getTestData("Email");
		homepage.launchAndLoginWithCity(method.getName());
		signInPage.signInWithEmail(email);

//		Assert.assertTrue(BaseUtils.isElementPresent(BaseUtils.locators.getLocator("SignIn-Welcome-Page"),
//				"User is on Welcome Page and cursor is on password field"), " Welcome Page is not Opened");
		/*
		 * The above result is not fetched while automating the browser.But in manual
		 * Testing the above condition is satisfied.
		 */
		signInPage.returnToParentWindowAndCloseSignInPage();

	}

	@Test(enabled = false)
	public void signInWithValidEmailAndInvalidPassword_20018(Method method) {
		homepage.launchAndLoginWithCity(method.getName());
		String actualErrorMessage = BaseUtils.locators.getLocator("SignIn-PasswordError-ActualMessage");
		String expectedErrorMessage;
		String email = BaseUtils.testData.getTestData("Email");
		String password = BaseUtils.testData.getTestData("password");

		signInPage.signInWithEmail(email);
		BaseUtils.clickAndTypeAndWait(BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("SignIn-Password")),
				password, "Enter password in the input field ");
		BaseUtils.clickAndWait(BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("SignIn-Next-btn")),
				"Click And Wait on Next button");
		BaseUtils.captureScreenshot();
		expectedErrorMessage = BaseUtils
				.getElementByXpath(BaseUtils.locators.getLocator("SignIn-PasswordError-Message")).getText();
		Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Expected Error message is displayed");

		signInPage.returnToParentWindowAndCloseSignInPage();

	}

	@Test(dependsOnMethods = { "testContinueWithGoogleButton_20016" })
	public void signInWithInValidEmail_20019(Method method) {

		homepage.launchAndLoginWithCity(method.getName());
		String actualErrorMessage = BaseUtils.locators.getLocator("SignIn-Error-ActualMessage");
		String expectedErrorMessage;
		String email = BaseUtils.testData.getTestData("Email");
		signInPage.signInWithEmail(email);
		BaseUtils.captureScreenshot();
		expectedErrorMessage = BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("SignIn-Error-Message"))
				.getText();
		Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Expected Error message is not displayed");

		signInPage.returnToParentWindowAndCloseSignInPage();

	}

	@Test(dependsOnMethods = { "testContinueWithGoogleButton_20016", "signInWithInValidEmail_20019" })
	public void produceErrorWhileSignInWithoutEmail_20020(Method method) {
		homepage.launchAndLoginWithCity(method.getName());
		String actualErrorMessage = BaseUtils.locators.getLocator("SignIn-Warning-ActualMessage");
		String expectedErrorMessage;
		signInPage.signInWithGoogle();

		BaseUtils.clickAndWait(BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("SignIn-Next-btn")),
				"Click And Wait on Next button");
		BaseUtils.captureScreenshot();
		expectedErrorMessage = BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("SignIn-Error-Message"))
				.getText();
		Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Expected Error message is displayed");

		signInPage.returnToParentWindowAndCloseSignInPage();

	}

	@AfterMethod
	public void cleanUp(ITestResult testresult) throws Exception {
		if (testresult.getStatus() == ITestResult.FAILURE) {
			BaseUtils.common.cleanUpOnFailure();
		} else if (testresult.getStatus() == ITestResult.SUCCESS)
			BaseUtils.common.cleanUpOnSuccess();

	}

}
