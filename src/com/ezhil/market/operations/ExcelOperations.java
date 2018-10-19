package com.ezhil.market.operations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ExcelOperations {

	public HSSFSheet getSheetNameByName(String sheetName, HSSFWorkbook marketBook) throws Exception {
		HSSFSheet workSheet = null;

		for (int i = 0; i < marketBook.getNumberOfSheets(); i++) {
			workSheet = marketBook.getSheetAt(i);
			if (workSheet.getSheetName().equals(sheetName)) {
				return workSheet;
			}
		}
		if(workSheet == null) {
			workSheet = marketBook.createSheet(sheetName);
		}
		return workSheet;
	}

	public void appendRow(List<WebElement> tableData, String fileName, String sheetName) {
		try {
			FileInputStream myxls = new FileInputStream(fileName);
			HSSFWorkbook marketSheet = new HSSFWorkbook(myxls);
			HSSFSheet worksheet = getSheetNameByName(sheetName, marketSheet);
			int a = worksheet.getLastRowNum();
			System.out.println(a);
			Row lastRow = worksheet.getRow(a);
			Row row = worksheet.createRow(++a);

			int row_num, col_num;
			row_num = 1;
			double colValue = 0.0;
			for (WebElement trElement : tableData) {
				List<WebElement> td_collection = trElement.findElements(By.xpath("td"));
				System.out.println("NUMBER OF COLUMNS=" + td_collection.size());
				col_num = 0;
				for (WebElement tdElement : td_collection) {
					// System.out.println("row # "+row_num+", col # "+col_num+
					// "text="+tdElement.getText());
					if (col_num == 2 && tdElement.getText().equals(lastRow.getCell(col_num).toString())) {
						System.out.println(sheetName + " already updated for " + tdElement.getText());
						return;
					}
					if (col_num > 2) {
						colValue = Double.parseDouble(tdElement.getText().replaceAll(",", ""));
						row.createCell(col_num).setCellValue(colValue);
					} else {
						row.createCell(col_num).setCellValue(tdElement.getText());
					}

					col_num++;
				}
				row_num++;
			}

			myxls.close();
			FileOutputStream output_file = new FileOutputStream(new File(fileName));
			// write changes
			marketSheet.write(output_file);
			output_file.close();
			System.out.println(fileName + " is successfully written");
		} catch (Exception e) {
			System.out.println("Not able to write the file");
			e.printStackTrace();
			System.exit(0);
		}
	}
}
