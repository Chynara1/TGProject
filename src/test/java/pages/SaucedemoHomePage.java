package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class SaucedemoHomePage {
    public  SaucedemoHomePage(){
        WebDriver driver= Driver.getDriver();
        PageFactory.initElements(driver,this);

    }
    @FindBy(id="add-to-cart-sauce-labs-backpack")
    public WebElement backPackProduct;

    @FindBy(id = "add-to-cart-sauce-labs-bike-light")
    public WebElement backLightProduct;

    @FindBy(id = "shopping_cart_container")
    public WebElement cart;

    @FindBy(xpath = "(//div[@class=\"inventory_item_price\"])[1]")
    public  WebElement backPackPrice;


    @FindBy(xpath = "(//div[@class=\"inventory_item_price\"])[2]")
    public  WebElement bikeLightPrice;
}
