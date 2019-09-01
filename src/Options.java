//this class has all the message that can be used to display in a message box

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Options 
{
	//deafult font, color
	Setting set = new Setting();
	Font textFont = set.getTextDefaultFont();
	Color foregroundColor = set.getDefaultForeGround();
		
	//check if there is the setting file
	//if the file is not exist then use the default value
	public void getSettings() throws FileNotFoundException
	{
		File file = new File("lib/settings");
		if(file.exists())
		{
			textFont = set.getUserTextFont();
			foregroundColor = set.getUserForeGround();
		}
	}
	
	public JPanel dbLoginSuccess() 
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Login Successful");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel dbLoginError() 
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Please check your ID or Password");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));		
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel dbDuplicateError() 
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("There is already an account with this ID!");
		label.setFont(textFont);
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel dbStdReg() 
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("You are registered as a student!");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel dbEmpReg() 
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("You are registered as an employee!");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}

	public JPanel regEmpty() 
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Some fields are left empty!");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}

	public JPanel regPassMismatch() 
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Your Password and Re-enter password do not match!");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel regWrongCode()
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("The employee code is incorrect!");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel defaultSetting()
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("The program will use default settings");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		Setting setting = new Setting();
		label.setFont(setting.getTextDefaultFont());
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(setting.getDefaultForeGround());
		panel.add(label);
		return panel;
	}
	
	public JPanel customSetting()
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("The program will use custom settings");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		Setting setting = new Setting();
		label.setFont(setting.getTextDefaultFont());
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(setting.getDefaultForeGround());
		panel.add(label);
		return panel;
	}
	
	public JPanel emptySearch()
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("We cannot find the book in our database!. Please try another one.");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel bookAvailable()
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("You can pick up this book from the librabry tommorrow.");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel bookUnavailable()
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("We are sorry. This book is not available right now. You may want to search again to get the new update from the server.");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel cancelBook()
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Are you sure you want to cancel this book?");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel cancelBookSuccess()
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("The book had been removed.");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel logOut()
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Are you sure to log out?");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel saveData()
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Do you want to save the data?");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel nullID()
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("We cannot find the student's ID in the database!");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel successCheckOut()
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("You had successfully checkout the book!");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel successReturn()
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Done!");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel checkUpdateBook()
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Do you want to update the database?");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel emptyUpdate()
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Some fields are left empty.");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel updateSuccess()
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("The database had been updated.");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel failEdit()
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("You can only edit books that are available!");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel failDelete()
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("You cannot delete this book right now!");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel duplicateISBN()
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("There is already a book with this ISBN in the database.");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel closeEditForm()
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Do you want to save your data?");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel confirmDelete()
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Are you sure to delete this book from the database?");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel successDelete()
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("The book had been remove from the database.");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel cleanUpComplete()
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("The system database files had been cleaned up.");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel failRecover()
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Your email or password is unrecognizable.");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel successRecover(String pass)
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Your password is: " + pass);
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
	
	public JPanel reSearch()
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
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Please don't forget to search again to see to latest data.");
		label.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));	
		label.setFont(textFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(foregroundColor);
		panel.add(label);
		return panel;
	}
}
