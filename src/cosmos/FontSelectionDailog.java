package cosmos;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;



public class FontSelectionDailog extends JFrame{
	private JTextField textField;
	private JLabel lblSize;
	private String[] fonts = {"Select Font", "Monospaced", "Serif", "SansSerif"};
	private String[] styles = {"Select Style", "Plain", "Bold", "Italic",};
	private JButton btnApply;
	private JButton btnCancel;
	private JComboBox fontList;
	private JComboBox styleList;
	private String selectedFont;
	private int selectedStyle;
	private int size;
	private CosmosNotepad cn;
	
	
	
//	this method will set font the notepad jtextarea which created this fontSelectionDailog Instance
	public void setFont() {
		cn.getJtextArea().setFont(new Font(selectedFont, selectedStyle-1, this.size));
	}
	
	public FontSelectionDailog(CosmosNotepad cn) {
		
		this.cn = cn;
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		this.setSize(500, 125);
		this.setLocationRelativeTo(cn);
		getContentPane().setLayout(null);
		
		
		
		
		
		lblSize = new JLabel("Size :");
		lblSize.setFont(new Font("Sitka Small", Font.BOLD, 16));
		lblSize.setBounds(305, 16, 55, 16);
		getContentPane().add(lblSize);
		
		textField = new JTextField("12");
		textField.setBounds(359, 16, 116, 16);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		
		fontList = new JComboBox(fonts);
		fontList.setBounds(12, 13, 116, 22);
		getContentPane().add(fontList);
		
		
		styleList = new JComboBox(styles);
		styleList.setBounds(164, 13, 116, 22);
		getContentPane().add(styleList);
		
//		                                                   Buttons Buttons

		btnApply = new JButton("APPLY");
		btnApply.setBounds(103, 48, 97, 25);
		getContentPane().add(btnApply);
		btnApply.addActionListener( (e)->{
			selectedFont=fontList.getSelectedItem().toString(); 
			//This selected Style is Not Completed
			selectedStyle=styleList.getSelectedIndex(); 
			try {
				this.size=Integer.parseInt(textField.getText());
				if(this.size <= 0) {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException ee) {
				JOptionPane.showMessageDialog(this, "Invalid Input : Only Integer Allowed /n & number Greater than 0", "Invalid Input", JOptionPane.WARNING_MESSAGE, NotepadFunctionalities.exclIco);
			}
			
			this.setFont();
		} );

		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(251, 48, 97, 25);
		getContentPane().add(btnCancel);
		btnCancel.addActionListener( (e) -> {this.dispose();});
		this.setVisible(true);
	}
	
}
