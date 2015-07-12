package basePageObject;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.TempMailPage;

import java.util.List;
/*** This class designed to provide interactions with temp-mail service.
 */
public class TempMail extends BasePageElement {
    BasePage page;

    public TempMail(WebDriver driver) {
        super(driver);
        page = new TempMailPage(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "click-to-delete")
    WebElement button_Delete;
    @FindBy(id = "click-to-refresh")
    WebElement button_Refresh;
    @FindBy(id = "mail")
    WebElement field_Email;
    @FindBy(className = "pm-text")
    WebElement letterText;
    @FindBy(className = "title-subject")
    List<WebElement> titles;
    @FindBy(css = "div.content .pm-text > *")
    List<WebElement> textContainers;
    @FindBy(css = "div.content .pm-text a[href *= 'http']")
    WebElement linkToCabinet;

    private void clickSubjectContainsText(String subject) {
        boolean hasSubject = false;
        for (WebElement title : titles) {
            if (title.getText().trim().contains(subject)) {
                title.click();
                hasSubject = true;
                break;
            }
        }
        if (!hasSubject) {
            System.out.println("Subject that must contain text: '" + subject + "' missing.");
        }
    }

    public boolean checkLetterContainsRequiredText(String subject, String phrasesToCheck) {
        if (!driver.getCurrentUrl().contains(page.getPageUrl())) {
            page.load();
        }
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(titles));
        } catch (TimeoutException e) {
            System.err.println("WAIT TIMEOUT: I waited for subjects to be present but reached timeout.");
        }
        clickSubjectContainsText(subject);
        boolean result = true;
        String bodyText = getElementsText(letterText).replaceAll("“", "\"").replaceAll("”", "\"");
        String[] arrayOfPhrases = phrasesToCheck.split("&&"); // aaa&&ddd&&ccc - will split for array // [aaa, ddd, ccc]
        for (String requiredPhrase : arrayOfPhrases) {
            if (!bodyText.contains(requiredPhrase)) {
                result = false;
                System.out.println("Letter's body doesn't contains required phrase: " + requiredPhrase);
            }
        }
        return result;
    }

    public String goAndGetNewEmail() {
        if (!driver.getCurrentUrl().contains(page.getPageUrl())) {
            page.load();
        }
        clickElement(button_Delete);
        clickElement(button_Refresh);
        return getElementsValue(field_Email);
    }

    public String getCurrentMail() {
        return getElementsValue(field_Email);
    }

    public BasePage getPage() {
        return page;
    }

    public String getEmailText() {
        return getElementsValue(field_Email);
    }

    public String getMessageText() {
        StringBuilder stringBuilder = new StringBuilder();
        String text;
        for (WebElement textContainer : textContainers) {
            if (getElementsText(textContainer).length() > 0) {
                text = getElementsText(textContainer).replaceAll("“", "\"").replaceAll("”", "\"");
                stringBuilder.append(text + "\n");
            }
        }
        return stringBuilder.toString();
    }

    public String getLinkText() {
        String text = getElementsText(linkToCabinet).replaceAll("“", "\"").replaceAll("”", "\"");
        if (text.endsWith("\"")) {
            text = text.substring(0, text.length() - 1);
        } else if (text.startsWith("\"")) {
            text = text.substring(1, text.length());
        }
        return text;
    }
}
