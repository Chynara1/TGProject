package utilities;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class BrowserUtils {
    public static void selectDropDownByValue(WebElement element,String value){
        Select select=new Select(element);
        select.selectByValue(value);
    }
}
