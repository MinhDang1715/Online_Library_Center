//this class is used to check out book for the student, connects with Employee.java

import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class CheckOutBook extends JDialog 
{
	private static final long serialVersionUID = 1L;
	
	//SQL
	private String url = "jdbc:mysql://localhost:3306/Online Library Center?useSSL=false";
	private String dbUser = "Minh";
	private String dbPass = "ThuongNhu";
	private Connection conn = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;
	
	private String id;
	
	//deafult font, color
	Setting set = new Setting();
	Font labelFont = set.getLabelDefaultFont();
	Font textFont = set.getTextDefaultFont();
	Color backgroundColor = set.getDefaultBackGround();
	Color foregroundColor = set.getDefaultForeGround();
	
	JTable tableSearchResult;
	
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
	
	public CheckOutBook() 
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
		
		setTitle("Check Out Book");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBackground(backgroundColor);
		
		//std ID
		JLabel lblStdID = new JLabel("Student ID:");
		lblStdID.setForeground(foregroundColor);
		lblStdID.setFont(textFont);
		lblStdID.setBackground(backgroundColor);
		
		JTextField idField = new JTextField();
		idField.setForeground(foregroundColor);
		idField.setFont(textFont);
		idField.setBackground(backgroundColor);
		idField.setColumns(10);
		
		//search
		JButton btnSearch = new JButton("Search");
		btnSearch.setForeground(foregroundColor);
		btnSearch.setBackground(backgroundColor);
		btnSearch.setFont(textFont);
		btnSearch.setFocusPainted(false);
		//search the database with the input value
		btnSearch.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String id = idField.getText();
				String sql = "SELECT stdName, si.stdID, bookName, bi.bookISBN  FROM stdInfo AS si, bookInfo AS bi, bookBorrow AS bb  WHERE si.stdID = ? AND bb.stdID = si.stdID AND bb.bookISBN = bi.bookISBN";
				ArrayList<UserInfo> userInfoData = new ArrayList<UserInfo>();
				UserInfo uInfo;
				try
				{
					conn = DriverManager.getConnection(url, dbUser, dbPass);
					ps = conn.prepareStatement(sql);
					ps.setString(1, id);
					rs = ps.executeQuery();
					//cannot find the id then pop up an error message
					if(!rs.next())
					{
						Options op = new Options();
						UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
						UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
						UIManager.put("OptionPane.buttonFont", textFont);
						UIManager.put("Button.background", backgroundColor);
						UIManager.put("Button.foreground", foregroundColor);
						JOptionPane.showConfirmDialog(null, op.nullID(), "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
					}
					else
					{
						//move back to the top row
						rs.beforeFirst();
						//put the data from the database to the userInfoData list
						while(rs.next())
						{
							uInfo = new UserInfo(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
							userInfoData.add(uInfo);
						}
					}
					
					//put the data in a table
					String[] columnHeader = {"Name", "ID", "Book Title", "ISBN"};
					DefaultTableModel model = new DefaultTableModel();
					model.setColumnIdentifiers(columnHeader);
					Object[] row = new Object[4];
					for(int i = 0; i < userInfoData.size(); i++)
					{
						row[0] = userInfoData.get(i).getName();
						row[1] = userInfoData.get(i).getID();
						row[2] = userInfoData.get(i).getBookName();
						row[3] = userInfoData.get(i).getBookISBN();
						model.addRow(row);
					}
					tableSearchResult.setModel(model);
					tableSearchResult.setVisible(true);
				}
				catch(SQLException e1)
				{
					System.out.println("SQLException: " + e1.getMessage());
					System.out.println("SQLState: " + e1.getSQLState());
					System.out.println("VendorError: " + e1.getErrorCode());
				}
			}
		});
		
		//check out
		JButton btnCheckOut = new JButton("Check Out");
		btnCheckOut.setForeground(foregroundColor);
		btnCheckOut.setFont(textFont);
		btnCheckOut.setBackground(backgroundColor);
		btnCheckOut.setVisible(false);
		btnCheckOut.setFocusPainted(false);
		//hide the button if the employee click outside of it
		btnCheckOut.addFocusListener(new FocusAdapter() 
		{
			@Override
			public void focusLost(FocusEvent e) 
			{
				btnCheckOut.setVisible(false);
			}
		});
		//check out the book
		btnCheckOut.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String sql = "INSERT INTO bookReturn VALUES (?, ?, ?, ?, ?)";
				//pass the information to the database
				try 
				{
					conn = DriverManager.getConnection(url, dbUser, dbPass);
					ps = conn.prepareStatement(sql);
					//insert user id
					ps.setString(1, (String) tableSearchResult.getValueAt(tableSearchResult.getSelectedRow(), 1));
					//insert book ISBN
					ps.setString(2, (String) tableSearchResult.getValueAt(tableSearchResult.getSelectedRow(), 3));
					//get the time the student check out the book
					ps.setString(3, new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(Calendar.getInstance().getTime()));
					//calculate the date the user needs to return the book
					Calendar c = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
					String date = sdf.format(c.getTime());
					try
					{
						c.setTime(sdf.parse(date));
						c.add(Calendar.DATE, 17);
						date = sdf.format(c.getTime());
						ps.setString(4, date);
					}
					catch(ParseException e1)
					{
						e1.printStackTrace();
					}
					//insert employee id
					ps.setString(5, id);
					ps.executeUpdate();
					
					//pop up success message
					Options op = new Options();
					UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
					UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
					UIManager.put("OptionPane.buttonFont", textFont);
					UIManager.put("Button.background", backgroundColor);
					UIManager.put("Button.foreground", foregroundColor);
					JOptionPane.showConfirmDialog(null, op.successCheckOut(), "Message", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
					
					//delete the book data from the bookBorrow table and remove the book from the search table
					String sql2 = "DELETE FROM bookBorrow WHERE stdID = ? AND bookISBN = ?";
					ps = conn.prepareStatement(sql2);
					ps.setString(1, (String) tableSearchResult.getValueAt(tableSearchResult.getSelectedRow(), 1));
					ps.setString(2, (String) tableSearchResult.getValueAt(tableSearchResult.getSelectedRow(), 3));
					ps.executeUpdate();
					//update the search table automatically
					btnSearch.doClick();
				} 
				catch(SQLException e1) 
				{
					System.out.println("SQLException: " + e1.getMessage());
					System.out.println("SQLState: " + e1.getSQLState());
					System.out.println("VendorError: " + e1.getErrorCode());
				}
			}
		});
		
		//scroll pane to show result
		JScrollPane scrollPaneResult = new JScrollPane();
		tableSearchResult = new JTable();
		tableSearchResult.getTableHeader().setForeground(foregroundColor);
		tableSearchResult.getTableHeader().setBackground(backgroundColor);
		tableSearchResult.getTableHeader().setFont(textFont);
		tableSearchResult.setForeground(foregroundColor);
		tableSearchResult.setFont(textFont);
		tableSearchResult.setBackground(backgroundColor);
		tableSearchResult.setRowHeight(50);
		tableSearchResult.setVisible(false);
		//cannot edit the table
		tableSearchResult.setDefaultEditor(Object.class, null);
		scrollPaneResult.setViewportView(tableSearchResult);
		//give the user the information of the book
		tableSearchResult.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				btnCheckOut.setVisible(true);
				
				//time format
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				
				//bring up the detail window
				BookCheckOutDetail checkOutDetaiWindow = new BookCheckOutDetail();
				checkOutDetaiWindow.lblNameField.setText((String) tableSearchResult.getValueAt(tableSearchResult.getSelectedRow(), 0));
				checkOutDetaiWindow.lblIDField.setText((String) tableSearchResult.getValueAt(tableSearchResult.getSelectedRow(), 1));
				checkOutDetaiWindow.lblTitleField.setText((String) tableSearchResult.getValueAt(tableSearchResult.getSelectedRow(), 2));
				checkOutDetaiWindow.lblISBNField.setText((String) tableSearchResult.getValueAt(tableSearchResult.getSelectedRow(), 3));
				//get the time the student check out the book
				checkOutDetaiWindow.lblDateCheckOutField.setText(sdf.format(Calendar.getInstance().getTime()));
				//calculate the date the user needs to return the book
				Calendar c = Calendar.getInstance();
				String date = checkOutDetaiWindow.lblDateCheckOutField.getText();
				try
				{
					c.setTime(sdf.parse(date));
					c.add(Calendar.DATE, 17);
					date = sdf.format(c.getTime());
					checkOutDetaiWindow.lblDateReturnField.setText(date);
				}
				catch(ParseException e1)
				{
					e1.printStackTrace();
				}
				checkOutDetaiWindow.BookCheckOutDetailWindow();
			}
		});
		
		
		//layout
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblStdID, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(idField, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
							.addGap(265))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(scrollPaneResult, GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(422)
									.addComponent(btnCheckOut, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
									.addGap(18)
									.addComponent(btnSearch, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)))
							.addGap(21))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(lblStdID, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
						.addComponent(idField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(scrollPaneResult, GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE, false)
						.addComponent(btnSearch)
						.addComponent(btnCheckOut, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addGap(7))
		);
		getContentPane().setLayout(groupLayout);
	}
	
	//set the employee ID 
	public void setID(String x)
	{
		id = x;
	}
	
	public void CheckOutBookWindow()
	{
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
