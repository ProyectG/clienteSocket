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
//import javaSocketObject.Test;


public class Cliente {

		private static String ip;
		private static String puerto;
	 	private static Socket clientSocket;
	    private static PrintWriter out;
	    private static BufferedReader in;
	    private static ObjectOutputStream objeto;
	    private static ObjectInputStream salida;
	    private static OutputStream envio;
	    private static InputStream entrada;
	    
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		if(args.length > 0)
		{
			for(String argumentos:args)
			{
				
				ip=args[0];
				puerto=args[1];
			}
		}else
		{
			System.out.println("Se asignan IP-Puerto por defecto.");
			ip="127.0.0.1";
			puerto="5555";
		}
		
		System.out.println("Iniciando cliente");
		startConnection(ip,Integer.parseInt(puerto));
		
		ejemploEscribir();
		ejemploLeer();
	
		stopConnection();
	}
	
	
	public static void ejemploEscribir() throws ClassNotFoundException, IOException
	{
		Map<String,Object> mensaje = new HashMap<String,Object>();
		javaSocketObject.File escritura = new javaSocketObject.File();
		escritura.setContenidoArchivo("Data");
		escritura.setNombreArchivo("test.txt");
		escritura.setUbicacionArchivo("/home/marco/");
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
	
	
	public static void ejemploCmd() throws ClassNotFoundException, IOException
	{
		Map<String,Object> mensaje = new HashMap<String,Object>();
		mensaje.put("tarea","ejecutar");
		String[] comandos = {"cd /home/nkey","ls -ltr"};
		mensaje.put("comando",comandos);
		
		mensaje = (Map<String,Object>) sendObject(mensaje);
		
		if((String)mensaje.get("resultado") != null)
			System.out.println("Resultado CMD : ["+(String)mensaje.get("resultado")+"]");
		else
			System.out.println("No hay resultado");
	}
	
	
	public static void ejemploLeer() throws ClassNotFoundException, IOException
	{
		Map<String,Object> mensaje = new HashMap<String,Object>();
		javaSocketObject.File lectura = new javaSocketObject.File();
		lectura.setNombreArchivo("test.txt");
		lectura.setUbicacionArchivo("/home/marco/");
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