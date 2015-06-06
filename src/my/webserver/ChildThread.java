package my.webserver;

import java.net.InetAddress;
import java.net.Socket;
import java.util.StringTokenizer;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextArea;


public class ChildThread extends Thread{
	
	int n;   //请求序号
	int[] count; //线程数
	boolean[] isRun; //当前服务正在运行
	Socket serverSocket; //服务器端的套接字
	String fileRoute;	//客户机请求文件的路径
	String resultLine;	//服务器对请求的处理结果
	JTextArea text;		
	PrintStream output;	//送回客户机的字节流
	String requestLine;	//保存请求报文的请求行
	DateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	//DateFormat fullDateFormat =
			//DateFormat.getDateTimeInstance(
			//DateFormat.FULL,
			//DateFormat.FULL);
	Date date = new Date();
	
	
	/* skt: 服务器套接字
	 * mainlist: 服务器主目录
	 * log： 服务器界面文本框
	 * counter: 请求计数器
	 */
	public ChildThread(	Socket skt, 
						String mainlist, 
						JTextArea log, 
						int threadNum, 
						int[] threadCount,
						boolean[] status ) throws IOException
	{
		serverSocket = skt;
		fileRoute = mainlist;
		text = log;
		n = threadNum;
		count = threadCount;
		//isRun = status;//只读
		output = new PrintStream(serverSocket.getOutputStream());
	}
	public void run()
	{
		try	{
			//++count[0]; //线程总数加1
			//if(isRun[0] == true)
			//{
				InputStream byteStream = serverSocket.getInputStream(); //获取字节流
				InputStreamReader charStream = new  InputStreamReader(byteStream); //字节流转化为字符流
				BufferedReader input = new BufferedReader(charStream); //字符流放入缓冲区
				requestLine = input.readLine(); //读取客户机发出的HTTP请求行
				String clientIP = serverSocket.getInetAddress().toString().substring(1); //获取客户机IP地址
				int clientPort = serverSocket.getPort();//获取客户机端口号
				String clientInf = new String("[Connection " + n + "]\n" + 
												clientIP + ": " + clientPort 
												+ "\n" + requestLine + "\n"); //客户机信息
				ProcessRequest(); //获取请求处理结果并发送响应报文给客户机
				String inf = new String(clientInf + resultLine);
				text.append(inf);
				text.setCaretPosition(text.getDocument().getLength());
			//}			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void ProcessRequest() throws Exception {
		
		String headLine = null;     //保存完整的响应报文
		//响应报文内容
		String statusLine = null;	//状态行
		String closeLine = "Connection: close\n"; 
		String timeLine = null;    //首部行Date：发送响应报文的日期
		String ServerLine = "Server: " + InetAddress.getLocalHost().getHostName() + "\n";
		String lengthLine = null; //首部行 Content-Length：发送对象的字节数
		String typeLine = null;	//首部行 Content-Type：实体主体中对象类型
		String entityBody = null;	//实体主体
		
		//以空格字符分解请求行
		StringTokenizer token = new StringTokenizer(requestLine);
		String method = token.nextToken();
		
		if( method.equals("GET") || method.equals("HEAD")){
			
			fileRoute += token.nextToken().substring(1); //客户机请求对象在服务器的路径		
			
			try{
				FileInputStream fileStream = new FileInputStream(fileRoute); //打开文件
				String type = getFileExtension(fileRoute);	//获取文件扩展名

				//响应报文内容
				statusLine = "HTTP/1.1 200 OK\n";
				timeLine = "Date: " + fullDateFormat.format(date) + "\n";
				lengthLine = "Content-Length: " + (new Integer(fileStream.available())).toString() + "\n";
				typeLine = getContentType(type) + "\n";
				headLine = statusLine + closeLine + timeLine + ServerLine + lengthLine + typeLine + "\n";
				output.print(headLine);				
				
				if( !(method.equals("HEAD")) ){
					//返回实体主体
					byte[] buffer = new byte[1024];
					int bytes = 0;	
					while ((bytes = fileStream.read(buffer)) != -1) {
						output.write(buffer, 0, bytes);
					}
				}
				
				fileStream.close();
				resultLine = "Result: succeed!\n\n";
			}
			catch(IOException e){
				//文件打开异常处理
				statusLine = "HTTP/1.1 404 Not Found\n";
				timeLine = "Date: " + fullDateFormat.format(date) + "\n";
				headLine = statusLine + closeLine + timeLine + ServerLine + "\n";
				output.print(headLine);
				entityBody = "<HTML>" + "<HEAD><TITLE>404 Not Found</TITLE></HEAD>" +
						"<BODY>404 Not Found" + "</BODY></HTML>\n";
				output.write(entityBody.getBytes());
				resultLine = "Result: file is not found!\n\n";
			}
		}
		
	}
	//获取文件扩展名
	private static String getFileExtension(String fileName) {
		
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		} else {
			return "";
		}
	}
	//获取 Content—Type 内容
	private  String getContentType(String suffix) {
		
		String contentType = null;
		switch(suffix){
		
			case "jpe":
			case "jpeg":
			case "jpg":
				contentType = "Content-Type: image/jpeg";
				break;
			case "txt":
			case "stm":
			case "htm":
			case "html":
				contentType = "Content-Type: text/html";
				break;
			case "css":
				contentType = "Content-Type: text/css";
				break;
			case "gif":
				contentType = "Content-Type: image/gif";
				break;
			case "png":
				contentType = "Content-Type: image/png";
				break;
			case "pdf":
				contentType = "Content-Type: application/pdf";
				break;
			case "doc":
			case "docx":
				contentType = "Content-Type: application/msword";
				break;
			case "mp3":
				contentType = "Content-Type: audio/mp3";
				break;
			default:
				resultLine = "无法识别文件格式";
				break;
		}
		return contentType;
	}

}

