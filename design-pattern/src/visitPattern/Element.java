package visitPattern;

/**
 * @author infear
 */
public abstract class Element {
    Element next;

    abstract void accept(Visitor visitor);

    public Element(Element next) {
        this.next = next;
    }

    public Element getNext() {
        return next;
    }
}
