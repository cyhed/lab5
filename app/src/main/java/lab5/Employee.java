package lab5;

public class Employee {
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


}
