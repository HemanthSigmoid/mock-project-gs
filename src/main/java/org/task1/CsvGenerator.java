package org.task1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class CsvGenerator {
    private static final int NUM_RECORDS=1000;
    private static final String[] BUILDING_CODES ={"BLDG1","BLDG2","BLDG3"};
    private static final String[] COMPANY ={"Sigmoid","Google","Meta","Amazon","Goldman Sachs"};
    private static final Random random = new Random();


    public static void main(String[] args) {
        try{
            for(int i=0;i<constant.NUM_FILES;i++) {
                generateCSVFILE( constant.path+"people_" +(i+1)+".csv");
            }
            System.out.println("CSV files generated");
        }
        catch(IOException e){
            System.out.println("IOException: "+e);
        }
    }

    private static void generateCSVFILE(String fileName) throws IOException{
        try(FileWriter writer = new FileWriter(fileName)){
            writer.write("Name,Age,Company,Building_Code,Phone_Number,Address\n");
            for(int i=0;i<NUM_RECORDS;i++){
                String name = "Person_"+(i+1);
                int age = random.nextInt(50) + 20;
                String company = COMPANY[random.nextInt(COMPANY.length)];
                String buildingCode = BUILDING_CODES[random.nextInt(BUILDING_CODES.length)];
                String phoneNumber = generatePhoneNumber();
                String address = "Address" + (i+1);
                writer.write(String.format("%s,%d,%s,%s,%s,%s\n", name, age, buildingCode,company, phoneNumber, address));
            }
        }
    }

    private static String generatePhoneNumber(){
        StringBuilder phoneNumber = new StringBuilder();
        for(int i=0;i<10;i++) phoneNumber.append(random.nextInt(10));
        String phoneNumberString = phoneNumber.toString();
        return phoneNumberString;
    }

}
