package main.java.com.luxoft.jva.collections.bankapp.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import main.java.com.luxoft.jva.collections.bankapp.domain.Client;

public class BankClient extends NetBank {

    public void run() {
        Socket requestSocket = null;
        try {
            // 1. creating a socket to connect to the server
            requestSocket = new Socket("localhost", 2004);
            System.out.println("Connected to localhost in port 2004");

            // 2. get Input and Output streams
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());

            // 3: Communicating with the server
            communicate();

        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (ClassNotFoundException classNot) {
            System.err.println("Data received in unknown format");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            // 4: Closing connection
            try {
                in.close();
                out.close();
                assert requestSocket != null;
                requestSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            System.out.println("finished");
        }
    }

    private void communicate() throws ClassNotFoundException, IOException {
    	getMessage();
        getAnswer("Hi, my server!");
        
        int commandNumber;
        Scanner in;
        do {
        	
        	in = new Scanner(System.in);
        	commandNumber = in.nextInt();
        	
        	switch(commandNumber) {
        	   case 1: getAnswer("getNumberOfClients"); break;
        	   case 2: getAnswer("getNumberOfAccounts"); break;
        	   case 3: getAnswer("getTotalSumInAccounts"); break;
        	   case 4: getAnswer("getBankCreditSum"); break;
        	   case 5: Client client = (Client)getObjectAnswer("getClient John");
                       System.out.println(client);
                       break;
        	   case 6: getAnswer("bye");
        	           break;
        	   default: System.out.println("Invalid command!");
                        break;   
        	}
        	
        } while (commandNumber != 6);
        
        in.close();
    	
    }
}