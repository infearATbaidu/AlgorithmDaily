package visitPattern;

import java.util.Arrays;
import java.util.List;

/**
 * 访问者模式适合在固定的结构引用于不同的算法
 *
 * @author infear
 */
public class VisitorAPP {

    public static void main(String args[]) {
        Element elementLast = new ElementLast();
        Element elementB = new ElementB(elementLast);
        Element elementA = new ElementA(elementB);

        List<Element> elements = Arrays.asList(elementA, elementB, elementLast);
        // 二次分发：先分发element->elementA/elementB 再分发visitor->PrintVisitor
        elements.forEach(element -> element.accept(new SimplePrintVisitor()));
        elements.forEach(element -> element.accept(new IteratePrintVisitor()));
    }
}
