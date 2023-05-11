
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerMenu implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Play")){
            try {
                StateMachine.ChangeWindow("Menu",new Guess_the_Word(),"Guess the Word");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
