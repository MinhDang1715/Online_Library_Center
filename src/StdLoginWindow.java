//student login GUI, connects with MainUI.java
import javax.swing.JDialog;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StdLoginWindow extends JDialog 
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
	public StdLoginWindow() 
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
		
		setTitle("Student Login");
		getContentPane().setBackground(backgroundColor);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JLabel lblStudentLogin = new JLabel("Student Login");
		lblStudentLogin.setFont(labelFont);
		lblStudentLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudentLogin.setForeground(foregroundColor);
		
		//ID
		JLabel lblID = new JLabel("ID:");
		lblID.setBackground(backgroundColor);
		lblID.setForeground(foregroundColor);
		lblID.setFont(textFont);
		
		JTextField idField = new JTextField();
		idField.setBackground(backgroundColor);
		idField.setFont(textFont);
		idField.setForeground(foregroundColor);
		idField.setColumns(10);
		
		//pass
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBackground(backgroundColor);
		lblPassword.setForeground(foregroundColor);
		lblPassword.setFont(textFont);
		
		JPasswordField passwordField = new JPasswordField();
		passwordField.setBackground(backgroundColor);
		passwordField.setFont(textFont);
		passwordField.setForeground(foregroundColor);
		
		//login
		JButton btnLogin = new JButton("Login");
		btnLogin.setFocusPainted(false);
		btnLogin.setForeground(foregroundColor);
		btnLogin.setFont(textFont);
		btnLogin.setBackground(backgroundColor);
		btnLogin.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String id = idField.getText();
				String pass = String.valueOf(passwordField.getPassword());
				DatabaseConection dc = new DatabaseConection();
				dc.stdLogin(id, pass);
				//if the user is login then dispose the window and disable the action when the window close
				if(dc.getResult())
				{
					dispose();
					removeWindowListener(wa);
					//update the day and time the student log into the system
					String time = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(Calendar.getInstance().getTime());
					dc.stdLoginTime(time,id);
				}
			}
		});
		
		//recover pass
		JLabel lblRecoverPass = new JLabel("Forget password?");
		lblRecoverPass.setForeground(foregroundColor);
		lblRecoverPass.setFont(textFont);
		Font f = lblRecoverPass.getFont();
		//make underline font
		@SuppressWarnings("rawtypes")
		Map fontAtt = f.getAttributes();
		fontAtt.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		lblRecoverPass.setFont(f.deriveFont(fontAtt));
		
		//change color when user focus it
		lblRecoverPass.addMouseMotionListener(new MouseMotionAdapter() 
		{
			@Override
			public void mouseMoved(MouseEvent e) 
			{
				lblRecoverPass.setForeground(new Color(0, 0, 0));
			}
		});

		//bring up forget password window
		lblRecoverPass.addMouseListener(new MouseAdapter() 
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
				lblRecoverPass.setForeground(foregroundColor);
			}
		});
		
		//register
		JLabel lblRegister = new JLabel("Register");
		lblRegister.setForeground(foregroundColor);
		lblRegister.setFont(textFont);
		lblRegister.setFont(f.deriveFont(fontAtt));
		
		//change color when user focus it
		lblRegister.addMouseMotionListener(new MouseMotionAdapter() 
		{
			@Override
			public void mouseMoved(MouseEvent e) 
			{
				lblRegister.setForeground(new Color(0, 0, 0));
			}
		});

		//bring up register window
		lblRegister.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				RegisterWindow regWindow = new RegisterWindow();
				regWindow.RegWindow();
				
				//hide login window
				setVisible(false);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				lblRegister.setForeground(foregroundColor);
			}
		});
		
		//Layout
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblStudentLogin, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
						.addComponent(lblID, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
						.addComponent(idField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
						.addComponent(lblPassword, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
						.addComponent(passwordField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
						.addComponent(lblRegister, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(lblRecoverPass, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnLogin, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(28)
					.addComponent(lblStudentLogin)
					.addGap(18)
					.addComponent(lblID, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
					.addGap(12)
					.addComponent(idField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(23)
					.addComponent(lblPassword, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(lblRegister, GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
					.addGap(16)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRecoverPass, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnLogin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(15))
		);
		getContentPane().setLayout(groupLayout);
	}

	public void LoginWindow() 
	{
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
