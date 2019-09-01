//the main gui for the employee

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EmployeeView extends JFrame 
{
	private static final long serialVersionUID = 1L;
	
	//deafult font, color
	Setting set = new Setting();
	Font labelFont = set.getLabelDefaultFont();
	Font textFont = set.getTextDefaultFont();
	Color backgroundColor = set.getDefaultBackGround();
	Color foregroundColor = set.getDefaultForeGround();

	JLabel lblWelcome;
	private String id = "";
	
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
	
	public EmployeeView() 
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
					dc.empLogoutTime(date, id);
					System.exit(0);
				}
			}
		});
		
		//welcome
		lblWelcome = new JLabel();
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setForeground(foregroundColor);
		lblWelcome.setFont(labelFont);
		
		//check out
		JButton btnCheckOut = new JButton("Book Checkout");
		btnCheckOut.setForeground(foregroundColor);
		btnCheckOut.setBackground(backgroundColor);
		btnCheckOut.setFont(textFont);
		btnCheckOut.setFocusPainted(false);
		//print up check out window
		btnCheckOut.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				CheckOutBook checkOutBook = new CheckOutBook();
				checkOutBook.setID(id);
				checkOutBook.CheckOutBookWindow();
			}
		});
		
		//update book database
		JButton btnUpdateBookDatabase = new JButton("Update Database");
		btnUpdateBookDatabase.setForeground(foregroundColor);
		btnUpdateBookDatabase.setFont(textFont);
		btnUpdateBookDatabase.setBackground(backgroundColor);
		btnUpdateBookDatabase.setFocusPainted(false);
		//pop up action for editing database
		btnUpdateBookDatabase.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				UpdateDataBaseMenu updateMenu = new UpdateDataBaseMenu();
				updateMenu.setID(id);
				updateMenu.UpdateDataBaseMenuWindow();
			}
		});
		
		//return
		JButton btnReturn = new JButton("Book Return");
		btnReturn.setForeground(foregroundColor);
		btnReturn.setFont(textFont);
		btnReturn.setBackground(backgroundColor);
		btnReturn.setFocusPainted(false);
		//bring up return window
		btnReturn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				ReturnBook returnBook = new ReturnBook();
				returnBook.setID(id);
				returnBook.ReturnBookWindow();
			}
		});
		
		//log out
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.setForeground(foregroundColor);
		btnLogOut.setFont(textFont);
		btnLogOut.setBackground(backgroundColor);
		btnLogOut.setFocusPainted(false);
		//ask the user if he want to log out
		btnLogOut.addActionListener(new ActionListener() 
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
					dc.empLogoutTime(date, id);
					System.exit(0);
				}
			}
		});
		
		//layout
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblWelcome, GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
					.addGap(0))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(65)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnLogOut, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
						.addComponent(btnUpdateBookDatabase, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
						.addComponent(btnReturn, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
						.addComponent(btnCheckOut, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE))
					.addGap(81))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblWelcome, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnCheckOut)
					.addGap(18)
					.addComponent(btnReturn, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnUpdateBookDatabase, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnLogOut, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(50, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
	}
	
	//set the employee ID 
	public void setID(String x)
	{
		id = x;
	}

	public void EmployeeViewWindow()
	{
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
