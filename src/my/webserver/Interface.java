package my.webserver;

import java.awt.*;
//import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

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
	public MyFrame frame;
}

class MyFrame extends JFrame{
	public MyFrame()
	{
		GridBagLayout layout;
		
		//set frame width, height and let platform pick screen location
		setSize(280, 380);
		setLocationByPlatform(true);
		setResizable(false);
		
		//set frame icon and title
		//Image img = kit.getImage("icon.gif");
		//setIconImage(img);
		setTitle("WebServer");
		
		//set content pane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(contentPane);
		
		layout = new GridBagLayout();
		contentPane.setLayout(layout);
		
		JLabel ipLabel = new JLabel("IP");
		ipText = new JTextArea();
		ipText.setText("127.0.0.1");
		ipText.setEditable(true);
		ipText.setLineWrap(false);
		ipText.setBorder(BorderFactory.createEtchedBorder());
		JLabel portLabel = new JLabel("Port");
		portText = new JTextArea();
		portText.setText("6789");
		portText.setEditable(true);
		portText.setLineWrap(false);
		portText.setBorder(BorderFactory.createEtchedBorder());
		JLabel folderLabel = new JLabel("Folder");
		folderText = new JTextArea();
		folderText.setText("Choose main folder");
		folderText.setEditable(false);
		folderText.setLineWrap(false);
		folderText.setBorder(BorderFactory.createEtchedBorder());
		folderBtn = new JButton("...");
		folderBtn.setPreferredSize(new Dimension(20, 20));;
		JLabel logLabel = new JLabel("Log ");
		logText = new JTextArea();
		logText.setWrapStyleWord(true);
		logText.setLineWrap(true);
		logText.setEditable(false);
		scrollPane = new JScrollPane(logText);
		scrollPane.setPreferredSize(new Dimension(220, 150));
		
		//add components to grid, using GBC convenience class
		contentPane.add(ipLabel, new GBC(0, 0).setAnchor(GBC.WEST).setInsets(0,5,0,0));
		contentPane.add(ipText, new GBC(1, 0).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(2));
		contentPane.add(portLabel, new GBC(0, 1).setAnchor(GBC.WEST).setInsets(0,5,0,0));
		contentPane.add(portText, new GBC(1, 1).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(2));
		contentPane.add(folderLabel, new GBC(0, 2).setAnchor(GBC.WEST).setInsets(0,5,0,0));
		contentPane.add(folderText, new GBC(1, 2).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(2));
		contentPane.add(folderBtn, new GBC(2, 2).setAnchor(GBC.CENTER).setInsets(2,0,2,5));
		contentPane.add(logLabel, new GBC(0, 3).setAnchor(GBC.WEST).setInsets(20,5,0,0));
		contentPane.add(scrollPane, new GBC(0, 4).setSpan(3, 1).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(0,5,0,5));
	}
	private JPanel contentPane;
	private JTextArea ipText;		//ip文本
	private JTextArea portText;		//端口文本
	private JTextArea folderText;	//文件路径文本
	public JTextArea logText;		//日志文本
	public JScrollPane scrollPane;	//滚动条面板
	private JButton folderBtn;		//“浏览”按钮
}
