package elements.detailed;

public class ElementFullName {

    public ElementFullName(String classification, String name) {
        this.classification = classification;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    public String getClassification() {
        return this.classification;
    }

    public void setName (String name) {
        this.name = name;
    }
    public void setClassification (String classification) {
        this.classification = classification;
    }

    private String classification;
    private String name;

    @Override public String toString() {
        return this.classification + " \"" + this.name + "\"";
    }
}
