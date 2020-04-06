package likou.top.interview.questions;

import java.util.List;

/**
 * @author infear
 */
public class NestedIntegerImpl implements NestedInteger {
    private List<NestedInteger> list;
    private Integer integer;

    public NestedIntegerImpl(Integer integer, List<NestedInteger> list) {
        this.list = list;
        this.integer = integer;
    }

    @Override
    public boolean isInteger() {
        return integer != null;
    }

    @Override
    public Integer getInteger() {
        return integer;
    }

    @Override
    public List<NestedInteger> getList() {
        return list;
    }
}
