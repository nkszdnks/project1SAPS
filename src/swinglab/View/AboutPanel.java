package swinglab.View;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class AboutPanel extends JPanel implements ActionListener {

    public AboutPanel() {
        setLayout(new BorderLayout());
        JTextArea info = new JTextArea(
                "Bank of TUC — Swing Lab\n" +
                        "This demo shows:\n" +
                        "• JFrame window\n" +
                        "• Panels with components (labels, fields, buttons)\n" +
                        "• Event handling with ActionListener\n" +
                        "• CardLayout + menu navigation\n" +
                        "• JTable for tabular data"
        );
        info.setEditable(false);
        info.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        add(info, BorderLayout.CENTER);

        JButton bb = new JButton("Close");
        add(bb,BorderLayout.PAGE_END);
        bb.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (AppMediator.getUser() != null)
            AppMediator.getCardLayout().show(AppMediator.getCards(),"dashboard");
        else
            AppMediator.getCardLayout().show(AppMediator.getCards(),"login");
    }
}

