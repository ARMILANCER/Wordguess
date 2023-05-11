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
        setTitle("Welcome to crossWord!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        image = new ImageIcon("sfondoGioco.png");
        lblImage = new JLabel(image);
        setContentPane(lblImage);

        btnStart = new JButton("START");
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
        btnStart.setBackground(Color.MAGENTA);
        btnStart.setBounds(150, 200, 100, 30);
    }

    public static void main(String[] args) {
        new Menu();
    }
}