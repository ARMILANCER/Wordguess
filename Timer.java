import java.awt.Font;
import java.io.IOException;

import javax.swing.*;

public class Timer extends JLabel implements Runnable{
	private int count = 0;
	private boolean stop = false;
	//nuova parte
	private GUI gui;
	private final static int maxTime = 600;
	
	public Timer(GUI gui) {
		super("" + maxTime);
		setFont(new Font("Arial", Font.BOLD, 20));
		new Thread(this).start();
		this.gui = gui;
	}
	/*public Timer() {
		super("0");
		setFont(new Font("Arial", Font.BOLD, 20));
		new Thread(this).start();
	}*/
	//fine
	
	
	
	
	@Override
	public void run() {
		synchronized(this) {
			while(!stop) {
			//nuova parte
				if(count >= 0) {
					setText("" + count);
					count--;
				}else
					gui.blockProgram(gui, 2);
					
				/*count++;
				setText("" + count);*/
				
				//fine
				try {
					wait(1000);
				}catch(InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	public synchronized void stop() {
		stop = true;
	}
	
	//nuova parte
	public synchronized int getCount() {
		return count;
	}
	//fine

}
