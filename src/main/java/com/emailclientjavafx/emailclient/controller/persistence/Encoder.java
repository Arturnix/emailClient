package com.emailclientjavafx.emailclient.controller.persistence;

import java.util.Base64;

public class Encoder { //add one resposibility to class, so encoding (decoding) is delegate to this separate class instead of putting this logic in PersistenceAccess class.
//if I would like to improve this encoding method I will change only logic in this one class.
    private static Base64.Encoder enc = Base64.getEncoder(); //singleton so no new word
    private static Base64.Decoder dec = Base64.getDecoder();

    public String encode(String text) {

        try {
            return enc.encodeToString(text.getBytes());
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decode(String text) {
        try {
            return new String(dec.decode(text.getBytes()));
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
