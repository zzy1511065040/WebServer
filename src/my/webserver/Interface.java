package my.webserver;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;




//jgoodies主题包
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
//import com.jgoodies.looks.windows.WindowsLookAndFeel;
//import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
//import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.*;

public class Interface {
	public Interface()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				frame = new MyFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});//magic
	}
	/*
	 * 获取应用的ip
	 */
	public String getIPAddr()
	{
		return frame.curIPAddr;
	}
	/*
	 * 获取应用的端口
	 */
	public int getPort()
	{
		return frame.curPort;
	}
	/*
	 * 获取应用的目录
	 */
	public String getFolder()
	{
		return frame.curDir;
	}
	/*
	 * 获得logText
	 */
	public JTextArea getLog()
	{
		return frame.logText;
	}
	private MyFrame frame;
}

class MyFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	public MyFrame()
	{
		GridBagLayout layout;
		
		//设置frame的宽度, 高度；由 platform选择窗口的位置；固定窗口大小
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLocationByPlatform(true);
		setResizable(false);
		
		//设置frame的icon和title
		//Image img = kit.getImage("icon.gif");
		//setIconImage(img);
		setTitle("WebServer");
		
		//设置菜单栏
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		setting = new JMenu("Setting");
		menuBar.add(setting);
		JMenuItem start = new JMenuItem("Start");
		setting.add(start);
		JMenuItem turn = new JMenuItem("Stop");
		setting.add(turn);
		JMenuItem quit = new JMenuItem("Quit");
		setting.add(quit);
		help = new JMenu("Help");
		menuBar.add(help);
		JMenuItem about = new JMenuItem("About");
		help.add(about);
		
		//设置内容面板
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(contentPane);
		
		layout = new GridBagLayout();
		contentPane.setLayout(layout);
		
		//ip文本框
		JLabel ipLabel = new JLabel("IP");
		ipText = new JTextField("127.0.0.1");
		ipText.setEditable(true);
		ipText.setBorder(BorderFactory.createEtchedBorder());
		ipText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				applyBtn.setEnabled(true);
			}
		});
		//port文本框
		JLabel portLabel = new JLabel("Port");
		portText = new JTextField("6789");
		portText.setEditable(true);
		portText.setBorder(BorderFactory.createEtchedBorder());
		portText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				applyBtn.setEnabled(true);
			}
		});
		//folder文本框
		JLabel folderLabel = new JLabel("Folder");
		folderText = new JTextField();
		//folderText.setText("Choose main folder");
		folderText.setEditable(false);
		//folderScrollPane = new JScrollPane(folderText);
		//folderScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		folderText.setBorder(BorderFactory.createEtchedBorder());//设置滚动栏边框
		//folderScrollPane.setPreferredSize(new Dimension(220, 150));
		folderBtn = new JButton("...");
		try {
			sltDir = new File(".").getCanonicalPath();//获得当前标准路径
			folderText.setText(sltDir);//默认当前路径
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		//“浏览”按钮
		folderBtn.setPreferredSize(new Dimension(20, 20));
		folderBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if (StFlag) {
				//	JOptionPane.showMessageDialog(null, "服务器正在运行中，请关闭服务后再修改", "Error", 0);
				//	return;
				//}				
				folderChooser = new JFileChooser();
				folderChooser.setCurrentDirectory(new File("."));
				folderChooser.setDialogTitle("Select a folder");
				folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				folderChooser.setAcceptAllFileFilterUsed(false);				
				if (folderChooser.showOpenDialog(folderBtn) == JFileChooser.APPROVE_OPTION) {
					sltDir = folderChooser.getSelectedFile().toString();
					folderText.setText(sltDir);
				}
				applyBtn.setEnabled(true);
			}
		});
		//log文本框（输出）
		JLabel logLabel = new JLabel("Log ");
		logText = new JTextArea();
		logText.setWrapStyleWord(true);
		logText.setLineWrap(true);
		logText.setEditable(false);
		//滚动条面板
		logScrollPane = new JScrollPane(logText);
		logScrollPane.setPreferredSize(new Dimension(logScrollPane.getWidth(), 150));
		//清空logText内容按钮
		cleanBtn = new JButton("<html><font size=2>Clean</font></html>");
		cleanBtn.setPreferredSize(new Dimension(60, 25));
		cleanBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logText.setText("");
			}
		});
		//应用当前设置按钮
		applyBtn = new JButton("<html><font size=2>Apply</font></html>");
		applyBtn.setPreferredSize(new Dimension(60, 25));
		applyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//点击apply时将当前的设置应用
				if( isIP(ipText.getText()) )
				{	//如果IP框中输入的是合法ip地址格式
					goodIP = true; //IP合法
					curIPAddr = ipText.getText();
				}
				else
				{
					goodIP = false; //IP不合法
					ipText.setText("");
					JOptionPane.showMessageDialog(	JOptionPane.getRootFrame(), 
												  	"Invalid IP address!", 
												  	"ERROR", 
												  	JOptionPane.ERROR_MESSAGE);					
				}
				int temp;
				if( portText.getText().length() < 7
						&& portText.getText().matches("[0-9]+")
						&& (temp = Integer.parseInt(portText.getText().trim())) > 0 
						&& temp < 65536 )
				{	//如果Port框中输入的是合法port
					goodPort = true; //port合法
					curPort = temp;
				}
				else
				{
					goodPort = false; //port不合法
					portText.setText("");
					JOptionPane.showMessageDialog(	JOptionPane.getRootFrame(), 
													"Invalid port number!", 
													"ERROR", 
													JOptionPane.ERROR_MESSAGE);
				}
				curDir = sltDir + "\\";
				if(setFlag = goodIP && goodPort)
					//如果ip port皆合法则设置应用成功
					applyBtn.setEnabled(false);
			}
		});
		//启动按钮
		startBtn = new JButton("<html><b><font size=3 color=green>Start</font></b></html>");
		startBtn.setPreferredSize(new Dimension(60, 25));
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(runFlag)
				{	//如果服务正在进行，则此时为stop按钮
					runFlag = false; //终止服务
					startBtn.setText("<html><b><font size=3 color=green>Start</font></b></html>");
					logText.append("Stoping...\n");
				}
				else
				{	//如果服务不在进行，则此时为start按钮
					if(setFlag)
					{	//若已经设置ip和port
						logText.append("Starting...\n");
						runFlag = true; //启动服务
						startBtn.setText("<html><b><font size=3 color=red>Stop</font></b></html>");
					}
					else
					{
						JOptionPane.showMessageDialog(	JOptionPane.getRootFrame(), 
								"Apply your settings first!", 
								"ERROR", 
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		//将组件加入网格, 用到GBC帮助类(GBC.java)
		contentPane.add(ipLabel, new GBC(0, 0).setAnchor(GBC.WEST).setInsets(0,5,0,0));
		contentPane.add(ipText, new GBC(1, 0).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(2));
		contentPane.add(portLabel, new GBC(0, 1).setAnchor(GBC.WEST).setInsets(0,5,0,0));
		contentPane.add(portText, new GBC(1, 1).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(2));
		contentPane.add(folderLabel, new GBC(0, 2).setAnchor(GBC.WEST).setInsets(0,5,0,0));
		contentPane.add(folderText, new GBC(1, 2).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(2));
		contentPane.add(folderBtn, new GBC(2, 2).setAnchor(GBC.CENTER).setInsets(2,0,2,5));
		contentPane.add(logLabel, new GBC(0, 3).setAnchor(GBC.WEST).setInsets(20,5,0,0));
		contentPane.add(logScrollPane, new GBC(0, 4).setSpan(3, 1).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(0,5,0,5));
		contentPane.add(cleanBtn, new GBC(0, 5).setAnchor(GBC.CENTER).setInsets(5,5,0,0));
		contentPane.add(applyBtn, new GBC(1, 5).setAnchor(GBC.CENTER).setInsets(5,5,0,0));
		contentPane.add(startBtn, new GBC(1, 5).setSpan(2, 1).setAnchor(GBC.EAST).setInsets(5,0,0,5));
		
		//设置主题
		PlasticLookAndFeel.setPlasticTheme(new DesertBluer());
        try {
            //设置观感
            UIManager.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
            //UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticLookAndFeel");
            //UIManager.setLookAndFeel("com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
            //UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {}
	}
	
	private boolean isIP(String text)
	{
		 if (text != null && !text.isEmpty()) {
	            // 定义正则表达式
	            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
	                    	 + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
	                    	 + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
	                    	 + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
	            // 判断ip地址是否与正则表达式匹配
	            if (text.matches(regex))
	            	return true;
		 }
		return false;
	}
	
	private JPanel contentPane;
	public JTextField ipText;		//ip文本
	public JTextField portText;		//端口文本
	public JTextField folderText;	//文件路径文本
	public JTextArea logText;		//日志文本
	private JScrollPane logScrollPane;	//滚动条面板
	private JScrollPane folderScrollPane;
	private JButton folderBtn;		//“浏览”按钮
	private JButton cleanBtn;		//清除log内容按钮
	private JButton applyBtn;		//“应用”按钮
	private JButton startBtn;		//“启动”按钮
	private JMenu setting;
	private JMenu help;
	private JFileChooser folderChooser;
	
	public String curIPAddr;	//当前应用的ip
	public int curPort;		//当前应用的端口
	public String curDir;		//当前应用的目录
	private String sltDir;
	
	public boolean runFlag = false;		//服务正在进行
	public boolean setFlag = false;		//已设置好ip和端口
	private boolean goodIP = true; 		//当前输入ip是否合法
	private boolean goodPort = true;	//当前输入port是否合法
	
	final int WINDOW_WIDTH = 320;
	final int WINDOW_HEIGHT = 400;
}
