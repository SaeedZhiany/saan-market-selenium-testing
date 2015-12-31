package test;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

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
            System.out.println("user being login");
        // log in
        driver.findElement(By.xpath(".//*[@id='loginOrRegister']/a[1]")).click();
        driver.findElement(By.xpath(".//*[@id='inputUsernameRegister']")).sendKeys("tempuser"); // username
        driver.findElement(By.xpath(".//*[@id='inputPasswordRegister']")).sendKeys("123"); // password
        driver.findElement(By.xpath(".//*[@id='loginModal']/div[2]/form/input")).click();      // submit login
        // search
        driver.findElement(By.xpath(".//*[@id='SearchString']")).sendKeys("galaxy"); // type a search key for example galaxy
        driver.findElement(By.xpath(".//*[@id='stickyNavbar']/div/div/div/div[1]/div/div/form/button")).click();
        // add to cart
        driver.findElement(By.xpath(".//*[@id='isotopeContainer']/div[2]/div/div[1]/div/div/a[2]")).click();
        // save name of the product
        String selectedProductName = driver.findElement(
                By.xpath(".//*[@id='isotopeContainer']/div[2]/div/div[2]/div[2]/h5")).getText();
        // verify cart content
        driver.findElement(By.xpath(".//*[@id='cartContainer']")).click();
        List<WebElement> products = driver.findElement(
                By.xpath(".//*[@id='cartContainer']/div[2]")).findElements(By.className("item-in-cart"));
        // verify count of products in cart
        Assert.assertEquals(1,products.size());
        // verify name of product in list that must be equal with the selected product
        String curProductName = products.get(0).findElement(
                By.xpath(".//*[@id='cartContainer']/div[2]/div[1]/div[2]/strong/a")).getText();
        Assert.assertEquals(curProductName, selectedProductName);

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
