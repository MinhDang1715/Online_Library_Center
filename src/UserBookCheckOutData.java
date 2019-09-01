//this class is used to give the student the information of book they want to borrow and the day he can pick up from the library, connects with StudentView.java

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UserBookCheckOutData
{
	private String bookName;
	private String checkOutDate;
	
	public UserBookCheckOutData(String name, String date)
	{
		bookName = name;
		
		//add one day to the day the user check out the book online
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try 
		{
			c.setTime(sdf.parse(date));
			c.add(Calendar.DATE, 1);
			date = new SimpleDateFormat("MM/dd/yyyy").format(c.getTime());
			checkOutDate = date;
		} 
		catch(ParseException e) 
		{
			e.printStackTrace();
		}
	}
	
	public String getBookName()
	{
		return bookName;
	}
	
	public String getCheckOutDate() throws ParseException
	{
		return checkOutDate;
	}
}
