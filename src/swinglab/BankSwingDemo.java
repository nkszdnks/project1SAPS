package swinglab;

import swinglab.BankFrame;

import javax.swing.SwingUtilities;

public class BankSwingDemo {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(BankFrame::new);
	}

}
