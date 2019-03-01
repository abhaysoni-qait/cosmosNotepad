package cosmos;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cosmosServer.TipsDailogBox;

import javax.swing.JLabel;

public class TipsBox extends JDialog {
	private JLabel tipsLabel ;
	private int tipNo = 0;
	private CosmosNotepad cn = null;
	private TipsDailogBox tdb = null;
	private int lastTipNo = 0;
	
	
	public void setLabel() {
		
		tdb = NotepadFunctionalities.getTips(cn, tipNo);
		if(tdb != null) {
		tipsLabel.setText(tdb.getMessage());
		this.setVisible(true);
		} else {
			this.dispose();
		}
		
	}
	
	private void setTip(int i) {
		
		if(tipNo <= 0) {
			tipNo = 0;
		}
	
		System.out.println(tipNo);
		
		if(lastTipNo != 0 && tipNo>=lastTipNo) {
			tipNo=lastTipNo;
		}
		
		if(tdb != null && tdb.getMessage().equalsIgnoreCase("No more Tips") && lastTipNo == 0) {
			lastTipNo = tipNo;
			return;
		}
		
		
		tipNo = tipNo + i;
		setLabel();
	}

	public TipsBox(CosmosNotepad cn) {
		this.cn = cn;
		getContentPane().setLayout(null);
		this.setTitle("Tips For Today");
		this.setLocationRelativeTo(cn);
		this.setIconImage(NotepadFunctionalities.cosmoIcon.getImage());
		this.setSize(450, 217);
		this.setAlwaysOnTop(true);
		
		
		JButton btn_nextTips = new JButton("  >>");
		btn_nextTips.setBounds(253, 142, 97, 25);
		getContentPane().add(btn_nextTips);
		btn_nextTips.addActionListener( (e) -> {this.setTip(1);} );
		
		JButton btn_prevTips = new JButton("  <<");
		btn_prevTips.setBounds(51, 142, 97, 25);
		getContentPane().add(btn_prevTips);
		btn_prevTips.addActionListener((e) -> {this.setTip(-1);} );
		
		tipsLabel = new JLabel("");
		tipsLabel.setBounds(12, 13, 408, 116);
		getContentPane().add(tipsLabel);
		
		
		setTip(0);
		
		}
}
