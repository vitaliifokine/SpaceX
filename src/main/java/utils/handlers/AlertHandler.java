package utils.handlers;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.security.UserAndPassword;
/**
 * This class designed to handle alerts system popup-ups (alerts, prompts etc.)
 * They're blocking any interactions on page until closed.
 */
public class AlertHandler {



    public synchronized static void confirm(WebDriver driver) {
        try {
            driver.switchTo().alert().accept();
        } catch (WebDriverException e) {
            System.err.println("Unable to confirm alert.");
        }
    }
    public synchronized static void dismiss(WebDriver driver) {
        try {
            driver.switchTo().alert().dismiss();
        } catch (WebDriverException e) {
            System.err.println("Unable to dismiss alert.");
        }
    }
    public synchronized static void prompt(WebDriver driver, String text) {
        try {
            driver.switchTo().alert().sendKeys(text);
            driver.switchTo().alert().accept();
        } catch (WebDriverException e) {
            System.err.println("Unable to complete Prompt.");
        }
    }
    public synchronized static String getAlertText(WebDriver driver) {
        try {
            return driver.switchTo().alert().getText();
        } catch (WebDriverException e) {
            System.err.println("Prompt not present to send keys to it.");
            return "";
        }
    }
    public synchronized static void authentificate (WebDriver driver, String user, String password) {
        driver.switchTo().alert().authenticateUsing(new UserAndPassword(user, password));
    }
    public synchronized static boolean isAlertPresent (WebDriver driver) {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

}
