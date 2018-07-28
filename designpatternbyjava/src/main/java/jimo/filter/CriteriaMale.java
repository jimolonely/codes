package jimo.filter;

import java.util.ArrayList;
import java.util.List;

public class CriteriaMale implements Criteria {
    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> result = new ArrayList<>();
        for (Person p : persons) {
            if ("MALE".equalsIgnoreCase(p.getGender())) {
                result.add(p);
            }
        }
        return result;
    }
}
