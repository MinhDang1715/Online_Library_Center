//this class is used for the basic connection to the database
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

public class DatabaseConection 
{
	//SQL
	private String url = "";
	private String dbUser = ;
	private String dbPass = ;
	private Connection conn = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;
	
	//user info 
	Boolean loginResult;
	
	//book info
	String bookStatus = "";

	
	//deafult font, color
	Setting set = new Setting();
	Font labelFont = set.getLabelDefaultFont();
	Font textFont = set.getTextDefaultFont();
	Color backgroundColor = set.getDefaultBackGround();
	Color foregroundColor = set.getDefaultForeGround();
	
	//check if there is the setting file
	//if the file is not exist then use the default value
	public void getSettings() throws FileNotFoundException
	{
		File file = new File("lib/settings");
		if(file.exists())
		{
			textFont = set.getUserTextFont();
			backgroundColor = set.getUserBackGround();
			foregroundColor = set.getUserForeGround();
		}
	}
	
	public void getConnection()
	{
		try
		{
			conn = DriverManager.getConnection(url, dbUser, dbPass);
			System.out.println("Connection Ebstablished");
		}
		catch(SQLException e)
		{
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		}
	}
	
	//student login 
	public void stdLogin(String id, String pass)
	{
		String sql = "SELECT * FROM StdInfo WHERE stdID = ? AND stdPass = ?";
		try
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
			
			conn = DriverManager.getConnection(url, dbUser, dbPass);
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pass);
			rs = ps.executeQuery();
			
			//option message class
			Options op = new Options();
			
			if(rs.next())
			{
				//find a user
				loginResult = true;
				
				//success message
				UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
				UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
				UIManager.put("OptionPane.buttonFont", textFont);
				UIManager.put("Button.background", backgroundColor);
				UIManager.put("Button.foreground", foregroundColor);
				JOptionPane.showConfirmDialog(null, op.dbLoginSuccess(), "Success!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);

				//open student window
				StudentView sv = new StudentView();
				sv.lblWelcome.setText("Welcome " + rs.getString(2));
				sv.setID(id);
				sv.StudentViewWindow();
			}
			else
			{
				//cannot find a user
				loginResult = false;
				UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
				UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
				UIManager.put("OptionPane.buttonFont", textFont);
				UIManager.put("Button.background", backgroundColor);
				UIManager.put("Button.foreground", foregroundColor);
				JOptionPane.showConfirmDialog(null, op.dbLoginError(), "Error!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
			}
			conn.close();
		}
		catch(SQLException e)
		{
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		}
		
	}
	
	//student register
	public void stdReg(String id, String name, String pass, String email)
	{
		String sql = "INSERT INTO stdInfo(stdID, stdName, stdPass, stdEmail) VALUES (?, ?, ?, ?)";
		
		try
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
			
			conn = DriverManager.getConnection(url, dbUser, dbPass);
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, name);
			ps.setString(3, pass);
			ps.setString(4, email);
			ps.executeUpdate();
			
			//option message class
			Options op = new Options();
			//print out success message
			UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
			UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
			UIManager.put("OptionPane.buttonFont", textFont);
			UIManager.put("Button.background", backgroundColor);
			UIManager.put("Button.foreground", foregroundColor);
			JOptionPane.showConfirmDialog(null, op.dbStdReg(), "Success!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
		}
		catch(SQLException e)
		{
			//option message class
			Options op = new Options();
			
			//pop up error if there is a duplicate ID
			UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
			UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
			UIManager.put("OptionPane.buttonFont", textFont);
			UIManager.put("Button.background", backgroundColor);
			UIManager.put("Button.foreground", foregroundColor);
			JOptionPane.showConfirmDialog(null, op.dbDuplicateError(), "Error!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
			
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		}
	}
	
	//employee login 
	public void empLogin(String id, String pass)
	{
		String sql = "SELECT * FROM empInfo WHERE empID = ? AND empPass = ?";
			
		try
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
			
			conn = DriverManager.getConnection(url, dbUser, dbPass);
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pass);
			rs = ps.executeQuery();
			
			//option message class
			Options op = new Options();
			
