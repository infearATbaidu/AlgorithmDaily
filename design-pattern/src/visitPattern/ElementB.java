package visitPattern;

/**
 * @author infear
 */
public class ElementB extends Element {
    public ElementB(Element next, int value) {
        super(next, value);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitB(this);
    }
}
