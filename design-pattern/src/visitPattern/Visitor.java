package visitPattern;

/**
 * @author infear
 */
public interface Visitor {
    void visitA(ElementA elementA);

    void visitB(ElementB elementB);

    void visitLast(ElementLast elementB);
}
