package core;

import java.util.List;

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

	public List<WebElement> getAllWeekendEvents() {
		List<WebElement> list = null;
		list = BaseUtils.getElements(BaseUtils.locators.getLocator("Sports-AllWeekendEvents"), "xpath");

		// Implement clicking and getting values in list

		return list;
	}

}
