import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.LineBorder;

/*
CASE SENSITIVE
NEX BLOCK
*/
public class GUI extends JFrame {
    /**
     *
     */
    ///////
    /// COMPONENT JFRAME
    ///////
    private static final long serialVersionUID = 1L;
    private JPanel pnlCenter, pnlSouth, pnlBtn, pnlWest, pnlHelp, pnlShop, pnlNorth, pnlGame, pnlCheck;
    private JTextField[][] tf;
    private JLabel[][] lbl;
    private JLabel lblHelp, lblPoints, lblGame;
    private JButton btnCheck, btnHelp, btnShop, btnExit; //btnStop;, btnPrevious, btnNext;
    private JTextArea ta;

    ///////
    /// OTHERS
    ///////
    // number helps
    private int helps; //= 3;
    // total points of the game
    private int points; // = 0;
    // shop point's pack and cost
    private final String[] packs = {"2 helps: 20 points", "6 helps: 50 points", "12 helps: 85 points", "32 helps: 150 points", "50 helps, 200 points"};
    // used in btnCheck to see if a line has already been completed
    private boolean[] done;
    
    // used in the textFields to see if the word is completed, if it is then it doesn't showw anything in the textArea
    private boolean[] complete;
    
    private int guessedWords = 0;
    // to see if the program is blocked due to time, if it is blocked the check open a new frame
    private boolean block = false;  
    // used in the gui's blockProgram function to print the showMessageDialog just one time
    private boolean dialog = false;
    // to see if the game is finished, used to block the help if it is
    private boolean finish = false;
    // used to see in which line the user is writing
    private int currentRow = -1;
    // set the background color of each textField
    private Color[][] tfBackgrounds;
    
    private Vector<String> fileContent;
    
    ///////
    /// TIMER
    ///////
    // calculate the point with the remaining game time
    private Timer timer;

    ///////
    ///
    ///////
    public GUI(String file, int rows, int columns) {//, int nHelps, int nPoints) {
        //int helps = nHelps;
        //int points = nPoints;
    	//String numbersFile = "Numbers.txt";
    	fileContent = new Vector<>();
    	String str = null, nHelps = null, score = null;
    	ta= new JTextArea();
    	try {
    		BufferedReader reader = new BufferedReader(new FileReader("Numbers.txt"));
    		
    		str = reader.readLine();
    		//fileContent.add(str);
    		
    		while(str != null) {
    			fileContent.add(str);
    			if(str.equals("HELPS")) {
    				str = reader.readLine();
    				fileContent.add(str);
    				nHelps = str;
    				ta.append(nHelps + "\n");
    			}else if(str.equals("POINTS")){
    				str = reader.readLine();
    				fileContent.add(str);
    				score = str;
    				ta.append(score);
    			}
    			str = reader.readLine();
    		}
    		
    		reader.close();
    	}catch(IOException e1) {
    		e1.printStackTrace();
    	}
    	
    	helps = Integer.parseInt(nHelps);
    	points = Integer.parseInt(score);
    	
    	setLayout(new BorderLayout());

        pnlNorth = new JPanel(new BorderLayout());
        pnlGame = new JPanel(new FlowLayout());


        pnlNorth.setBackground(Color.BLUE);
        pnlGame.setBackground(Color.BLUE);

        timer = new Timer(this);
        timer.setOpaque(true);
        timer.setBackground(Color.BLUE);
        timer.setForeground(new Color(255, 215, 0));
        timer.setFont(new Font("Times New Roman", Font.BOLD, 20));

        lblGame = new JLabel("GuessTheWord");
        lblGame.setOpaque(true);
        lblGame.setBackground(Color.BLUE);
        lblGame.setFont(new Font("Times new roman", Font.BOLD, 30));
        lblGame.setForeground(new Color(255, 215, 0));

        btnExit = new JButton("X");
        btnExit.setBackground(Color.BLUE);
        btnExit.setForeground(Color.BLACK);
        btnExit.setOpaque(true);
        btnExit.setBorderPainted(false);
        btnExit.setFont(new Font("Times New Roman", Font.BOLD, 20));
        btnExit.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "none");
        btnExit.setFocusPainted(false);
        btnExit.addActionListener(e ->{
            System.exit(0);
        });
        
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

