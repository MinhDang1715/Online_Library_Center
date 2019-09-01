//lets the user recover his password. connects with StdLoginWindow.java and EmpLoginWindow.java
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.plaf.ColorUIResource;

public class RecoverPassword extends JDialog 
{
	private static final long serialVersionUID = 1L;

	//deafult font, color
	Setting set = new Setting();
	Font labelFont = set.getLabelDefaultFont();
	Font textFont = set.getTextDefaultFont();
	Color backgroundColor = set.getDefaultBackGround();
	Color foregroundColor = set.getDefaultForeGround();
	private JTextField emailField;
	private JTextField idField;

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
		
	public RecoverPassword() 
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
						
		setTitle("Please enter these information to recover your password");
		getContentPane().setBackground(backgroundColor);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//email
		JLabel lblEmail = new JLabel("Please enter your email:");
		lblEmail.setForeground(foregroundColor);
		lblEmail.setFont(textFont);
		
		emailField = new JTextField();
		emailField.setBackground(backgroundColor);
		emailField.setFont(textFont);
		emailField.setForeground(foregroundColor);
		emailField.setColumns(10);
		
		//id
		JLabel lblID = new JLabel("Please enter your ID:");
		lblID.setForeground(foregroundColor);
		lblID.setFont(textFont);
		
		idField = new JTextField();
		idField.setBackground(backgroundColor);
		idField.setForeground(foregroundColor);
		idField.setFont(textFont);
		idField.setColumns(10);
		
		//employee check
		JCheckBox chckbxEmployee = new JCheckBox("Are you an employee?");
		chckbxEmployee.setForeground(foregroundColor);
		chckbxEmployee.setFont(textFont);
		chckbxEmployee.setBackground(backgroundColor);
		chckbxEmployee.setFocusPainted(false);
		
		//recover pass
		JButton btnRecoverPass = new JButton("Recover Password");
		btnRecoverPass.setBackground(backgroundColor);
		btnRecoverPass.setForeground(foregroundColor);
		btnRecoverPass.setFont(textFont);
		btnRecoverPass.setFocusPainted(false);
		//check for the user id and email 
		btnRecoverPass.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				//make sure the user had enter 2 fields
				if(idField.getText().isEmpty() || emailField.getText().isEmpty())
				{
					Options op = new Options();
					UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
					UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
					UIManager.put("OptionPane.buttonFont", textFont);
					UIManager.put("Button.background", backgroundColor);
					UIManager.put("Button.foreground", foregroundColor);
					JOptionPane.showConfirmDialog(null, op.regEmpty(), "Error!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
				}
				else
				{
					if(chckbxEmployee.isSelected())
					{
						DatabaseConection dc = new DatabaseConection();
						dc.empRecoverPass(idField.getText(), emailField.getText());
					}
					else
					{
						DatabaseConection dc = new DatabaseConection();
						dc.stdRecoverPass(idField.getText(), emailField.getText());
					}
				}
			}
		});
		
		//layout
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(lblID, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblEmail, GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(idField, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
										.addComponent(emailField, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)))
								.addComponent(chckbxEmployee, GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGap(474)
							.addComponent(btnRecoverPass, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEmail)
						.addComponent(emailField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblID, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
						.addComponent(idField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(chckbxEmployee)
					.addGap(18)
					.addComponent(btnRecoverPass)
					.addContainerGap(30, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
	}
	
	public void RecoverPasswordWindow() 
	{
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
