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
				new ServerSocket(6789, 0, InetAddress.getByName ("10.12.57.184"));//端口，队列长度，绑定地址
		while(true) {
			Socket connectionSocket = welcomeSocket.accept();
			clientSentence = connectionSocket.getInetAddress().toString();
			config.frame.logText.append("Client IP:"+clientSentence.substring(1)+"\n");
			config.frame.logText.setCaretPosition(config.frame.logText.getDocument().getLength());
			//设置光标到最后一行从而保持滚动条一直在最底端 
		}
	}

}
