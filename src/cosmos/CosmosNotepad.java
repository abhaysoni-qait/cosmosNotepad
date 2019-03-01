package cosmos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.JMenuBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import java.awt.Panel;
import java.awt.ScrollPane;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.SwingConstants;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CosmosNotepad extends JFrame{
	private JTextArea jtextArea = null;
	private JScrollPane jscrollpane = null;
	private JMenuBar menuBar = null;
	private double cosmoVersion = 2.0;
	private TipsBox tipbox = null;
	private JPopupMenu popupMenu;

	public double getCosmoVersion() {
		return cosmoVersion;
	}

	public JTextArea getJtextArea() {
		return jtextArea;
	}

	public JScrollPane getJscrollpane() {
		return jscrollpane;
	}
	
	public void setTextSize() {
		NotepadFunctionalities.setTextSize(this);
	}

//Will ask for confirmation before closing
	private void confirmClose() {
		NotepadFunctionalities.confirmClose(this);
	}

//	Will be called each time window is resized
	private void reSize() {
		NotepadFunctionalities.reSize(this);
	}

//	Will be called when help->about is clicked
	private void showAbout() {
		NotepadFunctionalities.showAbout(this);
	}

//	Called when new->load is clicked 
	private void loadFile() {
		NotepadFunctionalities.loadFile(this);
	}
//	Called When new->Save is clicked
	private void saveFile(String format) {
		NotepadFunctionalities.saveFile(this, format);
	}
// Called when Edit -> Color is clicked
	private void setColor() {
//		System.out.println("set ColoR mETHOD lAUNCHED");
		NotepadFunctionalities.setColor(this);
	}
// Called when Edit->Font is clicked
	private void setFont() {
		NotepadFunctionalities.setFont(this);
	}
	
//	Called when Tools->Search and replace is clicked
	private void searchReplace() {
		NotepadFunctionalities.searchReplace(this);
	}
	
//	Called when Helps -> check For Updates 
	private void checkForUpdates() {
		NotepadFunctionalities.checkForUpdates(this);
	}
	
//	Called when Helps -> Tips for today is click
	private void getTips() {
		 tipbox = new TipsBox(this);
	}
	
//	Called when File -> Print is clicked
	private void printDocument() {
		NotepadFunctionalities.printDocument(this);
	}
	
//	Called When Tools -> Image/pdf to text is clicked
	private void doOCR() {
		NotepadFunctionalities.doOCR(this);
	}
	

//	this Method is to Initialize ALL menu added to menubar
	private void initializeMenuBar() {
		JMenu mnFile = new JMenu("File");
		mnFile.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(mnFile);

		JMenuItem mntmNew = new JMenuItem("New ");
		mnFile.add(mntmNew);
//		launches new instance of Notepad
		mntmNew.addActionListener((e) -> {Launcher_CosmosNotepad.main(null);} );
		mntmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, java.awt.event.InputEvent.ALT_DOWN_MASK ));

		JMenuItem mntmLoadFile = new JMenuItem("Load File");
		mnFile.add(mntmLoadFile);
		mntmLoadFile.addActionListener( e -> { loadFile(); });
		
		JSeparator separator_4 = new JSeparator();
		mnFile.add(separator_4);

		JMenuItem mntmSaveFile = new JMenuItem("Save As");
		mnFile.add(mntmSaveFile);
		mntmSaveFile.addActionListener((e) -> {this.saveFile(null);} );
		mntmSaveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, java.awt.event.InputEvent.ALT_DOWN_MASK ));
		
		JMenu mnExportAs = new JMenu("Export as");
		mnFile.add(mnExportAs);
		
		JMenuItem mntmTextFile = new JMenuItem("Text File");
		mnExportAs.add(mntmTextFile);
		mntmTextFile.addActionListener( (e) -> {this.saveFile(".txt");});
		
		JMenuItem mntmHtmlFile = new JMenuItem("Html File");
		mnExportAs.add(mntmHtmlFile);
		mntmHtmlFile.addActionListener( (e) -> {this.saveFile(".html");});
		
		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);

		JMenuItem mntmPrint = new JMenuItem("Print");
		mnFile.add(mntmPrint);
		mntmPrint.addActionListener((e) -> { this.printDocument();} );
		mntmPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, java.awt.event.InputEvent.ALT_DOWN_MASK ));
		
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
//		ask for confirmation before quitting the application
		mntmExit.addActionListener((e) -> {confirmClose();});
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, java.awt.event.InputEvent.ALT_DOWN_MASK ));

		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);

		JMenuItem mntmColor = new JMenuItem("Color");
		mnEdit.add(mntmColor);
		mntmColor.addActionListener( (e) -> {this.setColor(); } );
		
		JMenuItem mntmSize = new JMenuItem("Size");
		mnEdit.add(mntmSize);
		mntmSize.addActionListener( (e)->{this.setTextSize();});

		JMenuItem mntmFontStyle = new JMenuItem("Font Style");
		mnEdit.add(mntmFontStyle);
		mntmFontStyle.addActionListener( (e)->{ setFont(); } );

		JSeparator separator_3 = new JSeparator();
		mnEdit.add(separator_3);

		JMenuItem mntmCut = new JMenuItem("Cut");
		mnEdit.add(mntmCut);
		mntmCut.addActionListener( (e) -> {jtextArea.cut();});
		mntmCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, java.awt.event.InputEvent.ALT_DOWN_MASK ));

		JMenuItem mntmCopy = new JMenuItem("Copy");
		mnEdit.add(mntmCopy);
		mntmCopy.addActionListener( (e) -> {jtextArea.copy();} );
		mntmCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, java.awt.event.InputEvent.ALT_DOWN_MASK ));

		JMenuItem mntmPaste = new JMenuItem("Paste");
		mnEdit.add(mntmPaste);
		mntmPaste.addActionListener( (e) -> {jtextArea.paste();});
		mntmPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, java.awt.event.InputEvent.ALT_DOWN_MASK ));

		JMenu mnTools = new JMenu("Tools");
		menuBar.add(mnTools);

		JMenuItem mntmSearch = new JMenuItem("Search & Replace");
		mnTools.add(mntmSearch);
		mntmSearch.addActionListener( (e) -> {searchReplace(); } );
		mntmSearch.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, java.awt.event.InputEvent.ALT_DOWN_MASK ));
		

		JMenuItem mntmImageToText = new JMenuItem("Image/PDF to Text");
		mnTools.add(mntmImageToText);
		mntmImageToText.addActionListener( (e) -> {this.doOCR(); } );
		
		
		JMenuItem mntmTextToSpeech = new JMenuItem("Text to Speech");
		mnTools.add(mntmTextToSpeech);
		mntmTextToSpeech.addActionListener((e)->{ new Thread(new Voice("kevin16",jtextArea.getText() )).start(); });

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmTipsForDay = new JMenuItem("Tips For Day");
		mnHelp.add(mntmTipsForDay);
		mntmTipsForDay.addActionListener( (e) -> { getTips(); } );

		JMenuItem mntmCheckForUpdates = new JMenuItem("Check For Updates");
		mnHelp.add(mntmCheckForUpdates);
		mntmCheckForUpdates.addActionListener((e) -> { checkForUpdates();} );

		JSeparator separator_2 = new JSeparator();
		mnHelp.add(separator_2);

		JMenuItem mntmAboutCosmos = new JMenuItem("About Cosmos");
		mnHelp.add(mntmAboutCosmos);
		mntmAboutCosmos.addActionListener( (e) -> { showAbout(); });
		
	}
	
