//main GUI for the setting window, connects with the MainUI.java
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.NumberFormatter;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Color;
import javax.swing.JTextField;
import java.text.NumberFormat;
import java.util.Scanner;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SettingWindow extends JDialog 
{
	private static final long serialVersionUID = 1L;
	
	//deafult font, color
	Setting set = new Setting();
	Font labelFont = set.getLabelDefaultFont();
	Font textFont = set.getTextDefaultFont();
	Color backgroundColor = set.getDefaultBackGround();
	Color foregroundColor = set.getDefaultForeGround();
	
	//settings file value
	String label;
	String text;
	String labelSize;
	String textSize;
	int r_BG;
	int g_BG;
	int b_BG;
	int r_FG;
	int g_FG;
	int b_FG;
	
	public SettingWindow()
	{
		//return to main menu and close current window
		addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowClosed(WindowEvent e) 
			{
				MainUI mainUI = new MainUI();
				mainUI.setVisible(true);
			}
		});
		
		setResizable(false);
		setTitle("Setting");
		getContentPane().setBackground(backgroundColor);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//formatted value for RGB
		NumberFormat format = NumberFormat.getInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum(255);
	    formatter.setAllowsInvalid(false);
	    formatter.setCommitsOnValidEdit(true);
	    
		//Background
		JLabel lblBackGround = new JLabel("Background Color");
		lblBackGround.setForeground(foregroundColor);
		lblBackGround.setFont(textFont);
		
		//slider
		JSlider r_BG_slider = new JSlider();
		r_BG_slider.setPaintTicks(true);
		r_BG_slider.setFont(textFont);
		r_BG_slider.setMaximum(255);
		r_BG_slider.setBackground(backgroundColor);
		r_BG_slider.setValue(0);
		
		JSlider g_BG_slider = new JSlider();
		g_BG_slider.setPaintTicks(true);
		g_BG_slider.setMaximum(255);
		g_BG_slider.setFont(textFont);
		g_BG_slider.setBackground(backgroundColor);
		g_BG_slider.setValue(0);
		
		JSlider b_BG_slider = new JSlider();
		b_BG_slider.setPaintTicks(true);
		b_BG_slider.setMaximum(255);
		b_BG_slider.setFont(textFont);
		b_BG_slider.setBackground(backgroundColor);
		b_BG_slider.setValue(0);
		
		//field
		JFormattedTextField r_BG_Field = new JFormattedTextField(formatter);
		r_BG_Field.setForeground(foregroundColor);
		r_BG_Field.setFont(textFont);
		r_BG_Field.setColumns(10);
		r_BG_Field.setText("0");

		
		JFormattedTextField g_BG_Field = new JFormattedTextField(formatter);
		g_BG_Field.setForeground(foregroundColor);
		g_BG_Field.setFont(textFont);
		g_BG_Field.setColumns(10);
		g_BG_Field.setText("0");
		
		JFormattedTextField b_BG_Field = new JFormattedTextField(formatter);
		b_BG_Field.setForeground(foregroundColor);
		b_BG_Field.setFont(textFont);
		b_BG_Field.setColumns(10);
		b_BG_Field.setText("0");
	
		//label name
		JLabel r_BG_label = new JLabel("R");
		r_BG_label.setForeground(foregroundColor);
		r_BG_label.setFont(textFont);
		r_BG_label.setBackground(backgroundColor);
		
		JLabel g_BG_label = new JLabel("G");
		g_BG_label.setForeground(foregroundColor);
		g_BG_label.setFont(textFont);
		g_BG_label.setBackground(backgroundColor);
		
		JLabel b_BG_label = new JLabel("B");
		b_BG_label.setForeground(foregroundColor);
		b_BG_label.setFont(textFont);
		b_BG_label.setBackground(backgroundColor);
		
		//Foreground
		JLabel lblForeGroundColor = new JLabel("Foreground Color");
		lblForeGroundColor.setForeground(foregroundColor);
		lblForeGroundColor.setFont(textFont);
		
		//slider
		JSlider r_FG_slider = new JSlider();
		r_FG_slider.setPaintTicks(true);
		r_FG_slider.setMaximum(255);
		r_FG_slider.setFont(textFont);
		r_FG_slider.setBackground(backgroundColor);
		r_FG_slider.setValue(0);
		
		JSlider g_FG_slider = new JSlider();
		g_FG_slider.setPaintTicks(true);
		g_FG_slider.setMaximum(255);
		g_FG_slider.setFont(textFont);
		g_FG_slider.setBackground(backgroundColor);
		g_FG_slider.setValue(0);
		
		JSlider b_FG_slider = new JSlider();
		b_FG_slider.setPaintTicks(true);
		b_FG_slider.setMaximum(255);
		b_FG_slider.setFont(textFont);
		b_FG_slider.setBackground(backgroundColor);
		b_FG_slider.setValue(0);
		
		//field
		JFormattedTextField r_FG_Field = new JFormattedTextField(formatter);
		r_FG_Field.setForeground(foregroundColor);
		r_FG_Field.setFont(textFont);
		r_FG_Field.setColumns(10);
		r_FG_Field.setText("0");
		
		JFormattedTextField g_FG_Field = new JFormattedTextField(formatter);
		g_FG_Field.setForeground(foregroundColor);
		g_FG_Field.setFont(textFont);
		g_FG_Field.setColumns(10);
		g_FG_Field.setText("0");
		
		JFormattedTextField b_FG_Field = new JFormattedTextField(formatter);
		b_FG_Field.setForeground(foregroundColor);
		b_FG_Field.setFont(textFont);
		b_FG_Field.setColumns(10);
		b_FG_Field.setText("0");
		
		//label name
		JLabel r_FG_label = new JLabel("R");
		r_FG_label.setForeground(foregroundColor);
		r_FG_label.setFont(textFont);
		r_FG_label.setBackground(backgroundColor);

		JLabel g_FG_label = new JLabel("G");
		g_FG_label.setForeground(foregroundColor);
		g_FG_label.setFont(textFont);
		g_FG_label.setBackground(backgroundColor);
		
		JLabel b_FG_label = new JLabel("B");
		b_FG_label.setForeground(foregroundColor);
		b_FG_label.setFont(textFont);
		b_FG_label.setBackground(backgroundColor);
		
		//label font and size
		JLabel lblLabelFont = new JLabel("Label Font : ");
		lblLabelFont.setForeground(new Color(0, 70, 175));
		lblLabelFont.setFont(new Font("Lucida Handwriting", Font.PLAIN, 13));
		
		JLabel lblLabelSize = new JLabel("Label Size :");
		lblLabelSize.setForeground(new Color(0, 70, 175));
		lblLabelSize.setFont(new Font("Lucida Handwriting", Font.PLAIN, 13));
		
		//text font and size
		JLabel lblTextFont = new JLabel("Text Font :");
		lblTextFont.setForeground(new Color(0, 70, 175));
		lblTextFont.setFont(new Font("Lucida Handwriting", Font.PLAIN, 13));
		
		JLabel lblTextSize = new JLabel("Text Size");
		lblTextSize.setForeground(new Color(0, 70, 175));
		lblTextSize.setFont(new Font("Lucida Handwriting", Font.PLAIN, 13));
		
		//get all the alvailable font and put it in 2 combo boxes
		//label font
		JComboBox<String> labelFontComboBox = new JComboBox<String>();
		//text font
		JComboBox<String> textFontComboBox = new JComboBox<String>();
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] allTheFont = e.getAllFonts();
	    for(Font f : allTheFont)
	    {
	    	labelFontComboBox.addItem(f.getFontName());
	    	textFontComboBox.addItem(f.getFontName());
	    }
		labelFontComboBox.setSelectedIndex(0);
	    textFontComboBox.setSelectedIndex(0);
		
		//label text size
		JComboBox<Integer> labelSizeComboBox = new JComboBox<Integer>();
		for(int i = 5; i <= 50; i++)
		{
			labelSizeComboBox.addItem(i);
		}
		labelSizeComboBox.setSelectedIndex(0);
		
		//text text size
		JComboBox<Integer> textSizeComboBox = new JComboBox<Integer>();
		for(int i = 5; i <= 30; i++)
		{
			textSizeComboBox.addItem(i);
		}
		textSizeComboBox.setSelectedIndex(0);
		
		//Sample
		JLabel lblSample = new JLabel("Sample :");
		lblSample.setForeground(foregroundColor);
		lblSample.setFont(textFont);
		
		JLabel sampleBackground = new JLabel("Sample Background");
		sampleBackground.setHorizontalAlignment(SwingConstants.CENTER);
		sampleBackground.setOpaque(true);		
		
		JButton sampleButton = new JButton("Sample Button");
		sampleButton.setFocusPainted(false);
		
		JTextField sampleText = new JTextField();
		sampleText.setText("Sample Text Input");
		sampleText.setColumns(10);
		
		JCheckBox sampleCheckbox = new JCheckBox("Sample Check Box");
		sampleCheckbox.setFocusPainted(false);
		
		//change the background of the components according to the value the user chose
		//change the value in r bar to user value
		r_BG_Field.addFocusListener(new FocusAdapter() 
		{
			@Override
			public void focusLost(FocusEvent e) 
			{
				r_BG_slider.setValue(Integer.valueOf(r_BG_Field.getText()));
				//udapte the color in background 
				sampleBackground.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
				sampleButton.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
				sampleText.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
				sampleCheckbox.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
			}
		});
		//when r slider move then give the user the current value on the slider
		r_BG_slider.addMouseMotionListener(new MouseMotionAdapter() 
		{
			@Override
			public void mouseDragged(MouseEvent e) 
			{
				r_BG_Field.setText(String.valueOf(r_BG_slider.getValue()));
				//udapte the color in background 
				sampleBackground.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
				sampleButton.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
				sampleText.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
				sampleCheckbox.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
			}
		});
		//change the value in g bar to user value
		g_BG_Field.addFocusListener(new FocusAdapter() 
		{
			@Override
			public void focusLost(FocusEvent e) 
			{
				g_BG_slider.setValue(Integer.valueOf(g_BG_Field.getText()));
				//udapte the color in background 
				sampleBackground.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
				sampleButton.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
				sampleText.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
				sampleCheckbox.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
			}
		});
		//when g slider move then give the user the current value on the slider
		g_BG_slider.addMouseMotionListener(new MouseMotionAdapter() 
		{
			@Override
			public void mouseDragged(MouseEvent e) 
			{
				g_BG_Field.setText(String.valueOf(g_BG_slider.getValue()));
				//udapte the color in background 
				sampleBackground.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
				sampleButton.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
				sampleText.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
				sampleCheckbox.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
			}
		});		
		//change the value in b bar to user value
		b_BG_Field.addFocusListener(new FocusAdapter() 
		{
			@Override
			public void focusLost(FocusEvent e) 
			{
				b_BG_slider.setValue(Integer.valueOf(b_BG_Field.getText()));
				//udapte the color in background 
				sampleBackground.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
				sampleButton.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
				sampleText.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
				sampleCheckbox.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
			}
		});
		//when b slider move then give the user the current value on the slider
		b_BG_slider.addMouseMotionListener(new MouseMotionAdapter() 
		{
			@Override
			public void mouseDragged(MouseEvent e) 
			{
				b_BG_Field.setText(String.valueOf(b_BG_slider.getValue()));
				//udapte the color in background 
				sampleBackground.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
				sampleButton.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
				sampleText.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
				sampleCheckbox.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
			}
		});		
		
		//change the foreground of the components according to the value the user chose
		//change the value in r bar to user value
		r_FG_Field.addFocusListener(new FocusAdapter() 
		{
			@Override
			public void focusLost(FocusEvent e) 
			{
				r_FG_slider.setValue(Integer.valueOf(r_FG_Field.getText()));
				//udapte the color in foreground 
				sampleBackground.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
				sampleButton.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
				sampleText.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
				sampleCheckbox.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
			}
		});
		//when r slider move then give the user the current value on the slider
		r_FG_slider.addMouseMotionListener(new MouseMotionAdapter() 
		{
			@Override
			public void mouseDragged(MouseEvent e) 
			{
				r_FG_Field.setText(String.valueOf(r_FG_slider.getValue()));
				//udapte the color in foreground 
				sampleBackground.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
				sampleButton.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
				sampleText.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
				sampleCheckbox.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
			}
		});
		//change the value in g bar to user value
		g_FG_Field.addFocusListener(new FocusAdapter() 
		{
			@Override
			public void focusLost(FocusEvent e) 
			{
				g_FG_slider.setValue(Integer.valueOf(g_FG_Field.getText()));
				//udapte the color in foreground 
				sampleBackground.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
				sampleButton.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
				sampleText.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
				sampleCheckbox.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
			}
		});
		//when g slider move then give the user the current value on the slider
		g_FG_slider.addMouseMotionListener(new MouseMotionAdapter() 
		{
			@Override
			public void mouseDragged(MouseEvent e) 
			{
				g_FG_Field.setText(String.valueOf(g_FG_slider.getValue()));
				//udapte the color in foreground 
				sampleBackground.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
				sampleButton.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
				sampleText.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
				sampleCheckbox.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
			}
		});
		//change the value in b bar to user value
		b_FG_Field.addFocusListener(new FocusAdapter() 
		{
			@Override
			public void focusLost(FocusEvent e) 
			{
				b_FG_slider.setValue(Integer.valueOf(b_FG_Field.getText()));
				//udapte the color in foreground 
				sampleBackground.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
				sampleButton.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
				sampleText.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
				sampleCheckbox.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
			}
		});
		//when b slider move then give the user the current value on the slider
		b_FG_slider.addMouseMotionListener(new MouseMotionAdapter() 
		{
			@Override
			public void mouseDragged(MouseEvent e) 
			{
				b_FG_Field.setText(String.valueOf(b_FG_slider.getValue()));
				//udapte the color in foreground 
				sampleBackground.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
				sampleButton.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
				sampleText.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
				sampleCheckbox.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
			}
		});
		
		//change label font and size according to the user choice
		labelFontComboBox.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String fontName = (String) labelFontComboBox.getSelectedItem();
				int size = (int) labelSizeComboBox.getSelectedItem();
				sampleBackground.setFont(new Font(fontName, Font.PLAIN, size));
			}
		});
		labelSizeComboBox.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String fontName = (String) labelFontComboBox.getSelectedItem();
				int size = (int) labelSizeComboBox.getSelectedItem();
				sampleBackground.setFont(new Font(fontName, Font.PLAIN, size));
			}
		});
		
		//change text font and size according to the user choice
		textFontComboBox.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String fontName = (String) textFontComboBox.getSelectedItem();
				int size = (int) textSizeComboBox.getSelectedItem();
				sampleButton.setFont(new Font(fontName, Font.PLAIN, size));
				sampleCheckbox.setFont(new Font(fontName, Font.PLAIN, size));
				sampleText.setFont(new Font(fontName, Font.PLAIN, size));
			}
		});
		textSizeComboBox.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String fontName = (String) textFontComboBox.getSelectedItem();
				int size = (int) textSizeComboBox.getSelectedItem();
				sampleButton.setFont(new Font(fontName, Font.PLAIN, size));
				sampleCheckbox.setFont(new Font(fontName, Font.PLAIN, size));
				sampleText.setFont(new Font(fontName, Font.PLAIN, size));
			}
		});
		
		//check if there is a settings file then set the current values of all component according to the settings
		File file = new File("lib/settings");
		if(file.exists())
		{
			try 
			{
				Scanner sc = new Scanner(file);
				labelFontComboBox.setSelectedItem(sc.nextLine());
				labelSizeComboBox.setSelectedItem(Integer.valueOf(sc.nextLine()));
				textFontComboBox.setSelectedItem(sc.nextLine());
				textSizeComboBox.setSelectedItem(Integer.valueOf(sc.nextLine()));
				r_BG_Field.setText(sc.nextLine());
				g_BG_Field.setText(sc.nextLine());
				b_BG_Field.setText(sc.nextLine());
				r_BG_slider.setValue(Integer.valueOf(r_BG_Field.getText()));
				b_BG_slider.setValue(Integer.valueOf(b_BG_Field.getText()));
				g_BG_slider.setValue(Integer.valueOf(g_BG_Field.getText()));
				r_FG_Field.setText(sc.nextLine());
				g_FG_Field.setText(sc.nextLine());
				b_FG_Field.setText(sc.nextLine());
				r_FG_slider.setValue(Integer.valueOf(r_FG_Field.getText()));
				g_FG_slider.setValue(Integer.valueOf(g_FG_Field.getText()));
				b_FG_slider.setValue(Integer.valueOf(b_FG_Field.getText()));
				//upadate the sample with current values
				sampleBackground.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
				sampleButton.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
				sampleText.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
				sampleCheckbox.setBackground(new Color(Integer.valueOf(r_BG_Field.getText()), Integer.valueOf(g_BG_Field.getText()), Integer.valueOf(b_BG_Field.getText())));
				sampleBackground.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
				sampleButton.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
				sampleText.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
				sampleCheckbox.setForeground(new Color(Integer.valueOf(r_FG_Field.getText()), Integer.valueOf(g_FG_Field.getText()), Integer.valueOf(b_FG_Field.getText())));
				String labelFontName = (String) labelFontComboBox.getSelectedItem();
				int labelFontSize = (int) labelSizeComboBox.getSelectedItem();
				sampleBackground.setFont(new Font(labelFontName, Font.PLAIN, labelFontSize));
				String textFontName = (String) textFontComboBox.getSelectedItem();
				int textFontSize = (int) textSizeComboBox.getSelectedItem();
				sampleButton.setFont(new Font(textFontName, Font.PLAIN, textFontSize));
				sampleCheckbox.setFont(new Font(textFontName, Font.PLAIN, textFontSize));
				sampleText.setFont(new Font(textFontName, Font.PLAIN, textFontSize));
				sc.close();
			} 
			catch(FileNotFoundException e2) 
			{
				e2.printStackTrace();
			}
		}
		
		//save settings
		JButton deafultButton = new JButton("Use Default Settings");
		deafultButton.setForeground(foregroundColor);
		deafultButton.setFont(textFont);
		deafultButton.setBackground(backgroundColor);
		deafultButton.setFocusPainted(false);
		
		//option message class
		Options op = new Options();
		//if the user chose to use the default setting then pop up the message, and over-write the settings file
		//and go back to mainUI
		deafultButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					FileWriter fw = new FileWriter("lib/settings");
					PrintWriter pw = new PrintWriter(fw);
					//print out label font and size
					pw.println("Lucida Handwriting Italic");
					pw.println("22");
					//print out text font and size
					pw.println("Lucida Handwriting Italic");
					pw.println("13");
					//print out background color
					pw.println("255");
					pw.println("255");
					pw.println("255");
					//print out foreground color
					pw.println("0");
					pw.println("70");
					pw.println("175");
					//close printer
					pw.close();
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
				
				UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
				UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
				UIManager.put("OptionPane.buttonFont", textFont);
				UIManager.put("Button.background", backgroundColor);
				UIManager.put("Button.foreground", foregroundColor);
				JOptionPane.showConfirmDialog(null, op.defaultSetting(), "Message!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		JButton customButton = new JButton("Use This Settings");
		customButton.setForeground(foregroundColor);
		customButton.setFont(textFont);
		customButton.setBackground(backgroundColor);
		customButton.setFocusPainted(false);
		//if the user chose to use the custom setting then pop up the message, then wirte a settings file in lib folder, and then go back to mainUI
		customButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					FileWriter fw = new FileWriter("lib/settings");
					PrintWriter pw = new PrintWriter(fw);
					//print out label font and size
					pw.println(labelFontComboBox.getSelectedItem());
					pw.println(labelSizeComboBox.getSelectedItem());
					//print out text font and size
					pw.println(textFontComboBox.getSelectedItem());
					pw.println(textSizeComboBox.getSelectedItem());
					//print out background color
					pw.println(r_BG_slider.getValue());
					pw.println(g_BG_slider.getValue());
					pw.println(b_BG_slider.getValue());
					//print out foreground color
					pw.println(r_FG_slider.getValue());
					pw.println(g_FG_slider.getValue());
					pw.println(b_FG_slider.getValue());
					//close printer
					pw.close();
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
				
				UIManager.put("OptionPane.background",new ColorUIResource(backgroundColor));
				UIManager.put("Panel.background",new ColorUIResource(backgroundColor));
				UIManager.put("OptionPane.buttonFont", textFont);
				UIManager.put("Button.background", backgroundColor);
				UIManager.put("Button.foreground", foregroundColor);
				JOptionPane.showConfirmDialog(null, op.customSetting(), "Message!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
			}
		});		
		
		//layout
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblSample, GroupLayout.PREFERRED_SIZE, 325, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
									.addGroup(groupLayout.createSequentialGroup()
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addComponent(r_BG_label)
											.addComponent(g_BG_label, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
											.addComponent(b_BG_label, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addGroup(groupLayout.createSequentialGroup()
												.addComponent(g_BG_slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(g_BG_Field, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
											.addGroup(groupLayout.createSequentialGroup()
												.addComponent(r_BG_slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(r_BG_Field, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
											.addGroup(groupLayout.createSequentialGroup()
												.addComponent(b_BG_slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(b_BG_Field, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))))
									.addComponent(lblBackGround, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(sampleBackground, GroupLayout.PREFERRED_SIZE, 314, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(r_FG_label, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(r_FG_slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(r_FG_Field, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(g_FG_label, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(g_FG_slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(g_FG_Field, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
									.addComponent(lblForeGroundColor, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(sampleCheckbox, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(b_FG_label, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(b_FG_slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(b_FG_Field, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
								.addComponent(sampleText, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE)
								.addComponent(sampleButton, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE))
							.addGap(68)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(lblTextFont, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblTextSize, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(textSizeComboBox, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
										.addComponent(deafultButton, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE)
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
											.addComponent(customButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(textFontComboBox, Alignment.LEADING, 0, 193, Short.MAX_VALUE))))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(lblLabelSize, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblLabelFont, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(labelFontComboBox, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)
										.addComponent(labelSizeComboBox, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))))))
					.addGap(3))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(36)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblBackGround)
						.addComponent(lblForeGroundColor, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblLabelFont, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelFontComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(r_BG_slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(r_BG_Field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(r_FG_label, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
						.addComponent(r_BG_label)
						.addComponent(r_FG_slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(r_FG_Field, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblLabelSize, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
							.addComponent(labelSizeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(g_BG_label, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
									.addComponent(g_BG_slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(g_BG_Field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(g_FG_label, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
									.addComponent(g_FG_slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblTextFont, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
									.addComponent(textFontComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(b_FG_slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(b_BG_slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(b_BG_label, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
											.addComponent(b_BG_Field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(b_FG_label, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)))
									.addGap(18)
									.addComponent(lblSample, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblTextSize, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
									.addComponent(textSizeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(b_FG_Field, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))))
						.addComponent(g_FG_Field, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(sampleBackground, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(sampleCheckbox)
								.addComponent(customButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addGap(56)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(sampleButton)
								.addComponent(deafultButton))
							.addGap(58)
							.addComponent(sampleText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
	}
	
	public void SetWindow()
	{
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
