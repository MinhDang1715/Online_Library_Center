//the GUI when the user open the program

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainUI extends JFrame 
{
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	//deafult font, color
	Setting set = new Setting();
	Font labelFont = set.getLabelDefaultFont();
	Font textFont = set.getTextDefaultFont();
	Color backgroundColor = set.getDefaultBackGround();
	Color foregroundColor = set.getDefaultForeGround();

	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					MainUI frame = new MainUI();
					frame.setVisible(true);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	//check if there is the setting file
	//if the file is not exist then use the default value
	public void getSettings() throws FileNotFoundException
	{
		File file = new File("lib/settings");
		if(file.exists())
		{
			labelFont = set.getUserLabelFont();
			textFont = set.getUserTextFont();
			backgroundColor = set.getUserBackGround();
			foregroundColor = set.getUserForeGround();
		}
	}

	public MainUI() 
	{
		//check if the user has a custom setting
		try 
		{
			getSettings();
		}
		catch(FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		setTitle("Online Library Center");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//set up contentPane to store all the components
		contentPane = new JPanel();
		contentPane.setBackground(backgroundColor);
		setContentPane(contentPane);

		//Welcome label
		JLabel mainLabel = new JLabel("Online Library Center");
		mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mainLabel.setFont(labelFont);
		mainLabel.setForeground(foregroundColor);
		
		//Student login button
		JButton stdLogin = new JButton("Student login");
		stdLogin.setFocusPainted(false);
		stdLogin.setBackground(backgroundColor);
		stdLogin.setForeground(foregroundColor);
		stdLogin.setFont(textFont);
		stdLogin.setToolTipText("This is where students login to visit the center.");
		//pop up login window
		stdLogin.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				StdLoginWindow stdLoginWin = new StdLoginWindow();
				stdLoginWin.LoginWindow();
				
				//and hide mainUI
				dispose();
			}
		});
		
		//Employee login button
		JButton empLogin = new JButton("Employee login");
		empLogin.setFocusPainted(false);
		empLogin.setBackground(backgroundColor);
		empLogin.setForeground(foregroundColor);
		empLogin.setFont(textFont);
		empLogin.setToolTipText("Grant employees access to the system.");
		//pop up employee login window
		empLogin.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				EmpLoginWindow empLoginWin = new EmpLoginWindow();
				empLoginWin.EmpWindow();
				
				//and hide mainUI
				dispose();
			}
		});

		
		//Register button
		JButton btnRegister = new JButton("Register");
		btnRegister.setFocusPainted(false);
		btnRegister.setBackground(backgroundColor);
		btnRegister.setForeground(foregroundColor);
		btnRegister.setToolTipText("Register for an account to use the library online.");
		btnRegister.setFont(textFont);
		//pop up register window
		btnRegister.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				RegisterWindow stdLoginWin = new RegisterWindow();
				stdLoginWin.RegWindow();
				
				//and hide mainUI
				dispose();
			}
		});
		
		//Setting button
		JButton btnSetting = new JButton();
		btnSetting.setFocusPainted(false);
		btnSetting.setBackground(backgroundColor);
		ImageIcon settingIcon = new ImageIcon("lib/Setting Icon.png");
		btnSetting.setIcon(settingIcon);
		btnSetting.setToolTipText("You can change background, and text color here.");
		//bring up setting window
		btnSetting.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				SettingWindow setWin = new SettingWindow();
				setWin.SetWindow();
				
				//and hide mainUI
				dispose();
			}
		});
		
		//Contentpane Layout
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(49)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnRegister, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
						.addComponent(empLogin, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
						.addComponent(stdLogin, GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE))
					.addGap(57))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(138)
					.addComponent(btnSetting, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
					.addGap(160))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(mainLabel, GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
					.addGap(19))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(48)
					.addComponent(mainLabel, GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
					.addGap(63)
					.addComponent(stdLogin, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
					.addGap(46)
					.addComponent(empLogin, GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
					.addGap(38)
					.addComponent(btnRegister, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
					.addGap(37)
					.addComponent(btnSetting, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGap(37))
		);
		contentPane.setLayout(gl_contentPane);
		
		//set the location to be in the middle of the screen
		pack();
		setLocationRelativeTo(null);
	}
}