			if(rs.next())
			{
				//find a user
				loginResult = true;
				
				UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
				UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
				UIManager.put("OptionPane.buttonFont", textFont);
				UIManager.put("Button.background", backgroundColor);
				UIManager.put("Button.foreground", foregroundColor);
				JOptionPane.showConfirmDialog(null, op.dbLoginSuccess(), "Success!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
				
				//open employee window
				EmployeeView ev = new EmployeeView();
				ev.lblWelcome.setText("Welcome " + rs.getString(2));
				ev.setID(id);
				ev.EmployeeViewWindow();
			}
			else
			{
				//cannot find a user
				loginResult = false;
				UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
				UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
				UIManager.put("OptionPane.buttonFont", textFont);
				UIManager.put("Button.background", backgroundColor);
				UIManager.put("Button.foreground", foregroundColor);
				JOptionPane.showConfirmDialog(null, op.dbLoginError(), "Error!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
			}
		}
		catch(SQLException e)
		{
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
	}
	
	//employee register
	public void empReg(String id, String name, String pass, String email)
	{
		String sql = "INSERT INTO empInfo(empID, empName, empPass, empEmail) VALUES (?, ?, ?, ?)";
				
		try
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
			
			conn = DriverManager.getConnection(url, dbUser, dbPass);
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, name);
			ps.setString(3, pass);
			ps.setString(4, email);
			ps.executeUpdate();
			
			//option message class
			Options op = new Options();
					
			//print out success message
			UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
			UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
			UIManager.put("OptionPane.buttonFont", textFont);
			UIManager.put("Button.background", backgroundColor);
			UIManager.put("Button.foreground", foregroundColor);
			JOptionPane.showConfirmDialog(null, op.dbEmpReg(), "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
		}
		catch(SQLException e)
		{
			//option message class
			Options op = new Options();
			
			//pop up error if there is a duplicate ID
			UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
			UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
			UIManager.put("OptionPane.buttonFont", textFont);
			UIManager.put("Button.background", backgroundColor);
			UIManager.put("Button.foreground", foregroundColor);
			JOptionPane.showConfirmDialog(null, op.dbDuplicateError(), "Error!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
			
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
	}

	//student login time
	public void stdLoginTime(String time, String id)
	{
		String sql = "UPDATE stdInfo SET stdLogin = ? WHERE stdID = ?";
		
		try
		{
			conn = DriverManager.getConnection(url, dbUser, dbPass);
			ps = conn.prepareStatement(sql);
			ps.setString(1, time);
			ps.setString(2, id);
			ps.executeUpdate();
		}
		catch(SQLException e)
		{
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
	}
	
	//std logout time
	public void stdLogoutTime(String time, String id)
	{
		//update the time the student log out of his account
		String sql = "UPDATE stdInfo SET stdLogout = ? WHERE stdID = ?";
		try
		{
			conn = DriverManager.getConnection(url, dbUser, dbPass);
			ps = conn.prepareStatement(sql);
			ps.setString(1, time);
			ps.setString(2, id);
			ps.executeUpdate();
		}
		catch(SQLException e1)
		{
			System.out.println("SQLException: " + e1.getMessage());
			System.out.println("SQLState: " + e1.getSQLState());
			System.out.println("VendorError: " + e1.getErrorCode());
		}
	}
	
	//employee login time
	public void empLoginTime(String time, String id)
	{
		String sql = "UPDATE empInfo SET empLogin = ? WHERE empID = ?";
			
		try
		{
			conn = DriverManager.getConnection(url, dbUser, dbPass);
			ps = conn.prepareStatement(sql);
			ps.setString(1, time);
			ps.setString(2, id);
			ps.executeUpdate();
		}
		catch(SQLException e)
		{
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
	}
	
	//employee logout time
	public void empLogoutTime(String time, String id)
	{
		//update the time the student log out of his account
		String sql = "UPDATE empInfo SET empLogout = ? WHERE empID = ?";
		try
		{
			conn = DriverManager.getConnection(url, dbUser, dbPass);
			ps = conn.prepareStatement(sql);
			ps.setString(1, time);
			ps.setString(2, id);
			ps.executeUpdate();
		}
		catch(SQLException e1)
		{
			System.out.println("SQLException: " + e1.getMessage());
			System.out.println("SQLState: " + e1.getSQLState());
			System.out.println("VendorError: " + e1.getErrorCode());
		}
	}
	
	//get login result 
	public Boolean getResult()
	{
		return loginResult;
	}
	
	//update the book bookBorrow according to the user
	public void bookBorrow(String id, String bookISBN, String time)
	{
		String sql = "INSERT INTO bookBorrow VALUES (?, ?, ?)";

		try
		{
			conn = DriverManager.getConnection(url, dbUser, dbPass);
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, bookISBN);
			ps.setString(3, time);
			ps.executeUpdate();
		}
		catch(SQLException e)
		{
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
	}
	
	//change the status of a book after it is borrowed
	public void checkOut(String bookISBN)
	{
		String sql = "UPDATE bookInfo SET bookStatus = 'Unavailable' WHERE bookISBN = ?";

		try
		{
			conn = DriverManager.getConnection(url, dbUser, dbPass);
			ps = conn.prepareStatement(sql);
			ps.setString(1, bookISBN);
			ps.executeUpdate();
		}
		catch(SQLException e)
		{
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
	}
	
	//clean the system database if there are any unused files then delete it
	public void cleanUp()
	{
		String sql = "SELECT coverID, summaryID FROM bookDetailID";

		try
		{
			conn = DriverManager.getConnection(url, dbUser, dbPass);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ArrayList<String> coverID = new ArrayList<String>();
			ArrayList<String> summaryID = new ArrayList<String>();
			while(rs.next())
			{
				coverID.add(rs.getString(1));
				summaryID.add(rs.getString(2));
			}
			
			//compare the name of each file in the folder which the corresponding list
			//if find a match then remove the file's name from the list
			//else delete the file if there is no match
			
			//summary .txt file
			File summaryFolder = new File("summary");
			File[] summaryFileTemp = summaryFolder.listFiles();
			//change from file array to an array list
			ArrayList<String> summaryFile = new ArrayList<String>();
			for(int i = 0; i < summaryFileTemp.length; i++)
			{
				String fileName = summaryFileTemp[i].toString().replace("summary\\", "");
				summaryFile.add(fileName);
			}
			//compare which one is unused
			for(int i = 0; i < summaryFile.size(); i++)
			{
				for(int t = 0; t < summaryID.size(); t++)
				{
					if(summaryFile.get(i).equals(summaryID.get(t)))
					{
						summaryFile.remove(i);
					}
				}
			}
			//delete
			for(int i = 0; i < summaryFile.size(); i++)
			{
				File fileToDelete = new File("summary/" + summaryFile.get(i));
				fileToDelete.delete();
			}
			
			//cover pictures
			File coverFolder = new File("cover");
			File[] coverFileTemp = coverFolder.listFiles();
			//change from file array to an array list
			ArrayList<String> coverFile = new ArrayList<String>();
			for(int i = 0; i < coverFileTemp.length; i++)
			{
				String fileName = coverFileTemp[i].toString().replace("cover\\", "");
				coverFile.add(fileName);
			}			
			//compare which one is unused
			for(int i = 0; i < coverFile.size(); i++)
			{
				for(int t = 0; t < coverID.size(); t++)
				{
					if(coverFile.get(i).equals(coverID.get(t)))
					{
						coverFile.remove(i);
					}
				}
			}
			//delete
			for(int i = 0; i < coverFile.size(); i++)
			{
				//keep the default unknow picture from being deleted
				if(!coverFile.get(i).equals("unknown.jpg"))
				{
					File fileToDelete = new File("cover/" + coverFile.get(i));
					fileToDelete.delete();
				}
			}
			
			//success message
			Options op = new Options();
			UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
			UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
			UIManager.put("OptionPane.buttonFont", textFont);
			UIManager.put("Button.background", backgroundColor);
			UIManager.put("Button.foreground", foregroundColor);
			JOptionPane.showConfirmDialog(null, op.cleanUpComplete(), "Message!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
		}
		catch(SQLException e)
		{
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
	}
	
	//check book status
	public void checkBookStatus(String bookISBN)
	{
		String sql = "SELECT bookStatus FROM bookInfo WHERE bookISBN = ?";

		try
		{
			conn = DriverManager.getConnection(url, dbUser, dbPass);
			ps = conn.prepareStatement(sql);
			ps.setString(1, bookISBN);
			rs = ps.executeQuery();
			if(rs.next())
			{
				bookStatus = rs.getString(1);
			}
		}
		catch(SQLException e)
		{
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
	}
	
	//get book status result
	public String getBookResult()
	{
		return bookStatus;
	}
	
	//recover password
	public void empRecoverPass(String id, String email)
	{
		String sql = "SELECT empPass FROM empInfo WHERE empID = ? AND empEmail = ?";
			
		try
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
				
			conn = DriverManager.getConnection(url, dbUser, dbPass);
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, email);
			rs = ps.executeQuery();
				
			//option message class
			Options op = new Options();
				
			if(rs.next())
			{	
				UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
				UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
				UIManager.put("OptionPane.buttonFont", textFont);
				UIManager.put("Button.background", backgroundColor);
				UIManager.put("Button.foreground", foregroundColor);
				JOptionPane.showConfirmDialog(null, op.successRecover(rs.getString(1)), "Success!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);

			}
			else
			{
				UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
				UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
				UIManager.put("OptionPane.buttonFont", textFont);
				UIManager.put("Button.background", backgroundColor);
				UIManager.put("Button.foreground", foregroundColor);
				JOptionPane.showConfirmDialog(null, op.failRecover(), "Error!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
			}
		}
		catch(SQLException e)
		{
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
	}
	
	//recover password
	public void stdRecoverPass(String id, String email)
	{
		String sql = "SELECT stdPass FROM stdInfo WHERE stdID = ? AND stdEmail = ?";
				
		try
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
					
			conn = DriverManager.getConnection(url, dbUser, dbPass);
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, email);
			rs = ps.executeQuery();
					
			//option message class
			Options op = new Options();
					
			if(rs.next())
			{	
				UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
				UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
				UIManager.put("OptionPane.buttonFont", textFont);
				UIManager.put("Button.background", backgroundColor);
				UIManager.put("Button.foreground", foregroundColor);
				JOptionPane.showConfirmDialog(null, op.successRecover(rs.getString(1)), "Success!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
			}
			else
			{
				UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
				UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
				UIManager.put("OptionPane.buttonFont", textFont);
				UIManager.put("Button.background", backgroundColor);
				UIManager.put("Button.foreground", foregroundColor);
				JOptionPane.showConfirmDialog(null, op.failRecover(), "Error!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
			}
		}
		catch(SQLException e)
		{
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
	}
	
	//get a random book in the given category
	public void stdRef(String bookCategory)
	{

	}
}
