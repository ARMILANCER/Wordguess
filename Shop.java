package Listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Shop {//implements ActionListener {

    private int points, nHelps;
    private JLabel lblHelp,lblPoints;

    public Shop(int points, int nHelps, JLabel lblHelp, JLabel lblPoints) {
        this.points = points;
        this.nHelps = nHelps;
        this.lblHelp = lblHelp;
        this.lblPoints = lblPoints;
    }

    private final String[] packs = {"2 helps: 20 points", "6 helps: 50 points", "12 helps: 85 points", "32 helps: 150 points", "50 helps, 200 points"};

   // @Override
    public void action(){
        //actionPerformed(ActionEvent e) {
        String selectedPack = (String) JOptionPane.showInputDialog(new JDialog(), "Select a pack", "Options", JOptionPane.PLAIN_MESSAGE, null, packs, packs[0]);
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
    }

    public int getHelps(){
        return nHelps;
    }

    public int getPoints(){
        return points;
    }
}
