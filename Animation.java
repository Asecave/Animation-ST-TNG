package animationSTTNG;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Animation extends JPanel implements ActionListener, KeyListener, MouseListener{
	
	int timer = 100;
	Timer tm = new Timer(timer, this);
	
	static int width = 400;
	static int height = 400;
	
	boolean setup = true;
	
	float[][] tiles = new float[41][41];
	
	float fade_speed = (float) 0.05;
	int chance = 7;
	
	public Animation() {
		tm.start();
		addKeyListener(this);
		addMouseListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		//Images
		
		//setup
		if (setup) {
			setup = false;
		}
		//Loop
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, width, height);
		g2d.setColor(Color.CYAN);
		for (int render_x = 0; render_x < 40; render_x++){
			for (int render_y = 0; render_y < 40; render_y++){
				if (tiles[render_x][render_y] > 0){
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, tiles[render_x][render_y]));
					g2d.fillRect(render_x*10, render_y*10, 10, 10);
					tiles[render_x][render_y] -= fade_speed;
				}
			}
		}
		for (int analyse_x = 0; analyse_x < 40; analyse_x++){
			for (int analyse_y = 0; analyse_y < 40; analyse_y++){
				if (tiles[analyse_x][analyse_y] > (0.99-fade_speed) && analyse_x > 0 && analyse_y > 0){
					if (analyse_x == analyse_y) {
						tiles[analyse_x-1][analyse_y-1] = 1;
						tiles[analyse_x-1][analyse_y] = 1;
						tiles[analyse_x][analyse_y-1] = 1;
						if (analyse_x > 1 && analyse_y > 1) {
							tiles[analyse_x-2][analyse_y-2] = 1;
							tiles[analyse_x-2][analyse_y-1] = 1;
							tiles[analyse_x-1][analyse_y-2] = 1;
						}
					}
					if (analyse_x < analyse_y && tiles[analyse_x+1][analyse_y] > 0) {
						if ((int)(Math.random()*chance) != 0)
							tiles[analyse_x-1][analyse_y] = 1;
					}
					if (analyse_x > analyse_y && tiles[analyse_x][analyse_y+1] > 0) {
						if ((int)(Math.random()*chance) != 0)
							tiles[analyse_x][analyse_y-1] = 1;
					}
				}
			}
		}
	}
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_E) {
			
		}
	}
	
	public void keyReleased(KeyEvent e) {
		
	}
	
	public void keyTyped(KeyEvent e) {}
	
	public static void main(String[] args) {
		Animation r = new Animation();
		JFrame jf = new JFrame();
		jf.setTitle("Animation");
		jf.setSize(width+6, height+28);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);
		jf.setFocusable(true);
		jf.setLocationRelativeTo(null);
		jf.add(r);
	}

	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			tiles[39][39] = 1;
		}
	}
	public void mouseReleased(MouseEvent e) {
		
	}
}