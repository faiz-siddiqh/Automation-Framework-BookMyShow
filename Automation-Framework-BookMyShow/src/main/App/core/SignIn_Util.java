package core;

import org.openqa.selenium.WebElement;

public class SignIn_Util {

	public void signIn() {
		BaseUtils.waitForTheElementToBeClickable(20,
				BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("homepage-SignIn-Button")));
		BaseUtils.clickAndWait(BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("homepage-SignIn-Button")),
				"Click on Sign In button");

	}

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

	public void returnToParentWindowAndCloseSignInPage() {
		BaseUtils.returnToParentHandle();
		WebElement closeButton = BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("SignIn-Close-Btn"));
		BaseUtils.waitForTheElementToBeClickable(10, closeButton);
		BaseUtils.clickAndWait(closeButton);

	}

}
