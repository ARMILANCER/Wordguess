
import java.awt.*;
import java.io.*;
import java.util.Random;
import java.util.Vector;

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
    private JPanel pnlCenter, pnlSouth, pnlBtn, pnlWest, pnlHelp, pnlShop, pnlNorth, pnlGame;
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
    private int nHelps = 3;
    // total points of the game
    private int points = 0;
    // shop point's pack and cost
    private final String[] packs = {"2 helps: 20 points", "6 helps: 50 points", "12 helps: 85 points", "32 helps: 150 points", "50 helps, 200 points"};
    // used in btnCheck to see if a line has already been completed
    private final boolean[] done;

    ///////
    ///
    ///////
    public GUI(String file, int rows, int columns) {

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
       // btnStop = new JButton("stop");
       // btnStop.addActionListener(e -> timer.stop());

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

       // pnlNorth.add(btnStop, BorderLayout.CENTER);


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
                            lbl[i][j] = new JLabel(String.valueOf(word[0].charAt(j)));
                            lbl[i][j].setOpaque(true);
                            lbl[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                            tf[i][j] = new JTextField();
                            tf[i][j].setHorizontalAlignment(JTextField.CENTER);
                            tf[i][j].setOpaque(true);
                            tf[i][j].setBackground(Color.CYAN);
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

        done = new boolean[rows];
        btnCheck = new JButton("Check");
        btnCheck.setBackground(Color.BLUE);
        btnCheck.setForeground(new Color(255, 215, 0));
        btnCheck.setOpaque(true);
        btnCheck.setBorderPainted(false);
        btnCheck.setFont(new Font("Arial", Font.BOLD, 20));

        // CHECK
        btnCheck.addActionListener(e -> {
            boolean correct = true;
            for (int i = 0; i < rows; i++) {
                int cont = 0;
                for (int j = 0; j < columns; j++) {
                    if (tf[i][j].getText().equals(lbl[i][j].getText())) {
                        tf[i][j].setEditable(false);
                        tf[i][j].setBackground(Color.YELLOW);
                        //lbl[i][j].setBackground(Color.GREEN.darker().brighter().brighter());

                        // GREEN IF COMPLETE
                        if (!tf[i][j].isEditable()) {
                            cont++;
                            // All columns are yellow
                            if (cont == columns) {
                                for (int n = 0; n < columns; n++) {
                                    tf[i][n].setBackground(Color.green);
                                }
                                //num.
                                if(!done[i]) {
                                    for (int s = 0; s < 3; s++) {
                                        boolean k = false;

                                        while (!k) {
                                            int r = new Random().nextInt(rows);
                                            int c = new Random().nextInt(columns);

                                            if (tf[r][c].getText().equals("")) {
                                                tf[r][c].setText(lbl[r][c].getText());
                                                tf[r][c].setBackground(Color.YELLOW);
                                                tf[r][c].setEditable(false);
                                                k = true;
                                                done[i] = true;
                                            }
                                        }
                                    }
                                }
                            }
                        } else cont = 0;
                    } else {
                        tf[i][j].setText(null);
                        correct = false;
                    }
                }
            }

            if (correct) {
                Container c = getContentPane();
                JPanel pnlCenter = (JPanel) c.getComponent(0);

                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {
                        pnlCenter.remove(tf[i][j]);
                        pnlCenter.add(lbl[i][j]);
                    }
                }

                pnlCenter.revalidate();
                pnlCenter.repaint();
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
            if (nHelps > 0) {
                int n, m;

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
                }

                nHelps--;
                lblHelp.setText("Helps remaining: " + nHelps);
            } else {
                JOptionPane.showMessageDialog(null, "Helps finished, buy more", "Conferma", JOptionPane.INFORMATION_MESSAGE);
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
                nHelps += h;
                points -= price;
                lblHelp.setText("Helps remaining: " + nHelps);
                lblPoints.setText("Total points: " + points);
                JOptionPane.showMessageDialog(null, "You have purchased with success " + h + " helps!\n" + "Helps remaining: " + nHelps + "\nPoints remaining: " + points, "confirms", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Insufficient points!", "confirms", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        pnlShop.add(btnShop);
        pnlShop.add(lblPoints);

        ta = new JTextArea();
        ta.setBackground(Color.BLUE);
        ta.setForeground(new Color(255, 215, 0));
        ta.setFont(new Font("Arial", Font.BOLD, 20));
        ta.setEditable(false);

        pnlBtn.add(btnCheck, BorderLayout.CENTER);
        pnlBtn.add(pnlHelp, BorderLayout.WEST);
        pnlBtn.add(pnlShop, BorderLayout.EAST);
        pnlSouth.add(pnlBtn);
        pnlSouth.add(ta);

        pnlWest = new JPanel(new GridLayout(rows, 1));
        pnlWest.setBackground(Color.BLUE);

        btnHint = new JButton[rows];


        for (int i = 0; i < rows; i++) {
            int j=i;
            btnHint[i] = new JButton("Level " + (i + 1));
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
    }
}
