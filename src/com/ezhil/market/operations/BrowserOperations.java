package com.ezhil.market.operations;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class BrowserOperations {

	private WebDriver driver;
	Actions builder;

	public void initializeDriver(String driverName, String path) {
		System.setProperty(driverName, path);
		driver = new ChromeDriver();
		builder = new Actions(driver);
	}

	public void loadUrl(String url, int waitTime) {
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
	}

	public void addSymbol(String eleSymbol,String stockName) {
		WebElement symbol = driver.findElement(By.name(eleSymbol));
		symbol.clear();
		Actions seriesOfActions = builder.moveToElement(symbol).click().sendKeys(symbol, stockName);
		seriesOfActions.perform();
		Select dropdown = new Select(driver.findElement(By.id("series")));
		dropdown.selectByVisibleText("EQ");
	}

	public void loadSymbol(String eleSubmit) {
		WebElement getData =driver.findElement(By.className(eleSubmit));
        Actions seriesOfAction = builder.click(getData);
        seriesOfAction.perform();
	}

	public List<WebElement> getDataFrmTable(String tableName){
		return driver.findElements(By.xpath(tableName));
	}
	
	public void close() {
		builder = null;
		driver.close();
	}
	
}
