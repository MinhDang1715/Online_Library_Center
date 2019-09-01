//this class is used to get the student name, the books he wants to borrow, and his ID from the database, connects with CheckOutBook.java

public class UserInfo 
{
	private String name;
	private String bookName;
	private String id;
	private String bookISBN;
	
	public UserInfo(String name,String id, String bookName,String bookISBN)
	{
		this.name = name;
		this.bookName = bookName;
		this.id = id;
		this.bookISBN = bookISBN;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getBookName()
	{
		return bookName;
	}
	
	public String getID()
	{
		return id;
	}
	
	public String getBookISBN()
	{
		return bookISBN;
	}
}
