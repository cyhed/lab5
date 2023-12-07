package lab5;

import java.util.Comparator;

public class Employee implements Comparable<Employee>{
    // Поля класса
    public int id;
    
    public String name;
    public String surname;
    public String middle_names;    

    public String job_title;

    // Конструктор
    public Employee(int id, String name, String surname, String middle_names, String job_title) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.middle_names = middle_names;
        this.job_title = job_title;
    }

    
    @Override
    public String toString() {
        return String.format("ID: %s , name: %s , surname: %s , middle_names: %s , job_title: %s",
                this.id, this.name, this.surname, this.middle_names, this.job_title);
    }

    @Override
    public int compareTo(Employee emp) {      
        return (this.id - emp.id);
    }

    public static Comparator<Employee> AlphabeticallyComparator = new Comparator<Employee>() {
 
        @Override
        public int compare(Employee e1, Employee e2) {           
            return (e1.name + e1.middle_names + e1.surname).compareTo(e2.name + e2.middle_names + e2.surname);
        }
    };

    public static Comparator<Employee> JobComparator = new Comparator<Employee>() {
 
        @Override
        public int compare(Employee e1, Employee e2) {
            return (e1.job_title).compareTo(e2.job_title);
        }
    };
}
