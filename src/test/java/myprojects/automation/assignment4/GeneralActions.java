package myprojects.automation.assignment4;


import myprojects.automation.assignment4.model.ProductData;
import myprojects.automation.assignment4.utils.Properties;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import java.util.List;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private static WebDriver driver;
    private static WebDriverWait wait;

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    /**
     * Logs in to Admin Panel.
     * @param login
     * @param password
     */
    public static void login(String login, String password) {
        // TODO implement logging in to Admin Panel
        driver.navigate().to(Properties.getBaseAdminUrl());
        driver.findElement(By.id("email")).sendKeys(login);
        driver.findElement(By.id("passwd")).sendKeys(password);
        driver.findElement(By.name("submitLogin")).click();
       // throw new UnsupportedOperationException();
    }

    public static void createProduct(ProductData newProduct) {
        // TODO implement product creation scenario
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click()",
                driver.findElement(By.xpath("//*[contains(@class, 'title')][contains(text(), 'товары')]")));
        waitForContentLoad();

       driver.findElement(By.id("page-header-desc-configuration-add")).click();
        waitForContentLoad();

        driver.findElement(By.id("form_step1_name_1")).sendKeys(newProduct.getName());
        driver.findElement(By.id("form_step1_qty_0_shortcut")).sendKeys(Keys.chord(Keys.CONTROL, "a"),newProduct.getQty().toString());
        driver.findElement(By.id("form_step1_price_shortcut")).sendKeys(Keys.chord(Keys.CONTROL, "a"),newProduct.getPrice());
        //

       // if (!driver.findElement(By.id("form_step1_active")).isSelected())
      //  {
       //     executor.executeScript("arguments[0].click()",driver.findElement(By.id("form_step1_active")));
       // }
        driver.findElement(By.className("switch-input ")).click();


        waitForContentLoad();
        driver.findElement(By.className("growl-close")).click();
        executor.executeScript("arguments[0].click()",
                driver.findElement(By.xpath("//*[contains(text(),'Сохранить')]")));

        //waitForContentLoad();

//        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("growl-close"))));
    //    executor.executeScript("arguments[0].click()",driver.findElement(By.className("growl-close")));

       // throw new UnsupportedOperationException();
    }

    public static void checkProduct(ProductData productData){
        driver.navigate().to(Properties.getBaseUrl());
        driver.findElement(By.xpath("//a[@href='http://prestashop-automation.qatestlab.com.ua/ru/2-home']")).click();
        GeneralActions.waitForContentLoad();

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("product-miniature"))));
        List<WebElement> products = driver.findElements(By.xpath("//*[@id=\"js-product-list\"]/div/article/div/div/h1/a"));
        System.out.println("Products count: "+ products.size());

      //  Assert.assertTrue(driver.findElements(By.xpath("//*[@id=\"js-product-list\"]/div/article/div/div/h1/a and [contains(text(),productData.getName())]")).size()>0,
       //         "My product has not been found!");
        for (WebElement product : products) {
            if (product.getText().equals(productData.getName())) {
                product.click();
            }
        }
        waitForContentLoad();

        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div/nav/ol/li[2]/a/span")).getText(), productData.getName(), "Names do not match");
        Assert.assertEquals(driver.findElement(By.className("current-price")).getText().replaceAll("[^\\d.,]", ""), productData.getPrice().toString(), "Prices do not match");
        Assert.assertEquals(driver.findElement(By.className("product-quantities")).getText().replaceAll("[^\\d]", ""), productData.getQty().toString(), "Qtys do not match");

    }


    /**
     * Waits until page loader disappears from the page
     */
    public static void waitForContentLoad() {
        // TODO implement generic method to wait until page content is loaded

        // wait.until(...);
        wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }
}
