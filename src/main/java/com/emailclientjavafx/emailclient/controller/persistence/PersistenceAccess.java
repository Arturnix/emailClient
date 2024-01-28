package com.emailclientjavafx.emailclient.controller.persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenceAccess { //persistence is to store program data/state somewhere on disc. When I reopen the program I dont need to type credenetials again. When I rerun the program it retrieves the data from previous runs.

    private String VALID_ACCOUNT_LOCATION = System.getProperty("user.home") + File.separator + "validAccounts.ser";
    public List<ValidAccount> loadFromPersistence() { //method called on program start

        List<ValidAccount> resultList = new ArrayList<ValidAccount>();

        try {
            FileInputStream fileInputStream = new FileInputStream(VALID_ACCOUNT_LOCATION);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<ValidAccount> persistedList = (List<ValidAccount>) objectInputStream.readObject();
            resultList.addAll(persistedList);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public void saveToPersistence(List<ValidAccount> validAccounts) { //method called on progrma end

       try {
           File file = new File(VALID_ACCOUNT_LOCATION);
           FileOutputStream fileOutputStream = new FileOutputStream(file);
           ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
           objectOutputStream.writeObject(validAccounts);
           fileOutputStream.close();
       } catch(Exception e) {
           e.printStackTrace();
       }
    }
}
