package visitPattern;

import java.util.Collections;

/**
 * @author infear
 */
public class IteratePrintVisitor implements Visitor {
    private static final String symbol = "-";
    private int indent = 0;

    @Override
    public void visitA(ElementA elementA) {
        System.out.println(String.join("", Collections.nCopies(indent++, symbol)) + elementA);
        elementA.getNext().accept(this);
    }

    @Override
    public void visitB(ElementB elementB) {
        System.out.println(String.join("", Collections.nCopies(indent++, symbol)) + elementB);
        elementB.getNext().accept(this);
    }

    @Override
    public void visitLast(ElementLast elementLast) {
        System.out.println(String.join("", Collections.nCopies(indent++, symbol)) + elementLast);
    }
}
