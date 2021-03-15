package core;

import java.io.IOException;

import org.xml.sax.SAXException;

/**
 * 
 * @author Faiz Ahmed Siddiqh
 *
 */
public class HomePage_Util {

	public void setUp(String className) throws SAXException, IOException {
		BaseUtils.common.setClassName(className);
		BaseUtils.testData.setTestFile("BookMyShow");
		BaseUtils.setUp();
	}

	public void launchAndLogin(String testName) {
		BaseUtils.common.setMethodName(testName);
		BaseUtils.common.setExtentTest(testName);
		BaseUtils.setUpDriver();
		BaseUtils.common.appLogin();
		BaseUtils.handlePersonalisedUpdatesAlert();

	}

	public void launchAndLoginWithCity(String testName) {
		BaseUtils.common.setMethodName(testName);
		BaseUtils.common.setExtentTest(testName);
		BaseUtils.setUpDriver();
		BaseUtils.common.appLogin();
		BaseUtils.handlePersonalisedUpdatesAlert();
		BaseUtils.selectCity();

	}

}