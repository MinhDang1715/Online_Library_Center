//this class check for the setting of the whole program

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Setting 
{
	//default label font size, style
	private final Font defaultLabelFont = new Font("Lucida Handwriting Italic", Font.PLAIN, 22);
	
	//default text font size style
	private final Font defaultTextFont = new Font("Lucida Handwriting Italic", Font.PLAIN, 13);
	
	//default background color
	private final Color defaultBackGround = new Color(255, 255, 255);
	
	//default foreground color
	private final Color defaultForeGround = new Color(0, 70, 175);
	
	//user choice of font, background and foreground
	Font userLabelFont;
	Font userTextFont;
	Color userBackGround;
	Color userForeGround;
	
	//user's settings are store in this file
	File file = new File("lib/settings");
	Scanner sc;
	
	public Font getLabelDefaultFont()
	{
		return defaultLabelFont;
	}
	
	public Font getTextDefaultFont()
	{
		return defaultTextFont;
	}
	
	public Color getDefaultBackGround()
	{
		return defaultBackGround;
	}
	
	public Color getDefaultForeGround()
	{
		return defaultForeGround;
	}
	
	//user's custom font are save in a .txt file
	public Font getUserLabelFont() throws FileNotFoundException
	{
		sc = new Scanner(file);
		String lblFontName = sc.nextLine();
		int lblFontSize = sc.nextInt();
		userLabelFont = new Font(lblFontName, Font.PLAIN, lblFontSize);
		return userLabelFont ;
	}
	
	public Font getUserTextFont() throws FileNotFoundException
	{
		sc = new Scanner(file);
		skipLine(sc, 3);
		String txtfontName = sc.nextLine();
		int txtFontSize = sc.nextInt();
		userTextFont = new Font(txtfontName, Font.PLAIN, txtFontSize);
		return userTextFont ;
	}
	
	public Color getUserBackGround() throws FileNotFoundException
	{
		sc = new Scanner(file);
		//go to line with the rgb value for user back ground
		skipLine(sc, 5);
		int r = sc.nextInt();
		int g = sc.nextInt();
		int b = sc.nextInt();
		userBackGround = new Color(r, g, b);
		return userBackGround;
	}
	
	public Color getUserForeGround() throws FileNotFoundException
	{
		sc = new Scanner(file);
		//go to line with the rgb value for user back ground
		skipLine(sc, 8);
		int r = sc.nextInt();
		int g = sc.nextInt();
		int b = sc.nextInt();
		userForeGround = new Color(r, g, b);
		return userForeGround;
	}
	
	//skip to the x line of a file
	public void skipLine(Scanner sc, int x) throws FileNotFoundException
	{
		for(int i = 1; i < x; i++)
		{
			sc.nextLine();
		}
	}
	
}
