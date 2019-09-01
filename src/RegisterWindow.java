//this class is the GUI for the resgistrations, connects with the MainUI.java

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JDialog;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class RegisterWindow extends JDialog 
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
	
	public RegisterWindow() 
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
		addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowClosed(WindowEvent e) 
			{
				MainUI mainUI = new MainUI();
				mainUI.setVisible(true);
			}
		});
		setTitle("Register");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setBackground(backgroundColor);

		JLabel lblRegister = new JLabel("Register Window");
		lblRegister.setForeground(foregroundColor);
		lblRegister.setFont(labelFont);
		lblRegister.setHorizontalAlignment(SwingConstants.CENTER);
		
		//ID
		JLabel lblID = new JLabel("ID:");
		lblID.setForeground(foregroundColor);
		lblID.setFont(textFont);
		
		JTextField idField = new JTextField();
		idField.setBackground(backgroundColor);
		idField.setFont(textFont);
		idField.setForeground(foregroundColor);
		idField.setColumns(10);
		
		//password
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(foregroundColor);
		lblPassword.setFont(textFont);
		
		JPasswordField	passwordField = new JPasswordField();
		passwordField.setBackground(backgroundColor);
		passwordField.setForeground(foregroundColor);
		passwordField.setFont(textFont);
		
		//re-enter password
		JLabel lblReenterPassword = new JLabel("Re-enter Password:");
		lblReenterPassword.setForeground(foregroundColor);
		lblReenterPassword.setFont(textFont);
		
		JPasswordField rePasswordField = new JPasswordField();
		rePasswordField.setBackground(backgroundColor);
		rePasswordField.setForeground(foregroundColor);
		rePasswordField.setFont(textFont);
		
		//email
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setForeground(foregroundColor);
		lblEmail.setFont(textFont);
		lblEmail.setBackground(backgroundColor);
		
		JTextField emailField = new JTextField();
		emailField.setForeground(foregroundColor);
		emailField.setBackground(backgroundColor);
		emailField.setFont(textFont);
		emailField.setColumns(10);
		
		//Employee checkbox
		JCheckBox chckbxEmployee = new JCheckBox("Are you an employee?");
		chckbxEmployee.setForeground(foregroundColor);
		chckbxEmployee.setFont(textFont);
		chckbxEmployee.setBackground(backgroundColor);
		chckbxEmployee.setFocusPainted(false);
		
		//employee code
		JLabel employee = new JLabel("Employee code :");
		employee.setForeground(foregroundColor);
		employee.setFont(textFont);
		employee.setBackground(backgroundColor);
		employee.setToolTipText("Leave it blank if you are a student!");
		employee.setVisible(false);
		
		JTextField codeField = new JTextField();
		codeField.setBackground(backgroundColor);
		codeField.setFont(textFont);
		codeField.setForeground(foregroundColor);
		codeField.setColumns(10);
		codeField.setVisible(false);
		
		//if check box is check the make the employee code label and field visible
		chckbxEmployee.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(!codeField.isVisible() && !employee.isVisible())
				{
					employee.setVisible(true);
					codeField.setVisible(true);
					pack();
				}
				else
				{
					employee.setVisible(false);
					codeField.setVisible(false);
					pack();
				}
			}
		});
		
		//name
		JLabel lblName = new JLabel("Name:");
		lblName.setVerticalAlignment(SwingConstants.BOTTOM);
		lblName.setForeground(foregroundColor);
		lblName.setFont(textFont);
		
		JTextField nameField = new JTextField();
		nameField.setForeground(foregroundColor);
		nameField.setFont(textFont);
		nameField.setBackground(backgroundColor);
		nameField.setColumns(10);
		
		//register
		JButton btnRegister = new JButton("Sign-up");
		btnRegister.setFocusPainted(false);
		btnRegister.setForeground(foregroundColor);
		btnRegister.setFont(textFont);
		btnRegister.setBackground(backgroundColor);
		btnRegister.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String id = idField.getText();
				String name = nameField.getText();
				String pass = String.valueOf(passwordField.getPassword());
				String email = emailField.getText(); 
				String rePass = String.valueOf(rePasswordField.getPassword());
				String employeeCode = "12345";
				
				//option message class
				Options op = new Options();
				
				//check if the password is correct
				if(!rePass.equals(pass))
				{
					UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
					UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
					UIManager.put("OptionPane.buttonFont", textFont);
					UIManager.put("Button.background", backgroundColor);
					UIManager.put("Button.foreground", foregroundColor);
					JOptionPane.showConfirmDialog(null, op.regPassMismatch(), "Error!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
				}
				//check if any input field is empty
				else if(idField.getText().isEmpty() || nameField.getText().isEmpty() || emailField.getText().isEmpty() || pass.isEmpty() || rePass.isEmpty())
				{
					UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
					UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
					UIManager.put("OptionPane.buttonFont", textFont);
					UIManager.put("Button.background", backgroundColor);
					UIManager.put("Button.foreground", foregroundColor);
					JOptionPane.showConfirmDialog(null, op.regEmpty(), "Error!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
				}
				//if the employee code is leave blank when the check box is chosen or the checkbox is not chosen then register the as an a student
				else if(codeField.getText().isEmpty() || !chckbxEmployee.isSelected())
				{
					DatabaseConection db = new DatabaseConection();
					db.stdReg(id, name, pass, email);
				}
				//check if employee code is enter correctly
				else if(!codeField.getText().equals(employeeCode))
				{
					UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
					UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
					UIManager.put("OptionPane.buttonFont", textFont);
					UIManager.put("Button.background", backgroundColor);
					UIManager.put("Button.foreground", foregroundColor);
					JOptionPane.showConfirmDialog(null, op.regWrongCode(), "Error!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
				}
				//if correct then register as as an employee
				else if(codeField.getText().equals(employeeCode))
				{
					DatabaseConection db = new DatabaseConection();
					db.empReg(id, name, pass, email);
				}
			}
		});
		
		//Layout
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(158)
					.addComponent(btnRegister, GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
					.addGap(172))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(codeField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
						.addComponent(emailField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
						.addComponent(lblID, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
						.addComponent(employee, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
						.addComponent(lblEmail, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
						.addComponent(chckbxEmployee, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(idField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE))
					.addGap(86)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblReenterPassword, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblName, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED))
								.addComponent(lblPassword, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE))
							.addGap(0))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
							.addGap(44))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(nameField, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
							.addGap(45))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(rePasswordField, GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
							.addGap(46))))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(116)
					.addComponent(lblRegister, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(144))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblRegister, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblID, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
						.addComponent(lblName, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(idField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(nameField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPassword, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
						.addComponent(lblEmail, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(emailField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(chckbxEmployee, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(employee, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
							.addGap(2))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(0)
							.addComponent(lblReenterPassword, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rePasswordField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addGap(10)))
					.addGap(5)
					.addComponent(codeField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnRegister, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(8))
		);
		getContentPane().setLayout(groupLayout);
	}
	
	public void RegWindow()
	{
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
