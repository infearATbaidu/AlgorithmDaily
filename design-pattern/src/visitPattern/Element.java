package visitPattern;

/**
 * @author infear
 */
public abstract class Element {
    Element next;
    int value;

    abstract void accept(Visitor visitor);

    public Element(Element next, int value) {
        this.next = next;
        this.value = value;
    }

    public Element getNext() {
        return next;
    }

    public int getValue() {
        return value;
    }
}
