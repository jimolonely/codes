package jimo.filter;

import java.util.List;

public class OrCriteria implements Criteria {
    private Criteria criteria;
    private Criteria otherCriteria;

    public OrCriteria(Criteria criteria, Criteria otherCriteria) {
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }

    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> p1 = criteria.meetCriteria(persons);
        List<Person> p2 = otherCriteria.meetCriteria(persons);
        for (Person p : p2) {
            if (!p1.contains(p)) {
                p1.add(p);
            }
        }
        return p1;
    }
}
