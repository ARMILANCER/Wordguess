
import java.awt.*;
import java.io.*;
import javax.swing.*;

/*manca da fare che quando si indovina una parola tutta la linea diventa verde e non solo quando le si indovinano tutte
i due bottoni previous e next, stampare l'indizio riguardo alla parolla nella textArea e che quando si indovina una parola genera delle lettere a random come aiuto
*/
public class GUI extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnlCenter, pnlSouth, pnlBtn, pnlWest;
	private JTextField[][] tf;
	private JLabel[][] lbl;
	private JButton[] btnHint;
	private JButton btnCheck, btnPrevious, btnNext;
	private JTextArea ta;
	
	public GUI(String file, int rows, int columns) {
		setTitle("Game ---");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		setLayout(new BorderLayout());
		
		pnlCenter = new JPanel(new GridLayout(rows, columns));
		
		lbl = new JLabel[rows][columns];
		tf = new JTextField[rows][columns];
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			for(int i = 0; i < rows; i++) {
				String nextLine = reader.readLine();
				boolean found = false, ok = false;
				//nextLine = reader.readLine();
				//boolean ok = true;
				
				while(found == false) {
					if(nextLine.length() == columns) {

							for(int j = 0; j < columns; j++) {
								lbl[i][j] = new JLabel(String.valueOf(nextLine.charAt(j)));
								lbl[i][j].setOpaque(true);
								lbl[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
								tf[i][j] = new JTextField();
								tf[i][j].setOpaque(true);
								tf[i][j].setBackground(Color.CYAN);
								//pnlCenter.add(lbl[i][j]);
								pnlCenter.add(tf[i][j]);
							}
						found = true;
						
					}else {
						nextLine = reader.readLine();
					}
					
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		pnlSouth = new JPanel(new GridLayout(2, 1));
		pnlBtn = new JPanel(new BorderLayout());
		pnlBtn.setBackground(Color.BLUE);
		
		btnCheck = new JButton("Check");
		btnCheck.setBackground(Color.BLUE);
		btnCheck.setForeground(new Color(255, 215, 0));
		btnCheck.setOpaque(true);
		btnCheck.setBorderPainted(false);
		btnCheck.setFont(new Font("Arial", Font.BOLD, 20));
		btnCheck.addActionListener(e ->{
			boolean correct = true;
			
			for(int i = 0; i < rows; i++) {
			 	for(int j = 0; j < columns; j++){
					if(tf[i][j].getText().equals(lbl[i][j].getText())) {
						tf[i][j].setEditable(false);
						tf[i][j].setBackground(Color.YELLOW);
						lbl[i][j].setBackground(Color.GREEN.darker().brighter().brighter());
					}else {
						tf[i][j].setText(null);
						correct = false;
					}
				}
			}
			
			if(correct == true) {
				Container c = getContentPane();
				JPanel pnlCenter = (JPanel) c.getComponent(0);
				
				for(int i = 0; i < rows; i++) {
					for(int j = 0; j < columns; j++) {
						pnlCenter.remove(tf[i][j]);
						pnlCenter.add(lbl[i][j]);
					}
				}
				
				pnlCenter.revalidate();
				pnlCenter.repaint();
			}
		});
		
		btnPrevious = new JButton("<--");
		btnPrevious.setBackground(Color.BLUE);
		btnPrevious.setForeground(new Color(255, 215, 0));
		btnPrevious.setOpaque(true);
		btnPrevious.setBorderPainted(false);
		btnPrevious.setFont(new Font("Arial", Font.BOLD, 20));
		btnPrevious.addActionListener(e ->{
			 
         
		});
		
		btnNext = new JButton("-->");
		btnNext.setBackground(Color.BLUE);
		btnNext.setForeground(new Color(255, 215, 0));
		btnNext.setOpaque(true);
		btnNext.setBorderPainted(false);
		btnNext.setFont(new Font("Arial", Font.BOLD, 20));
		btnNext.addActionListener(e ->{
			
		});
		
		ta = new JTextArea();
		ta.setBackground(Color.BLUE);
		ta.setForeground(Color.WHITE);
		//ta.setEditable(false);
		
		pnlBtn.add(btnCheck, BorderLayout.CENTER);
		pnlBtn.add(btnPrevious, BorderLayout.WEST);
		pnlBtn.add(btnNext, BorderLayout.EAST);
		
		pnlSouth.add(pnlBtn);
		pnlSouth.add(ta);
		
		pnlWest = new JPanel(new GridLayout(rows, 1));
		
		btnHint = new JButton[rows];
		for(int i = 0; i < rows; i ++) {
			btnHint[i] = new JButton("Level " + (i + 1));
			pnlWest.add(btnHint[i]);
			int line = i;
			btnHint[i].addActionListener(e ->{
				String word = "";
				
				
				for(int j = 0; j < columns; j++) {
					word += lbl[line][j].getText();
				}
				
				try {
					BufferedReader reader = new BufferedReader(new FileReader(file));
					String nextLine = reader.readLine();
					boolean stop = false;
					
					while(stop == false) {
						if(nextLine.equals(word)) {
							nextLine = reader.readLine();
							while(!nextLine.equals("stop")) {
								ta.append(nextLine);
								nextLine = reader.readLine();
							}
							stop = true;
						}
						
					}
					reader.close();
				}catch(IOException e1) {
					e1.printStackTrace();
				}
				
			});
		}
		
		
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
		add(pnlWest, BorderLayout.WEST);
	}
}