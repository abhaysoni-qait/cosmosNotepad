package cosmos;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

public class SizeSelectDailog extends JDialog {
	private JTextField sizeTextField;
	private CosmosNotepad cn;
	
	
	
	public SizeSelectDailog(CosmosNotepad cn) {
		
		this.cn = cn;
		
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setBounds(100, 100, 450, 80);
		this.setLayout(null);
		this.setLocationRelativeTo(cn);
		this.setResizable(false);
		
		
		JButton btnApply = new JButton("Apply");
		btnApply.setBounds(230, 4, 97, 25);
		getContentPane().add(btnApply);
		btnApply.addActionListener( (e) -> { setTextAreaSize(); });
		
		JButton btnNewButton = new JButton("Cancel");
		btnNewButton.setBounds(335, 4, 97, 25);
		this.add(btnNewButton);
		btnNewButton.addActionListener( (e) -> {this.dispose();} );
		
		JLabel lblSize = new JLabel("Size : ");
		lblSize.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSize.setBounds(12, 8, 52, 16);
		this.add(lblSize);
		
		sizeTextField = new JTextField();
		sizeTextField.setBounds(64, 5, 116, 22);
		getContentPane().add(sizeTextField);
		sizeTextField.setColumns(10);
		
		this.setVisible(true);

	}



	private void setTextAreaSize() {
		int size;
		try {
			size = Integer.parseInt(this.sizeTextField.getText());
			if(size <= 0) {
				throw new NumberFormatException();
			}
		}catch (NumberFormatException e){
			JOptionPane.showMessageDialog(this, "Invalid Input : Only Integer Allowed /n & number Greater than 0", "Invalid Input", JOptionPane.WARNING_MESSAGE, NotepadFunctionalities.exclIco);
			return;
		}
		cn.getJtextArea().setFont(new Font(cn.getJtextArea().getFont().getFontName(), cn.getJtextArea().getFont().getStyle(),size ));
	}
}
