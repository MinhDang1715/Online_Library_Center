//this class is used to get the book basic information from the database when the user do a search, connects with BookSearch.java

public class BookInfo 
{
	private String ISBN;
	private String title;
	private String author;
	private String category;
	private String status;

	public BookInfo(String ISBN, String title, String author, String category, String status)
	{
		this.ISBN = ISBN;
		this.title = title;
		this.author = author;
		this.category = category;
		this.status = status;
	}
	
	public String getISBN()
	{
		return ISBN;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getAuthor()
	{
		return author;
	}
	
	public String getCategory()
	{
		return category;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	
	
}
