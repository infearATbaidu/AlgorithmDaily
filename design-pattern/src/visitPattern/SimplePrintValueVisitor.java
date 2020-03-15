package visitPattern;

/**
 * @author infear
 */
public class SimplePrintValueVisitor implements Visitor {
    @Override
    public void visitA(ElementA elementA) {
        System.out.println(elementA.getValue());
    }

    @Override
    public void visitB(ElementB elementB) {
        System.out.println(elementB.getValue());
    }

    @Override
    public void visitLast(ElementLast elementLast) {
        System.out.println(elementLast.getValue());
    }
}
