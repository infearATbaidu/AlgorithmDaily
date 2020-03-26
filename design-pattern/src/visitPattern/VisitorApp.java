package visitPattern;

import java.util.Arrays;
import java.util.List;

/**
 * 访问者模式适合在固定的结构引用于不同的算法
 *
 * @author infear
 */
public class VisitorApp {

    public static void main(String args[]) {
        Element elementLast = new ElementLast();
        Element elementB = new ElementB(elementLast, 100);
        Element elementA = new ElementA(elementB, 10);

        List<Element> elements = Arrays.asList(elementA, elementB, elementLast);
        // 二次分发：先分发element->elementA/elementB 再分发visitor->PrintVisitor
        elements.forEach(element -> element.accept(new SimplePrintVisitor()));
        elements.forEach(element -> element.accept(new SimplePrintValueVisitor()));
        elements.forEach(element -> element.accept(new IteratePrintVisitor()));
    }
}
