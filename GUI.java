package Interface;

import Data.ResizeArray;
import Home.StateMachine;
import Listener.Shop;

import java.awt.*;
import java.io.*;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.*;

/*
CASE SENSITIVE
NEX BLOCK
*/
public class GUI extends JFrame {
    ///////
    /// COMPONENT JFRAME
    ///////
    private static final long serialVersionUID = 1L;
    private JPanel pnlCenter, pnlSouth, pnlBtn, pnlWest, pnlHelp, pnlShop, pnlNorth, pnlGame, pnlCheck;
    private JTextField[][] tf;
    private JLabel[][] lbl;
    private JLabel lblHelp, lblPoints, lblGame;
    private JButton[] btnHint;
    private JButton btnCheck, btnHelp, btnShop, btnExit; //btnStop;, btnPrevious, btnNext;
    private JTextArea ta;

    ///////
    /// OTHERS
    ///////
    // number helps
    private int nHelps = 50;
    // total points of the game
    private int points = 0;
    // shop point's pack and cost
    //private final String[] packs = {"2 helps: 20 points", "6 helps: 50 points", "12 helps: 85 points", "32 helps: 150 points", "50 helps, 200 points"};
    // used in btnCheck to see if a line has already been completed
    private boolean[] done;
    private  int guessedWords=0;
    // to see if the program is blocked due to time, if it is blocked the check open a new frame
    private boolean block = false;  
    // used in the gui's blockProgram function to print the showMessageDialog just one time
    boolean show = false;
    // to see if the game is finished, used to block the help if it is
    private boolean finish = false;

    // constructor data
    private String file;
    private int rows, columns;

    // DATA
    ResizeArray lvl = new ResizeArray();

