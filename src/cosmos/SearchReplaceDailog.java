package cosmos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.Highlighter.Highlight;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Shape;

import javax.swing.JTextField;

public class SearchReplaceDailog extends JDialog {
	private JTextField searchedTerm;
	private JTextField ReplacingTerm;
	private CosmosNotepad cn;
	private int pointerEndPos ;
	private int startIndex = -1;
	private Highlighter highlighter;
	private HighlightPainter highlight;
	private HighlightPainter highlightSelectedText;
	private int sequenceNumber = 0;
	
	
	
	

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int i) {
		if(sequenceNumber==1 && i==-1) {
			return;
		}
		this.sequenceNumber = sequenceNumber + i;
		
	}

	private void search() {

		if(searchedTerm.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "Please Enter the Word/Sentence you want to Search", "Search And Replace - Warning", JOptionPane.WARNING_MESSAGE, NotepadFunctionalities.exclIco);
		}else {
			
			startIndex = cn.getJtextArea().getText().indexOf(searchedTerm.getText(), pointerEndPos);

			while (startIndex != -1) {
				pointerEndPos = startIndex + searchedTerm.getText().length();
				try {
					highlighter.addHighlight(startIndex, pointerEndPos, highlight);					
				} catch (BadLocationException e) {
					JOptionPane.showMessageDialog(cn, e.toString(), "Cosmos Notepad : Warning" , JOptionPane.WARNING_MESSAGE, NotepadFunctionalities.exclIco);
				}
				startIndex = cn.getJtextArea().getText().indexOf(searchedTerm.getText(), pointerEndPos);
			}
			
			startIndex = -1;
			pointerEndPos = 0;
			
		}
	}
	
	private void selectText(int sequenceNumber) {
		
		highlighter.removeAllHighlights();
		
		for (int i=0; i<=sequenceNumber; i++) {
			startIndex = cn.getJtextArea().getText().indexOf(searchedTerm.getText(), pointerEndPos);
			pointerEndPos = startIndex + searchedTerm.getText().length();
			if(startIndex == -1) {
				pointerEndPos = 0;
			}
		}
		
		if(startIndex==-1) {
			return;
		}
		
		try {
//			System.out.println(startIndex);
//			System.out.println(pointerEndPos);
			highlighter.addHighlight(startIndex, pointerEndPos, highlightSelectedText);
			cn.getJtextArea().select(startIndex, pointerEndPos);
		} catch (BadLocationException e) {
			JOptionPane.showMessageDialog(cn, e.toString(), "Cosmos Notepad : Warning" , JOptionPane.WARNING_MESSAGE, NotepadFunctionalities.exclIco);
		}

		pointerEndPos = 0;
		search();
	}

	private void replace() {
		cn.getJtextArea().replaceSelection(ReplacingTerm.getText());
	}
	
	
	private void replaceAll() {
				
		if(searchedTerm.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "Please Enter the Word/Sentence you want to Search", "Search And Replace - Warning", JOptionPane.WARNING_MESSAGE, NotepadFunctionalities.exclIco);
		}else {
			cn.getJtextArea().setText(cn.getJtextArea().getText().replaceAll(searchedTerm.getText(), ReplacingTerm.getText() ));
		}
	}
	
	
	public SearchReplaceDailog(CosmosNotepad cn) {
		
		this.cn = cn;
		highlighter = cn.getJtextArea().getHighlighter();
		highlight = new DefaultHighlighter.DefaultHighlightPainter(Color.orange);
		highlightSelectedText = new DefaultHighlighter.DefaultHighlightPainter(Color.red);
		
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		this.setLocationRelativeTo(cn);
		this.setSize(450, 220);
		this.setAlwaysOnTop(true);
		
		JLabel lblSearch = new JLabel("Search :");
		lblSearch.setFont(new Font("Segoe UI Black", Font.PLAIN, 18));
		lblSearch.setBounds(12, 14, 88, 16);
		getContentPane().add(lblSearch);
		
		JLabel lblReplaceBy = new JLabel("Replace By : ");
		lblReplaceBy.setFont(new Font("Segoe UI Black", Font.PLAIN, 18));
		lblReplaceBy.setBounds(12, 57, 112, 26);
		getContentPane().add(lblReplaceBy);
		
		searchedTerm = new JTextField();
		searchedTerm.setBounds(159, 13, 273, 22);
		getContentPane().add(searchedTerm);
		searchedTerm.setColumns(10);
		
		ReplacingTerm = new JTextField();
		ReplacingTerm.setColumns(10);
		ReplacingTerm.setBounds(159, 61, 273, 22);
		getContentPane().add(ReplacingTerm);
		
		JButton btn_preTerm = new JButton("    <<    ");
		btn_preTerm.setBounds(12, 138, 88, 25);
		getContentPane().add(btn_preTerm);
		btn_preTerm.addActionListener( (e) -> {
			setSequenceNumber(-1);
			selectText(getSequenceNumber());
			} );
		
		JButton btn_nextTerm = new JButton("    >>    ");
		btn_nextTerm.setBounds(112, 138, 88, 25);
		getContentPane().add(btn_nextTerm);
		btn_nextTerm.addActionListener( (e) -> {
			setSequenceNumber(1);
			selectText(getSequenceNumber());
			} );
		
		JButton btnReplace = new JButton("Replace");
		btnReplace.setBounds(212, 138, 97, 25);
		getContentPane().add(btnReplace);
		btnReplace.addActionListener( (e) -> { replace(); } );
		
		JButton btnReplaceAll = new JButton("Replace All");
		btnReplaceAll.setBounds(321, 138, 97, 25);
		getContentPane().add(btnReplaceAll);
		btnReplaceAll.addActionListener( (e) -> {replaceAll();} );
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(159, 100, 97, 25);
		getContentPane().add(btnSearch);
		btnSearch.addActionListener( (e) -> { search(); });
		
		JLabel lblCaseSensitive = new JLabel("case sensitive : TRUE");
		lblCaseSensitive.setBounds(159, 37, 159, 16);
		getContentPane().add(lblCaseSensitive);
		
		
//		Set visibility True
		this.setVisible(true);
	}
}
