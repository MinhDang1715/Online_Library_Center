//employee login GUI, connect with MainUI.java
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EmpLoginWindow extends JDialog 
{
	private static final long serialVersionUID = 1L;
	
	//deafult font, color
	Setting set = new Setting();
	Font labelFont = set.getLabelDefaultFont();
	Font textFont = set.getTextDefaultFont();
	Color backgroundColor = set.getDefaultBackGround();
	Color foregroundColor = set.getDefaultForeGround(); 
	
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
	
	@SuppressWarnings("unchecked")
	public EmpLoginWindow()
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
		
		//if the window close the return to mainUI
		WindowAdapter wa = new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) 
			{
				MainUI mainUI = new MainUI();
				mainUI.setVisible(true);
			}
		};
		
		addWindowListener(wa);

		setTitle("Employee Login");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setBackground(backgroundColor);
		
		//name of window
		JLabel lblEmployeeLogin = new JLabel("Employee Login");
		lblEmployeeLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmployeeLogin.setForeground(foregroundColor);
		lblEmployeeLogin.setFont(labelFont);
		
		//ID
		JLabel lblID = new JLabel("ID:");
		lblID.setForeground(foregroundColor);
		lblID.setFont(textFont);
		
		JTextField idField = new JTextField();
		idField.setBackground(backgroundColor);
		idField.setFont(textFont);
		idField.setForeground(foregroundColor);
		idField.setColumns(10);
		
		//pass
		JLabel lblPass = new JLabel("Password:");
		lblPass.setForeground(foregroundColor);
		lblPass.setFont(textFont);
		
		JPasswordField passwordField = new JPasswordField();
		passwordField.setBackground(backgroundColor);
		passwordField.setFont(textFont);
		passwordField.setForeground(foregroundColor);
		
		//login
		JButton btnLogin = new JButton("Login");
		btnLogin.setForeground(foregroundColor);
		btnLogin.setFont(textFont);
		btnLogin.setFocusPainted(false);
		btnLogin.setBackground(backgroundColor);
		btnLogin.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String id = idField.getText();
				String pass = String.valueOf(passwordField.getPassword());
				DatabaseConection dc = new DatabaseConection();
				dc.empLogin(id, pass);
				//if the user is login then dispose the window and disable the action when the window close
				if(dc.getResult())
				{
					dispose();
					removeWindowListener(wa);
					//update the day and time the employee log into the system
					String time = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(Calendar.getInstance().getTime());
					dc.empLoginTime(time,id);
				}
			}
		});

		//register
		JLabel lblReg = new JLabel("Register");
		lblReg.setForeground(foregroundColor);
		lblReg.setFont(textFont);
		//make underline font
		Font f = lblReg.getFont();
		@SuppressWarnings("rawtypes")
		Map fontAtt = f.getAttributes();
		fontAtt.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		lblReg.setFont(f.deriveFont(fontAtt));
		
		//change color when user focus it
		lblReg.addMouseMotionListener(new MouseMotionAdapter() 
		{
			@Override
			public void mouseMoved(MouseEvent e) 
			{
				lblReg.setForeground(new Color(0, 0, 0));
			}
		});
		
		//bring up register window
		lblReg.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				RegisterWindow regWindow = new RegisterWindow();
				regWindow.RegWindow();
				
				//dispose login window
				setVisible(false);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				lblReg.setForeground(foregroundColor);
			}
		});
		
		//recover pass
		JLabel lblForgetPass = new JLabel("Forget password?");
		lblForgetPass.setForeground(foregroundColor);
		lblForgetPass.setFont(textFont);
		
		//make underline font
		lblForgetPass.setFont(f.deriveFont(fontAtt));
		
		//change color when user focus it
		lblForgetPass.addMouseMotionListener(new MouseMotionAdapter() 
		{
			@Override
			public void mouseMoved(MouseEvent e) 
			{
				lblForgetPass.setForeground(new Color(0, 0, 0));
			}
		});
	
		//bring up forget password window
		lblForgetPass.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				RecoverPassword rp = new RecoverPassword();
				rp.RecoverPasswordWindow();
			}
			//change to default color
			@Override
			public void mouseExited(MouseEvent e) 
			{
				lblForgetPass.setForeground(foregroundColor);
			}
		});
		
		//Layout
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblReg, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblPass, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblForgetPass, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
									.addGap(18)
									.addComponent(btnLogin, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED))
								.addComponent(idField, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
								.addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblEmployeeLogin, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
										.addComponent(lblID, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))
									.addGap(2)))
							.addGap(12))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(27)
					.addComponent(lblEmployeeLogin, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblID, GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(idField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(29)
					.addComponent(lblPass, GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(19)
					.addComponent(lblReg, GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
					.addGap(15)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblForgetPass, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
						.addComponent(btnLogin, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
					.addGap(21))
		);
		getContentPane().setLayout(groupLayout);
	}
	
	public void EmpWindow()
	{
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
