package com.ezhil.market;

import java.util.List;
import java.util.StringTokenizer;

import org.openqa.selenium.WebElement;

import com.ezhil.market.operations.BrowserOperations;
import com.ezhil.market.operations.ExcelOperations;
import com.ezhil.market.util.ConfigUtil;

public class ProcessRequest {

	public void processRequest() throws Exception {
		// read properties file
		ConfigUtil cu = new ConfigUtil();
		BrowserOperations bo = new BrowserOperations();
		ExcelOperations eo = new ExcelOperations();
		
		cu.initalizeMap();
		System.out.println(cu.getPropMap("browser.driver"));
		// Read CSV data from Internet
		try {
			
			//Initalize and load URL
			bo.initializeDriver(cu.getPropMap("browser.driver"), cu.getPropMap("driver.path"));
			bo.loadUrl(cu.getPropMap("url"), Integer.parseInt(cu.getPropMap("wait.time")));
			
			StringTokenizer st = new StringTokenizer(cu.getPropMap("stock.names"),"|");
			
			while(st.hasMoreTokens()) {
				String stockName = st.nextToken();
				//add symbol and get data
				bo.addSymbol(cu.getPropMap("symbol"), stockName);
				bo.loadSymbol(cu.getPropMap("load.button"));
				List<WebElement> tableData = bo.getDataFrmTable(cu.getPropMap("table.name"));
				
		        //append the row in the excel
				eo.appendRow(tableData, cu.getPropMap("excel.filename"), stockName);
			}
			
			bo.close();
			System.out.println("Google Chrome launched!");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

	}

}
