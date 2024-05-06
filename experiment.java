import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class experiment {
    // private static Employee[] arrayOfEmps = {
    // new Employee(1, "Jeff Bezos", 100000.0),
    // new Employee(2, "Bill Gates", 200000.0),
    // new Employee(3, "Mark Zuckerberg", 300000.0)
    // };
    // private static List<Employee> empList = Arrays.asList(arrayOfEmps);

    // Populating List by adding integer elements
    // using add() method

    public static void main(String[] arg) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        // Employee[] arrayOfEmps = {
        // new Employee(1, "Jeff Bezos", 100000.0),
        // new Employee(2, "Bill Gates", 200000.0),
        // new Employee(3, "Mark Zuckerberg", 300000.0)
        // };
        // List<Integer> empList = Arrays.asList(arrayOfEmps);
        List<Integer> newList = Stream.of(list).filter(e -> e != 2).collect(Collectors.toList());

        System.out.println(list.size());
        System.out.println(newList.size());
    }
}

// class Employee {
// integer id;
// string name;
// BigDecimal salary;
// }
