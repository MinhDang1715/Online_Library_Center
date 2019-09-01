//this class will give the user the detail of the book when they click on the result from the BookSearch.java, and they can delete the book from here

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BookDeleteForm extends JDialog 
{
	private static final long serialVersionUID = 1L;

	private String id = "";
	private String bookISBN = "";
	
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
	
	JLabel lblTitleDetail;
	JLabel lblISBNDetail;
	JLabel lblCategoryDetail;
	JLabel lblAuthorDetail;
	JLabel lblPicture;
	JTextArea bookSummary;
	JLabel lblStatusDetail;
	JScrollPane scrollPane;
	
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
	
	public BookDeleteForm() 
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

		setBackground(backgroundColor);	
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Book Info");
		
		addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowClosed(WindowEvent e) 
			{
				//set the book available again if not delete
				try
				{
					String sql = "UPDATE bookInfo SET bookStatus = 'Available' WHERE bookISBN = ?";
					conn = DriverManager.getConnection(url, dbUser, dbPass);
					ps = conn.prepareStatement(sql);
					ps.setString(1, lblISBNDetail.getText());
					ps.executeUpdate();
				}
				catch(SQLException e1) 
				{
					System.out.println("SQLException: " + e1.getMessage());
					System.out.println("SQLState: " + e1.getSQLState());
					System.out.println("VendorError: " + e1.getErrorCode());
				}
			}
		});
		
		//title
		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setForeground(foregroundColor);
		lblTitle.setFont(textFont);
		
		lblTitleDetail = new JLabel("");
		lblTitleDetail.setForeground(foregroundColor);
		lblTitleDetail.setFont(textFont);
		
		//ISBN
		JLabel lblISBN = new JLabel("ISBN:");
		lblISBN.setForeground(foregroundColor);
		lblISBN.setFont(textFont);
		
		lblISBNDetail = new JLabel("");
		lblISBNDetail.setForeground(foregroundColor);
		lblISBNDetail.setFont(textFont);
		
		//author
		JLabel lblAuthor = new JLabel("Author:");
		lblAuthor.setForeground(foregroundColor);
		lblAuthor.setFont(textFont);
		
		lblAuthorDetail = new JLabel("");
		lblAuthorDetail.setForeground(foregroundColor);
		lblAuthorDetail.setFont(textFont);
		
		//category
		JLabel lblCategory = new JLabel("Category:");
		lblCategory.setForeground(foregroundColor);
		lblCategory.setFont(textFont);
		
		lblCategoryDetail = new JLabel("");
		lblCategoryDetail.setForeground(foregroundColor);
		lblCategoryDetail.setFont(textFont);
		
		//book picture
		lblPicture = new JLabel("");
		lblPicture.setSize(330, 450);
		
		//book summary
		bookSummary = new JTextArea();
		bookSummary.setWrapStyleWord(true);
		bookSummary.setLineWrap(true);
		bookSummary.setForeground(foregroundColor);
		bookSummary.setFont(textFont);
		bookSummary.setBackground(backgroundColor);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		bookSummary.setBorder(border);
		bookSummary.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(bookSummary);
		scrollPane.setViewportView(bookSummary);
		
		//status
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setForeground(foregroundColor);
		lblStatus.setFont(textFont);
		
		lblStatusDetail = new JLabel("");
		lblStatusDetail.setFont(textFont);

		//borrow
		JButton btnDelete = new JButton("Remove from Database");
		btnDelete.setFocusPainted(false);
		btnDelete.setBackground(backgroundColor);
		btnDelete.setFont(textFont);
		btnDelete.setForeground(foregroundColor);
		btnDelete.addActionListener(new ActionListener() 
		{
			Options op = new Options();
			public void actionPerformed(ActionEvent e) 
			{
				//warning message
				UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
				UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
				UIManager.put("OptionPane.buttonFont", textFont);
				UIManager.put("Button.background", backgroundColor);
				UIManager.put("Button.foreground", foregroundColor);
				int reply = JOptionPane.showConfirmDialog(null, op.confirmDelete(), "Warning!", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(reply == JOptionPane.YES_OPTION)
				{
					//update empDelete table, it shows who remove what and the time he remove it
					String sql = "INSERT INTO empDelete VALUES(?, ?, ?)";
					try
					{
						conn = DriverManager.getConnection(url, dbUser, dbPass);
						ps = conn.prepareStatement(sql);
						ps.setString(1, id);
						ps.setString(2, bookISBN);
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
					
					//delete the files in the system
					String sql2 =  "SELECT coverID, summaryID FROM bookDetailID WHERE bookISBN = ?";
					try
					{
						conn = DriverManager.getConnection(url, dbUser, dbPass);
						ps = conn.prepareStatement(sql2);
						ps.setString(1, bookISBN);
						rs = ps.executeQuery();
						if(rs.next())
						{
							//only delete if not unknown.jpg
							if(!rs.getString(1).equals("unknown.jpg"))
							{
								File cover = new File("cover/" + rs.getString(1));
								cover.delete();
							}
							
							File summary = new File("summary/" + rs.getString(2));
							summary.delete();
						}
					}
					catch(SQLException e1)
					{
						System.out.println("SQLException: " + e1.getMessage());
						System.out.println("SQLState: " + e1.getSQLState());
						System.out.println("VendorError: " + e1.getErrorCode());
					}

					//remove from the database the book with the chosen isbn
					try
					{
						//remove the book information from the bookInfo table and bookDetailID
						String sql3 = "DELETE FROM bookdetailid, bookInfo USING bookdetailid, bookInfo WHERE bookdetailid.bookISBN = bookInfo.bookISBN AND bookInfo.bookISBN = ?";
						conn = DriverManager.getConnection(url, dbUser, dbPass);
						ps = conn.prepareStatement(sql3);
						ps.setString(1, bookISBN);
						ps.executeUpdate();
						
						//success message
						UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
						UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
						UIManager.put("OptionPane.buttonFont", textFont);
						UIManager.put("Button.background", backgroundColor);
						UIManager.put("Button.foreground", foregroundColor);
						JOptionPane.showConfirmDialog(null, op.successDelete(), "Message", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
					}
					catch(SQLException e1)
					{
						System.out.println("SQLException: " + e1.getMessage());
						System.out.println("SQLState: " + e1.getSQLState());
						System.out.println("VendorError: " + e1.getErrorCode());
					}
					UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
					UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
					UIManager.put("OptionPane.buttonFont", textFont);
					UIManager.put("Button.background", backgroundColor);
					UIManager.put("Button.foreground", foregroundColor);
					JOptionPane.showConfirmDialog(null, op.reSearch(), "Message", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
					dispose();
				}
			}
		});
		
		//layout
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblPicture, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(18)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblStatus, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(lblStatusDetail, GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
										.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)))
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnDelete))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblTitle, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
								.addComponent(lblISBN, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
								.addComponent(lblAuthor, GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
								.addComponent(lblCategory, GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblCategoryDetail, GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
								.addComponent(lblAuthorDetail, GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
								.addComponent(lblTitleDetail, GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
								.addComponent(lblISBNDetail, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblTitleDetail, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblISBN, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
						.addComponent(lblISBNDetail, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
							.addComponent(lblAuthor, GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblAuthorDetail, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCategory, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblCategoryDetail, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 327, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblStatus, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblStatusDetail, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnDelete))
						.addComponent(lblPicture, GroupLayout.PREFERRED_SIZE, 460, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(33, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
	}

	//set the book ISBN
	public void setBookISBN(String bookNum)
	{
		bookISBN = bookNum;
	}
	
	//set the user ID 
	public void setID(String x)
	{
		id = x;
	}

	public void BookDeleteFormWindow()
	{
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
