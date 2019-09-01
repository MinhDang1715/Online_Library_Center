import javax.swing.JDialog;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;

public class RandomBook extends JDialog 
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
	
	public RandomBook() 
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
		
		setTitle("What category would you like?");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	
		JLabel lblChooseCategory = new JLabel("Please choose your category:");
		lblChooseCategory.setForeground(foregroundColor);
		lblChooseCategory.setFont(textFont);
		
		JComboBox<String> comboBoxCategory = new JComboBox<String>();
		comboBoxCategory.setMaximumRowCount(3);
		comboBoxCategory.setBackground(backgroundColor);
		comboBoxCategory.setForeground(foregroundColor);
		comboBoxCategory.setFont(textFont);
		//add category to combo box
		try 
		{
			String sql = "SELECT bookCategory FROM bookInfo GROUP BY bookCategory";
			conn = DriverManager.getConnection(url, dbUser, dbPass);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next())
			{
				comboBoxCategory.addItem(rs.getString(1));
			}
		}
		catch(SQLException e) 
		{
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
		comboBoxCategory.setSelectedItem(1);
		
		JButton btnFind = new JButton("Go");
		btnFind.setForeground(foregroundColor);
		btnFind.setBackground(backgroundColor);
		btnFind.setFont(textFont);
		btnFind.setFocusPainted(false);
		//find an available random book with the chosen category 
		btnFind.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				//get the total row return
				int totalRow = 0;
				String sql = "SELECT count(*) AS TotalRow FROM bookInfo AS bi, bookDetailID AS bd WHERE bi.bookISBN = bd.bookISBN AND bookCategory = ? AND bookStatus = ?";
				try
				{
					conn = DriverManager.getConnection(url, dbUser, dbPass);
					ps = conn.prepareStatement(sql);
					ps.setString(1, (String) comboBoxCategory.getSelectedItem());
					ps.setString(2, "Available");
					rs = ps.executeQuery();
					if(rs.next())
					{
						totalRow = rs.getInt(1);
					}
				}
				catch(SQLException e1)
				{
					System.out.println("SQLException: " + e1.getMessage());
					System.out.println("SQLState: " + e1.getSQLState());
					System.out.println("VendorError: " + e1.getErrorCode());
				}
				//choose a random row
				String sql2 = "SELECT bookName, bi.bookISBN, bookAuthor, bookCategory, coverID, summaryID FROM bookInfo AS bi, bookDetailID AS bd WHERE bi.bookISBN = bd.bookISBN AND " + 
							"bookCategory = ? AND bookStatus = ?";
				try
				{
					conn = DriverManager.getConnection(url, dbUser, dbPass);
					ps = conn.prepareStatement(sql2);
					ps.setString(1, (String) comboBoxCategory.getSelectedItem());
					ps.setString(2, "Available");
					rs = ps.executeQuery();
					Random rng = new Random();
					int randomRow = rng.nextInt(totalRow) + 1;
					rs.absolute(randomRow);
					BookDetail bd = new BookDetail();
					bd.lblTitleDetail.setText(rs.getString(1));
					bd.lblISBNDetail.setText(rs.getString(2));
					bd.lblAuthorDetail.setText(rs.getString(3));
					bd.lblCategoryDetail.setText(rs.getString(4));
					bd.lblStatusDetail.setText("Available");
					bd.lblStatusDetail.setForeground(Color.green);
					//get book cover, and summary id 
					String coverID = rs.getString(5);
					String summaryID = rs.getString(6);
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
					//pass the user id to Book Detail
					bd.setID(id);
					//show the window
					bd.BookDetailWindow();
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
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblChooseCategory, GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(126)
					.addComponent(comboBoxCategory, 0, 178, Short.MAX_VALUE)
					.addGap(128))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(323)
					.addComponent(btnFind, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblChooseCategory, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(comboBoxCategory, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnFind)
					.addContainerGap(30, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
	}
	
	//set the user ID 
	public void setID(String x)
	{
		id = x;
	}
	
	public void RandomBookWindow() 
	{
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
