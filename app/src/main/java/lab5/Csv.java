package lab5;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.List;

public class Csv {

    static final String CSV_FILE_NAME = "app\\patient.csv";

    public static String convertToCsvString(String... dataset){

        String[] newData = new String[dataset.length];
        int i = 0 ;
        for(String data:dataset){
             
            try{                
                newData[i++]= escapeSpecialCharacters(data) ; 
            } catch (NullPointerException e){ 
                data = null;
            }
        }        
        return String.join(",",newData);
    }

    
    public static String escapeSpecialCharacters(String data) {
        
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }


    public static void writeStringsToCsv(List<String>  csvStrings) throws IOException {   

        File csvOutputFile = new File(CSV_FILE_NAME);
        try (FileWriter csvWriter = new FileWriter(csvOutputFile);) {
            for(String csvString : csvStrings){
                csvWriter.append(csvString);
                csvWriter.append("\n");

            }
        }        
    }
}
