//this class holds the information of the book that the students needs to return to the library, connects with StudentViews.java

public class BookReturnInfo
{
	private String bookName;
	private String dateReturn;
	
	public BookReturnInfo(String bookName, String dateReturn)
	{
		this.bookName = bookName;
		this.dateReturn = dateReturn;
	}
	
	public String getBookName()
	{
		return bookName;
	}
	
	public String getDateReturn()
	{
		return dateReturn;
	}
}
