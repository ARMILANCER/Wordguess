import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.*

public class ProgressBar extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JProgressBar bar;
	private JPanel pnlSouth, pnlCenter, pnlNorth;
	private JLabel lbl;
	private JButton btnExit;
	private String file;
	private int rows, columns;
	
	//nuova parte
	private Vector<String> originalFileContent;
	
	public ProgressBar(String file, int rows, int columns, Vector<String> originalFileContent) {
	//public ProgressBar(String file, int rows, int columns) {
	//fine 
		this.file = file;
		this.rows = rows;
		this.columns = columns;
		//nuova parte
		this.originalFileContent = originalFileContent;
		//fine
		
		pnlNorth = new JPanel(new BorderLayout());
		pnlNorth.setBackground(Color.BLUE);
		
		btnExit = new JButton("X");
		btnExit.setBackground(Color.BLUE);
		btnExit.setForeground(new Color(255, 215, 0));
		btnExit.setBorderPainted(false);
		btnExit.setFont(new Font("Times New Roman", Font.BOLD, 15));
		//nuova parte
		btnExit.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "none");
		btnExit.setFocusPainted(false);
		//fine
		btnExit.addActionListener(e ->{
		//nuova parte
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter("Numbers.txt"));

				for(int i = 0; i < originalFileContent.size(); i++) {
					writer.write(originalFileContent.get(i));
					writer.newLine();
				}

				writer.close();
			}catch(IOException e1) {
				e1.printStackTrace();
			}
			//fine
			System.exit(0);
		});
		
		//nuova parte
		// When the mouse cursor pass on the button ì, the background color get changed to red
		btnExit.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent e) {
				btnExit.setBackground(Color.RED);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnExit.setBackground(Color.BLUE);
			}
			
		});
		//fine
		
		buttonKey(btnExit, KeyEvent.VK_ESCAPE);
		
		pnlNorth.add(btnExit, BorderLayout.EAST);
		
		pnlCenter = new JPanel();
		pnlCenter.setBackground(Color.BLUE);
		
		pnlSouth = new JPanel(new BorderLayout());
		//nuova parte
		lbl = new JLabel("downloading resources...");
		//fine
		lbl.setOpaque(true);
		lbl.setBackground(Color.BLUE);
		lbl.setForeground(new Color(255, 215, 0));
		lbl.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		bar = new JProgressBar(0, 100);
		bar.setStringPainted(true);
		bar.setBackground(Color.BLUE);
		bar.setForeground(Color.BLACK);
		bar.setFont(new Font("Times New Roman", Font.BOLD, 15));
		progress();
		
		pnlSouth.add(lbl, BorderLayout.CENTER);
		pnlSouth.add(bar, BorderLayout.SOUTH);

		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		setUndecorated(true);
	}
	
	public void progress() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				int currentValue = 0;
				
				while(currentValue <= 100) {
					bar.setValue(currentValue);
					
					try {
						Thread.sleep(new Random().nextInt(500) + 100);
					}catch(InterruptedException e1) {
						e1.printStackTrace();
					}
					
					currentValue += new Random().nextInt(10) + 1;		
				}
				
				bar.setValue(100);
				lbl.setText("Opening the game...");
				bar.setString("Completed!");
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				dispose();
				//nuova parte
				GUI gui = new GUI(file, rows, columns, originalFileContent);
				//GUI gui = new GUI(file, rows, columns);
				//
				gui.setVisible(true);
			}
			
		});
		thread.start();
	}
	
	public static void buttonKey(JButton btn, int key) {
	    Action action = new AbstractAction() {
			@Override
	         public void actionPerformed(ActionEvent e) {
	            btn.doClick();
	         }
	    };
   
	    // Set the btn actionCommand to the key to identify which action should be performed 
	    btn.setActionCommand("buttonAction" + key);
	       
	    // Associate the action object to the keyboard key
	    btn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key, 0), "buttonAction" + key);
	    btn.getActionMap().put("buttonAction" + key, action);
	}
}
