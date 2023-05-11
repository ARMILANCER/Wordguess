
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerComboBox implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Menu")){
            try {
                StateMachine.CloseGame("Guess the Word","Menu");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
