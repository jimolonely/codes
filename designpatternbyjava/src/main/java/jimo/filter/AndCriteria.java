package jimo.filter;

import java.util.List;

/**
 * 找出同时符合标准的人
 */
public class AndCriteria implements Criteria {

    private Criteria criteria;
    private Criteria otherCriteria;

    public AndCriteria(Criteria criteria, Criteria otherCriteria) {
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }

    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> persons2 = this.criteria.meetCriteria(persons);
        return otherCriteria.meetCriteria(persons2);
    }
}