        pnlGame.add(lblGame, BorderLayout.CENTER);
        pnlNorth.add(timer, BorderLayout.WEST);
        pnlNorth.add(pnlGame, BorderLayout.CENTER);
        pnlNorth.add(btnExit, BorderLayout.EAST);

        pnlCenter = new JPanel(new GridLayout(rows, columns));
        pnlCenter.setBackground(Color.BLUE);

        tfBackgrounds = new Color[rows][columns];
        for(int i = 0;  i < rows; i++) {
        	for(int j = 0; j < columns; j++) {
        		tfBackgrounds[i][j] = Color.CYAN;
        	}
        }
        
        lbl = new JLabel[rows][columns];
        tf = new JTextField[rows][columns];
        complete = new boolean[rows];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
 
            for (int i = 0; i < rows; i++) {
                String nextLine = reader.readLine();
                boolean found = false;
                
                while (found == false) {
                    if (nextLine.length() == columns) {
                    	 int line = i;
                        for (int j = 0; j < columns; j++) {
                            lbl[i][j] = new JLabel(String.valueOf(nextLine.charAt(j)));
                            lbl[i][j].setHorizontalAlignment(JLabel.CENTER);
                            lbl[i][j].setOpaque(true);
                            lbl[i][j].setBackground(Color.RED);
                            lbl[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                           
                            tf[i][j] = new JTextField();
                            tf[i][j].setHorizontalAlignment(JTextField.CENTER);
                            tf[i][j].setOpaque(true);
                            tf[i][j].setBackground(Color.CYAN);
                            tf[i][j].setForeground(Color.BLACK);
                            int pillar = j;
                            tf[i][j].addMouseListener(new MouseAdapter() {             
								@Override
								public void mouseReleased(MouseEvent e) {	
									if(!complete[line]) {
										int count = 0;
						                String nextLine;
						                try {
						                    boolean exit = false;
						                    BufferedReader reader = new BufferedReader(new FileReader(file));
						                    nextLine = reader.readLine();
	
						                    while (exit == false) {
						                        if (nextLine.length() == columns) {
						                            if (line == count) {
						                                nextLine = reader.readLine();
						                                while (!nextLine.contains("stop")) {
						                                    ta.setText(nextLine);
						                                    nextLine = reader.readLine();
						                                }
						                                exit = true;
						                            } else {
						                                count++;
						                                nextLine = reader.readLine();
						                            }
						                        } else {
						                            nextLine = reader.readLine();
						                        }
						                    }
						                    reader.close();
						                } catch (IOException e1) {
						                    e1.printStackTrace();
						                }					                
									}
		
									if(currentRow != -1) {
										for(int j = 0; j < columns; j++) {
											if(tfBackgrounds[currentRow][j] == Color.YELLOW)
												tf[currentRow][j].setBackground(Color.YELLOW);
											else if(tfBackgrounds[currentRow][j] == Color.GREEN)
												tf[currentRow][j].setBackground(Color.GREEN);
											else
												tf[currentRow][j].setBackground(Color.CYAN);
										}
									}
									
								
									
									for(int j = 0; j < columns; j++) {
										if(j == pillar)
											tf[line][pillar].setBackground(new Color(51, 204, 255));
										else
											tf[line][j].setBackground(new Color(0, 255, 204));
									}
									currentRow = line;
								}
                            });
                            
                            if (tf[i][j].getText().isEmpty()) 
                            	tf[i][j].setBorder(new LineBorder(new Color(102, 0, 102)));
                            
                            tf[i][j].setDocument(new Format(1));
                            pnlCenter.add(tf[i][j]);
                        }
                        found = true;

                    } else {
                        nextLine = reader.readLine();
                    }

                }
            }

            reader.close();
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
        btnCheck.setFont(new Font("Times New Roman", Font.BOLD, 20));
        btnCheck.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "none");
        btnCheck.setFocusPainted(false);
        
