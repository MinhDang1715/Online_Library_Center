/*this class will give the user the basic information when he click on the Book to Return table in the main GUI for the student
* connects with StudentView.java
*/

import javax.swing.JDialog;
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;

public class BookReturnDetail_Window extends JDialog 
{
	private static final long serialVersionUID = 1L;
	
	//deafult font, color
	Setting set = new Setting();
	Font labelFont = set.getLabelDefaultFont();
	Font textFont = set.getTextDefaultFont();
	Color backgroundColor = set.getDefaultBackGround();
	Color foregroundColor = set.getDefaultForeGround();

	JLabel lblTitleField;
	JLabel lblDateReturn;
	
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
	
	public BookReturnDetail_Window() 
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
		
		setTitle("Book Info");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBackground(backgroundColor);
		
		//title
		JLabel lblInfo = new JLabel("Book Info");
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo.setForeground(foregroundColor);
		lblInfo.setFont(labelFont);
		
		//book title
		JLabel lblTitle = new JLabel("Book To Return:");
		lblTitle.setForeground(foregroundColor);
		lblTitle.setFont(textFont);
		
		lblTitleField = new JLabel("");
		lblTitleField.setForeground(foregroundColor);
		lblTitleField.setFont(textFont);
		
		//return date
		JLabel lblDateToReturn = new JLabel("Date To Return:");
		lblDateToReturn.setForeground(foregroundColor);
		lblDateToReturn.setFont(textFont);
		
		lblDateReturn = new JLabel("");
		lblDateReturn.setForeground(foregroundColor);
		lblDateReturn.setFont(textFont);
			
		//layout
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(lblInfo, GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDateToReturn, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTitleField, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
						.addComponent(lblDateReturn, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblInfo, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTitle)
						.addComponent(lblTitleField, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDateToReturn, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDateReturn, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(30, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
	}
	
	public void BookReturnDetailWindow() 
	{
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
