//this class is the GUI for the student when they want to search for a book, connects with StudentView.java
import javax.swing.JDialog;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class BookSearch extends JDialog 
{
	private static final long serialVersionUID = 1L;
	
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

	JTable resultTable;
	JScrollPane scrollPane;
	
	private boolean stdSearch = false;
	private boolean empEdit  = false;
	private boolean empDelete  = false;
	
	//user id
	String id = "";

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
	
	public BookSearch() 
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
		
		setTitle("Seach for books");
		setBackground(backgroundColor);
		
		//book name
		JLabel lblBookName = new JLabel("Book Name:");
		lblBookName.setForeground(foregroundColor);
		lblBookName.setFont(textFont);
		
		JTextField bookNameField = new JTextField();
		bookNameField.setForeground(foregroundColor);
		bookNameField.setFont(textFont);
		bookNameField.setBackground(backgroundColor);
		bookNameField.setColumns(10);
		
		//ISBN
		JLabel lblISBN = new JLabel("ISBN:");
		lblISBN.setForeground(foregroundColor);
		lblISBN.setFont(textFont);
		
		JTextField isbnField = new JTextField();
		isbnField.setForeground(foregroundColor);
		isbnField.setFont(textFont);
		isbnField.setColumns(10);
		isbnField.setBackground(backgroundColor);
		
		//author
		JLabel lblAuthor = new JLabel("Author:");
		lblAuthor.setForeground(foregroundColor);
		lblAuthor.setFont(textFont);
		
		JTextField authorField = new JTextField();
		authorField.setForeground(foregroundColor);
		authorField.setFont(textFont);
		authorField.setColumns(10);
		authorField.setBackground(backgroundColor);
		
		//category
		JLabel lblCategory = new JLabel("Category:");
		lblCategory.setForeground(foregroundColor);
		lblCategory.setFont(textFont);
		
		JComboBox<String> categoryComboBox = new JComboBox<String>();
		categoryComboBox.setForeground(foregroundColor);
		categoryComboBox.setFont(textFont);
		categoryComboBox.setMaximumRowCount(5);
		categoryComboBox.addItem("N/A");
		//add category to combo box
		try 
		{
			String sql = "SELECT bookCategory FROM bookInfo GROUP BY bookCategory";
			conn = DriverManager.getConnection(url, dbUser, dbPass);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next())
			{
				categoryComboBox.addItem(rs.getString(1));
			}
		}
		catch(SQLException e) 
		{
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
		categoryComboBox.setSelectedItem(1);
		
		//search
		JButton btnSearch = new JButton("Search");
		btnSearch.setForeground(foregroundColor);
		btnSearch.setFont(textFont);
		btnSearch.setBackground(backgroundColor);
		btnSearch.setFocusPainted(false);
		
		//object to store the search result
		String[] columnName = {"Book ISBN", "Title", "Author", "Category", "Status"};
		
		btnSearch.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					String sql = "SELECT * FROM bookInfo WHERE bookName LIKE ? AND bookAuthor LIKE ? AND bookISBN LIKE ? AND bookCategory LIKE ?";
					conn = DriverManager.getConnection(url, dbUser, dbPass);
					ps = conn.prepareStatement(sql);
					ps.setString(1, bookNameField.getText() + "%");
					ps.setString(2, authorField.getText() + "%");
					ps.setString(3, isbnField.getText() + "%");
					if(categoryComboBox.getSelectedItem().equals("N/A"))
					{
						ps.setString(4, "%");
					}
					else
					{	
						ps.setString(4, String.valueOf(categoryComboBox.getSelectedItem()));
					}
					rs = ps.executeQuery();
					//add book info into book
					//they have attribute which is define in BookInfo class
					//if cannot find anything then pop up message
					if(!rs.next())
					{
						Options op = new Options();
						UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
						UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
						JOptionPane.showConfirmDialog(null, op.emptySearch(), "Error!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
						//clear out the result table if it is visible
						if(resultTable.isVisible())
						{
							resultTable.setVisible(false);
						}
						scrollPane.setEnabled(false);
					}
					else
					{
						resultTable.setVisible(true);
						scrollPane.setEnabled(true);
						ArrayList<BookInfo> book = new ArrayList<BookInfo>();
						BookInfo bi;
						//move back to first round since we skip the top one
						rs.beforeFirst();
						while(rs.next())
						{
							bi = new BookInfo(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(7));
							book.add(bi);
						}
						
						//show search result
						DefaultTableModel model = new DefaultTableModel();
						model.setColumnIdentifiers(columnName);
						Object[] row = new Object[5];
						for(int i = 0; i < book.size(); i++)
						{
							//set each value to the correct colu
							row[0] = book.get(i).getISBN();
							row[1] = book.get(i).getTitle();
							row[2] = book.get(i).getAuthor();
							row[3] = book.get(i).getCategory();
							row[4] = book.get(i).getStatus();
							model.addRow(row);
						}
						resultTable.setModel(model);	
					}
				}
				catch(SQLException e2) 
				{
					System.out.println("SQLException: " + e2.getMessage());
					System.out.println("SQLState: " + e2.getSQLState());
					System.out.println("VendorError: " + e2.getErrorCode());
				}
			}
		});
		
		//show result table
		resultTable = new JTable();
		resultTable.setVisible(false);
		resultTable.setFillsViewportHeight(true);
		resultTable.setForeground(foregroundColor);
		resultTable.setFont(textFont);
		resultTable.getTableHeader().setFont(textFont);
		resultTable.getTableHeader().setForeground(foregroundColor);
		resultTable.getTableHeader().setBackground(backgroundColor);
		resultTable.setRowHeight(50);
		resultTable.setBackground(backgroundColor);
		resultTable.setToolTipText("Please click on the row to get more details");
		//cannot edit the table
		resultTable.setDefaultEditor(Object.class, null);
		resultTable.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				//if this is the student do the search then open the BookDetail window for the student to view
				if(stdSearch)
				{
					studentSearchWindow();
				}
				
				//if the employee want to edit the data base
				if(empEdit)
				{
					empEditWindow();
				}
				
				//if the employee want to add a new book to the data base
				if(empDelete)
				{
					empDeleteWindow();
				}
			}
		});
		
		//add to scrollpane
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(760, 380));
		scrollPane.setViewportView(resultTable);

		//layout
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 916, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblBookName, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
								.addComponent(lblAuthor, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(authorField)
								.addComponent(bookNameField, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblISBN, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
								.addComponent(lblCategory, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(isbnField, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
									.addGap(184))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(categoryComboBox, 0, 244, Short.MAX_VALUE)
									.addGap(59)
									.addComponent(btnSearch, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
									.addGap(44))))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblBookName, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
						.addComponent(bookNameField, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblISBN, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
								.addComponent(isbnField, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAuthor, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
						.addComponent(authorField, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCategory, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(2)
									.addComponent(categoryComboBox, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
								.addComponent(btnSearch, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))))
					.addGap(40)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 505, GroupLayout.PREFERRED_SIZE)
					.addGap(33))
		);
		getContentPane().setLayout(groupLayout);
	}
	
	//set the user ID 
	public void setID(String x)
	{
		id = x;
	}
	
	//set the value to true if it is a student wants to search for books in the database
	public void setStdSearch(boolean b)
	{
		stdSearch = b;
	}
	
	//set the value to true if it is an employee wants to edit book's information in the database
	public void setEmpEdit(boolean b)
	{
		empEdit = b;
	}
	
	//set the value to true if it is an employee wants to remove books from the database
	public void setEmpDelete(boolean b)
	{
		empDelete = b;
	}
	
	//student search window
	public void studentSearchWindow()
	{
		BookDetail bd = new BookDetail();
		String coverID = "";
		String summaryID = "";
		//get book detail from the selected row
		bd.lblISBNDetail.setText((String) resultTable.getValueAt(resultTable.getSelectedRow(), 0));
		bd.lblTitleDetail.setText((String) resultTable.getValueAt(resultTable.getSelectedRow(), 1));
		bd.lblAuthorDetail.setText((String) resultTable.getValueAt(resultTable.getSelectedRow(), 2));
		bd.lblCategoryDetail.setText((String) resultTable.getValueAt(resultTable.getSelectedRow(), 3));
		bd.lblStatusDetail.setText((String) resultTable.getValueAt(resultTable.getSelectedRow(), 4));
		if(resultTable.getValueAt(resultTable.getSelectedRow(), 4).equals("Available"))
		{
			bd.lblStatusDetail.setForeground(Color.green);
		}
		else
		{
			bd.lblStatusDetail.setForeground(Color.red);
		}
		
		//get the cover ID from the bookDetailID 
		try 
		{
			String sql = "SELECT coverID, summaryID FROM bookDetailID WHERE bookISBN = ?";
			conn = DriverManager.getConnection(url, dbUser, dbPass);
			ps = conn.prepareStatement(sql);
			ps.setString(1, (String) resultTable.getValueAt(resultTable.getSelectedRow(), 0));
			rs = ps.executeQuery();
			if(rs.next())
			{
				//get book cover, and summary id by look at ISBN in server
				coverID = rs.getString(1);
				summaryID = rs.getString(2);
				//then get the image from the server
				BufferedImage img = null;
				try 
				{
					   img = ImageIO.read(new File("cover/" + coverID));
				} 
				catch(IOException e3) 
				{
					  e3.printStackTrace();
				}
				//resize image to fit to lblPicture
				Image resizeCover = img.getScaledInstance(bd.lblPicture.getWidth(), bd.lblPicture.getHeight(), Image.SCALE_SMOOTH);
				ImageIcon bookCover = new ImageIcon(resizeCover);
				bd.lblPicture.setIcon(bookCover);
				
				//get the text from the summary 
				String summary = "";
				try
				{
					String fileName = "summary/" + summaryID;
					File file = new File(fileName);
					Scanner sc = new Scanner(file);
					summary = sc.nextLine();
					while(sc.hasNextLine())
					{
						summary = summary + sc.nextLine() + "\n";
					}
					sc.close();
				}
				catch(IOException e4)
				{
					e4.printStackTrace();
				}
				//add the summary from the file to the summary area
				bd.bookSummary.setText(summary);
				//change the scroll bar to top position
				bd.bookSummary.setSelectionStart(0);
				bd.bookSummary.setSelectionEnd(0);
			}
		} 
		catch(SQLException e1) 
		{
			System.out.println("SQLException: " + e1.getMessage());
			System.out.println("SQLState: " + e1.getSQLState());
			System.out.println("VendorError: " + e1.getErrorCode());
		}
		
		//pass the user id to Book Detail
		bd.setID(id);
		//show the window
		bd.BookDetailWindow();
	}
	
	public void empEditWindow()
	{
		//can only edit a book that is avaiable 
		String bookISBN = (String) resultTable.getValueAt(resultTable.getSelectedRow(), 0);
		DatabaseConection dc = new DatabaseConection();
		dc.checkBookStatus(bookISBN);
		if(!dc.getBookResult().equals("Available"))
		{
			Options op = new Options();
			UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
			UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
			UIManager.put("OptionPane.buttonFont", textFont);
			UIManager.put("Button.background", backgroundColor);
			UIManager.put("Button.foreground", foregroundColor);
			JOptionPane.showConfirmDialog(null, op.failEdit(), "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
		}
		else
		{
			BookEditForm be = new BookEditForm();
			//make the book unavailble for checkOut or edit by other people
			dc.checkOut(bookISBN);
			//signal the system the user want to update the book information to the database
			be.setEmpEdit(true);
			be.setBookISBN((String) resultTable.getValueAt(resultTable.getSelectedRow(), 0));
			String coverID = "";
			String summaryID = "";
			be.bookNameField.setText((String) resultTable.getValueAt(resultTable.getSelectedRow(), 1));
			be.isbnField.setText((String) resultTable.getValueAt(resultTable.getSelectedRow(), 0));
			be.categoryField.setText((String) resultTable.getValueAt(resultTable.getSelectedRow(), 3));
			be.authorField.setText((String) resultTable.getValueAt(resultTable.getSelectedRow(), 2));
			//get the cover ID from the bookDetailID 
			try 
			{
				String sql = "SELECT coverID, summaryID FROM bookDetailID WHERE bookISBN = ?";
				conn = DriverManager.getConnection(url, dbUser, dbPass);
				ps = conn.prepareStatement(sql);
				ps.setString(1, (String) resultTable.getValueAt(resultTable.getSelectedRow(), 0));
				rs = ps.executeQuery();
				if(rs.next())
				{
					//get book cover, and summary id by look at ISBN in server
					coverID = rs.getString(1);
					//keep the name of the cover pic
					be.setPicName(coverID);
					summaryID = rs.getString(2);
					//then get the image from the server
					BufferedImage img = null;
					try 
					{
						img = ImageIO.read(new File("cover/" + coverID));
					} 
					catch(IOException e3) 
					{
						e3.printStackTrace();
					}
					//resize image to fit to lblPicture
					Image resizeCover = img.getScaledInstance(be.lblPicture.getWidth(), be.lblPicture.getHeight(), Image.SCALE_SMOOTH);
					ImageIcon bookCover = new ImageIcon(resizeCover);
					be.lblPicture.setIcon(bookCover);
					//just take the file name, leave of .txt
					be.summaryNameField.setText(summaryID.replace(".txt", ""));
							
					//get the text from the summary 
					String summary = "";
					try
					{
						String fileName = "summary/" + summaryID;
						File file = new File(fileName);
						Scanner sc = new Scanner(file);
						summary = sc.nextLine();
						while(sc.hasNextLine())
						{
							summary = summary + sc.nextLine() + "\n";
						}
						sc.close();
					}
					catch(IOException e4)
					{
						e4.printStackTrace();
					}
					//add the summary from the file to the summary area
					be.bookSummary.setText(summary);
					//change the scroll bar to top position
					be.bookSummary.setSelectionStart(0);
					be.bookSummary.setSelectionEnd(0);
				}
			}
			catch(SQLException e1) 
			{
				System.out.println("SQLException: " + e1.getMessage());
				System.out.println("SQLState: " + e1.getSQLState());
				System.out.println("VendorError: " + e1.getErrorCode());
			}		
			
			//get year and publisher data
			String sql2 = "SELECT bookYear, bookPublisher FROM bookinfo WHERE bookISBN = ?";
			try 
			{
				ps = conn.prepareStatement(sql2);
				ps.setString(1, (String) resultTable.getValueAt(resultTable.getSelectedRow(), 0));
				rs = ps.executeQuery();
				if(rs.next())
				{
					be.yearField.setText(rs.getString(1));
					be.publisherField.setText(rs.getString(2));
				}
			} 
			catch(SQLException e) 
			{
				e.printStackTrace();
			}
			
			//pass emp id 
			be.setID(id);
			//show window
			be.BookEditFormWindow();
		}
	}
	
	public void empDeleteWindow()
	{
		//can only delete a book that is avaiable 
		String bookISBN = (String) resultTable.getValueAt(resultTable.getSelectedRow(), 0);
		DatabaseConection dc = new DatabaseConection();
		dc.checkBookStatus(bookISBN);
		if(!dc.getBookResult().equals("Available"))
		{
			Options op = new Options();
			UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
			UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
			UIManager.put("OptionPane.buttonFont", textFont);
			UIManager.put("Button.background", backgroundColor);
			UIManager.put("Button.foreground", foregroundColor);
			JOptionPane.showConfirmDialog(null, op.failDelete(), "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
		}
		else
		{
			BookDeleteForm bd = new BookDeleteForm();
			//make the book unavailble for checkOut or edit by other people
			dc.checkOut(bookISBN);
			//give the form the id of the user and the isbn of the book
			bd.setID(id);
			bd.setBookISBN((String) resultTable.getValueAt(resultTable.getSelectedRow(), 0));
			bd.lblISBNDetail.setText((String) resultTable.getValueAt(resultTable.getSelectedRow(), 0));
			bd.lblTitleDetail.setText((String) resultTable.getValueAt(resultTable.getSelectedRow(), 1));
			bd.lblAuthorDetail.setText((String) resultTable.getValueAt(resultTable.getSelectedRow(), 2));
			bd.lblCategoryDetail.setText((String) resultTable.getValueAt(resultTable.getSelectedRow(), 3));
			bd.lblStatusDetail.setText((String) resultTable.getValueAt(resultTable.getSelectedRow(), 4));
			if(bd.lblStatusDetail.getText().equals("Available"))
			{
				bd.lblStatusDetail.setForeground(Color.green);
			}
			else
			{
				bd.lblStatusDetail.setForeground(Color.red);
			}
			try
			{
				String sql = "SELECT coverID, summaryID FROM bookDetailID WHERE bookISBN = ?";
				conn = DriverManager.getConnection(url, dbUser, dbPass);
				ps = conn.prepareStatement(sql);
				ps.setString(1, (String) resultTable.getValueAt(resultTable.getSelectedRow(), 0));
				rs = ps.executeQuery();
				if(rs.next())
				{
					//get the book cover ID and summary ID
					String coverID = rs.getString(1);
					String summaryID = rs.getString(2);
					
					//find the files in the database
					BufferedImage img = null;
					try 
					{
						img = ImageIO.read(new File("cover/" + coverID));
					} 
					catch(IOException e2) 
					{
						e2.printStackTrace();
					}
					//resize image to fit to lblPicture
					Image resizeCover = img.getScaledInstance(bd.lblPicture.getWidth(), bd.lblPicture.getHeight(), Image.SCALE_SMOOTH);
					ImageIcon bookCover = new ImageIcon(resizeCover);
					bd.lblPicture.setIcon(bookCover);

					//get the text from the summary 
					String summary = "";
					try
					{
						String fileName = "summary/" + summaryID;
						File file = new File(fileName);
						Scanner sc = new Scanner(file);
						summary = sc.nextLine();
						while(sc.hasNextLine())
						{
							summary = summary + sc.nextLine() + "\n";
						}
						sc.close();
					}
					catch(IOException e3)
					{
						e3.printStackTrace();
					}
					bd.bookSummary.setText(summary);
					//move the scroll bar to top
					bd.bookSummary.setSelectionStart(0);
					bd.bookSummary.setSelectionEnd(0);
				}
			}
			catch(SQLException e1)
			{
				System.out.println("SQLException: " + e1.getMessage());
				System.out.println("SQLState: " + e1.getSQLState());
				System.out.println("VendorError: " + e1.getErrorCode());
			}
			bd.BookDeleteFormWindow();
		}
	}
	
	
	public void BookSearchWindow()
	{
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
