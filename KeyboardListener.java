import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {
    public boolean su, giu, destra, sinistra;

    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP) {
            su = true;
        }
        if (code == KeyEvent.VK_DOWN) {
            giu = true;
        }
        if (code == KeyEvent.VK_LEFT) {
            sinistra = true;
        }
        if (code == KeyEvent.VK_RIGHT) {
            destra = true;
        }
        if (code == KeyEvent.VK_UP) {
        }
        if (code == KeyEvent.VK_DOWN) {
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP) {
            su = false;
        }
        if (code == KeyEvent.VK_DOWN) {
            giu = false;
        }
        if (code == KeyEvent.VK_LEFT) {
            sinistra = false;
        }
        if (code == KeyEvent.VK_RIGHT) {
            destra = false;
        }
        if (code == KeyEvent.VK_UP) {
            //  laby.Zoom(1);
        }
        if (code == KeyEvent.VK_DOWN) {
            // laby.Zoom(-1);
        }
    }
}
