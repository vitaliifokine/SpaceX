package pages;

import basePageObject.BasePage;
import org.openqa.selenium.WebDriver;

public class MakeITShowPage extends BasePage {

        public MakeITShowPage(WebDriver driver) {
            super(driver);
            setPAGE_URL("http://makeitshow.ru/ru");
            setPAGE_TITLE("Make it show");
        }
    }

