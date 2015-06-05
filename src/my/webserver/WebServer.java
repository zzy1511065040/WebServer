package my.webserver;

import java.io.*;
import java.net.*;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class WebServer {

	public static void main(String[] args) throws IOException 
	{
		int counter = 0;
		Interface config;
		config = new Interface();
		
		ServerSocket welcomeSocket = 
				new ServerSocket(6789, 0, InetAddress.getByName ("127.0.0.1"));//端口，队列长度，绑定地址
		while(true) {
			Socket connectionSocket = welcomeSocket.accept();
			new ChildThread(connectionSocket, config.getFolder(), config.getLog(),  counter).start();
			counter++;
		}
	}

}
