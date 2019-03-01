package cosmos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImageFilter;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.MessageFormat;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import cosmosServer.RemoteMethods;
import cosmosServer.TipsDailogBox;
import cosmosServer.UpdateDailogBox;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;


public class NotepadFunctionalities {
	//	changing this will change icon everywhere
	public static ImageIcon cosmoImg = new ImageIcon("src\\Assests\\Icon\\Cosmos Icon.png");
	public static ImageIcon cosmoIcon = new ImageIcon(cosmoImg.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
	public static ImageIcon quesImg = new ImageIcon("src\\Assests\\Extras\\cosmos_ques.png");
	public static ImageIcon quesIcon = new ImageIcon(quesImg.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
	public static ImageIcon exclImg = new ImageIcon("src\\Assests\\Extras\\cosmos_excl.png");
	public static ImageIcon exclIco = new ImageIcon(exclImg.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
	public static ImageIcon netFailImg = new ImageIcon("src\\Assests\\Extras\\cosmos_noInternet.png");
	public static ImageIcon netFailIco = new ImageIcon(netFailImg.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
	public static ImageIcon printImg = new ImageIcon("src\\Assests\\Extras\\cosmos_print.png");
	public static ImageIcon printIco = new ImageIcon(printImg.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
	public static ImageIcon smileyConImg = new ImageIcon("src\\Assests\\Extras\\cosmos_failed.png");
	public static ImageIcon smileyConIco = new ImageIcon(smileyConImg.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
	public static String 	cosmoServerAddress = "rmi://127.0.0.1:13500/cosmos";


//	Will ask for confirmation before closing
	public static void confirmClose(CosmosNotepad cn) {
		int choose = JOptionPane.showConfirmDialog(cn, "Are you Sure you want to exit", "Cosmos Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, quesIcon);
		if (choose == JOptionPane.YES_OPTION) {
			//				System.out.println("Yes Selected");
			cn.dispose();
		}else {
			return ;
		}
	}

//	Dailog containing info about cosmos notepad
	public static void showAbout(CosmosNotepad cn) {
		JOptionPane.showMessageDialog(cn, "This Notepad CosmO was created by Mr. Abhay Soni \n Current Version : 2.0", "Cosmo : Smart Notepad", JOptionPane.PLAIN_MESSAGE, cosmoIcon);
	}

//	this method is called everytime the frame size is changed and scrollpane is adjusted according to new frame size
	public static void reSize(CosmosNotepad cn) {
		cn.getJscrollpane().setBounds(0, 0, cn.getWidth()-20, cn.getHeight()-70);
		//		do not forget to set jtextarea size also other wise it will give problems
		cn.getJtextArea().setBounds(0, 0, cn.getJscrollpane().getWidth(), cn.getJscrollpane().getHeight());

	}
//	File Loading Process
	public static void loadFile(CosmosNotepad cn) {
		JFileChooser jf = new JFileChooser();
		jf.showOpenDialog(cn);
		BufferedReader bf = null;
		JTextArea jtextArea = cn.getJtextArea();
		jtextArea.setText("");
		
		try {
			bf = new BufferedReader(new FileReader(jf.getSelectedFile()));
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(cn, "Requested File Does Not Exist", "Load File - Error!", JOptionPane.WARNING_MESSAGE, exclIco);
			bf = null;
			jf = null;
			jtextArea = null;
			return ; 
		} catch (NullPointerException e) {
//			System.out.println("No File Selected");
			return ; 
		}

		int n = 0;

		do{
			try {
				n = bf.read();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(cn, "Error occured while Reading From Requested File");
				try {
					bf.close();
				} catch (IOException ee) {
					System.out.println(ee.toString());
				}
				jf = null;
				jtextArea = null;
				return ;
			}
			jtextArea.append(String.valueOf((char)n));
		}while(n != -1);
		
		try {
			bf.close();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		jf = null;
		jtextArea = null;
		return ;
	}

//	file Saving Process
	public static void saveFile(CosmosNotepad cn, String format) {
		JFileChooser jf = new JFileChooser();
		jf.showSaveDialog(cn);
		File file = null;

		try {
			if(format != null) {
				file = new File(jf.getSelectedFile().getAbsolutePath() + format);
			} else {
				file = new File(jf.getSelectedFile().getAbsolutePath());
			}
		} catch (NullPointerException e) {
			System.out.println("Saving Process Cancelled");
			return ;
		}
		
		
		BufferedWriter bw = null;
		if(file.exists()) {
			//			file with same name already exist 
			int choice = JOptionPane.showConfirmDialog(cn, "File With Same Name Already Exist \n Do you want to Over-Write it", "Save File - Warning", JOptionPane.WARNING_MESSAGE);
			if(choice == JOptionPane.YES_OPTION) {
				try {
					bw = new BufferedWriter(new FileWriter(file));
					bw.write(cn.getJtextArea().getText());
				} catch (IOException e) {
					System.out.println(e.toString());
					JOptionPane.showMessageDialog(cn, "Error occured while Writing to Requested File");
				}
			}else {
				return;
			}
		}else {		
			try {
				file.createNewFile();
				bw = new BufferedWriter(new FileWriter(file));
				bw.write(cn.getJtextArea().getText());
			} catch (IOException e) {
				System.out.println(e.toString());
				JOptionPane.showMessageDialog(cn, "Error occured while Writing to Requested File");
			}
		}

		try {
			bw.close();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		file = null;
		jf = null;
		return ;
	}
//  setColor Of Whole Document
	public static void setColor(CosmosNotepad cn) {
		Color color = new JColorChooser().showDialog(cn, "Choose Text Color", Color.BLACK);
		cn.getJtextArea().setForeground(color);
	}

//  This method will set the new font For jtextArea (a new dailog box will appear)
	public static void setFont(CosmosNotepad cn) {
//		cn.setFocusableWindowState(false);
		
		
//		A jframe containing options to set Font	
		new FontSelectionDailog(cn);
		
	}

//	set Text Size of JtextArea (a new dailog box will appear)
	public static void setTextSize(CosmosNotepad cn) {
		new SizeSelectDailog(cn);
		}

//	(a new dailog box will appear) in which whatever word you will write, all the matching word in jtextarea will be highighted
	public static void searchReplace(CosmosNotepad cn) {
		new SearchReplaceDailog(cn);
	}

//	This method connects to cosmos Server and with help of rmi technology check whether if there is an update available or not
	public static void checkForUpdates(CosmosNotepad cn) {
		RemoteMethods remoteMethods = null;
		UpdateDailogBox updateDailogBox = null;
		System.out.println("Connecting to Server");
		try {
			remoteMethods = (RemoteMethods) Naming.lookup(cosmoServerAddress);
			updateDailogBox = remoteMethods.versionCheck(cn.getCosmoVersion());
		} catch (MalformedURLException | NotBoundException e) {
			JOptionPane.showMessageDialog(cn, e.toString(), "Update : Some Error Occured", JOptionPane.WARNING_MESSAGE, exclIco);
			return;
		} catch (RemoteException e) {
//			e.printStackTrace();
			JOptionPane.showMessageDialog(cn, " Check Your Internet Connection or \n Server is not available at the current moment", "Update : Some Error Occured", JOptionPane.WARNING_MESSAGE, netFailIco);
			return;
		}
		
		if(updateDailogBox.getIcon() != null) {
//			System.out.println("no icon received");
			JOptionPane.showMessageDialog(cn, updateDailogBox.getMessage(), "Update : Information", updateDailogBox.getMessageType(), updateDailogBox.getIcon());
			return;
		}
		JOptionPane.showMessageDialog(cn, updateDailogBox.getMessage(), "Update : Information", updateDailogBox.getMessageType(), cosmoIcon);
		return;
	}

	
//	this Method each time when called Tries to connect with cosmos server and Bring the lattest tips for users
	public static TipsDailogBox getTips(CosmosNotepad cn, int i) {
		RemoteMethods remoteMethod = null;
		TipsDailogBox tipDailog = null;

		try {
			remoteMethod = (RemoteMethods)Naming.lookup(cosmoServerAddress);
			tipDailog = remoteMethod.tips(i);
		} catch (MalformedURLException | NotBoundException e) {
			JOptionPane.showMessageDialog(cn, e.toString(), "Update : Some Error Occured", JOptionPane.WARNING_MESSAGE, exclIco);
			return null;
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(cn, " Check Your Internet Connection or \n Server is not available at the current moment", "Update : Some Error Occured", JOptionPane.WARNING_MESSAGE, netFailIco);
			return null;
		}

		return tipDailog;

	}

//	This Method will print jtextArea
	public static void printDocument(CosmosNotepad cn) {
		try {
			if( cn.getJtextArea().print(null, new MessageFormat("Cosmos Notepad : Created By Abhay Soni")) ) {
				JOptionPane.showMessageDialog(cn, "Document Successfully Printed", "Cosmos : Print", JOptionPane.INFORMATION_MESSAGE, printIco);
			}else {
				JOptionPane.showMessageDialog(cn, "Printing Operation Aborted", "Cosmos : Print", JOptionPane.INFORMATION_MESSAGE, smileyConIco);
			}
		} catch (PrinterException e) {
			JOptionPane.showMessageDialog(cn, "Cosmos : Printing Operation Aborted due to Some \n unkown Error", "Cosmos : Print", JOptionPane.INFORMATION_MESSAGE, exclIco);
		}
	}

	
//	This method uses Tesseract api to do OCR, basically analyse whatever written in Image and gives a digital output of
//	it so you do not have to write it down 
	public static void doOCR(CosmosNotepad cn) {
		File file;
		String scannedData = null;
		JFileChooser jf;
		
		Tesseract tesseract = new Tesseract();
		tesseract.setDatapath("src\\OCRlib\\tessdata");
		tesseract.setLanguage("eng");
		jf = new JFileChooser();
		
		try {
		jf.showOpenDialog(cn);
		file = new File(jf.getSelectedFile().getAbsolutePath());
		} catch ( NullPointerException e) {
			return ;
		}
		
		try {
			scannedData = tesseract.doOCR(file);
		} catch (TesseractException e) {
//			System.out.println(e.toString());
			JOptionPane.showMessageDialog(cn, "Unexpected Error Occured", "Notepad Cosmos : Warning", JOptionPane.WARNING_MESSAGE, exclIco);
			return;
		}
		
		cn.getJtextArea().setText(scannedData);
		
	}


	
	

}
