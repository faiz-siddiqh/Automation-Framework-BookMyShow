package core;

import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;

public class Movies_Util {

	/**
	 * Navigate to the Movies Page
	 */
	public void NavigateToMoviesPage() {
		WebElement element = BaseUtils.getElementByXpath(BaseUtils.locators.getLocator("homepage-Movies-Button"));
		BaseUtils.waitForTheElementToBeClickable(10, element);
		BaseUtils.clickAndWait(element);

	}

	/**
	 * Select a specific language in the Filter Section
	 * 
	 * @param languageToBeFiltered-This is read from the testdata file
	 */
	public void filterLanguage(String languageToBeFiltered) {

		List<WebElement> listOfMovies = BaseUtils.getElements(BaseUtils.locators.getLocator("Movies-filterLanguage"),
				"xpath");

		for (WebElement eachMovie : listOfMovies) {

			String language = eachMovie.getText();
			if (language.equalsIgnoreCase(languageToBeFiltered)) {
				BaseUtils.clickAndWait(eachMovie);
				return;
			}

		}

	}

	/**
	 * Printing all the languages displayed in the movies page to the console as
	 * well as to the Results sheet in excel file
	 */
	public void printAllTheLanguages() {

		XSSFWorkbook excelBook = BaseUtils.testData.getExcelWorkBook();
		String filePath = BaseUtils.testData.filePath;
		XSSFSheet sheet = excelBook.getSheet("Results");
		NavigateToMoviesPage();
		List<WebElement> languagesList = BaseUtils.getElements(BaseUtils.locators.getLocator("Movies-getLanguages"),
				"xpath");
		BaseUtils.common.logInfo("Printing languages on Console and inside the testdata excel file");
		int count = 25; // Becoz we are writing from line 26
		for (WebElement eachElement : languagesList) {
			String language = eachElement.getText();
			System.out.println(language);
			XSSFRow row = sheet.createRow(count);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(language);
			count++;

		}
		try {
			// Writing the data to the excel file
			FileOutputStream fos = new FileOutputStream(filePath);
			excelBook.write(fos);
			fos.flush();
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
			BaseUtils.common.logInfo("Unable to find Testdata file");
		}

	}

}
