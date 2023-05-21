import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;
// DA AGGIUNGERE: layout, non dare sempre lo stesso file
public class Winner extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel pnlSouth, pnlCenter, pnlNew, pnlNorth;
	private JLabel lblPoints, lblResult;
	private JButton btnBack, btnNew, btnExit;
	
	private final int maxPoints = 300;
	private final int maxTime = 5; //10 minuti

	public Winner(GUI gui, int count) {
		setLayout(new BorderLayout());
		setTitle("Winner");
		
		pnlNorth = new JPanel(new BorderLayout());
		
		btnExit = new JButton("x");
		btnExit.setBackground(Color.BLUE);
        btnExit.setForeground(new Color(255, 215, 0));
        btnExit.setOpaque(true);
        btnExit.setBorderPainted(false);
        btnExit.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnExit.addActionListener(e ->{
			System.exit(0);
		});
		
		pnlCenter = new JPanel();
		pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
		pnlCenter.setBackground(Color.BLUE);
		
		lblResult = new JLabel();
		lblResult.setOpaque(true);
		lblResult.setBackground(Color.BLUE);
		lblResult.setForeground(new Color(255, 215, 0));
		lblResult.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblResult.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		if(count <= 0)
			lblResult.setText("You've lost due to time!");
		else
			lblResult.setText("You've won, congratulations!");
		
		lblPoints = new JLabel();
		lblPoints.setOpaque(true);
		lblPoints.setBackground(Color.BLUE);
		lblPoints.setForeground(new Color(255, 215, 0));
		lblPoints.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblPoints.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		if(count <= 0)
			lblPoints.setText("" + 0);
		else if(count >= maxTime) 
			lblPoints.setText("" + maxTime);
		else {
			// Calculate the score
			int penalty = (int) count / maxTime;
            int score = (int) (maxPoints * (1 - penalty));
           
            lblPoints.setText("" + score);
		}
		
		// Add a empty space at the top
		pnlCenter.add(Box.createVerticalGlue());
		// Add a rigid structure to create a vertical empty space
		pnlCenter.add(Box.createVerticalStrut(10));
		pnlCenter.add(lblResult);
		pnlCenter.add(lblPoints);
		// Add a rigid structure to create a vertical empty space
		pnlCenter.add(Box.createVerticalStrut(10));
		// Add a empty space at the bottom
		pnlCenter.add(Box.createVerticalGlue());
		
		pnlSouth = new JPanel(new BorderLayout());
		pnlSouth.setBackground(Color.BLUE);
		/*pnlNew = new JPanel(new FlowLayout());
		pnlNew.setBackground(Color.BLUE);*/

		btnBack = new JButton("Back");
		btnBack.setBackground(Color.BLUE);
		btnBack.setForeground(new Color(255, 215, 0));
		btnBack.setOpaque(true);
		btnBack.setBorderPainted(false);
		btnBack.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnBack.addActionListener(e ->{
			gui.setVisible(true);
			dispose();
		});
		
		btnNew = new JButton("New game");
		btnNew.setBackground(Color.BLUE);
		btnNew.setForeground(new Color(255, 215, 0));
		btnNew.setOpaque(true);
		btnNew.setBorderPainted(false);
		btnNew.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnNew.addActionListener(e ->{
			dispose();
			new Menu();
		});
	
		//pnlNew.add(btnNew);
		
		buttonKey(btnBack, KeyEvent.VK_LEFT);
		buttonKey(btnNew, KeyEvent.VK_RIGHT);
		buttonKey(btnExit, KeyEvent.VK_ESCAPE);
		
		pnlSouth.add(btnBack, BorderLayout.WEST);
		//pnlBtn.add(pnlNew, BorderLayout.CENTER);
		//pnlBtn.add(btnExit, BorderLayout.EAST);
		pnlSouth.add(btnNew, BorderLayout.EAST);
		
		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
		
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
    public static void buttonKey(JButton btn, int key) {
    	Action action = new AbstractAction() {
			@Override
            public void actionPerformed(ActionEvent e) {
            	btn.doClick();
            }
        };

        // String actionKey = "buttonAction" + key;
        
        // Set the btn actionCommand to the key to identify which action should be performed 
        btn.setActionCommand("buttonAction" + key);
       
        // Associate the action object to the keyboard key
        btn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key, 0), "buttonAction" + key);
        btn.getActionMap().put("buttonAction" + key, action);
    }
}