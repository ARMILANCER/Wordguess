import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Random;

public class Menu extends JFrame {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblImage;
    private JButton btnStart;
    private ImageIcon image;
    public Menu(){
    	setLayout(null);
    	
    	image = new ImageIcon("GuessTheWorld.jpeg");
        lblImage = new JLabel(image);
        setContentPane(lblImage);

        btnStart = new JButton("START");
        btnStart.setBackground(new Color(255, 215, 0));
        btnStart.setOpaque(true);
        btnStart.setBorderPainted(false);
        btnStart.setFont(new Font("Arial", Font.BOLD, 20));
       // btnStart.setBounds(150, 200, 100, 30);
        btnStart.setBounds(900, 700, 100, 30);
        btnStart.addActionListener(e ->{
        	int rand = new Random().nextInt(5) + 1, rows = 0;
        	String file = "" + rand + ".txt", line = null, nextLine = null;
        	try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				line = reader.readLine();
				nextLine = reader.readLine();
				
				while(nextLine != null) {
					if(nextLine.equals("stop"))
						rows++;
					nextLine = reader.readLine();
				}
				
				
				
				reader.close();
        	} catch (IOException  e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        	GUI gui = new GUI(file, rows, line.length());
        	gui.setVisible(true);
        	dispose();
        });

        add(btnStart);
        
        setTitle("Welcome to crossWord!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Menu();
    }
}
