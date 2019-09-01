//the main gui for the student

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.plaf.ColorUIResource;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StudentView extends JFrame 
{
	static final long serialVersionUID = 1L;
	
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
	
	JLabel lblWelcome;
	JTable tableBookPickUp;
	JTable tableBookReturn;
	JButton btnCancel;
	JButton btnUpdatePickUp;
	
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

	public StudentView() 
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
		
		setTitle("Main Center");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBackground(backgroundColor);
		
		//if the user close the window then pop up warning message
		addWindowListener(new WindowAdapter() 
		{
			Options op = new Options();
			
			@Override
			public void windowClosing(WindowEvent e) 
			{
				//Warning message
				UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
				UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
				UIManager.put("OptionPane.buttonFont", textFont);
				UIManager.put("Button.background", backgroundColor);
				UIManager.put("Button.foreground", foregroundColor);
				int message = JOptionPane.showConfirmDialog(null, op.logOut(), "Message", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(message == JOptionPane.YES_OPTION)
				{
					//update the time the user logout of the programe
					String date = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(Calendar.getInstance().getTime());
					DatabaseConection dc = new DatabaseConection();
					dc.stdLogoutTime(date, id);
					System.exit(0);
				}
			}
		});
		
		//welcome message
		lblWelcome = new JLabel();
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(labelFont);
		lblWelcome.setForeground(foregroundColor);
		
		//book search
		JButton btnSearch = new JButton("Search for books");
		btnSearch.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				BookSearch bs = new BookSearch();
				//pass the user id to book search
				bs.setID(id);
				bs.setStdSearch(true);
				bs.BookSearchWindow();
			}
		});
		btnSearch.setForeground(foregroundColor);
		btnSearch.setFont(textFont);
		btnSearch.setBackground(backgroundColor);
		btnSearch.setFocusPainted(false);
		
		//log out
		JButton btnLogout = new JButton("Log out");
		btnLogout.setForeground(foregroundColor);
		btnLogout.setFont(textFont);
		btnLogout.setBackground(backgroundColor);
		btnLogout.setFocusPainted(false);
		//if the user clicked log out then pop up warning message
		btnLogout.addActionListener(new ActionListener() 
		{
			Options op = new Options();
			
			public void actionPerformed(ActionEvent e) 
			{
				//Warning message
				UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
				UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
				UIManager.put("OptionPane.buttonFont", textFont);
				UIManager.put("Button.background", backgroundColor);
				UIManager.put("Button.foreground", foregroundColor);
				int message = JOptionPane.showConfirmDialog(null, op.logOut(), "Message", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(message == JOptionPane.YES_OPTION)
				{
					//update the time the user logout of the programe
					String date = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(Calendar.getInstance().getTime());
					DatabaseConection dc = new DatabaseConection();
					dc.stdLogoutTime(date, id);
					System.exit(0);
				}
			}
		});
		
		//random book
		JButton btnRandomBook = new JButton("Random Books");
		btnRandomBook.setForeground(foregroundColor);
		btnRandomBook.setFont(textFont);
		btnRandomBook.setBackground(backgroundColor);
		btnRandomBook.setFocusPainted(false);
		//open a window to let the user to choose which category he would like
		btnRandomBook.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				RandomBook rb = new RandomBook();
				rb.setID(id);
				rb.RandomBookWindow();
			}
		});
		
		//book to return
		JLabel lblBookToReturn = new JLabel("Book To Return :");
		lblBookToReturn.setForeground(foregroundColor);
		lblBookToReturn.setFont(textFont);
		
		//book to pick up
		JLabel lblBookToPickUp = new JLabel("Book To Pick Up:");
		lblBookToPickUp.setFont(textFont);
		lblBookToPickUp.setForeground(foregroundColor);
		
		//show what book you can pick up from the library
		JScrollPane scrollPanePickUp = new JScrollPane();
		tableBookPickUp = new JTable();
		tableBookPickUp.getTableHeader().setForeground(foregroundColor);
		tableBookPickUp.getTableHeader().setBackground(backgroundColor);
		tableBookPickUp.getTableHeader().setFont(textFont);
		tableBookPickUp.setForeground(foregroundColor);
		tableBookPickUp.setFont(textFont);
		tableBookPickUp.setBackground(backgroundColor);
		tableBookPickUp.setRowHeight(50);
		tableBookPickUp.setVisible(false);
		tableBookPickUp.setToolTipText("You can click on the row to see the detail of the book.");
		//cannot edit the table
		tableBookPickUp.setDefaultEditor(Object.class, null);
		scrollPanePickUp.setViewportView(tableBookPickUp);
		//make cancel button appear if the user click on the table 
		tableBookPickUp.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				btnCancel.setVisible(true);
				
				//the user can see the information of the row too
				BookPickUpDetail_Window bookPUD = new BookPickUpDetail_Window();
				bookPUD.lblBookNameDetail.setText((String) tableBookPickUp.getValueAt(tableBookPickUp.getSelectedRow(), 0));
				bookPUD.lblDatePickUpDetail.setText((String) tableBookPickUp.getValueAt(tableBookPickUp.getSelectedRow(), 1));
				bookPUD.BookPickUpDetailWindow();
			}
		});
		
		//cancel the book order for check out
		btnCancel = new JButton("Cancel this Book");
		btnCancel.setBackground(backgroundColor);
		btnCancel.setForeground(foregroundColor);
		btnCancel.setFont(textFont);
		btnCancel.setFocusPainted(false);
		btnCancel.setVisible(false);
		//the user can cancel the book if they want to
		btnCancel.addActionListener(new ActionListener() 
		{
			Options op = new Options();
			public void actionPerformed(ActionEvent e) 
			{
				//Warning message
				UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
				UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
				UIManager.put("OptionPane.buttonFont", textFont);
				UIManager.put("Button.background", backgroundColor);
				UIManager.put("Button.foreground", foregroundColor);
				int message = JOptionPane.showConfirmDialog(null, op.cancelBook(), "Message", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
				//if the user choose yes then cancel the book and print out the completion message
				if(message == JOptionPane.YES_OPTION)
				{
					//for delete the book from the bookBorrow table
					String sql = "DELETE FROM bookBorrow WHERE bookISBN = (SELECT bookISBN FROM bookInfo WHERE bookName = ?)";
					//for update the deleted book status in bookInfo table
					String sql2 = "UPDATE bookInfo SET bookStatus = 'Available' WHERE bookName = ?";
					
					//delete book from bookBorrow table
					try
					{
						conn = DriverManager.getConnection(url, dbUser, dbPass);
						ps = conn.prepareStatement(sql);
						String bookName = (String) tableBookPickUp.getValueAt(tableBookPickUp.getSelectedRow(), 0);
						ps.setString(1, bookName);
						ps.executeUpdate();
						
					}
					catch(SQLException e1)
					{
						System.out.println("SQLException: " + e1.getMessage());
						System.out.println("SQLState: " + e1.getSQLState());
						System.out.println("VendorError: " + e1.getErrorCode());
					}	
					
					//update bookStatus
					try
					{
						conn = DriverManager.getConnection(url, dbUser, dbPass);
						ps = conn.prepareStatement(sql2);
						String bookName = (String) tableBookPickUp.getValueAt(tableBookPickUp.getSelectedRow(), 0);
						ps.setString(1, bookName);
						ps.executeUpdate();
					}
					catch(SQLException e1)
					{
						System.out.println("SQLException: " + e1.getMessage());
						System.out.println("SQLState: " + e1.getSQLState());
						System.out.println("VendorError: " + e1.getErrorCode());
					}
					//Successful deletion message
					UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
					UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
					UIManager.put("OptionPane.buttonFont", textFont);
					UIManager.put("Button.background", backgroundColor);
					UIManager.put("Button.foreground", foregroundColor);
					JOptionPane.showConfirmDialog(null, op.cancelBookSuccess(), "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
					
					//update the tablePickUp
					btnUpdatePickUp.doClick();
				}
			}
		});
		
		//make the button disappear if the user click somewhere else
		btnCancel.addFocusListener(new FocusAdapter() 
		{
			@Override
			public void focusLost(FocusEvent e) 
			{
				btnCancel.setVisible(false);
			}
		});
		
		//show what time you need to return the book
		JScrollPane scrollPaneReturn = new JScrollPane();
		tableBookReturn = new JTable();
		tableBookReturn.getTableHeader().setForeground(foregroundColor);
		tableBookReturn.getTableHeader().setBackground(backgroundColor);
		tableBookReturn.getTableHeader().setFont(textFont);
		tableBookReturn.setForeground(foregroundColor);
		tableBookReturn.setFont(textFont);
		tableBookReturn.setBackground(backgroundColor);
		tableBookReturn.setRowHeight(50);
		tableBookReturn.setVisible(false);
		tableBookReturn.setToolTipText("You can click on the row to see the detail of the book.");
		//cannot edit the table
		tableBookReturn.setDefaultEditor(Object.class, null);
		scrollPaneReturn.setViewportView(tableBookReturn);
		//pop up a window to show the user the detail of the book to return
		tableBookReturn.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				BookReturnDetail_Window bookReturnInfo = new BookReturnDetail_Window();
				bookReturnInfo.lblTitleField.setText((String) tableBookReturn.getValueAt(tableBookReturn.getSelectedRow(), 0));
				bookReturnInfo.lblDateReturn.setText((String) tableBookReturn.getValueAt(tableBookReturn.getSelectedRow(), 1));
				bookReturnInfo.BookReturnDetailWindow();
			}
		});
		
		//button to update the information of the books that the user has check out
		btnUpdatePickUp = new JButton("Update");
		btnUpdatePickUp.setForeground(foregroundColor);
		btnUpdatePickUp.setFont(textFont);
		btnUpdatePickUp.setBackground(backgroundColor);
		btnUpdatePickUp.setFocusPainted(false);
		btnUpdatePickUp.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				//Array to store the user data
				ArrayList<UserBookCheckOutData> userData = new ArrayList<UserBookCheckOutData>();
				UserBookCheckOutData checkOutData;
				
				//get user check out data to put in the bookPickUp table
				String sql = "SELECT bookName, checkOutDate FROM bookInfo AS BI, bookBorrow AS BB WHERE bb.stdid = ? AND bi.bookISBN = bb.bookISBN";
				try
				{
					conn = DriverManager.getConnection(url, dbUser, dbPass);
					ps = conn.prepareStatement(sql);
					ps.setString(1, id);
					rs = ps.executeQuery();
					while(rs.next())
					{
						checkOutData= new UserBookCheckOutData(rs.getString(1), rs.getString(2).substring(0, 10));
						userData.add(checkOutData);
					}
					
					//show data
					DefaultTableModel model = new DefaultTableModel();
					//column header for tableBookPickUp
					String[] bookPickUpHeader = {"Title", "Date to Pick Up"};
					model.setColumnIdentifiers(bookPickUpHeader);
					Object[] row = new Object[2];
					for(int i = 0; i < userData.size(); i++)
					{		
						try 
						{
							row[0] = userData.get(i).getBookName();
							row[1] = userData.get(i).getCheckOutDate();
							model.addRow(row);
						} 
						catch(ParseException e1) 
						{
							e1.printStackTrace();
						}
					}
					tableBookPickUp.setVisible(true);
					tableBookPickUp.setModel(model);
				}
				catch(SQLException e2)
				{
					System.out.println("SQLException: " + e2.getMessage());
					System.out.println("SQLState: " + e2.getSQLState());
					System.out.println("VendorError: " + e2.getErrorCode());
				}
			}
		});
		
		//button to update the information of the books that the user has to return
		JButton btnUpdateReturn = new JButton("Update");
		btnUpdateReturn.setForeground(foregroundColor);
		btnUpdateReturn.setFont(textFont);
		btnUpdateReturn.setBackground(backgroundColor);
		btnUpdateReturn.setFocusPainted(false);
		//get the data from the bookReturn table from the database
		btnUpdateReturn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				String sql = "SELECT bookName, returnDate FROM bookInfo AS bi, bookReturn AS br WHERE bi.bookISBN = br.bookISBN AND stdID = ?";
				ArrayList<BookReturnInfo> bookReturn = new ArrayList<BookReturnInfo>();
				BookReturnInfo book;
				//get the book and the date the student needs to return the book
				try
				{
					conn = DriverManager.getConnection(url, dbUser, dbPass);
					ps = conn.prepareStatement(sql);
					ps.setString(1, id);
					rs = ps.executeQuery();
					
					//adding the data to the array for display in the table
					while(rs.next())
					{
						book = new BookReturnInfo(rs.getString(1), rs.getString(2).substring(0, 10));
						bookReturn.add(book);
					}
					
					//update data for tableBookReturn
					String[] columnHeader = {"Title", "Date to Return"};
					DefaultTableModel model = new DefaultTableModel();
					model.setColumnIdentifiers(columnHeader);
					Object[] row = new Object[2];
					for(int i = 0; i < bookReturn.size(); i++)
					{
						row[0] = bookReturn.get(i).getBookName();
						row[1] = bookReturn.get(i).getDateReturn();
						model.addRow(row);
					}
					tableBookReturn.setModel(model);
					tableBookReturn.setVisible(true);
				}
				catch(SQLException e1)
				{
					System.out.println("SQLException: " + e1.getMessage());
					System.out.println("SQLState: " + e1.getSQLState());
					System.out.println("VendorError: " + e1.getErrorCode());
				}
			}
		});

		//layout
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(lblWelcome, GroupLayout.DEFAULT_SIZE, 957, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblBookToPickUp, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnUpdatePickUp, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnCancel, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addComponent(scrollPanePickUp, GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE))
					.addGap(65)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnUpdateReturn, GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
						.addComponent(lblBookToReturn, GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
						.addComponent(scrollPaneReturn, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnLogout, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
						.addComponent(btnRandomBook, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
						.addComponent(btnSearch, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblWelcome, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblBookToReturn, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(4)
									.addComponent(lblBookToPickUp, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPaneReturn, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
								.addComponent(scrollPanePickUp, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnSearch)
							.addGap(36)
							.addComponent(btnRandomBook, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
							.addGap(36)
							.addComponent(btnLogout, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE, false)
						.addComponent(btnUpdatePickUp)
						.addComponent(btnUpdateReturn, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addGap(28))
		);
		getContentPane().setLayout(groupLayout);
	}
	
	//set the student ID 
	public void setID(String x)
	{
		id = x;
	}

	public void StudentViewWindow()
	{
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
