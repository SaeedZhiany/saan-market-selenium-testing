package test;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

/**
 * Created by SAEED on 2015-12-31
 * for project saan-market-selenium-testing .
 */

public class Test {

    private WebDriver driver;
    private String baseUrl;

    private final StringBuffer verificationErrors = new StringBuffer();

    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:25557";
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    public void uiTest() throws Exception {
            System.out.println("beginning of test");
        driver.get(baseUrl + "/Home/Index");
        // log in
        System.out.println("user being login");
        driver.findElement(By.xpath(".//*[@id='loginOrRegister']/a[1]")).click();
        driver.findElement(By.xpath(".//*[@id='inputUsernameRegister']")).sendKeys("tempuser"); // username
        driver.findElement(By.xpath(".//*[@id='inputPasswordRegister']")).sendKeys("123"); // password
        driver.findElement(By.xpath(".//*[@id='loginModal']/div[2]/form/input")).click();      // submit login

        Assert.assertEquals("tempuser", driver.findElement(By.xpath(".//*[@id='loginOrRegister']/a[1]")).getText());
        System.out.println("user login successfully");
        // search
        System.out.println("user being search");
        driver.findElement(By.xpath(".//*[@id='SearchString']")).sendKeys("galaxy"); // type a search key for example galaxy
        driver.findElement(By.xpath(".//*[@id='stickyNavbar']/div/div/div/div[1]/div/div/form/button")).click();
        // add to cart
        System.out.println("search page is opening");
        System.out.println("adding galaxy s6 to cart");

        Actions action = new Actions(driver);
        WebElement mouseOverOnProduct = driver.findElement(By.xpath(".//*[@id='isotopeContainer']/div[2]/div/div[1]/div/img"));
        action.moveToElement(mouseOverOnProduct).build().perform();
        driver.findElement(By.xpath(".//*[@id='isotopeContainer']/div[2]/div/div[1]/div/div/a[2]")).click();

        // save name of the product
        System.out.println("verifying cart");
        String selectedProductName = driver.findElement(
                By.xpath(".//*[@id='isotopeContainer']/div[2]/div/div[2]/div[2]/h5")).getText();

        // verify cart content
        action.moveToElement(driver.findElement(By.xpath(".//*[@id='cartContainer']"))).build().perform();
        //driver.findElement(By.xpath(".//*[@id='cartContainer']")).click();
        List<WebElement> products = driver.findElement(
                By.xpath(".//*[@id='cartContainer']/div[2]")).findElements(By.className("item-in-cart"));
        // verify count of products in cart
        Assert.assertEquals(1,products.size());
        // verify name of product in list that must be equal with the selected product

        String curProductName = driver.findElement(
                By.xpath(".//*[@id='cartContainer']/div[2]/div[1]/div[2]/strong/a")).getText();
        System.out.println("product in list is: " + curProductName);
        Assert.assertEquals(curProductName, selectedProductName);
        System.out.println("test is complete successfully");

    }

    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    public static void main(String[] args) throws Exception {
        Test t = new Test();
        t.setUp();
        t.uiTest();
        t.tearDown();
    }
}
