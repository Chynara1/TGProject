package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class SaucedemoCheckoutOverviewPage {
    public SaucedemoCheckoutOverviewPage() {
        WebDriver driver = Driver.getDriver();
        PageFactory.initElements(driver, this);

    }
    @FindBy(xpath = "//div[@class=\"summary_subtotal_label\"]")
    public WebElement totalPrice;

}