//	this method initializes pop up menu
	private void initializePopUpMenu() {
		
//		Adding a Pop-UP Menu 
		popupMenu = new JPopupMenu();
		
		JMenuItem mntmCut = new JMenuItem("Cut");
		popupMenu.add(mntmCut);
		mntmCut.addActionListener( (e) -> {jtextArea.cut();});

		JMenuItem mntmCopy = new JMenuItem("Copy");
		popupMenu.add(mntmCopy);
		mntmCopy.addActionListener( (e) -> {jtextArea.copy();} );

		JMenuItem mntmPaste = new JMenuItem("Paste");
		popupMenu.add(mntmPaste);
		mntmPaste.addActionListener( (e) -> {jtextArea.paste();});
		
		popupMenu.add(new JSeparator());
		
		JMenuItem mntmSaveFile = new JMenuItem("Save As");
		popupMenu.add(mntmSaveFile);
		mntmSaveFile.addActionListener((e) -> {this.saveFile(null);} );;
		
		addPopup(jtextArea, popupMenu);
	}


	public CosmosNotepad() {
		
		
//		this will not let your application get close, if some one click on quit button 
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//		this will set the title to Cosmos
		this.setTitle("Cosmos");
//		this will set a icon image on jframe window
		this.setIconImage(NotepadFunctionalities.cosmoIcon.getImage());
//		for setting absoulute layout
		getContentPane().setLayout(null);
		
		
//		this will Pop up a Dailog box confirming the closing  of notepad
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				confirmClose();
			}
		});
		
//		this will let the window to resizeable
		this.setResizable(true);
//		this will let our application window to appear in the center of the screen when started
		this.setLocationRelativeTo(null);
//		this is default size of jframe
		this.setPreferredSize(new Dimension(400, 400));
		this.setSize(getPreferredSize());
//		this will create a menu bar and the 2nd statement will set the menu bar on the top
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);			
		
		jtextArea = new JTextArea();
		jscrollpane = new JScrollPane(jtextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jscrollpane.setBounds(0, 0, this.getWidth()-20, this.getHeight()-70);
		jtextArea.setBounds(0, 0, jscrollpane.getWidth(), jscrollpane.getHeight());
	
//		jscrollpane.setViewportView(jtextArea);
		getContentPane().add(jscrollpane);

		
//		this will call reSize() everytime frame is resized
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
//				System.out.println("Component resize");
				reSize();
			}
		});
		

		initializeMenuBar();	
		initializePopUpMenu();
		
		this.setVisible(true);
	}


	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}

