package core;

import java.io.IOException;

import org.xml.sax.SAXException;

/**
 * 
 * @author Faiz Ahmed Siddiqh
 *
 */
public class HomePage_Util {

	/**
	 * To SetUp the necessary drivers,files before the execution of
	 * methods[APPLICABLE FOR ALL THE MODULES]
	 * 
	 * @param className
	 * @throws SAXException
	 * @throws IOException
	 */
	public void setUp(String className) throws SAXException, IOException {
		BaseUtils.common.setClassName(className);
		BaseUtils.testData.setTestFile("BookMyShow");
		BaseUtils.setUp();
	}

	/**
	 * To Launch and login to the BaseUrl or Homepage of the App
	 * 
	 * @param testName-Name of the current testmethod
	 */
	public void launchAndLogin(String testName) {
		BaseUtils.common.setMethodName(testName);
		BaseUtils.common.setExtentTest(testName);
		BaseUtils.setUpDriver();
		BaseUtils.common.appLogin();
		BaseUtils.handlePersonalisedUpdatesAlert();

	}

	/**
	 * To Launch And Login to the baseURL and Select The personalised City
	 * 
	 * @param testName
	 */
	public void launchAndLoginWithCity(String testName) {
		BaseUtils.common.setMethodName(testName);
		BaseUtils.common.setExtentTest(testName);
		BaseUtils.setUpDriver();
		BaseUtils.common.appLogin();
		BaseUtils.handlePersonalisedUpdatesAlert();
		BaseUtils.selectCity();

	}

}