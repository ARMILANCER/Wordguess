package Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Random;

public class Menu extends JFrame {
    private static final long serialVersionUID = 1L;
    private JLabel lblImage;
    private JButton btnStart;
    private ImageIcon image;
    //nuova parte
    private String originalFile = "OriginalContent.txt";
    private Vector<String> originalFileContent;
    //fine
    public Menu(){
        setLayout(null);
        image = new ImageIcon();
        //Auto dimenzionamento
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width= (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        try {
            image.setImage((ImageIO.read(new File("GuessTheWorld.jpeg"))).getScaledInstance(width,height,Image.SCALE_DEFAULT));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /*
         */
        lblImage = new JLabel(image);
        setContentPane(lblImage);

        btnStart = new JButton("START");
        btnStart.setBackground(new Color(255, 215, 0));
        btnStart.setOpaque(true);
        btnStart.setBorderPainted(false);
        btnStart.setFont(new Font("Arial", Font.BOLD, 20));
        // btnStart.setBounds(150, 200, 100, 30);
        btnStart.setBounds(655,615 , 100, 30);
        //nuova parte
         // Remove the default possibility to activate the button with the key "SPACE"
        btnStart.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "none");
        // Remove the default selection box
        btnStart.setFocusPainted(false);
        //fine
        
        // Start game
        btnStart.addActionListener(e ->{
            int rand = new Random().nextInt(5) + 1;
            String file = "" + rand + ".csv";
            String[] dimension = new String[2];
            try(BufferedReader buf = new BufferedReader(new FileReader(file))){
                String line=null;
                line = buf.readLine();
                dimension = line.split(";");
                //
                reader.close();
                //
            } catch (IOException  exception) {
                exception.printStackTrace();
            }
            System.out.println( "rows: "+dimension[0]+" columns: "+dimension[1]);
            //non richiamare più la GUI
            //new GUI(file,Integer.parseInt(dimension[0]),Integer.parseInt(dimension[1]));
            //non so se ti serve o cosaa ma chiudi il reader nel primo try, e chiamalo reader plz
            //nuova parte
             originalFileContent = new Vector<>();
        	try {
        		BufferedReader reader = new BufferedReader(new FileReader(originalFile));
        		nextLine = reader.readLine();
        		
        		while(nextLine != null) {
        			originalFileContent.add(nextLine);
        			nextLine = reader.readLine();
        		}
        		
        		reader.close();
        	}catch(IOException e1) {
        		e1.printStackTrace();
        	}
        	//metti i parametri che usavi prima aggiungendo originalFileContent
        	ProgressBar bar = new ProgressBar(file, rows, firstLine.length(), originalFileContent);
        	bar.setVisible(true);	
         
            dispose();
        });
        
        buttonKey(btnStart, KeyEvent.VK_ENTER);
       //fine
       
        add(btnStart);

        setTitle("Welcome to GuessTheWorld!");
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    //nuova parte
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
    //fine

    public static void main(String[] args) {
        new Menu();
    }
}
