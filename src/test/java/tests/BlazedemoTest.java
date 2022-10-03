package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.TestBase;

import java.util.concurrent.TimeUnit;

public class BlazedemoTest extends TestBase {

    @Test(priority = 1,groups = {"regression"})
    public void verifyChosenFlightMessageTest() {
        driver.get(ConfigReader.getProperty("BlazeDemoURL"));
        WebElement fromCity = driver.findElement(By.name("fromPort"));
        Select select = new Select(fromCity);
        select.selectByValue("Paris");

        WebElement toPort = driver.findElement(By.name("toPort"));
        Select select1 = new Select(toPort);
        select1.selectByValue("London");

        driver.findElement(By.tagName("input")).click();

        String actualMessage = driver.findElement(By.tagName("h3")).getText();
        String expectedMessage = "Flights from Paris to London:";
        Assert.assertEquals(actualMessage, expectedMessage);
    }

    @Test(priority = 2,groups = {"smoke"})
    public void verifySelectedFlightInfoTest() {

        driver.get(ConfigReader.getProperty("BlazeDemoURL"));
        WebElement fromCity = driver.findElement(By.name("fromPort"));
        Select select = new Select(fromCity);
        select.selectByValue("Paris");

        WebElement toPort = driver.findElement(By.name("toPort"));
        Select select1 = new Select(toPort);
        select1.selectByValue("London");

        driver.findElement(By.tagName("input")).click();
        String flightNumber = driver.findElement(By.xpath("//tbody//tr[1]//td[2]")).getText();
        String airlineName = driver.findElement(By.xpath("//tbody//tr[1]//td[3]")).getText();
        String price = driver.findElement(By.xpath("//tbody//tr[1]//td[6]")).getText();
        driver.findElement(By.xpath("(//input[@type='submit'])[1]")).click();

        String actualFlightName=driver.findElement(By.xpath("//p[1]")).getText();
        String actualFlightNumber=driver.findElement(By.xpath("//p[2]")).getText();
        String actualPrice =driver.findElement(By.xpath("//p[3]")).getText();

        SoftAssert softAssert=new SoftAssert();
        softAssert.assertEquals(actualPrice.substring(actualPrice.indexOf(" ")+1),price.substring(1));
        softAssert.assertEquals(actualFlightName.substring(actualFlightName.indexOf(" ")+1),airlineName);
        softAssert.assertEquals(actualFlightNumber.substring(actualFlightNumber.lastIndexOf(" ")+1),flightNumber);
        softAssert.assertAll();
    }

    @Test(priority = 3,groups = {"regression"})
    public void validateDestinationOfTheWeekTest(){
        driver.get(ConfigReader.getProperty("BlazeDemoURL"));
        driver.findElement(By.xpath("//a[@href=\"vacation.html\"]")).sendKeys(Keys.ENTER);
        String actualMessage= driver.findElement(By.xpath("(//div[@class=\"container\"])[2]")).getText();
        String expectedMessage="Destination of the week: Hawaii !";
        Assert.assertEquals(actualMessage,expectedMessage);
        System.out.println(actualMessage);
    }


}
