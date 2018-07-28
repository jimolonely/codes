package jimo.filter;

import java.util.ArrayList;
import java.util.List;

public class FilterPatternDemo {
    public static void main(String[] args) {
        List<Person> persons = new ArrayList<Person>();

        persons.add(new Person("Robert", "Male", "Single"));
        persons.add(new Person("John", "Male", "Married"));
        persons.add(new Person("Laura", "Female", "Married"));
        persons.add(new Person("Diana", "Female", "Single"));
        persons.add(new Person("Mike", "Male", "Single"));
        persons.add(new Person("Bobby", "Male", "Single"));

        Criteria criteriaFemale = new CriteriaFemale();
        Criteria criteriaMale = new CriteriaMale();
        Criteria criteriaSingle = new CriteriaSingle();
        AndCriteria singleAndMaleCriteria = new AndCriteria(criteriaMale, criteriaSingle);
        OrCriteria singleOrFemaleCriteria = new OrCriteria(criteriaFemale, criteriaSingle);

        System.out.println("Males:");
        criteriaMale.meetCriteria(persons).forEach(System.out::println);
        System.out.println("\nMale and single:");
        singleAndMaleCriteria.meetCriteria(persons).forEach(System.out::println);
        System.out.println("\nFemale or single:");
        singleOrFemaleCriteria.meetCriteria(persons).forEach(System.out::println);
    }
}
