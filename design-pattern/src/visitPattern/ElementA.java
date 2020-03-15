package visitPattern;

/**
 * @author infear
 */
public class ElementA extends Element {
    public ElementA(Element next, int value) {
        super(next, value);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitA(this);
    }
}
