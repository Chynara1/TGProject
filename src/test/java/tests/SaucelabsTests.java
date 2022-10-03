package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.TestBase;

import java.util.List;

public class SaucelabsTests extends TestBase {


    @Test(priority = 1, groups = {"regression", "smoke"})
    public void validateLoginTest() {
        driver.get(ConfigReader.getProperty("SauceLabsURL"));
        SaucedemoLoginPage loginPage = new SaucedemoLoginPage();
        loginPage.login();


        String actualPageHeader = driver.findElement(By.xpath("//span[@class='title']")).getText();
        String expectedPageHeader = "PRODUCTS";
        Assert.assertEquals(actualPageHeader, expectedPageHeader);
    }

    @Test(priority = 2, groups = {"regression"})
    public void validateFilterByPriceTest() {
        driver.get(ConfigReader.getProperty("SauceLabsURL"));
        SaucedemoLoginPage loginPage = new SaucedemoLoginPage();
        loginPage.login();


        WebElement filter = driver.findElement(By.xpath("//select[@class=\"product_sort_container\"]"));
        Select select = new Select(filter);
        select.selectByValue("lohi");

        List<WebElement> prices = driver.findElements(By.xpath("//div[@class=\"inventory_item_price\"]"));
        //7.99, $9.99 etc
        for (int i = 1; i < prices.size(); i++) {
            //$9.99 -> 9.99 sorting from string to double
            String price = prices.get(i).getText().substring(1); //$9.99 -> "9.99"
            double priceDouble = Double.parseDouble(price); //"9.99" -> 9.99
            String price2 = prices.get(i - 1).getText().substring(1); //$7.99 - "7.99"
            double priceDouble2 = Double.parseDouble(price2); //"7.99" - 7.99

            Assert.assertTrue(priceDouble >= priceDouble2);

        }
    }

    @Test(priority = 3, groups = {"regression", "smoke"})
    public void validateOrderFunctionalityTest() {
        driver.get(ConfigReader.getProperty("SauceLabsURL"));
        SaucedemoLoginPage loginPage = new SaucedemoLoginPage();
        loginPage.login();

        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.xpath("//a[@class=\"shopping_cart_link\"]")).click();
        String price = driver.findElement(By.xpath("//div[@class=\"inventory_item_price\"]")).getText();
        driver.findElement(By.id("checkout")).click();
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("123456");
        driver.findElement(By.id("continue")).click();

        String checkoutPrice = driver.findElement(By.xpath("//div[@class=\"summary_subtotal_label\"]")).getText();
        //Item total: $29.99
        Assert.assertEquals(checkoutPrice.substring(checkoutPrice.lastIndexOf(" ") + 1), price);


        System.out.println("Practicing the substring method");
        String actualTotalSummary = driver.findElement(By.xpath("//div[@class=\"summary_total_label\"]")).getText();
        String expectedTotalSummary = "Total: $32.39";
        Assert.assertEquals(actualTotalSummary.substring(actualTotalSummary.lastIndexOf(" ") + 1),
                expectedTotalSummary.substring(expectedTotalSummary.lastIndexOf(" ") + 1));
        System.out.println(actualTotalSummary);
        driver.findElement(By.id("finish")).sendKeys(Keys.ENTER);
        String actualSuccessMessage = driver.findElement(By.xpath("//h2[@class=\"complete-header\"]")).getText();
        String expectedSuccessMessage = "THANK YOU FOR YOUR ORDER";
        Assert.assertEquals(actualSuccessMessage, expectedSuccessMessage);

    }

    @Test(priority = 4, groups = {"regression", "smoke"})
    public void ValidateCheckoutTotalTest() {
        driver.get(ConfigReader.getProperty("SauceLabsURL"));
        SaucedemoLoginPage loginPage = new SaucedemoLoginPage();
        loginPage.login();

        SaucedemoHomePage homePage = new SaucedemoHomePage();
        homePage.backPackProduct.click();
        homePage.backLightProduct.click();

        String backBackPrice = homePage.backPackPrice.getText();
        String bikeLightPrice = homePage.bikeLightPrice.getText();
        homePage.cart.click();

        SaucedemoMyCartPage myCartPage = new SaucedemoMyCartPage();
        myCartPage.checkoutButton.click();
        //checkoutPage
        SauceDemoCheckoutPage checkoutPage = new SauceDemoCheckoutPage();
        checkoutPage.checkoutWithValidInfo();

        SaucedemoCheckoutOverviewPage checkoutOverviewPage = new SaucedemoCheckoutOverviewPage();
        String actualTotalPrice = checkoutOverviewPage.totalPrice.getText();
        //validate - bacKPackPrice + BikeLightPrice ==actual total price
        //bacKPackPrice -> "$29.99"
        //BikeLightPrice-> "$9.99"

        double backPackPriceDouble = Double.parseDouble(backBackPrice.substring(1));
        double bikLightPriceDouble = Double.parseDouble(bikeLightPrice.substring(1));
        //actualTotalPrice -> Item total: $39.98"
        double actualTotalPriceDouble = Double.parseDouble(actualTotalPrice.substring
                (actualTotalPrice.indexOf("$") + 1));
        System.out.println(actualTotalPriceDouble);
        Assert.assertTrue(backPackPriceDouble + bikLightPriceDouble == actualTotalPriceDouble);


    }
}
