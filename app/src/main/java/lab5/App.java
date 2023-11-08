package lab5;


import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;




public class App {    
    
    static DbHandler dbHandler;    
    static Scanner scanner = new Scanner(System.in);
    static boolean adminUserFlag;
    static String ADMIN_PSWD = "pswd";
    public static void main(String[] args) {
        
        try{
            //Создаем обработчик , через него взаимодействие с бд    
            dbHandler = DbHandler.getInstance();
        }catch (SQLException e) {
            e.printStackTrace();
        } 

        boolean work = true;
        while (work) {
            System.out.println("1. AllEmployees\n2. AllPatient\n3. PatientsUndergoingTreatmentWriteToCsv\n4. addPatient\n5. addEmployee\n6. adminAuthorization");

            switch (scanner.nextLine()) {


                case "1":
    
                List<Employee> employees = dbHandler.getAllEmployees();
                for (Employee employee : employees) {
                    System.out.println(employee.toString());
                }
                
                break;
    
                case "2":   
                {         
                    List<Patient> patients = dbHandler.getAllPatient();
                    for (Patient patient : patients) {
                        System.out.println(patient.toString());
                    }        
                   
                }
                break;
                
                case "3":
                {
                    List<String[]>  patientsUndergoingTreatment = dbHandler.getPatientsUndergoingTreatment();
                    List<String> csvPatients = new ArrayList<String>();
                    for(String[] recordsString :patientsUndergoingTreatment){       
                        String csvString = Csv.convertToCsvString(recordsString[0], 
                                            recordsString[1], 
                                            recordsString[2], 
                                            recordsString[3], 
                                            recordsString[4],
                                            recordsString[5]);
                        csvPatients.add(csvString);
    
                        
                    }
                    try{ 
                        Csv.writeStringsToCsv(csvPatients);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
    
                }
                break;

                case "4":
                {
                    if(!adminUserFlag){
                        System.out.println("You not admin");
                        break;
                    }

                    System.out.println("Enter: name surname middle_name address");
                    String name = scanner.nextLine();
                    String surname = scanner.nextLine();
                    String middle_name = scanner.nextLine();
                    String address = scanner.nextLine();

                    
                    Patient patient = new Patient(0, name, surname, middle_name, address);

                    if(!dbHandler.addPatient(patient)){
                        System.err.println("add failed");
                    }
                }
                break;

                case "5":

                    if(!adminUserFlag){
                        System.out.println("You not admin");
                        break;
                    }

                    System.out.println("Enter: name surname middle_name job_title");
                    String name = scanner.nextLine();
                    String surname = scanner.nextLine();
                    String middle_name = scanner.nextLine();
                    String job_title = scanner.nextLine();

                    
                    Employee employee = new Employee(0, name, surname, middle_name, job_title);

                    if(!dbHandler.addEmployee(employee)){
                        System.err.println("add failed");
                    }

                break;

                case "6":
                    adminUserFlag = adminAuthorization();
                    if(adminUserFlag)
                        System.out.println("you are logged in");
                break;

                default:
                    work = false;
                    break;
            }
        }

        
        
    }

    public static boolean adminAuthorization(){
        
        String password = scanner.nextLine();       
        
      
        if(password.equals(ADMIN_PSWD)){
            return true;
        }
        return false;
    }
}
