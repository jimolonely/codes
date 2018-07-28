# Filter Pattern
下面以过滤男人，女和单身为例讲解过滤器模式.主要有单个的过滤器和且和或的逻辑组成。

![filter](./filter_pattern_uml_diagram.jpg?raw=true)

## 1
```java
public class Person {
    private String name;
    private String gender;
    private String maritalStatus;

    public Person(String name, String gender, String maritalStatus) {
        this.name = name;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    @Override
    public String toString() {
        return "[ Name: " + name + ", Gender: " + gender + ", status: " + maritalStatus + " ]";
    }
}
```
## 2
```java
public interface Criteria {
    List<Person> meetCriteria(List<Person> persons);
}
```
## 3
```java
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
```
```java
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
```
```java
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
```
逻辑关系
```java
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
```
```java
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
```
## 4
```java
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
```
```
Males:
[ Name: Robert, Gender: Male, status: Single ]
[ Name: John, Gender: Male, status: Married ]
[ Name: Mike, Gender: Male, status: Single ]
[ Name: Bobby, Gender: Male, status: Single ]

Male and single:
[ Name: Robert, Gender: Male, status: Single ]
[ Name: Mike, Gender: Male, status: Single ]
[ Name: Bobby, Gender: Male, status: Single ]

Female or single:
[ Name: Laura, Gender: Female, status: Married ]
[ Name: Diana, Gender: Female, status: Single ]
[ Name: Robert, Gender: Male, status: Single ]
[ Name: Mike, Gender: Male, status: Single ]
[ Name: Bobby, Gender: Male, status: Single ]
```