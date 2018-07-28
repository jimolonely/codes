package jimo.filter;

import java.util.ArrayList;
import java.util.List;

public class CriteriaSingle implements Criteria {

    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> result = new ArrayList<>();
        for (Person p : persons) {
            if ("SINGLE".equalsIgnoreCase(p.getMaritalStatus())) {
                result.add(p);
            }
        }
        return result;
    }
}
