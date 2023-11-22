package lab5;


import java.io.FileInputStream;
import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


import java.util.logging.Level;
import java.util.logging.Logger;



import java.util.logging.LogManager;


public class App {    
    
    static Logger LOGGER;
    static {
        try(FileInputStream ins = new FileInputStream(".\\log.config")){ 
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(App.class.getName());
            
        }catch (Exception ignore){           
            ignore.printStackTrace();
        }
    }

    static DbHandler dbHandler;    
    static Scanner scanner = new Scanner(System.in);
    static boolean adminUserFlag;
    static String ADMIN_PSWD = "pswd";

    public static void main(String[] args) {
        LOGGER.log(Level.INFO,"App start");

        try{
            LOGGER.log(Level.INFO,"connect DB");               
            dbHandler = DbHandler.getInstance();
        }catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "connect DB fail");
            e.printStackTrace();
        } 
        
        boolean work = true;
        while (work) {
            
            System.out.println("1. AllEmployees\n2. AllPatient\n3. PatientsUndergoingTreatmentWriteToCsv\n4. addPatient\n5. addEmployee\n6. adminAuthorization\n");
            System.out.println("\n*------------\n");
            System.out.println("7. AddRecord\n"+
            "8. Discharge\n"+
            "9. AddAssignment\n"+
            "10. AssignmentCompletion\n");
            System.out.println("\nesle. Exit");
            switch (scanner.nextLine()) {


                case "1":
                LOGGER.log(Level.INFO,"employees output request");
                List<Employee> employees = dbHandler.getAllEmployees();
                for (Employee employee : employees) {
                    System.out.println(employee.toString());
                }
                
                break;
    
                case "2":   
                {         
                    LOGGER.log(Level.INFO,"patients output request");
                    List<Patient> patients = dbHandler.getAllPatient();
                    for (Patient patient : patients) {
                        System.out.println(patient.toString());
                    }        
                   
                }
                break;
                
                case "3":
                {
                    LOGGER.log(Level.INFO,"request for compiling a CSV report for patients");
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
                        LOGGER.log(Level.WARNING,"error writing to csv file" + e.getMessage());
                        
                    }
    
                }
                break;

                case "4":
                {
                    if(!adminUserFlag){
                        System.out.println("You not admin");
                        break;
                    }

                    LOGGER.log(Level.INFO,"add patient to db");

                    System.out.println("Enter: name surname middle_name address");
                    String name = scanner.nextLine();
                    String surname = scanner.nextLine();
                    String middle_name = scanner.nextLine();
                    String address = scanner.nextLine();

                    
                    Patient patient = new Patient(0, name, surname, middle_name, address);

                    if(!dbHandler.addPatient(patient)){
                        LOGGER.log(Level.WARNING,"error add patient to DB");
                    }
                }
                break;

                case "5":

                    if(!adminUserFlag){
                        System.out.println("You not admin");
                        break;
                    }
                    LOGGER.log(Level.INFO,"add employee to db");

                    System.out.println("Enter: name surname middle_name job_title");
                    String name = scanner.nextLine();
                    String surname = scanner.nextLine();
                    String middle_name = scanner.nextLine();
                    String job_title = scanner.nextLine();

                    
                    Employee employee = new Employee(0, name, surname, middle_name, job_title);

                    if(!dbHandler.addEmployee(employee)){
                        LOGGER.log(Level.WARNING,"error add employee to DB");
                    }

                break;

                case "6":
                    LOGGER.log(Level.INFO,"administrator authorization attempt");
                    adminUserFlag = adminAuthorization();
                    if(adminUserFlag)
                        LOGGER.log(Level.INFO,"administrator authorization success");
                break;

                case "7":
                    AddRecord();
                    break;

                case "8":
                    Discharge();
                    break;

                case "9":
                    AddAssignment();
                    break;
                case "10":
                    Completion();
                    break;
                default:
                    work = false;
                    break;
            }
        }

        LOGGER.log(Level.INFO,"App end");
        
    }

    public static boolean adminAuthorization(){
        
        String password = scanner.nextLine();       
        
      
        if(password.equals(ADMIN_PSWD)){
            return true;
        }
        return false;
    }
    public static void AddRecord(){
        System.out.println("Enter idPatient");
        int idPatient = Integer.parseInt(scanner.nextLine()) ;

        System.out.println("Enter idDisaese");
        int idDisaese = Integer.parseInt(scanner.nextLine()) ;

        System.out.println("Enter idEmployee");
        int idEmployee = Integer.parseInt(scanner.nextLine()) ;

        System.out.println("Enter description");
        String description = scanner.nextLine();

        Date date = new Date();
        long date_of_applicatio = date.getTime()/1000;   

        dbHandler.AddRecord(idPatient, idDisaese, idEmployee, date_of_applicatio, description);
    }
    public static void Discharge (){
        System.out.println("Enter idRecord");
        int idRecord = Integer.parseInt(scanner.nextLine()) ;

        Date date = new Date();
        long date_discharge = date.getTime()/1000;  
        
        dbHandler.Discharge(idRecord, date_discharge);
        
    }

    public static void AddAssignment(){
        System.out.println("Enter idEmployee");
        int idEmployee = Integer.parseInt(scanner.nextLine()) ;

        System.out.println("Enter idRecord");
        int idRecord = Integer.parseInt(scanner.nextLine()) ;       

        System.out.println("Enter description");
        String description = scanner.nextLine();        

        dbHandler.AddAssignment(idEmployee, idRecord,  description);
    }

    public static void Completion (){
        System.out.println("Enter idAssignment");
        int idAssignment = Integer.parseInt(scanner.nextLine()) ;

        Date date = new Date();
        long date_discharge = date.getTime()/1000;  
        
        dbHandler.Completion(idAssignment, date_discharge);
        
    }
}
