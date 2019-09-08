package cl.cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javaSocketObject.Test;


public class Cliente {

	 	private static Socket clientSocket;
	    private static PrintWriter out;
	    private static BufferedReader in;
	    private static ObjectOutputStream objeto;
	    private static ObjectInputStream salida;
	    private static OutputStream envio;
	    private static InputStream entrada;
	    
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		System.out.println("Iniciando cliente");
		startConnection("127.0.0.1",5555);
		
		String archivo = "<xml>"
				+ "<data>Esto es un archivo</data>"
				+ "<nombre>esto es posibilemente una idea</nombre>"
				+ "</xml>";
		
		Map<String,Object> mensaje = new HashMap<String,Object>();
		
		Test test = new Test();
		test.setMensaje("Hola Mundo");
		test.setNumero(1);
		
		
		
		mensaje.put("llave", (Object) test);
		
		String respuesta = (String) sendObject(mensaje);
		System.out.println(respuesta);
		//System.out.println(sendMessage("file|"+archivo+"|/home/nkey/Documentos/archivodemo.txt"));
		stopConnection();
	}
	 
	    public static void startConnection(String ip, int port) throws IOException {
	        clientSocket = new Socket(ip, port);
	        out = new PrintWriter(clientSocket.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	        envio = clientSocket.getOutputStream();
	        entrada = clientSocket.getInputStream();
	        objeto = new ObjectOutputStream(envio);
	        salida = new ObjectInputStream(entrada);
	    }
	 
	    public static String sendObject(Object obj) throws IOException, ClassNotFoundException
	    {
	    	objeto.writeObject(obj);
	    	Test resp = (Test) salida.readObject();
	    	return resp.getMensaje();
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
