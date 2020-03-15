package visitPattern;

/**
 * @author infear
 */
public class ElementA extends Element {
    public ElementA(Element next) {
        super(next);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitA(this);
    }
}
