package Listener;

/*import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Check implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

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

    }
}

 */