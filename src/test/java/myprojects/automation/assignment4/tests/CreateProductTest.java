package myprojects.automation.assignment4.tests;

import myprojects.automation.assignment4.BaseTest;
import myprojects.automation.assignment4.GeneralActions;
import myprojects.automation.assignment4.model.ProductData;
import myprojects.automation.assignment4.utils.Properties;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class CreateProductTest extends BaseTest {
    public static ProductData generatedProduct;

    @DataProvider
    public Object[][] getLoginData() {
        return new String[][]{
                {"webinar.test@gmail.com", "Xcg7299bnSmMuRLp9ITw"}
        };
    }

    @Test(dataProvider = "getLoginData")
    public void createNewProduct(String login, String password) {
        // TODO implement test for product creation

        // actions.login(login, password);
        // ...
        GeneralActions.login(login, password);
        generatedProduct = ProductData.generate();
        GeneralActions.createProduct(generatedProduct);

        GeneralActions.waitForContentLoad();

        GeneralActions.checkProduct(generatedProduct);
        // TODO implement logic to check product visibility on website
    }
}
