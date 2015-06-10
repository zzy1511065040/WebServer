package my.webserver;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;





//jgoodies�����
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
//import com.jgoodies.looks.windows.WindowsLookAndFeel;
//import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
//import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.*;

public class Interface extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public Interface(boolean[] status, int[] threadCount)
	{
		this.runFlag = status[0];
		//this.threadCount = threadCount;
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setVisible(true);
		
		GridBagLayout layout;
		
		//����frame�Ŀ��, �߶ȣ��� platformѡ�񴰿ڵ�λ�ã��̶����ڴ�С
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLocationByPlatform(true);
		setResizable(true);
		
		//����frame��icon��title
		//Image img = kit.getImage("icon.gif");
		//setIconImage(img);
		setTitle("WebServer");
		
		//���ò˵���
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
		
		//���start
		start.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "���������������У���رշ�������޸�", "Error", 0);
		    }
		    });

		//���stop
		turn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "<html><p>Version V1.0.0<br>2015@hust</p></html>", "Message", 1);
			}
			});
		
		//���quit
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			});
		
		//���about
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "<html><p>Version V1.0.0<br>2015@hust<br><a href=https://github.com/glkwhr/WebServer>https://github.com/glkwhr/WebServer</a></p></html>", "Message", 1);
			}
			});
		
		//�����������
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(contentPane);
		
		layout = new GridBagLayout();
		contentPane.setLayout(layout);
		
		//ip�ı���
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
		//port�ı���
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
		//folder�ı���
		JLabel folderLabel = new JLabel("Folder");
		folderText = new JTextField();
		//folderText.setText("Choose main folder");
		folderText.setEditable(false);
		//folderScrollPane = new JScrollPane(folderText);
		//folderScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		folderText.setBorder(BorderFactory.createEtchedBorder());//���ù������߿�
		//folderScrollPane.setPreferredSize(new Dimension(220, 150));
		folderBtn = new JButton("...");
		try {
			sltDir = new File(".").getCanonicalPath();//��õ�ǰ��׼·��
			folderText.setText(sltDir);//Ĭ�ϵ�ǰ·��
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		//���������ť
		folderBtn.setPreferredSize(new Dimension(20, 20));
		folderBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if (StFlag) {
				//	JOptionPane.showMessageDialog(null, "���������������У���رշ�������޸�", "Error", 0);
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
		//log�ı��������
		JLabel logLabel = new JLabel("Log ");
		logText = new JTextArea();
		logText.setWrapStyleWord(true);
		logText.setLineWrap(true);
		logText.setEditable(false);
		//���������
		logScrollPane = new JScrollPane(logText);
		logScrollPane.setPreferredSize(new Dimension(logScrollPane.getWidth(), 150));
		//���logText���ݰ�ť
		cleanBtn = new JButton("<html><font size=3>Clean</font></html>");
		cleanBtn.setPreferredSize(new Dimension(60, 25));
		cleanBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logText.setText("");
				counter = 0;
			}
		});
		//Ӧ�õ�ǰ���ð�ť
		applyBtn = new JButton("<html><font size=3>Apply</font></html>");
		applyBtn.setPreferredSize(new Dimension(60, 25));
		applyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//���applyʱ����ǰ������Ӧ��
				if( isIP(ipText.getText()) )
				{	//���IP����������ǺϷ�ip��ַ��ʽ
					goodIP = true; //IP�Ϸ�
					curIPAddr = ipText.getText();
				}
				else
				{
					goodIP = false; //IP���Ϸ�
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
				{	//���Port����������ǺϷ�port
					goodPort = true; //port�Ϸ�
					curPort = temp;
				}
				else
				{
					goodPort = false; //port���Ϸ�
					portText.setText("");
					JOptionPane.showMessageDialog(	JOptionPane.getRootFrame(), 
													"Invalid port number!", 
													"ERROR", 
													JOptionPane.ERROR_MESSAGE);
				}
				curDir = sltDir + "\\";
				if(setFlag = goodIP && goodPort)
					//���ip port�ԺϷ�������Ӧ�óɹ�
					applyBtn.setEnabled(false);
			}
		});
		//������ť
		startBtn = new JButton("<html><b><font size=3 color=green>Start</font></b></html>");
		startBtn.setPreferredSize(new Dimension(60, 25));
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(runFlag == true)
				{	//����������ڽ��У����ʱΪstop��ť
					status[0] = runFlag = false; //��ֹ����
					startBtn.setText("<html><b><font size=3 color=green>Start</font></b></html>");
					logText.append("Service stopped\n");
					ipText.setEnabled(true);
					portText.setEnabled(true);
					folderBtn.setEnabled(true);
				}
				else
				{	//��������ڽ��У����ʱΪstart��ť
					if(setFlag == true)
					{	//���Ѿ�����ip��port
						ipText.setEnabled(false);
						portText.setEnabled(false);
						folderBtn.setEnabled(false);
						logText.append("Service started\n");
						status[0] = runFlag = true; //��������
						startBtn.setText("<html><b><font size=3 color=red>Stop</font></b></html>");
					}
					else
					{
						JOptionPane.showMessageDialog(	JOptionPane.getRootFrame(), 
								"Apply your settings first!", 
								"ERROR", 
								0);
					}
				}
			}
		});
		
		
		//�������������, �õ�GBC������(GBC.java)
		contentPane.add(ipLabel, new GBC(0, 0).setAnchor(GBC.WEST).setInsets(10,5,0,0));
		contentPane.add(ipText, new GBC(1, 0).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(10,2,2,2));
		contentPane.add(portLabel, new GBC(0, 1).setAnchor(GBC.WEST).setInsets(0,5,0,0));
		contentPane.add(portText, new GBC(1, 1).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(2));
		contentPane.add(folderLabel, new GBC(0, 2).setAnchor(GBC.WEST).setInsets(0,5,0,0));
		contentPane.add(folderText, new GBC(1, 2).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(2));
		contentPane.add(folderBtn, new GBC(2, 2).setAnchor(GBC.CENTER).setInsets(2,0,2,5));
		contentPane.add(logLabel, new GBC(0, 3).setAnchor(GBC.WEST).setInsets(10,5,0,0));
		contentPane.add(logScrollPane, new GBC(0, 4).setSpan(3, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(0,5,0,5));
		contentPane.add(cleanBtn, new GBC(0, 5).setAnchor(GBC.CENTER).setInsets(5,5,0,0));
		contentPane.add(applyBtn, new GBC(1, 5).setAnchor(GBC.CENTER).setInsets(5,5,0,0));
		contentPane.add(startBtn, new GBC(1, 5).setSpan(2, 1).setAnchor(GBC.EAST).setInsets(5,0,0,5));
		
		//��������
		PlasticLookAndFeel.setPlasticTheme(new DesertBluer());
        try {
            //���ù۸�
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
	            // ����������ʽ
	            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
	                    	 + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
	                    	 + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
	                    	 + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
	            // �ж�ip��ַ�Ƿ���������ʽƥ��
	            if (text.matches(regex))
	            	return true;
		 }
		return false;
	}
	/*
	 * ��ȡӦ�õ�ip
	 */
	public String getIPAddr()
	{
		return curIPAddr;
	}
	/*
	 * ��ȡӦ�õĶ˿�
	 */
	public int getPort()
	{
		return curPort;
	}
	/*
	 * ��ȡӦ�õ�Ŀ¼
	 */
	public String getFolder()
	{
		return curDir;
	}
	/*
	 * ���logText
	 */
	public JTextArea getLog()
	{
		return logText;
	}
	/*
	 * ��õ�ǰ����״̬runFlag
	 */
	public boolean getRunFlag()
	{
		return runFlag;
	}
	/*
	 * ���counter
	 */
	public int getCounter()
	{
		return counter;
	}
	public void incCounter()
	{
		++counter;
	}
	public void cleanCounter()
	{
		counter = 0;
	}
	private JPanel contentPane;
	private JTextField ipText;		//ip�ı�
	private JTextField portText;		//�˿��ı�
	private JTextField folderText;	//�ļ�·���ı�
	private JTextArea logText;		//��־�ı�
	private JScrollPane logScrollPane;	//���������
	private JButton folderBtn;		//���������ť
	private JButton cleanBtn;		//���log���ݰ�ť
	private JButton applyBtn;		//��Ӧ�á���ť
	private JButton startBtn;		//����������ť
	private JMenu setting;
	private JMenu help;
	private JFileChooser folderChooser;
	//private JToolBar toolBar;
	
	private int counter = 0; //�������
	private int curPort;		//��ǰӦ�õĶ˿�
	//private int[] threadCount;	//��ǰ�߳�������ֻ����
	private String curIPAddr;	//��ǰӦ�õ�ip
	private String curDir;		//��ǰӦ�õ�Ŀ¼
	private String sltDir;
	
	private boolean runFlag;		//�������ڽ���
	private boolean setFlag = false;		//�����ú�ip�Ͷ˿�
	private boolean goodIP = true; 		//��ǰ����ip�Ƿ�Ϸ�
	private boolean goodPort = true;	//��ǰ����port�Ƿ�Ϸ�
	
	private final int WINDOW_WIDTH = 320;
	private final int WINDOW_HEIGHT = 450;
}