        btnCheck.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				btnCheck.setBackground(new Color(0, 51, 204));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnCheck.setBackground(Color.BLUE);
			}
        	
        });
        
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
	                        tfBackgrounds[i][j] = Color.YELLOW;
	                        lbl[i][j].setBackground(Color.GREEN.darker().brighter().brighter());
	                        // GREEN IF COMPLETE
	                       if (!tf[i][j].isEditable()) {
	                            cont++;
	                            // All columns are yellow
	                            if(cont==columns) {
	                                for (int n = 0; n < columns; n++) {
	                                    tf[i][n].setBackground(Color.GREEN);
	                                    tfBackgrounds[i][n] = Color.GREEN;
	                                    ta.setText(null);
	                                    // remove the textField's mouseListener
	                                    for(MouseListener ml : tf[i][n].getMouseListeners()) {
	                                    	tf[i][n].removeMouseListener(ml);
	                                    }
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
		                                                tf[r][c].setFocusable(false);
		                                                tf[r][c].setEditable(false);
		                                                tfBackgrounds[r][c] = Color.YELLOW;
		                                                lbl[r][c].setBackground(Color.GREEN.darker().brighter().brighter());
		                                                k = true;
		                                                done[i] = true;
		                                                
		                                                int check = 0;
		                                                
		                                                for(int p = 0; p < columns; p++) {
		                                                	if (!tf[i][p].isEditable()) {
		                                                		check++;
		                                                        // All columns are yellow
		                                                        if (check == columns) {
		                                                            for (int n = 0; n < columns; n++) {
		                                                                tf[i][n].setBackground(Color.GREEN);
		                                                                tfBackgrounds[i][n] = Color.GREEN;
		                                                                ta.setText(null);
		                                                                complete[i] = true;
		                                                            }
		                                                        }
		                                                	}else
		                                                		check = 0;
		                                                }
		                                            }
		                                        }
		                                    }
		                                }
	                                }else complete[i] = true;
	                            }
	                        } else cont = 0;
	                    } else  if(!tf[i][j].getText().equals("")){                    	
	                    	tf[i][j].setBackground(Color.RED);
	                    	int line = i, pillar = j;
	                    	
	                    	Thread thread = new Thread(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									try {
										Thread.sleep(500);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									tf[line][pillar].setBackground(Color.CYAN);
			                    	tf[line][pillar].setText(null);
								}
	                    		
	                    	});
	                    	thread.start();
	                    	
	                    }
	                }
	            }
	
	            for(int i = 0; i < rows; i++) {
	            	for(int j = 0; j < columns; j++) {
	            		if(tf[i][j].isEditable()) 
	            			correct = false;
	            	}
	            }
	            if (correct == true) {
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
	                
	                Winner winner = new Winner(this, timer.getCount(), fileContent, helps, points);
	               	winner.setVisible(true);
	                setVisible(false);      
	            }
        	}else {
        		 Winner winner = new Winner(this, timer.getCount(), fileContent, helps, points);
	             winner.setVisible(true);
	             setVisible(false);  
        	}
        });

        lblHelp = new JLabel("Helps remaining: " + helps);
        lblHelp.setOpaque(true);
        lblHelp.setBackground(Color.BLUE);
        lblHelp.setForeground(new Color(255, 215, 0));

        btnHelp = new JButton();
        btnHelp.setIcon(new ImageIcon("lampadina.jpg"));
        btnHelp.setBackground(Color.BLUE);
        btnHelp.setBorderPainted(false);
        btnHelp.setOpaque(true);
        btnHelp.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "none");
        btnHelp.setFocusPainted(false);
        btnHelp.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				btnHelp.setBackground(new Color(0, 51, 204));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnHelp.setBackground(Color.BLUE);
			}
        	
        });
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
	      
	            if (helps > 0) {
	                int n, m;

	                if(emptyCells >= 3) {
		                for (int i = 0; i < 3; i++) {
		                    boolean different = false;
		
		                    while (different == false) {
		                        n = new Random().nextInt(rows);
		                        m = new Random().nextInt(columns);
		
		                        if (tf[n][m].getText().equals("")) {
		                            tf[n][m].setText(lbl[n][m].getText());
		                            tf[n][m].setBackground(Color.YELLOW);
		                            tf[n][m].setEditable(false);
		                            lbl[n][m].setBackground(Color.GREEN.darker().brighter().brighter());
		                            different = true;
		                        }
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
	                            tfBackgrounds[n][m] = Color.YELLOW;
	                            lbl[n][m].setBackground(Color.GREEN.darker().brighter().brighter());	
	                            tf[n][m].setEditable(false);
	                            different = true;
	                        }
	                    }
	                        
	                }
	
	                helps--;
	                lblHelp.setText("Helps remaining: " + helps);
	            } else {
	                JOptionPane.showMessageDialog(null, "Helps finished, buy more", "Conferma", JOptionPane.INFORMATION_MESSAGE);
	            }
        	}
        	

            for(int i = 0; i < fileContent.size(); i++) {
            	if(fileContent.get(i).equals("HELPS"))
            		fileContent.set(i + 1, "" + helps);
            	else if(fileContent.get(i).equals("POINTS"))
            		fileContent.set(i + 1, "" + points);
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
        btnShop.setFont(new Font("Times New Roman", Font.BOLD, 20));
        btnShop.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "none");
        btnShop.setFocusPainted(false);
        btnShop.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				btnShop.setBackground(new Color(0, 51, 204));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnShop.setBackground(Color.BLUE);
			}
        	
        });
        btnShop.addActionListener(e -> {
            String selectedPack = (String) JOptionPane.showInputDialog(this, "Select a pack", "Options", JOptionPane.PLAIN_MESSAGE, null, packs, packs[0]);
            int price = 0;
            int h = 0;
            switch (selectedPack) {
                case "2 helps: 20 points":
                    price = 20;
                    h = 2;
                    break;
                case "6 helps: 50 points":
                    price = 50;
                    h = 6;
                    break;
                case "12 helps: 85 points":
                    price = 85;
                    h = 12;
                    break;
                case "32 helps: 150 points":
                    price = 150;
                    h = 32;
                    break;
                case "50 helps, 200 points":
                    price = 200;
                    h = 50;
                    break;
            }
            
            if (points >= price) {
            	helps += h;
                points -= price;
                lblHelp.setText("Helps remaining: " + helps);
                lblPoints.setText("Total points: " + points);
                JOptionPane.showMessageDialog(null, "You have purchased with success " + h + " helps!\n" + "Helps remaining: " + helps + "\nPoints remaining: " + points, "confirms", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Insufficient points!", "confirms", JOptionPane.INFORMATION_MESSAGE);
            }
            

            for(int i = 0; i < fileContent.size(); i++) {
            	if(fileContent.get(i).equals("HELPS"))
            		fileContent.set(i + 1, "" + helps);
            	else if(fileContent.get(i).equals("POINTS"))
            		fileContent.set(i + 1, "" + points);
            }
        });

        pnlShop.add(btnShop);
        pnlShop.add(lblPoints);

        //ta = new JTextArea();
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setBackground(Color.BLUE);
        ta.setForeground(new Color(255, 215, 0));
        ta.setFont(new Font("Times New Roman", Font.BOLD, 20));
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
        pnlBtn.add(pnlHelp, BorderLayout.WEST);
        pnlBtn.add(pnlShop, BorderLayout.EAST);
        pnlSouth.add(pnlBtn);
        pnlSouth.add(ta);

        add(pnlNorth, BorderLayout.NORTH);
        add(pnlCenter, BorderLayout.CENTER);
        add(pnlSouth, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
    }
    
    public void blockProgram(GUI gui, int n) {	
    	btnHelp.setEnabled(false);
    	btnShop.setEnabled(false);
    	ta.setText(null);
    	
    	for(int i = 0; i  < tf.length; i++) {
    		for(int j = 0; j < tf[0].length; j++) {
    			pnlCenter.remove(tf[i][j]);
                pnlCenter.add(lbl[i][j]);
            }
        }
        pnlCenter.revalidate();
        pnlCenter.repaint();
        
        if(dialog == false) {
        	if(n == 2)
        		JOptionPane.showMessageDialog(null, "You've runned out of time!");
	        Winner winner = new Winner(this, timer.getCount(), fileContent, helps, points);
	       	winner.setVisible(true);
	       	setVisible(false);
        }
        
        dialog = true;
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
