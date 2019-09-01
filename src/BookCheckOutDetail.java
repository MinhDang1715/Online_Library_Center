//this class give the employee the basic information of the book the students want to check out, connects with CheckOutBook.java, and ReturnBook.java

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;

public class BookCheckOutDetail extends JDialog 
{
	private static final long serialVersionUID = 1L;
	
	//deafult font, color
	Setting set = new Setting();
	Font labelFont = set.getLabelDefaultFont();
	Font textFont = set.getTextDefaultFont();
	Color backgroundColor = set.getDefaultBackGround();
	Color foregroundColor = set.getDefaultForeGround();

	JLabel lblNameField;
	JLabel lblIDField;
	JLabel lblDateCheckOutField;
	JLabel lblDateReturnField;
	JLabel lblTitleField;
	JLabel lblISBNField;
	
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
	
	public BookCheckOutDetail() 
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
				
		setTitle("Book detail");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBackground(backgroundColor);
		
		//title label
		JLabel lblInfo = new JLabel("Book Info");
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo.setForeground(foregroundColor);
		lblInfo.setFont(labelFont);
		
		//name
		JLabel lblName = new JLabel("Name:");
		lblName.setForeground(foregroundColor);
		lblName.setFont(textFont);
		
		
		lblNameField = new JLabel("");
		lblNameField.setForeground(foregroundColor);
		lblNameField.setFont(textFont);
		
		//id
		JLabel  lblID = new JLabel("ID:");
		lblID.setForeground(foregroundColor);
		lblID.setFont(textFont);

		lblIDField = new JLabel("");
		lblIDField.setForeground(foregroundColor);
		lblIDField.setFont(textFont);
		
		//date check out
		JLabel lblDateCheckOut = new JLabel("Date check out:");
		lblDateCheckOut.setForeground(foregroundColor);
		lblDateCheckOut.setFont(textFont);
		
		lblDateCheckOutField = new JLabel("");
		lblDateCheckOutField.setForeground(foregroundColor);
		lblDateCheckOutField.setFont(textFont);
		
		//date return
		JLabel lblDateReturn = new JLabel("Date to return:");
		lblDateReturn.setForeground(foregroundColor);
		lblDateReturn.setFont(textFont);
		
		lblDateReturnField = new JLabel("");
		lblDateReturnField.setForeground(foregroundColor);
		lblDateReturnField.setFont(textFont);
		
		//book title
		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setForeground(foregroundColor);
		lblTitle.setFont(textFont);
		
		lblTitleField = new JLabel("");
		lblTitleField.setForeground(foregroundColor);
		lblTitleField.setFont(textFont);
		
		//boook ISBN
		JLabel lblISBN = new JLabel("ISBN:");
		lblISBN.setForeground(foregroundColor);
		lblISBN.setFont(textFont);
		
		lblISBNField = new JLabel("");
		lblISBNField.setForeground(foregroundColor);
		lblISBNField.setFont(textFont);
		
		//layout
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(lblInfo, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblDateCheckOut, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblDateCheckOutField, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
					.addGap(172))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblDateReturn, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblDateReturnField, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
					.addGap(189))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblTitleField, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
							.addGap(9)
							.addComponent(lblNameField, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblID, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblIDField, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblISBN, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblISBNField, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblInfo, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblName)
									.addComponent(lblNameField, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblID, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblISBN, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblISBNField, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
									.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblTitleField, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblDateCheckOut, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblDateCheckOutField, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblDateReturn, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblDateReturnField, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)))
						.addComponent(lblIDField, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(30, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
	}
	
	public void BookCheckOutDetailWindow() 
	{
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
