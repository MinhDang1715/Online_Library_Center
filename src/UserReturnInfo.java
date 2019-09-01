//this class is used to get the student name, the books he borrowed, and his ID from the database, and the date the book needs to be return
//connects with ReturnBook.java

public class UserReturnInfo 
{
	private String  name;
	private String  id;
	private String  bookName;
	private String  isbn;
	private String  checkOutDate;
	private String  returnDate;
	
	public UserReturnInfo(String name, String id, String bookName, String isbn, String checkOutDate, String returnDate)
	{
		this.name = name;
		this.id = id;
		this.bookName = bookName;
		this.isbn = isbn;
		this.checkOutDate = checkOutDate;
		this.returnDate = returnDate;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getID()
	{
		return id;
	}
	
	public String getBookName()
	{
		return bookName;
	}
	
	public String getISBN()
	{
		return isbn;
	}
	
	public String getCheckOutDate()
	{
		return checkOutDate;
	}
	
	public String getReturnDate()
	{
		return returnDate;
	}
	
}
