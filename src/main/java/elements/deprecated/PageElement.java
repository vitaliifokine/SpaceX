package elements.deprecated;


import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import utils.Timeouts;

public class PageElement {

    public PageElement(WebElement element) {
        this.element = element;
        if (this instanceof Wrapper) {
            // checking for instance to avoid STACK OVERFLOW (cause of inheritance of wrapper)
        } else {
            this.wrapper = new Wrapper(element);
        }
        this.isWrapped = false;
    }
    public PageElement(WebElement element, WebElement wrapper) {
        this.element = element;
        this.wrapper = new Wrapper(wrapper);
        this.isWrapped = true;
    }

    public boolean appears() {
        boolean status = true;
        int retry = 0;
        while (!isDisplayed()) {
            try {Thread.sleep(100);} catch (InterruptedException e) {}
            retry++;
            if (retry > 10 * timeout) {
                status = false;
                System.err.println("WAIT TIMEOUT: I waited until "+elementName+" appear using locator '"+getLocator()+"' but reached timeout while waiting.");
                break;
            }
        }
        return status;
    }
    public boolean appears(int timeout) {
        boolean status = true;
        int retry = 0;
        while (!isDisplayed()) {
            try {Thread.sleep(100);} catch (InterruptedException e) {}
            retry++;
            if (retry > 10 * timeout) {
                status = false;
                System.err.println("WAIT TIMEOUT: I waited until "+elementName+" appear using locator '"+getLocator()+"' but reached timeout while waiting.");
                break;
            }
        }
        return status;
    }
    public boolean disappears() {
        boolean status = true;
        int retry = 0;
        while (isDisplayed()) {
            try {Thread.sleep(100);} catch (InterruptedException e) {}
            retry++;
            if (retry > 10 * timeout) {
                status = false;
                System.err.println("WAIT TIMEOUT: I waited until "+elementName+" disappear using locator '"+getLocator()+"' but reached timeout while waiting.");
                break;
            }
        }
        return status;
    }
    public boolean disappears(int timeout) {
        boolean status = true;
        int retry = 0;
        while (isDisplayed()) {
            try {Thread.sleep(100);} catch (InterruptedException e) {}
            retry++;
            if (retry > 10 * timeout) {
                status = false;
                System.err.println("WAIT TIMEOUT: I waited until "+elementName+" disappear using locator '"+getLocator()+"' but reached timeout while waiting.");
                break;
            }
        }
        return status;
    }
    public boolean isDisplayed() {
        if (element != null) {
            if (isWrapped) {
                return wrapper.isDisplayed();
            } else  {
                try {
                    return element.isDisplayed();
                } catch (WebDriverException ex) {
                    return false;
                }
            }

        } else {
            return false;
        }
    }
    public String getTagName() {
        try {
            if (appears()) {
                return this.element.getTagName();
            } else {
                return "";
            }
        } catch (WebDriverException ex) {
            return "";
        }

    }
    public String getLocator() {
        return this.element.toString();
    }
    public Wrapper wrapper() {
        if (isWrapped) {
            return this.wrapper;
        } else {
            System.err.println("Message: You're asked for wrapper when element actually isn't wrapped. Returning this element as Wrapper...");
            return new Wrapper(this.element);
        }

    }
    @Override public String toString() {
        return "Element {name: '" + elementName + "'; locator: '"+getLocator()+"'; tag: '"+getTagName()+"'};";
    }

    protected WebElement element() {
        return this.element;
    }

    private WebElement element;
    private Wrapper wrapper;
    protected String elementName = "element";
    protected boolean isWrapped = false;
    private int timeout = Timeouts.SEC_MEDIUM;
}
