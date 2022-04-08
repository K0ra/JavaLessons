package main.java.com.luxoft.jva.collections.bankapp.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import main.java.com.luxoft.jva.collections.bankapp.domain.Bank;
import main.java.com.luxoft.jva.collections.bankapp.domain.Client;
import main.java.com.luxoft.jva.collections.bankapp.exceptions.ClientExistsException;
import main.java.com.luxoft.jva.collections.bankapp.exceptions.NotEnoughFundsException;
import main.java.com.luxoft.jva.collections.bankapp.service.BankReport;
import main.java.com.luxoft.jva.collections.bankapp.service.BankService;
import main.java.com.luxoft.jva.collections.bankapp.utils.Arguments;

public class BankServer extends NetBank {
	
	Bank bank;
	BankReport bankReport;
	private String message;

    public BankServer(Arguments arguments) {
        BankService.setSerializationFileName(arguments.get("-loadbank"));

        bank = BankService.readBank();
        bankReport = new BankReport(bank);

    }

    public void run() {
        ServerSocket providerSocket = null;
        Socket connection = null;
        while (true) {
	        try {
	            // 1. creating a server socket
	            providerSocket = new ServerSocket(2004); //, 10
	
	            // 2. Wait for connection
	            System.out.println("Waiting for connection");
	            connection = providerSocket.accept();
	            System.out.println("Connection received from " + connection.getInetAddress().getHostName());
	
	            // 3. get Input and Output streams
	            out = new ObjectOutputStream(connection.getOutputStream());
	            out.flush();
	            in = new ObjectInputStream(connection.getInputStream());
	            sendMessage("Connection successful");
	
	            // 4. The two parts communicate via the input and output streams
	            communicate();
	
	        } catch (NotEnoughFundsException notEnoughFundsException) {
	            System.err.println(notEnoughFundsException.getMessage());
	        } catch (IOException ioException) {
	            ioException.printStackTrace();
	        } catch (ClientExistsException clientExistsException) {
	        	clientExistsException.printStackTrace();
	        } catch (ClassNotFoundException e) {
	            System.err.println("Data received in unknown format");
	        } finally {
	            // 4: Closing connection
	            try {
	                assert connection != null;
	                connection.close();
	
	                providerSocket.close();
	
	            } catch (IOException ioException) {
	                ioException.printStackTrace();
	            }
	        }
        }
    }

    private void communicate() throws IOException, ClassNotFoundException, NotEnoughFundsException, ClientExistsException {
    	do {
    		message = (String)getMessage();
    		String command = getCommand(message);
    		String argument = getArgument(message);
    		
            switch (command) {
                case "Hi, my server!": 
                	sendMessage("Hello!"); 
                break;
                
                case "bye": 
                	sendMessage("bye"); 
                break;
                
                case "getNumberOfClients": 
                	sendMessage("Bank has " + bankReport.getNumberOfClients() + " clients"); 
                break;
                
                case "getNumberOfAccounts": 
                	sendMessage("Bank has " + bankReport.getNumberOfClients() + " accounts"); 
                break;
                
                case "getTotalSumInAccounts": 
                	sendMessage("Bank has " + bankReport.getTotalSumInAccounts() + " total sum in accounts"); 
                break;
                
                case "getBankCreditSum": 
                	sendMessage("Bank has " + bankReport.getBankCreditSum() + " total credit sum"); 
                break;
                
                case "getClient": 
                	sendObject(BankService.getClient(bank, argument));
                    sendMessage("Client has been sent."); 
                break;
                
                case "addClient": 
                	BankService.addClient(bank, Client.parseClient(argument)); 
                	sendMessage("ok"); 
                break;

                default: sendMessage("Sorry, I do not understand you...");
            }
    	 } while (!message.equals("bye"));
    }
    
    private String getCommand(String message) {
    	if (!message.startsWith("getClient") && !message.startsWith("addClient")) {
    		return message;
    	}
    	
    	return message.substring(0, message.lastIndexOf(" "));
    }
    
    private String getArgument(String message) {
    	if (!message.startsWith("getClient") && !message.startsWith("addClient")) {
    		return null;
    	}
    	
    	return message.substring(message.lastIndexOf(" ") + 1);
    }
}