package cl.cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {

	 	private static Socket clientSocket;
	    private static PrintWriter out;
	    private static BufferedReader in;
	
	public static void main(String[] args) throws IOException {
		System.out.println("Iniciando cliente");
		startConnection("127.0.0.1",5555);
		
		String archivo = "<xml>"
				+ "<data>Esto es un archivo</data>"
				+ "<nombre>esto es posibilemente una idea</nombre>"
				+ "</xml>";
		
		System.out.println(sendMessage("file|"+archivo+"|/home/nkey/Documentos/archivodemo.txt"));
		stopConnection();
	}
	 
	    public static void startConnection(String ip, int port) throws IOException {
	        clientSocket = new Socket(ip, port);
	        out = new PrintWriter(clientSocket.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	    }
	 
	    public static String sendMessage(String msg) throws IOException {
	        out.println(msg);
	        String resp = in.readLine();
	        return resp;
	    }
	 
	    public static void stopConnection() throws IOException {
	        in.close();
	        out.close();
	        clientSocket.close();
	    }

}
