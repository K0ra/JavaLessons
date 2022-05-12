package main.java.com.luxoft.jva.collections.bankapp.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class NetBank {

    protected ObjectOutputStream out;
    protected ObjectInputStream in;

    protected void sendMessage(String msg) throws IOException {
        sendObject(msg);
        System.out.println("request> " + msg);
    }

    protected String getMessage() throws IOException, ClassNotFoundException {
        String result = (String) getObject();
        System.out.println("response> " + result);
        return result;
    }

    protected void sendObject(Serializable object) throws IOException {
        out.writeObject(object);
        out.flush();
    }

    protected Object getObject() throws IOException, ClassNotFoundException {
        return in.readObject();
    }

    protected String getAnswer(String question) throws IOException, ClassNotFoundException {
        sendMessage(question);
        return getMessage();
    }
    
    protected Object getObjectAnswer(String question) throws IOException, ClassNotFoundException {
        sendMessage(question);
        return getObject();
    }
}