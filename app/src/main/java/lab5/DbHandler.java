package lab5;

import org.sqlite.JDBC;
import java.sql.*;
import java.util.*;


public class DbHandler {

    // Константа, в которой хранится адрес подключения
    private static final  String DB_URL = "jdbc:sqlite:app\\bd\\hospital.db";

    //  одиночка, чтобы не плодить множество
    // экземпляров класса DbHandler
    private static DbHandler instance = null;    
 
    public static synchronized DbHandler getInstance() throws SQLException {
        if (instance == null)
            instance = new DbHandler();
        return instance;
    }

    // Объект, в котором будет храниться соединение с БД
    private Connection connection;

    private DbHandler() throws SQLException {
        try{
        // Регистрируем драйвер, с которым будем работать
        // в нашем случае Sqlite
        DriverManager.registerDriver(new JDBC());
        // Выполняем подключение к базе данных
        this.connection = DriverManager.getConnection(DB_URL);
        
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        System.out.println("Opened database successfully");
        
    }

    public List<Employee> getAllEmployees() {
 
        // Statement используется для того, чтобы выполнить sql-запрос
        try (Statement statement = this.connection.createStatement()) {
            // В данный список будем загружать наших Сотрудников, полученные из БД
            List<Employee> employees = new ArrayList<Employee>();
            // В resultSet будет храниться результат нашего запроса,
            // который выполняется командой statement.executeQuery()
            ResultSet resultSet = statement.executeQuery("SELECT idEmployee, name, surname, middle_names, job_title FROM Employee");
            // Проходимся по нашему resultSet и заносим данные в products
            while (resultSet.next()) {
                employees.add(new Employee(resultSet.getInt("idEmployee"),
                                            resultSet.getString("name"),
                                            resultSet.getString("surname"),
                                            resultSet.getString("middle_names"),
                                            resultSet.getString("job_title")));
            }
            // Возвращаем наш список
            return employees;
 
        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустую коллекцию
            return Collections.emptyList();
        }
    }

    public List<Patient> getAllPatient() {
 
        // Statement используется для того, чтобы выполнить sql-запрос
        try (Statement statement = this.connection.createStatement()) {
            // В данный список будем загружать наших Сотрудников, полученные из БД
            List<Patient> patients = new ArrayList<Patient>();
            // В resultSet будет храниться результат нашего запроса,
            // который выполняется командой statement.executeQuery()
            ResultSet resultSet = statement.executeQuery("SELECT idPatient, name, surname, middle_names, address FROM Patient");
            // Проходимся по нашему resultSet и заносим данные в products
            while (resultSet.next()) {
                patients.add(new Patient(resultSet.getInt("idPatient"),
                                            resultSet.getString("name"),
                                            resultSet.getString("surname"),
                                            resultSet.getString("middle_names"),
                                            resultSet.getString("address")));
            }
            // Возвращаем наш список
            return patients;
 
        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустую коллекцию
            return Collections.emptyList();
        }
    }

    public List<String[]> getPatientsUndergoingTreatment(){
        try (Statement statement = this.connection.createStatement()) {
             // В данный список будем загружать наши записи
             
            List<String[]> patients = new ArrayList<String[]>();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM patients_undergoing_treatment");

            // Проходимся по нашему resultSet и заносим данные в patients
            while (resultSet.next()) {
                patients.add(new String[]{resultSet.getString("Record"),
                                            resultSet.getString("patient_name"),
                                            resultSet.getString("patient_surname"),
                                            resultSet.getString("patient_middle_names"),
                                            resultSet.getString("date_of_application"),
                                            resultSet.getString("assignment_count")});
            }
            // Возвращаем наш список
            return patients;        
        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустую коллекцию
            return Collections.emptyList();
            
        }
    }
    public boolean addPatient(Patient patient){
        try (Statement statement = this.connection.createStatement()) {            
                
            statement.executeUpdate(String.format("INSERT INTO Patient (name,surname,middle_names,address ) VALUES  ('%s','%s','%s','%s')",
                    patient.name, patient.surname, patient.middle_names, patient.address));           
           return true;
               
        } catch (SQLException e) {
            e.printStackTrace();           
            return false;           
        } catch (java.lang.NullPointerException e) {
            e.printStackTrace();           
            return false;       
        }
    }

    public boolean addEmployee(Employee employee){
        try (Statement statement = this.connection.createStatement()) {            
                
            statement.executeUpdate(String.format("INSERT INTO Employee (name,surname,middle_names,job_title ) VALUES  ('%s','%s','%s','%s')",
            employee.name, employee.surname, employee.middle_names, employee.job_title));           
           return true;
               
        } catch (SQLException e) {
            e.printStackTrace();           
            return false;           
        } catch (java.lang.NullPointerException e) {
            e.printStackTrace();           
            return false;       
        }
    }
}
