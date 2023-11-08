package lab5;

public class Patient {
    public int id;    
    
    public String name;
    public String surname;
    public String middle_names;  
    
    public String address;

    public Patient(int id, String name, String surname, String middle_names, String address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.middle_names = middle_names;
        this.address = address;
    }

    
    @Override
    public String toString() {
        return String.format("ID: %s , name: %s , surname: %s , middle_names: %s , address: %s",
                this.id, this.name, this.surname, this.middle_names, this.address);
    }
}
