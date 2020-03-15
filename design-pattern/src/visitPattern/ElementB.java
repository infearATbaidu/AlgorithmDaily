package visitPattern;

/**
 * @author infear
 */
public class ElementB extends Element {
    public ElementB(Element next) {
        super(next);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitB(this);
    }
}
