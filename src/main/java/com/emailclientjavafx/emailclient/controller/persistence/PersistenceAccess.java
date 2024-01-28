package com.emailclientjavafx.emailclient.controller.persistence;

import javax.swing.border.EmptyBorder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenceAccess { //persistence is to store program data/state somewhere on disc. When I reopen the program I dont need to type credenetials again. When I rerun the program it retrieves the data from previous runs.

    private String VALID_ACCOUNT_LOCATION = System.getProperty("user.home") + File.separator + "validAccounts.ser";
    private Encoder encoder = new Encoder();
    public List<ValidAccount> loadFromPersistence() { //method called on program start

        List<ValidAccount> resultList = new ArrayList<ValidAccount>();

        try {
            FileInputStream fileInputStream = new FileInputStream(VALID_ACCOUNT_LOCATION);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<ValidAccount> persistedList = (List<ValidAccount>) objectInputStream.readObject();
            decodePasswords(persistedList);
            resultList.addAll(persistedList);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    private void decodePasswords(List<ValidAccount> persistedList) {

        for(ValidAccount validAccount : persistedList) {
            String originalPassword = validAccount.getPassword();
            validAccount.setPassword(encoder.decode(originalPassword));
        }
    }

    private void encodePasswords(List<ValidAccount> persistedList) {

        for(ValidAccount validAccount : persistedList) {
            String originalPassword = validAccount.getPassword();
            validAccount.setPassword(encoder.encode(originalPassword));
        }
    }

    public void saveToPersistence(List<ValidAccount> validAccounts) { //method called on progrma end

       try {
           File file = new File(VALID_ACCOUNT_LOCATION);
           FileOutputStream fileOutputStream = new FileOutputStream(file);
           ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
           encodePasswords(validAccounts);
           objectOutputStream.writeObject(validAccounts);
           objectOutputStream.close();
           fileOutputStream.close();
       } catch(Exception e) {
           e.printStackTrace();
       }
    }
}
