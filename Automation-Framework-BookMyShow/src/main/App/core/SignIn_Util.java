package core;

import org.openqa.selenium.WebElement;

public class SignIn_Util {

	/**
	 * Sign In to the signIn page
	 */
	public void signIn() {
		BaseUtils.waitForTheElementToBeClickable(20,
				BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("homepage-SignIn-Button")));
		BaseUtils.clickAndWait(BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("homepage-SignIn-Button")),
				"Click on Sign In button");

	}

	/**
	 * Navigate to the Get-Started page after clicking to sign In button
	 */
	public void signInWithGoogle() {
		signIn();
		BaseUtils.isElementPresent(BaseUtils.locators.getLocator("SignIn-Get_Started"), "Get Started Page is Opened");
		BaseUtils.isElementPresent(BaseUtils.locators.getLocator("SignIn-ContinueWithGoogle"),
				"Continue with Google link is Present");
		WebElement continueWithGoogleElement = BaseUtils
				.getElementByXpath(BaseUtils.locators.getLocator("SignIn-ContinueWithGoogle"));
		BaseUtils.waitForTheElementToBeClickable(15, continueWithGoogleElement);
		BaseUtils.clickAndWait(continueWithGoogleElement, "Click on \"Continue With Google\" button");

		BaseUtils.switchToHandle();
		BaseUtils.waitForThePageToLoad();

		BaseUtils.waitForTheElementToBePresent(10,
				BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("SignIn-Google")),
				"Sign In With Google Window is opened and cursor is on Email Input field");

	}

	/**
	 * Sign In using the Google with the email passed
	 * 
	 * @param email
	 */
	public void signInWithEmail(String email) {
		signInWithGoogle();
		BaseUtils.clickAndTypeAndWait(BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("SignIn-Email")), email,
				"Enter email in the input field ");
		BaseUtils.clickAndWait(BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("SignIn-Next-btn")),
				"Click And Wait on Next button");
//		BaseUtils.waitForTheElementToBePresent(10,
//				BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("SignIn-Welcome-Page")),
//				"Waiting for Welcome Page to Load");

	}

	public void signInWithEmailByEnteringCaptcha(String email) {
		signInWithGoogle();
		BaseUtils.clickAndTypeAndWait(BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("SignIn-Email")), email,
				"Enter email in the input field ");

		try {
			Thread.sleep(20000);

			// Enter the captcha while sleep
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BaseUtils.clickAndWait(BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("SignIn-Next-btn")),
				"Click And Wait on Next button");
//		BaseUtils.waitForTheElementToBePresent(10,
//				BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("SignIn-Welcome-Page")),
//				"Waiting for Welcome Page to Load");

	}

	/**
	 * return to the homepage by closing the sign In window
	 */
	public void returnToParentWindowAndCloseSignInPage() {
		BaseUtils.returnToParentHandle();
		WebElement closeButton = BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("SignIn-Close-Btn"));
		BaseUtils.waitForTheElementToBeClickable(10, closeButton);
		BaseUtils.clickAndWait(closeButton);

	}

}
