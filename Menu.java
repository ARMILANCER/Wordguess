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
        btnStart.setBounds(655,615 , 130, 30);
        // Remove the default possibility to activate the button with the key "SPACE"
        btnStart.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "none");
        // Remove the default selection box
        btnStart.setFocusPainted(false);
        // Start game
        btnStart.addActionListener(e ->{
            int rand = new Random().nextInt(5) + 1;
            String file = "" + rand + ".csv";
            String[] dimension = new String[2];
            try(BufferedReader buf = new BufferedReader(new FileReader(file))){
                String line=null;
                line = buf.readLine();
                dimension = line.split(";");
            } catch (IOException  exception) {
                exception.printStackTrace();
            }
            System.out.println( "rows: "+dimension[0]+" columns: "+dimension[1]);
            new GUI(file,Integer.parseInt(dimension[0]),Integer.parseInt(dimension[1]));
            
          
            
            dispose();
        });
        
       
        add(btnStart);

        setTitle("Welcome to crossWord!");
        setUndecorated(true);
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
