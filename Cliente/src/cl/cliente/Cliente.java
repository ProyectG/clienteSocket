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

import javaSocketObject.File;
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
		
		
		ejemploEscribir();
		ejemploLeer();
		
		//Detener coneccion
		stopConnection();
	}
	
	
	public static void ejemploEscribir() throws ClassNotFoundException, IOException
	{
		Map<String,Object> mensaje = new HashMap<String,Object>();
		javaSocketObject.File escritura = new javaSocketObject.File();
		escritura.setContenidoArchivo("Data");
		escritura.setNombreArchivo("test.txt");
		escritura.setUbicacionArchivo("/home/nkey/");
		escritura.setMd5(true);
		escritura.setTamaño(true);
		
		mensaje.put("tarea", "escribir");
		mensaje.put("plugin", "javaSocketObject.File");
		mensaje.put("objeto",escritura);
		
		javaSocketObject.File respuesta = (javaSocketObject.File) sendObject(mensaje);
		System.out.println("Contenido Archivo :"+respuesta.getContenidoArchivo());
		System.out.println("Tamaño :"+respuesta.getResultadoTamaño());
		System.out.println("MD5 :"+respuesta.getResultadoMD5());
	}
	
	
	public static void ejemploLeer() throws ClassNotFoundException, IOException
	{
		Map<String,Object> mensaje = new HashMap<String,Object>();
		javaSocketObject.File lectura = new javaSocketObject.File();
		lectura.setNombreArchivo("test.txt");
		lectura.setUbicacionArchivo("/home/nkey/");
		lectura.setMd5(true);
		lectura.setTamaño(true);
		
		mensaje.put("tarea", "leer");
		mensaje.put("plugin", "javaSocketObject.File");
		mensaje.put("objeto",lectura);
		
		javaSocketObject.File respuesta = (javaSocketObject.File) sendObject(mensaje);
		System.out.println("Contenido Archivo :"+respuesta.getContenidoArchivo());
		System.out.println("Tamaño :"+respuesta.getResultadoTamaño());
		System.out.println("MD5 :"+respuesta.getResultadoMD5());
	}
	 
	    public static void startConnection(String ip, int port) throws IOException {
	        clientSocket = new Socket(ip, port);
	        envio = clientSocket.getOutputStream();
	        entrada = clientSocket.getInputStream();
	        objeto = new ObjectOutputStream(envio);
	        salida = new ObjectInputStream(entrada);
	    }
	 
	    public static Object sendObject(Object obj) throws IOException, ClassNotFoundException
	    {
	    	objeto.writeObject(obj);
	    	Object resp = salida.readObject();
	    	return resp;
	    }
	    
	    public static String sendMessage(String msg) throws IOException {
	    	out.println(msg);
	        String resp = in.readLine();
	        return resp;
	    }
	 
	    public static void stopConnection() throws IOException {
	        objeto.close();
	        salida.close();
	        clientSocket.close();
	    }
	    
}