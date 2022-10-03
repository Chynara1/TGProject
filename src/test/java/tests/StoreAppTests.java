package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.StoreAppCreateAccountPage;
import pages.StoreAppHomePage;
import pages.StoreAppLoginPage;
import utilities.BrowserUtils;
import utilities.ConfigReader;
import utilities.TestBase;

import java.util.Random;

public class StoreAppTests extends TestBase {
    String email;
    String passwordSignIn;

    @DataProvider(name = "registerData")
    public static Object[][] registerData(){
        Object[][] data=new Object[][]{
                {"Patel","Harsh","khljfsjhflsjhshjk","1","2","2000","123 road","Alaska","12","12345","92872847274","Home address"},
                {"Kim","Yan","khljfsjhflsjhsk","1","2","2006","r994 road","Alaska","32","12345","92872847274","Work address"},
                {"John","Doe","khljfsjhflsjhsmjjk","1","2","2003","848 road","new york","5","12345","92872847274","8585 address"}
        };
        return data;
    }
    @Test(dataProvider = "registerData", groups = {"regression","smoke"})
    public void validateResgisterFunctionalityTest(String firstName,String lastName, String password, String birthDay, String birthMonth,
                                                   String birthYear, String address, String city, String sate, String zipcode, String phoneNumber,
                                                   String addressAlias) {
        driver.get(ConfigReader.getProperty("StoreAppURL"));
        StoreAppHomePage homePage = new StoreAppHomePage();
        homePage.loginButton.click();
        StoreAppLoginPage loginPage = new StoreAppLoginPage();
        Random random = new Random();
        int randomNum = random.nextInt();
        email=randomNum+"queen@gmail.com";

        loginPage.registerEmailBox.sendKeys(randomNum + "queen@gmail.com");
        loginPage.createAccountButton.click();

        StoreAppCreateAccountPage createAccountPage = new StoreAppCreateAccountPage();
        createAccountPage.firstName.sendKeys(firstName);
        createAccountPage.lastName.sendKeys(lastName);
        passwordSignIn=password;
        createAccountPage.password.sendKeys(password);
        BrowserUtils.selectDropDownByValue(createAccountPage.birthDay,birthDay);
        BrowserUtils.selectDropDownByValue(createAccountPage.birthMont, birthMonth);
        BrowserUtils.selectDropDownByValue(createAccountPage.birthYear, birthYear);
        createAccountPage.address.sendKeys(address);
        createAccountPage.city.sendKeys(city);
        BrowserUtils.selectDropDownByValue(createAccountPage.state, sate);
        createAccountPage.zipCode.sendKeys(zipcode);
        createAccountPage.phoneNumber.sendKeys(phoneNumber);
        createAccountPage.reference.sendKeys(addressAlias);
        createAccountPage.registerButton.sendKeys(Keys.ENTER);

        String actualTitle= driver.getTitle();
        String expectedTitle="My account - My Store";
        Assert.assertEquals(actualTitle,expectedTitle);


    }

    @Test(dependsOnMethods = "validateResgisterFunctionalityTest", groups = {"regression","smoke"})
    public void validateSignInFunctionalityTest(){
        driver.get(ConfigReader.getProperty("StoreAppURL"));
        StoreAppHomePage homePage=new StoreAppHomePage();
        homePage.loginButton.click();
        StoreAppLoginPage loginPage=new StoreAppLoginPage();
        loginPage.signIn(email,passwordSignIn);
        String actualTitle= driver.getTitle();
        String expectedTitle ="My account - My Store";

        Assert.assertEquals(actualTitle,expectedTitle);

    }

}
