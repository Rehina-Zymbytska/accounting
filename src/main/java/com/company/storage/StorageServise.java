package com.company.storage;


import com.company.entity.Operation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StorageServise {

    private final static String OPERATION_DB = "operation_db.txt";

    private static StorageServise INSTANCE;

    public static  StorageServise getInstance(){
        if (INSTANCE == null){
            INSTANCE = new StorageServise();
        }
        return INSTANCE;
    }

    private Gson gson;

    private StorageServise(){
        gson = new Gson();
    }

    public void saveOperation(Operation operation){
        List<Operation> list = readListFromDB();
        list.add(operation);
        String json = gson.toJson(list);
        writeJsonToDB(json);
    }

    public double getBalanceByDate(Date date){
        double balance = 0;
        List<Operation> operations = readListFromDB();
        for (Operation operation : operations) {
            if (operation.getDate().before(date)) balance += operation.getSum();
        }
        BigDecimal decimal = new BigDecimal(balance);
        decimal = decimal.setScale(2,BigDecimal.ROUND_HALF_EVEN);
        return decimal.doubleValue();
    }

    private void writeJsonToDB(String json){
        try (PrintWriter out = new PrintWriter(OPERATION_DB)){
            out.println(json);
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    private List<Operation> readListFromDB(){
        try(BufferedReader br = new BufferedReader(new FileReader(OPERATION_DB))) {
            return gson.fromJson(br, new TypeToken<List<Operation>>(){}.getType());
        } catch (IOException e){
            return new ArrayList<>();
        }
    }


}
