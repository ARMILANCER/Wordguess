/*import java.awt.Font;
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
	}
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
				setText("" + count);
				
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

}*/
	
//contando che ho cambiato la maggior parte delle cose faccio copia e incolla e bona
import java.awt.Font;
import javax.swing.*;


public class Timer extends JLabel implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean stop = false;
	private GUI gui;
	private static final int maxMinutes = 10, maxSeconds = 60;
	private int count = maxMinutes * maxSeconds, seconds, minutes;
	private String time = "";
	public Timer(GUI gui) {
		super("10:00");
		//super("" + maxTime);
		setFont(new Font("Arial", Font.BOLD, 20));
		new Thread(this).start();
		this.gui = gui;
	}

	@Override
	public void run() {
		synchronized(this) {
			while(!stop) {
				if(count >= 0) {
					minutes = count / maxSeconds;
					seconds = count % maxSeconds;
					
					time = String.format("%02d:%02d", minutes, seconds);
					
					setText(time);
					count--;
				}else
					gui.blockProgram(gui, 2);
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

	public synchronized int getCount() {
		return count;
	}
}
	
