/*this class will give the user the basic information when he click on the Book to Pick Up table in the main GUI for the student
* connects with StudentView.java
*/

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JDialog;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

public class BookPickUpDetail_Window extends JDialog 
{
	private static final long serialVersionUID = 1L;
	
	//deafult font, color
	Setting set = new Setting();
	Font labelFont = set.getLabelDefaultFont();
	Font textFont = set.getTextDefaultFont();
	Color backgroundColor = set.getDefaultBackGround();
	Color foregroundColor = set.getDefaultForeGround();
	
	JLabel lblDatePickUpDetail;
	JLabel lblBookNameDetail;
	
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
	
	public BookPickUpDetail_Window() 
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
		
		//dialog setting
		setBackground(backgroundColor);
		setTitle("Book Info");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//title of the window
		JLabel lblBookInfo = new JLabel("Book Info");
		lblBookInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookInfo.setForeground(foregroundColor);
		lblBookInfo.setFont(labelFont);
		
		//book name info
		JLabel lblBookName = new JLabel("Book To Pick Up:");
		lblBookName.setForeground(foregroundColor);
		lblBookName.setFont(textFont);
		
		lblBookNameDetail = new JLabel("");
		lblBookNameDetail.setForeground(foregroundColor);
		lblBookNameDetail.setFont(textFont);
		
		//date to pick up info
		JLabel lblDateToPickUp = new JLabel("Date To Pick Up:");
		lblDateToPickUp.setForeground(foregroundColor);
		lblDateToPickUp.setFont(textFont);

		lblDatePickUpDetail = new JLabel("");
		lblDatePickUpDetail.setForeground(foregroundColor);
		lblDatePickUpDetail.setFont(textFont);
		
		//layout
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblBookName, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblBookNameDetail, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblDateToPickUp, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblDatePickUpDetail, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)))
					.addContainerGap())
				.addComponent(lblBookInfo, GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblBookInfo, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblBookName, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblBookNameDetail, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDatePickUpDetail, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDateToPickUp, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(33, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
	}
	
	public void BookPickUpDetailWindow() 
	{
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
