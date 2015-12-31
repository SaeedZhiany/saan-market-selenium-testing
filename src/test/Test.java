package test;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

/**
 * Created by SAEED on 2015-12-31
 * for project saan-market-selenium-testing .
 */

public class Test {

    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private final StringBuffer verificationErrors = new StringBuffer();

    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://www.binaryhexconverter.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public void uiTest() throws Exception {
        System.out.println("hello");
        driver.get(baseUrl + "/binary-to-decimal-converter");
        driver.findElement(By.id("tabin")).clear();
        driver.findElement(By.id("tabin")).sendKeys("100");
        driver.findElement(By.cssSelector("input[type=\"button\"]")).click();
        System.out.println("The result that fetch from browser is : "+driver.findElement(By.id("resulttxt")).getAttribute("value"));

    }

    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }

    public static void main(String[] args) throws Exception {
        Test t = new Test();
        t.setUp();
        t.uiTest();
        t.tearDown();
    }
}
