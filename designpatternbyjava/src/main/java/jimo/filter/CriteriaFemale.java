package jimo.filter;

import java.util.ArrayList;
import java.util.List;

public class CriteriaFemale implements Criteria {
    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> result = new ArrayList<>();
        for (Person p : persons) {
            if ("female".equalsIgnoreCase(p.getGender())) {
                result.add(p);
            }
        }
        return result;
    }
}
