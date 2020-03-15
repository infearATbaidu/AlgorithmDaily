package visitPattern;

/**
 * @author infear
 */
public class SimplePrintVisitor implements Visitor {
    @Override
    public void visitA(ElementA elementA) {
        System.out.println(elementA);
    }

    @Override
    public void visitB(ElementB elementB) {
        System.out.println(elementB);
    }

    @Override
    public void visitLast(ElementLast elementLast) {
        System.out.println("end");
    }
}
