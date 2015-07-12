package elements.detailed;

import org.openqa.selenium.WebElement;

import java.lang.reflect.Constructor;
import java.util.List;

public class Elements <T extends PageElement> {

    public Elements (Class<T> cl, List<WebElement> WebElements, String name) {
        this.WebElements = WebElements;
        this.fullName = new ElementFullName(this.classification, name);

        try {
            this.ctor = cl.getConstructor(WebElement.class, String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            this.ctor = null;
        }

    }

    public Result<T> getElementWithText(String text) {
        this.elem = null;
        boolean status = false;
        String message;

        for (WebElement el : this.WebElements) {
            if (el.getText().contains(text)) {
                initialize(el, "from " + this.fullName);
                status = true;
                break;
            }
        }
        if (status) {
            message = "Element with text '"+text+"' found.";
        } else  {
            message = "Element with text '"+text+"' not found.";
        }
        return new Result<T>(status, elem, message);
    }
    public Result<T> getElementContainsText(String text) {

        this.elem = null;
        boolean status = false;
        String message;

        for (WebElement el : this.WebElements) {
            if (el.getText().contains(text)) {
                initialize(el, "from " + this.fullName);
                status = true;
                break;
            }
        }
        if (status) {
            message = "Element contains text '"+text+"' found.";
        } else  {
            message = "Element contains text '"+text+"' not found.";
        }
        return new Result<T>(status, elem, message);
    }
    public Result<T> getElementInPosition(int pos) {

        this.elem = null;
        boolean status = false;
        String message;

        int counter = 0;
        if (pos <= this.WebElements.size()) {

            for (WebElement el : this.WebElements) {
                counter++;
                if (counter >= pos) {
                    initialize(el,  "from " + this.fullName);
                    status = true;
                    break;
                }
            }

            if (status) {
                message = "Element #"+pos+" found successfully.";
            } else {
                message = "Element #"+pos+" not found for some reason...";
            }

        } else {
            message = "Required position #"+pos+" of element is more than count of elements = '"+this.WebElements.size()+"'.";
        }

        return new Result<T>(status, elem, message);
    }
    public Result<T> getElementWithAttribute(WebElementAttribute attribute) {
        this.elem = null;
        boolean status = false;
        String message;

        for (WebElement el : this.WebElements) {
            if (el.getAttribute(attribute.getName()).contains(attribute.getValue())) {
                initialize(el, "from " + this.fullName);
                status = true;
                break;
            }
        }
        if (status) {
            message = "Element with attribute ("+attribute+") found.";
        } else  {
            message = "Element with attribute ("+attribute+") not found.";
        }
        return new Result<T>(status, elem, message);
    }

    private void initialize(WebElement element, String name) {
        try {
            elem = ctor.newInstance(element, name);
        } catch (Exception e) {
            e.printStackTrace();
            elem = null;
        }
    }

    private Constructor<T> ctor;
    private T elem;
    private List<WebElement> WebElements;
    private String classification = "WebElements";
    protected ElementFullName fullName;

}
