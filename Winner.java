package Interface;

import Home.StateMachine;

import javax.swing.*;
import java.awt.*;
import java.awt.Menu;

public class Winner extends JFrame{
    private JPanel pnlBtn, pnlLbl, pnlNew;
    private JLabel lblPoints, lblResult;
    private JButton btnBack, btnNew, btnExit;

    private final int maxPoints = 300;
    private final int maxTime = 600; //5 minuti
    private boolean back = false;
    // constructor data
    private int count;
    public Winner( int countC){
        count = countC;
    }
    public Winner(){
        pnlLbl = new JPanel();

        lblResult = new JLabel();
        if(count > maxTime)
            lblResult.setText("You've lost due to time!");
        else
            lblResult.setText("You've won, congratulations!");

        lblPoints = new JLabel();
        if(count <= 0)
            lblPoints.setText("" + maxPoints);
        else if(count >= maxTime)
            lblPoints.setText("" + 0);
        else {
            int penalty = (int) count / maxTime; // Calcola la penalità proporzionale al tempo
            int score = (int) (maxPoints * (1 - penalty)); // Calcola il punteggio sottraendo la penalità

            lblPoints.setText("" + score);
        }

        pnlLbl.add(lblResult);
        pnlLbl.add(lblPoints);

        pnlBtn = new JPanel(new BorderLayout());
        pnlNew = new JPanel(new FlowLayout());

        btnBack = new JButton("Back");
        btnBack.addActionListener(e ->{
            try {
                StateMachine.ChangeWindow("winnerInterface","game");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        btnNew = new JButton("New game");
        btnNew.addActionListener(e ->{
            try {
                StateMachine.ChangeWindow("game","gameMenu");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        btnExit = new JButton("Exit");
        btnExit.addActionListener(e ->{
            System.exit(0);
        });

        pnlNew.add(btnNew);

        pnlBtn.add(btnBack, BorderLayout.WEST);
        pnlBtn.add(pnlNew, BorderLayout.CENTER);
        pnlBtn.add(btnExit, BorderLayout.EAST);

        add(pnlLbl, BorderLayout.CENTER);
        add(pnlBtn, BorderLayout.SOUTH);

        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
