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
	 * 获取当前ip输入框的文本
	 */
	public String getIPAddr()
	{
		return frame.ipText.getText();
	}
	/*
	 * 获取当前port输入框的文本
	 */
	public String getPort()
	{
		return frame.portText.getText();
	}
	/*
	 * 获取当前folder框的文本
	 */
	public String getFolder()
	{
		return frame.folderText.getText();
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
		ipText = new JTextArea();
		ipText.setText("127.0.0.1");
		ipText.setEditable(true);
		ipText.setLineWrap(false);
		ipText.setBorder(BorderFactory.createEtchedBorder());
		//port文本框
		JLabel portLabel = new JLabel("Port");
		portText = new JTextArea();
		portText.setText("6789");
		portText.setEditable(true);
		portText.setLineWrap(false);
		portText.setBorder(BorderFactory.createEtchedBorder());
		//folder文本框
		JLabel folderLabel = new JLabel("Folder");
		folderText = new JTextArea();
		//folderText.setText("Choose main folder");
		folderText.setEditable(false);
		folderText.setLineWrap(false);
		folderScrollPane = new JScrollPane(folderText);
		folderScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		folderScrollPane.setBorder(BorderFactory.createEtchedBorder());//设置滚动栏边框
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
		logScrollPane.setPreferredSize(new Dimension(220, 150));
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
		//启动按钮
		startBtn = new JButton("<html><font size=2>Start</font></html>");
		startBtn.setPreferredSize(new Dimension(60, 25));
		
		//将组件加入网格, 用到GBC帮助类(GBC.java)
		contentPane.add(ipLabel, new GBC(0, 0).setAnchor(GBC.WEST).setInsets(0,5,0,0));
		contentPane.add(ipText, new GBC(1, 0).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(2));
		contentPane.add(portLabel, new GBC(0, 1).setAnchor(GBC.WEST).setInsets(0,5,0,0));
		contentPane.add(portText, new GBC(1, 1).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(2));
		contentPane.add(folderLabel, new GBC(0, 2).setAnchor(GBC.WEST).setInsets(0,5,0,0));
		contentPane.add(folderScrollPane, new GBC(1, 2).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(2));
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
	private JPanel contentPane;
	public JTextArea ipText;		//ip文本
	public JTextArea portText;		//端口文本
	public JTextArea folderText;	//文件路径文本
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
	private String sltDir;
	final int WINDOW_WIDTH = 260;
	final int WINDOW_HEIGHT = 360;
}