    ///////
    ///
    ///////
    public GUI() {
        file = lvl.getCurrentFile();
        rows = lvl.getRow();
        columns = lvl.getColumns();

        setLayout(new BorderLayout());

        pnlNorth = new JPanel(new BorderLayout());
        pnlGame = new JPanel(new FlowLayout());


        pnlNorth.setBackground(Color.BLUE);
        pnlGame.setBackground(Color.BLUE);

        ///////
        /// TIMER
        ///////
        // calculate the point with the game time
        Timer timer = new Timer();
        timer.setOpaque(true);
        timer.setBackground(Color.BLUE);
        timer.setForeground(new Color(255, 215, 0));
        timer.setFont(new Font("Arial", Font.BOLD, 20));



        lblGame = new JLabel("GuessTheWord");
        lblGame.setOpaque(true);
        lblGame.setBackground(Color.BLUE);
        lblGame.setFont(new Font("Times new roman", Font.BOLD, 30));
        lblGame.setForeground(new Color(255, 215, 0));

        btnExit = new JButton("Exit");
        btnExit.setBackground(Color.BLUE);
        btnExit.setForeground(new Color(255, 215, 0));
        btnExit.setOpaque(true);
        btnExit.setBorderPainted(false);
        btnExit.setFont(new Font("Arial", Font.BOLD, 20));
        btnExit.addActionListener(e ->{
            System.exit(0);
        });
        pnlGame.add(lblGame, BorderLayout.CENTER);
        pnlNorth.add(timer, BorderLayout.WEST);
        pnlNorth.add(pnlGame, BorderLayout.CENTER);
        pnlNorth.add(btnExit, BorderLayout.EAST);

        pnlCenter = new JPanel(new GridLayout(rows, columns));
        pnlCenter.setBackground(Color.BLUE);

        lbl = new JLabel[rows][columns];
        tf = new JTextField[rows][columns];
        //
        Vector<String> question = new Vector<>();
        // MAKE TABLE
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            boolean firtLine = true;
            // i - 1 Because the first line contains the file dimension
            for (int i = -1; i < rows; i++) {
                String [] word;
                String nextLine = reader.readLine();
                // word[0] -> contain the word word[1] -> contain the question
                // jump the firsts line because the first line contains the file dimension
                if (!firtLine) {
                    word = nextLine.split(";");
                    boolean found = false;
                    question.add(word[1]);
                        System.out.println(word[0]);
                        for (int j = 0; j < columns; j++) {
                            AtomicInteger c = new AtomicInteger(j);
                            // serve per capire quando è finito il gioco
                            lbl[i][j] = new JLabel(String.valueOf(word[0].charAt(j)));
                            lbl[i][j].setOpaque(true);
                            lbl[i][j].setBackground(Color.RED);
                            lbl[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                            //
                            tf[i][j] = new JTextField();
                            tf[i][j].setHorizontalAlignment(JTextField.CENTER);
                            tf[i][j].setOpaque(true);
                            tf[i][j].setBackground(Color.CYAN);
                            tf[i][j].setDocument(new Format(1));
                            tf[i][j].addActionListener(e->{
                                
                            });

                            //pnlCenter.add(lbl[i][j]);
                            pnlCenter.add(tf[i][j]);
                        }
                }
                firtLine = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        pnlSouth = new JPanel(new GridLayout(2, 1));
        pnlSouth.setBackground(Color.BLUE);
        pnlBtn = new JPanel(new BorderLayout());
        pnlBtn.setBackground(Color.BLUE);
        pnlHelp = new JPanel(new GridLayout(2, 1));
        pnlCheck = new JPanel(new FlowLayout());
        pnlCheck.setBackground(Color.BLUE);
        
        done = new boolean[rows];
        btnCheck = new JButton("Check");
        btnCheck.setBackground(Color.BLUE);
        btnCheck.setForeground(new Color(255, 215, 0));
        btnCheck.setOpaque(true);
        btnCheck.setBorderPainted(false);
        btnCheck.setFont(new Font("Arial", Font.BOLD, 20));

        // CHECK
        btnCheck.addActionListener(e -> {
            if(block == false) {
                boolean correct = true;
                boolean alreadyIncremented = false;
                int half = (int) rows/2,  threeQuarters = (int)rows * 3 / 4;
               
                for (int i = 0; i < rows; i++) {
                    int cont = 0;
                    for (int j = 0; j < columns; j++) {
                        if (tf[i][j].getText().equals(lbl[i][j].getText())) {
                            tf[i][j].setEditable(false);
                            tf[i][j].setBackground(Color.YELLOW);
                            lbl[i][j].setBackground(Color.GREEN.darker().brighter().brighter());
                            // GREEN IF COMPLETE
                            if (!tf[i][j].isEditable()) {
                                cont++;
                                // All columns are yellow
                                if(cont==columns) {
                                    for (int n = 0; n < columns; n++) {
                                        tf[i][n].setBackground(Color.green);
                                        if(!alreadyIncremented) {
                                            guessedWords++;
                                            alreadyIncremented = true;
                                        }
                                    }
                                    //num.
                                    int max;
                                    if(guessedWords <= half)
                                        max = 3;
                                    else if(guessedWords > half && guessedWords <= threeQuarters)
                                        max = 1;
                                    else
                                        max = 0;
                                    
                                            if(max != 0) {
                                        if(done[i] == false) {
                                            for (int s = 0; s < max; s++) {
                                                boolean k = false;

                                                while (k == false) {
                                                    int r = new Random().nextInt(rows);
                                                    int c = new Random().nextInt(columns);

                                                    if (tf[r][c].getText().equals("")) {
                                                        tf[r][c].setText(lbl[r][c].getText());
                                                        tf[r][c].setBackground(Color.YELLOW);
                                                        tf[r][c].setEditable(false);
                                                        lbl[i][j].setBackground(Color.GREEN.darker().brighter().brighter());
                                                        k = true;
                                                        done[i] = true;

                                                        int check = 0;

                                                        for(int p = 0; p < columns; p++) {
                                                            if (!tf[i][j].isEditable()) {
                                                                check++;
                                                                // All columns are yellow
                                                                if (check == columns) {
                                                                    for (int n = 0; n < columns; n++) {
                                                                        tf[i][n].setBackground(Color.green);
                                                                    }
                                                                }
                                                            }else
                                                                check = 0;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else cont = 0;
                        } else  tf[i][j].setText(null);
                    }
                }

                for(int i = 0; i < rows; i++) {
                    for(int j = 0; j < columns; j++) {
                        if(tf[i][j].isEditable())
                            correct = false;
                    }
                }
                if (correct) {
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < columns; j++) {
                            pnlCenter.remove(tf[i][j]);
                            pnlCenter.add(lbl[i][j]);
                        }
                    }

                    pnlCenter.revalidate();
                    pnlCenter.repaint();
                    timer.stop();

                    finish = true;
	                blockProgram(this, 1);
                    
                    try {
                        StateMachine.ChangeWindow("game","winnerInterface");
                        StateMachine.close("game");
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }else {
        		 Winner winner = new Winner(this, timer.getCount());
	             winner.setVisible(true);
	             setVisible(false);     
        	}
        });

        lblHelp = new JLabel("Helps remaining: " + nHelps);
        lblHelp.setOpaque(true);
        lblHelp.setBackground(Color.BLUE);
        lblHelp.setForeground(new Color(255, 215, 0));

        btnHelp = new JButton();
        btnHelp.setIcon(new ImageIcon("lampadina.jpeg"));
        //btnHelp.setBackground(new Color(255, 215, 0));
        btnHelp.setBackground(Color.BLUE);
        btnHelp.setBorderPainted(false);
        btnHelp.setOpaque(true);
        btnHelp.addActionListener(e -> {
            if(!finish) {
        		int emptyCells = 0;
	        	for(int i = 0; i < rows; i++) {
	        		for(int j = 0; j < columns; j++) {
	        			String color = "" + lbl[i][j].getBackground();
	        			if(color.equals("java.awt.Color[r=255,g=0,b=0]")) 
	        				emptyCells++;	        			
	        		}
	        	}
                if (nHelps > 0) {
                    int n, m;
                    
                    if(emptyCells >= 3) {
                        for (int i = 0; i < 3; i++) {
                            boolean different = false;

                            while (!different) {
                                n = new Random().nextInt(rows);
                                m = new Random().nextInt(columns);
                                if (tf[n][m].getText().equals("")) {
                                    tf[n][m].setText(lbl[n][m].getText());
                                    tf[n][m].setBackground(Color.YELLOW);
                                    tf[n][m].setEditable(false);
                                    different = true;
                                }
                            }
                        }else {
	                	boolean different = false;
	            		
	                    while (different == false) {
	                        n = new Random().nextInt(rows);
	                        m = new Random().nextInt(columns);
	
	                        if (tf[n][m].getText().equals("")) {
	                            tf[n][m].setText(lbl[n][m].getText());
	                            tf[n][m].setBackground(Color.YELLOW);
	                            lbl[n][m].setBackground(Color.GREEN.darker().brighter().brighter());	
	                            tf[n][m].setEditable(false);
	                            different = true;
	                        }
	                    }
	                        
	                }

                    nHelps--;
                    lblHelp.setText("Helps remaining: " + nHelps);
                } else {
                    JOptionPane.showMessageDialog(null, "Helps finished, buy more", "Conferma", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        pnlHelp.add(btnHelp);
        pnlHelp.add(lblHelp);

        pnlShop = new JPanel(new GridLayout(2, 1));

        lblPoints = new JLabel("Total points: " + points);
        lblPoints.setOpaque(true);
        lblPoints.setBackground(Color.BLUE);
        lblPoints.setForeground(new Color(255, 215, 0));

        btnShop = new JButton("Shop");
        btnShop.setBackground(Color.BLUE);
        btnShop.setForeground(new Color(255, 215, 0));
        btnShop.setOpaque(true);
        btnShop.setBorderPainted(false);
        btnShop.setFont(new Font("Arial", Font.BOLD, 20));
        btnShop.addActionListener(e ->{
            Shop shop = new Shop(points,nHelps,lblHelp,lblPoints);
            shop.action();
            nHelps = shop.getHelps();
            points = shop.getPoints();
        });

        pnlShop.add(btnShop);
        pnlShop.add(lblPoints);

        ta = new JTextArea();
        ta.setBackground(Color.BLUE);
        ta.setForeground(new Color(255, 215, 0));
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true); 
        ta.setFont(new Font("Arial", Font.BOLD, 20));
        ta.setEditable(false);

        // Associating btnHelp botton with the key: ","
        buttonKey(btnHelp, KeyEvent.VK_COMMA);
        // Associating btnCheck botton with the key: "Enter"
        buttonKey(btnCheck, KeyEvent.VK_ENTER);
        // Associating btnShop botton with the key: "."
        buttonKey(btnShop, KeyEvent.VK_PERIOD);
        //// Associating btnShop botton with the key: "ESC"
        buttonKey(btnExit, KeyEvent.VK_ESCAPE);
            
         pnlCheck.add(btnCheck);
        
        pnlBtn.add(pnlCheck, BorderLayout.CENTER);
        //pnlBtn.add(btnCheck, BorderLayout.CENTER);
        pnlBtn.add(pnlHelp, BorderLayout.WEST);
        pnlBtn.add(pnlShop, BorderLayout.EAST);
        pnlSouth.add(pnlBtn);
        pnlSouth.add(ta);

        pnlWest = new JPanel(new GridLayout(rows, 1));
        pnlWest.setBackground(Color.BLUE);

        btnHint = new JButton[rows];


        for (int i = 0; i < rows; i++) {
            int j=i;
            btnHint[i] = new JButton("Row " + (i + 1));
            pnlWest.add(btnHint[i]);
            btnHint[i].addActionListener(e -> {
                ta.setText(question.get(j));
            });
        }

        add(pnlNorth, BorderLayout.NORTH);
        add(pnlCenter, BorderLayout.CENTER);
        add(pnlSouth, BorderLayout.SOUTH);
        add(pnlWest, BorderLayout.WEST);

        // Set Frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
    }
                                  
                                  public void blockProgram(GUI gui, int n) {	
    	btnHelp.setEnabled(false);
    	btnShop.setEnabled(false);
    	ta.setText(null);
    	
    	for(int i = 0; i < tf.length; i++) {
    		btnHint[i].setEnabled(false);
    	}
    	
    	for(int i = 0; i  < tf.length; i++) {
    		for(int j = 0; j < tf[0].length; j++) {
    			pnlCenter.remove(tf[i][j]);
                pnlCenter.add(lbl[i][j]);
            }
        }
        pnlCenter.revalidate();
        pnlCenter.repaint();
        
        if(show == false) {
        	if(n == 2)
        		JOptionPane.showMessageDialog(null, "You've runned out of time!");
	        Winner winner = new Winner(this, timer.getCount());
	       	winner.setVisible(true);
	       	setVisible(false);
        }
        
        show = true;
        block = true;
    }
    
    // Function to associate a keyboard key to a button
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
