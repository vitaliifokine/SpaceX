package elements.detailed;


public class WebElementAttribute {

    public WebElementAttribute(String name, String value) {
        this.name = name; this.value = value;
    }

    public String getName () {
        return this.name;
    }
    public String getValue () {
        return this.value;
    }
    @Override public String toString() {
        return "Web element attribute: {name = '" + this.name + "'; value = '" + this.value + "'}";
    }

    private String name;
    private String value;

}
