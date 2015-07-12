package pages;

import basePageObject.BasePage;
import org.openqa.selenium.WebDriver;

public class TempMailPage extends BasePage {

    public TempMailPage(WebDriver driver) {
        super(driver);
        setPAGE_URL("https://temp-mail.ru/");
        setPAGE_TITLE("Временный Email - Одноразовая почта - Анонимная почта - Бесплатная почта - Временная почта - Temp Mail");
    }
}
