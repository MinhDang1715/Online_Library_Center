//this form is use for edit and add a book to the database, it connecst with BookSearch.java and UpdateDataBaseMenu.java
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.ColorUIResource;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BookEditForm extends JDialog
{
	private static final long serialVersionUID = 1L;
	
	//SQL
	private String url = "jdbc:mysql://localhost:3306/Online Library Center?useSSL=false";
	private String dbUser = "Minh";
	private String dbPass = "ThuongNhu";
	private Connection conn = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;
	
	//deafult font, color
	Setting set = new Setting();
	Font labelFont = set.getLabelDefaultFont();
	Font textFont = set.getTextDefaultFont();
	Color backgroundColor = set.getDefaultBackGround();
	Color foregroundColor = set.getDefaultForeGround();
	
	JTextField bookNameField;
	JTextField isbnField;
	JTextField authorField;
	JTextField categoryField;
	JLabel lblPicture;
	JTextArea bookSummary;
	JTextField summaryNameField;
	JTextField yearField;
	JTextField publisherField;
	
	private String id;
	private String picName = ""; //to store the name of the chosen file
	private String oldPicName = ""; //to hold the old value of the cover name in the database to compare
	private String picFormat = "";
	private boolean empEdit  = false;
	private boolean empAdd  = false;
	private String oldBookISBN;
	private File bookCoverImage;
	
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
	
	public BookEditForm() 
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
		
		setTitle("Book Information Editing");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBackground(backgroundColor);
		//whenever the user close the window, ask them if they want to save the information or not
		addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent e) 
			{
				Options op = new Options();
				UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
				UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
				UIManager.put("OptionPane.buttonFont", textFont);
				UIManager.put("Button.background", backgroundColor);
				UIManager.put("Button.foreground", foregroundColor);
				int reply = JOptionPane.showConfirmDialog(null, op.closeEditForm(), "Message", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(reply == JOptionPane.YES_OPTION)
				{
					//edit the book database
					if(empEdit)
					{
						editDatabase();
						dispose();
					}
					//add a new book to the database
					else if(empAdd)
					{
						addDatabase();
						dispose();
					}
				}
				if(reply == JOptionPane.NO_OPTION)
				{
					if(empEdit)
					{
						//change the bookStatus back to available
						try
						{
							String sql5 = "UPDATE bookInfo SET bookStatus = 'Available' WHERE bookISBN = ?";
							conn = DriverManager.getConnection(url, dbUser, dbPass);
							ps = conn.prepareStatement(sql5);
							ps.setString(1, isbnField.getText());
							ps.executeUpdate();
						}
						catch(SQLException e1) 
						{
							System.out.println("SQLException: " + e1.getMessage());
							System.out.println("SQLState: " + e1.getSQLState());
							System.out.println("VendorError: " + e1.getErrorCode());
						}
						dispose();
					}
					else if(empAdd)
					{
						dispose();
					}
				}
				//close the window and do not save the data
				else if(reply == JOptionPane.NO_OPTION)
				{
					dispose();
				}		
			}
		});
		
		//book name
		JLabel lblBookName = new JLabel("Book Name:");
		lblBookName.setForeground(foregroundColor);
		lblBookName.setFont(textFont);
		
		bookNameField = new JTextField();
		bookNameField.setBackground(backgroundColor);
		bookNameField.setForeground(foregroundColor);
		bookNameField.setFont(textFont);
		bookNameField.setColumns(10);
		
		//isbn
		JLabel lblISBN = new JLabel("ISBN:");
		lblISBN.setForeground(foregroundColor);
		lblISBN.setFont(textFont);
		
		isbnField = new JTextField();
		isbnField.setForeground(foregroundColor);
		isbnField.setFont(textFont);
		isbnField.setColumns(10);
		isbnField.setBackground(backgroundColor);
		
		//author
		JLabel lblAuthor = new JLabel("Author:");
		lblAuthor.setForeground(foregroundColor);
		lblAuthor.setFont(textFont);
		
		authorField = new JTextField();
		authorField.setForeground(foregroundColor);
		authorField.setFont(textFont);
		authorField.setColumns(10);
		authorField.setBackground(backgroundColor);
		
		//category
		JLabel lblCategory = new JLabel("Category:");
		lblCategory.setForeground(foregroundColor);
		lblCategory.setFont(textFont);
		
		categoryField = new JTextField();
		categoryField.setForeground(foregroundColor);
		categoryField.setFont(textFont);
		categoryField.setColumns(10);
		categoryField.setBackground(backgroundColor);
		
		//cover picture
		JLabel lblBookCover = new JLabel("Book Cover:");
		lblBookCover.setForeground(foregroundColor);
		lblBookCover.setFont(textFont);
		
		lblPicture = new JLabel("");
		lblPicture.setSize(400, 500);
		
		JButton btnUploadPictureFile = new JButton("Upload file");
		btnUploadPictureFile.setBackground(backgroundColor);
		btnUploadPictureFile.setForeground(foregroundColor);
		btnUploadPictureFile.setFont(textFont);
		btnUploadPictureFile.setFocusPainted(false);
		//let the user choose a file to upload
		btnUploadPictureFile.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				//let the user choose a file to upload
				JFileChooser fc = new JFileChooser();
				//file filter for fc
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Image File (.jpg, .png, .jpeg)", "jpg", "png", "jpeg");
				fc.setFileFilter(filter);
				//remove "all file" option
				fc.setAcceptAllFileFilterUsed(false);
				fc.setCurrentDirectory(new java.io.File("C:/Users/Desktop"));
				fc.setDialogTitle("Please choose a image file to upload");
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				if(fc.showOpenDialog(btnUploadPictureFile) == JFileChooser.APPROVE_OPTION)
				{
					BufferedImage bi = null;
					try
					{
						bi = ImageIO.read(fc.getSelectedFile());
						//get the selected image to save it later
						bookCoverImage = fc.getSelectedFile();
						picName=fc.getSelectedFile().getName();
						picFormat = picName.substring(picName.length() - 3, picName.length());
					}
					catch(IOException e1)
					{
						e1.printStackTrace();
					}
					//resize image to fit in display
					Image resize = bi.getScaledInstance(lblPicture.getWidth(), lblPicture.getHeight(), Image.SCALE_SMOOTH);
					ImageIcon bookCover = new ImageIcon(resize);
					lblPicture.setIcon(bookCover);
					
				}
			}
		});
		
		//summary
		JLabel lblSummary = new JLabel("Summary File Name:");
		lblSummary.setForeground(foregroundColor);
		lblSummary.setFont(textFont);
		
		JScrollPane scrollPane = new JScrollPane();
		bookSummary = new JTextArea();
		bookSummary.setBackground(backgroundColor);
		bookSummary.setWrapStyleWord(true);
		bookSummary.setLineWrap(true);
		bookSummary.setFont(textFont);
		bookSummary.setForeground(foregroundColor);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		bookSummary.setBorder(border);
		scrollPane.setViewportView(bookSummary);
		
		summaryNameField = new JTextField();
		summaryNameField.setBackground(backgroundColor);
		summaryNameField.setForeground(foregroundColor);
		summaryNameField.setFont(textFont);
		summaryNameField.setColumns(10);
		
		//year 
		JLabel lblYear = new JLabel("Year:");
		lblYear.setForeground(foregroundColor);
		lblYear.setFont(textFont);
		
		yearField = new JTextField();
		yearField.setForeground(foregroundColor);
		yearField.setFont(textFont);
		yearField.setColumns(10);
		yearField.setBackground(backgroundColor);
		
		//publisher
		JLabel lblPublisher = new JLabel("Publisher:");
		lblPublisher.setForeground(foregroundColor);
		lblPublisher.setFont(textFont);
		
		publisherField = new JTextField();
		publisherField.setForeground(foregroundColor);
		publisherField.setFont(textFont);
		publisherField.setColumns(10);
		publisherField.setBackground(backgroundColor);
		
		//save
		JButton btnSave = new JButton("Save");
		btnSave.setForeground(foregroundColor);
		btnSave.setFont(textFont);
		btnSave.setBackground(backgroundColor);
		btnSave.setFocusPainted(false);
		//save the updated ifnormation
		btnSave.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				//edit the book database
				if(empEdit)
				{
					editDatabase();
				}
				//add a new book to the database
				else if(empAdd)
				{
					addDatabase();
				}
			}
		});
		
		//layout
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblBookName, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(bookNameField, GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout.createSequentialGroup()
													.addComponent(lblISBN, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(isbnField, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE))
												.addGroup(groupLayout.createSequentialGroup()
													.addComponent(lblAuthor, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(publisherField, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
														.addComponent(authorField, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE))))
											.addGap(18))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblBookCover, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(btnUploadPictureFile, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addGap(269)))
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblPublisher, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblPicture, GroupLayout.PREFERRED_SIZE, 443, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblCategory, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(categoryField, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblYear, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(yearField, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(310)
									.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblSummary, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(summaryNameField, GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE))
										.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE))))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblBookName, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(bookNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblISBN, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(isbnField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(categoryField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCategory, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAuthor, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(authorField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(yearField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblYear, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPublisher, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(publisherField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(9)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(summaryNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblSummary, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnUploadPictureFile)
							.addComponent(lblBookCover, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)))
					.addGap(13)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPicture, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
	}
	
	//set the employee id
	public void setID(String x)
	{
		id = x;
	}
	
	//set the value to true if it is an employee wants to edit book's information in the database
	public void setEmpEdit(boolean b)
	{
		empEdit = b;
	}
	
	//set the value to true if it is an employee wants to add a book to the database
	public void setEmpAdd(boolean b)
	{
		empAdd = b;
	}
	
	//set the employee id
	public void setBookISBN(String x)
	{
		oldBookISBN = x;
	}
	
	//get the pic name
	public void setPicName(String x)
	{
		picName = x;
		oldPicName = picName;
	}
	//edit book database
	public void editDatabase()
	{
		//make sure all fields are not empty
		if(bookNameField.getText().isEmpty() || isbnField.getText().isEmpty() || yearField.getText().isEmpty() || categoryField.getText().isEmpty() || publisherField.getText().isEmpty() || authorField.getText().isEmpty() || summaryNameField.getText().isEmpty() || bookSummary.getText().isEmpty())
		{
			//make sure the user want to update the data
			Options op = new Options();
			UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
			UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
			UIManager.put("OptionPane.buttonFont", textFont);
			UIManager.put("Button.background", backgroundColor);
			UIManager.put("Button.foreground", foregroundColor);
			JOptionPane.showConfirmDialog(null, op.emptyUpdate(), "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
		}
		else
		{
			//make sure the user want to update the data
			Options op = new Options();
			UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
			UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
			UIManager.put("OptionPane.buttonFont", textFont);
			UIManager.put("Button.background", backgroundColor);
			UIManager.put("Button.foreground", foregroundColor);
			int reply = JOptionPane.showConfirmDialog(null, op.checkUpdateBook(), "Message", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			
			//update if answer yes
			if(reply == JOptionPane.YES_OPTION)
			{
				//update empEdit table, it shows who eidt what and the time he edit it
				String sql = "INSERT INTO empEdit VALUES(?, ?, ?)";
				try
				{
					conn = DriverManager.getConnection(url, dbUser, dbPass);
					ps = conn.prepareStatement(sql);
					ps.setString(1, id);
					ps.setString(2, isbnField.getText());
					//get the current date and time the employee edit the book and it 
					ps.setString(3, new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(Calendar.getInstance().getTime()));
					ps.executeUpdate();
				} 
				catch(SQLException e1) 
				{
					System.out.println("SQLException: " + e1.getMessage());
					System.out.println("SQLState: " + e1.getSQLState());
					System.out.println("VendorError: " + e1.getErrorCode());
				}
				
				//update book basic info
				String sql2 = "REPLACE bookInfo VALUES (?, ?, ?, ?, ?, ?, ?)";
				
				try 
				{
					conn = DriverManager.getConnection(url, dbUser, dbPass);
					ps = conn.prepareStatement(sql2);
					ps.setString(1, isbnField.getText());
					ps.setString(2, bookNameField.getText());
					ps.setString(3, authorField.getText());
					ps.setString(4, yearField.getText());
					ps.setString(5, categoryField.getText());
					ps.setString(6, publisherField.getText());
					ps.setString(7, "Available");
					ps.executeUpdate();
				} 
				catch(SQLException e1) 
				{
					System.out.println("SQLException: " + e1.getMessage());
					System.out.println("SQLState: " + e1.getSQLState());
					System.out.println("VendorError: " + e1.getErrorCode());
				}
						
				//update book coverID and summaryID
				String sql3 = "REPLACE bookDetailID VALUES (?, ?, ?)";
				try
				{
					conn = DriverManager.getConnection(url, dbUser, dbPass);
					ps = conn.prepareStatement(sql3);
					ps.setString(1, isbnField.getText());
					//if no cover was chosen then set it to unknown
					if(picName.equals(""))
					{
						ps.setString(2, "unknown.jpg");
					}
					else
					{
						ps.setString(2, picName);
					}
					ps.setString(3, summaryNameField.getText() + ".txt");
					ps.executeUpdate();
					//save the summary text to the database file system
					try 
					{
						FileWriter fw = new FileWriter("summary/" + summaryNameField.getText() +".txt");
						PrintWriter pw = new PrintWriter(fw);
						pw.print(bookSummary.getText());
						pw.close();
					} 
					catch(IOException e1) 
					{
						e1.printStackTrace();
					}								
					//only save the file if the picture name is not unknown.jpg or there is no need to update a new picture
					if(!picName.equals("unknown.jpg") && !picName.equals(oldPicName))
					{
						//save the cover in the system file
						try
						{
							BufferedImage cover = ImageIO.read(bookCoverImage);
							File coverOutput = new File("cover/" + picName);
							ImageIO.write(cover, picFormat, coverOutput);
						}
						catch(IOException e2)
						{
							e2.printStackTrace();
						}
					}
				} 
				catch(SQLException e1) 
				{
					System.out.println("SQLException: " + e1.getMessage());
					System.out.println("SQLState: " + e1.getSQLState());
					System.out.println("VendorError: " + e1.getErrorCode());
				}
				
				//delete the old book information if the employee asign it with a new ISBN
				if(!isbnField.getText().equals(oldBookISBN))
				{
					String sql4 = "DELETE FROM bookInfo , bookdetailid USING bookInfo , bookdetailid WHERE bookInfo.bookisbn = bookdetailid.bookisbn AND bookInfo.bookisbn = ?";
					try
					{
						conn = DriverManager.getConnection(url, dbUser, dbPass);
						ps = conn.prepareStatement(sql4);
						ps.setString(1, oldBookISBN);
						ps.executeUpdate();
					} 
					catch(SQLException e1) 
					{
						System.out.println("SQLException: " + e1.getMessage());
						System.out.println("SQLState: " + e1.getSQLState());
						System.out.println("VendorError: " + e1.getErrorCode());
					}
				}
				
				//change the bookStatus back to available
				try
				{
					String sql5 = "UPDATE bookInfo SET bookStatus = 'Available' WHERE bookISBN = ?";
					conn = DriverManager.getConnection(url, dbUser, dbPass);
					ps = conn.prepareStatement(sql5);
					ps.setString(1, isbnField.getText());
					ps.executeUpdate();
				}
				catch(SQLException e1) 
				{
					System.out.println("SQLException: " + e1.getMessage());
					System.out.println("SQLState: " + e1.getSQLState());
					System.out.println("VendorError: " + e1.getErrorCode());
				}
				
				//success
				UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
				UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
				UIManager.put("OptionPane.buttonFont", textFont);
				UIManager.put("Button.background", backgroundColor);
				UIManager.put("Button.foreground", foregroundColor);
				JOptionPane.showConfirmDialog(null, op.updateSuccess(), "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
	
	//add a book to the database
	public void addDatabase()
	{
		//make sure all fields are not empty
		if(bookNameField.getText().isEmpty() || isbnField.getText().isEmpty() || yearField.getText().isEmpty() || categoryField.getText().isEmpty() || publisherField.getText().isEmpty() || authorField.getText().isEmpty() || summaryNameField.getText().isEmpty() || bookSummary.getText().isEmpty())
		{
			//make sure the user want to update the data
			Options op = new Options();
			UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
			UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
			UIManager.put("OptionPane.buttonFont", textFont);
			UIManager.put("Button.background", backgroundColor);
			UIManager.put("Button.foreground", foregroundColor);
			JOptionPane.showConfirmDialog(null, op.emptyUpdate(), "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
		}
		else
		{
			//make sure the user want to update the data
			Options op = new Options();
			UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
			UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
			UIManager.put("OptionPane.buttonFont", textFont);
			UIManager.put("Button.background", backgroundColor);
			UIManager.put("Button.foreground", foregroundColor);
			int reply = JOptionPane.showConfirmDialog(null, op.checkUpdateBook(), "Message", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			if(reply == JOptionPane.YES_OPTION)
			{
				//check the database if it already has a book with the same ISBN
				String sql = "SELECT bookISBN FROM bookInfo WHERE bookISBN = ?";
				try
				{
					conn = DriverManager.getConnection(url, dbUser, dbPass);
					ps = conn.prepareStatement(sql);
					ps.setString(1, isbnField.getText());
					rs = ps.executeQuery();
					//if there is a duplicate then prompt error
					if(rs.next())
					{
						UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
						UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
						UIManager.put("OptionPane.buttonFont", textFont);
						UIManager.put("Button.background", backgroundColor);
						UIManager.put("Button.foreground", foregroundColor);
						JOptionPane.showConfirmDialog(null, op.duplicateISBN(), "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
					}
					//other wise update the book
					else
					{
						//update empEdit table, it shows who eidt what and the time he edit it
						String sql2 = "INSERT INTO empAdd VALUES(?, ?, ?)";
						try
						{
							conn = DriverManager.getConnection(url, dbUser, dbPass);
							ps = conn.prepareStatement(sql2);
							ps.setString(1, id);
							ps.setString(2, isbnField.getText());
							//get the current date and time the employee edit the book and it 
							ps.setString(3, new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(Calendar.getInstance().getTime()));
							ps.executeUpdate();
						} 
						catch(SQLException e1) 
						{
							e1.printStackTrace();
						}
						
						//update book basic info
						String sql3 = "INSERT INTO bookInfo VALUES (?, ?, ?, ?, ?, ?, ?)";
						
						try 
						{
							conn = DriverManager.getConnection(url, dbUser, dbPass);
							ps = conn.prepareStatement(sql3);
							ps.setString(1, isbnField.getText());
							ps.setString(2, bookNameField.getText());
							ps.setString(3, authorField.getText());
							ps.setString(4, yearField.getText());
							ps.setString(5, categoryField.getText());
							ps.setString(6, publisherField.getText());
							ps.setString(7, "Available");
							ps.executeUpdate();
						} 
						catch(SQLException e1) 
						{
							e1.printStackTrace();
						}
						
						//update book coverID and summaryID
						String sql4 = "INSERT INTO bookDetailID VALUES (?, ?, ?)";
						try
						{
							conn = DriverManager.getConnection(url, dbUser, dbPass);
							ps = conn.prepareStatement(sql4);
							ps.setString(1, isbnField.getText());
							//if no cover was chosen then set it to unknown
							if(picName.equals(""))
							{
								ps.setString(2, "unknown.jpg");
							}
							else
							{
								ps.setString(2, picName);
							}
							ps.setString(3, summaryNameField.getText() + ".txt");
							ps.executeUpdate();
							//save the summary text to the database file system
							try 
							{
								FileWriter fw = new FileWriter("summary/" + summaryNameField.getText() +".txt");
								PrintWriter pw = new PrintWriter(fw);
								pw.print(bookSummary.getText());
								pw.close();
							} 
							catch(IOException e1) 
							{
								e1.printStackTrace();
							}		
							//only save the file if the picture name is not unknown.jpg
							if(!picName.equals("unknown.jpg") && !picName.equals(""))
							{
								//save the cover in the system file
								try
								{
									BufferedImage cover = ImageIO.read(bookCoverImage);
									File coverOutput = new File("cover/" + picName);
									ImageIO.write(cover, picFormat, coverOutput);
								}
								catch(IOException e2)
								{
									e2.printStackTrace();
								}
							}
						} 
						catch(SQLException e1) 
						{
							e1.printStackTrace();
						}
					}
					//success
					UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
					UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
					UIManager.put("OptionPane.buttonFont", textFont);
					UIManager.put("Button.background", backgroundColor);
					UIManager.put("Button.foreground", foregroundColor);
					JOptionPane.showConfirmDialog(null, op.updateSuccess(), "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
				}
				catch(SQLException e1)
				{
					System.out.println("SQLException: " + e1.getMessage());
					System.out.println("SQLState: " + e1.getSQLState());
					System.out.println("VendorError: " + e1.getErrorCode());
				}
			}
		}						
	}
	
	public void BookEditFormWindow() 
	{
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
