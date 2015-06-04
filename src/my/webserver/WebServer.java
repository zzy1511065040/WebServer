package my.webserver;

import java.io.*;
import java.net.*;

import javax.swing.JFrame;

public class WebServer {

	public static void main(String[] args) throws IOException 
	{
		String clientSentence;
		
		Interface config;
		config = new Interface();
		
		ServerSocket welcomeSocket = 
				new ServerSocket(6789, 0, InetAddress.getByName ("127.0.0.1"));//端口，队列长度，绑定地址
		while(true) {
			Socket connectionSocket = welcomeSocket.accept();
			clientSentence = connectionSocket.getInetAddress().toString();
			//config.writeLog("Client IP:"+clientSentence.substring(1)+"\n");
		}
	}

}
