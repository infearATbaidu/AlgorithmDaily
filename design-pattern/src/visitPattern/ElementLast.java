package visitPattern;

/**
 * @author infear
 */
public class ElementLast extends Element {
    public ElementLast() {
        super(null);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitLast(this);
    }
}
