import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class UpdateDataBaseMenu extends JDialog 
{

	private static final long serialVersionUID = 1L;
	
	//deafult font, color
	Setting set = new Setting();
	Font labelFont = set.getLabelDefaultFont();
	Font textFont = set.getTextDefaultFont();
	Color backgroundColor = set.getDefaultBackGround();
	Color foregroundColor = set.getDefaultForeGround();
	
	private String id;
	
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
	
	public UpdateDataBaseMenu() 
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
				
		setTitle("What would you like to do?");
		setBackground(backgroundColor);

		
		//title
		JLabel lblNewLabel = new JLabel("Update Database");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(foregroundColor);
		lblNewLabel.setFont(labelFont);
		
		//edit book info from the database
		JButton btnEditBook = new JButton("Edit Book Data");
		btnEditBook.setForeground(foregroundColor);
		btnEditBook.setFont(textFont);
		btnEditBook.setBackground(backgroundColor);
		btnEditBook.setFocusPainted(false);
		//pop up search table to find the book 
		btnEditBook.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				//set empEdit to true 
				BookSearch bookSearch = new BookSearch();
				bookSearch.setEmpEdit(true);
				bookSearch.setID(id);
				bookSearch.BookSearchWindow();	
			}
		});
		
		//add a new book to the database
		JButton btnAddBook = new JButton("Add New Book");
		btnAddBook.setForeground(foregroundColor);
		btnAddBook.setFont(textFont);
		btnAddBook.setBackground(backgroundColor);
		btnAddBook.setFocusPainted(false);
		//pop up window to add a book to a database
		btnAddBook.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				//set empEdit to true 
				BookEditForm addBook = new BookEditForm();
				addBook.setID(id);
				addBook.setEmpAdd(true);
				addBook.BookEditFormWindow();
			}
		});
		
		//delete a book from the database
		JButton btnDeleteBook = new JButton("Delete Book from Database");
		btnDeleteBook.setForeground(foregroundColor);
		btnDeleteBook.setFont(textFont);
		btnDeleteBook.setBackground(backgroundColor);
		btnDeleteBook.setFocusPainted(false);
		//pop up search table to find the book 
		btnDeleteBook.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				//set empDelete to true 
				BookSearch bookSearch = new BookSearch();
				bookSearch.setEmpDelete(true);
				bookSearch.setID(id);
				bookSearch.BookSearchWindow();	
			}
		});
		
		//clean up system database
		JButton btnCleanUpSystem = new JButton("Clean Up System Database");
		btnCleanUpSystem.setForeground(foregroundColor);
		btnCleanUpSystem.setFont(textFont);
		btnCleanUpSystem.setFocusPainted(false);
		btnCleanUpSystem.setBackground(backgroundColor);
		//clean up any unused files
		btnCleanUpSystem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				DatabaseConection dc = new DatabaseConection();
				dc.cleanUp();
			}
		});
		
		//layout
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(88)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnCleanUpSystem, GroupLayout.PREFERRED_SIZE, 290, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnEditBook, GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
								.addComponent(btnDeleteBook, GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
								.addComponent(btnAddBook, GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE))
							.addGap(74))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnEditBook)
					.addGap(18)
					.addComponent(btnAddBook, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnDeleteBook, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnCleanUpSystem, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(43, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
	}
	
	//set the student ID 
	public void setID(String x)
	{
		id = x;
	}

	public void UpdateDataBaseMenuWindow() 
	{
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
