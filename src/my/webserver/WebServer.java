package my.webserver;

import java.awt.EventQueue;
import java.io.*;
import java.net.*;

import javax.swing.JFrame;

public class WebServer {

	public static void main(String[] args) throws IOException 
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{		
				config = new Interface(status, threadCount);
				config.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				config.setVisible(true);
			}
		});
		//System.out.println("1");
		ServerSocket welcomeSocket = null;
		Socket connectionSocket = null;
		
		try
		{
			while(true) {
				EventQueue.getCurrentEvent();
				//不加上面这句（改成控制台输出随便什么也行）会出错（config那块没被invoke），
				//但不知道为什么，可能与事件队列有关_(:з」∠)_
				if(status[0] == true)
				{	//如果服务正在运行
					if(welcomeSocket == null)
					{	//如果还未建立连接
						//System.out.println("");
						welcomeSocket = 
								new ServerSocket(	config.getPort(), 
													0, 
													InetAddress.getByName (config.getIPAddr())
													);//端口，队列长度，绑定地址
					}//System.out.println("1");
					connectionSocket = welcomeSocket.accept();					
					config.incCounter();//counter++
					new ChildThread(connectionSocket, 
									config.getFolder(), 
									config.getLog(),  
									config.getCounter(),
									threadCount,
									status
									).start();//新建线程
					
				}
				else if(welcomeSocket != null)
				{	//如果当前服务不在运行且之前建立了连接
					welcomeSocket.close();//关闭连接（close之后就置为null）
					config.cleanCounter();
					System.gc();
				}
			}
		}
		catch(IOException ex)
		{
			System.err.println(ex);
		}
		finally
		{
			if(welcomeSocket != null)
			{
				welcomeSocket.close();
				connectionSocket.close();
			}
		}
	}
	private static Interface config;
	private static boolean[] status = {false};
	private static int[] threadCount = {0};	//线程总数
}
