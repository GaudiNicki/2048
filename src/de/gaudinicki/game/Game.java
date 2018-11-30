package de.gaudinicki.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import de.gaudinicki.gui.GuiScreen;
import de.gaudinicki.gui.LeaderboardsPanel;
import de.gaudinicki.gui.MainMenuPanel;
import de.gaudinicki.gui.PlayPanel;

public class Game extends JPanel implements KeyListener, MouseListener, MouseMotionListener, Runnable{

	
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 500;
	public static final int HEIGHT = 650;
	public static final Font mainFont = new Font("Bebas Neue Regular", Font.PLAIN, 28);
	private Thread game;
	private boolean running;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private GuiScreen screen;
	
	public Game() {
		this.setFocusable(true);
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		
		screen = GuiScreen.getInstance();
		screen.add("Menu", new MainMenuPanel());
		screen.add("Play", new PlayPanel());
		screen.add("Leaderboards", new LeaderboardsPanel());
		screen.setCurrentPanel("Menu");
	}
	
	private void update() {
		screen.update();
		Keyboard.update();
		
	}
	
	private void render() {
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		screen.render(g);
		g.dispose();
		
		Graphics2D g2d = (Graphics2D) getGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
	}

	@Override
	public void run() {
		
		long fpsTimer = System.currentTimeMillis();
		double nsPerUpdate = 1000000000.0 / 60;
		
		//last update time in nanoseconds
		double then = System.nanoTime();
		double unprocessed = 0;
		
		while(running) {
			
			boolean shouldRender = false;
			double now = System.nanoTime();
			unprocessed += (now - then) / nsPerUpdate;
			then = now;
			
			//update queue
			while(unprocessed >= 1) {
				update();
				unprocessed--;
				shouldRender = true;
			}
			
			//render
			if(shouldRender) {
				render();
				shouldRender = false;
			}
			else {
				try {
					Thread.sleep(1);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			//FPS Timer
			if(System.currentTimeMillis() - fpsTimer > 1000) {
				fpsTimer += 1000;
			}
		}
	}
	
	public synchronized void start() {
		if(running) return;
		running = true;
		game = new Thread(this, "game");
		game.start();
	}
	
	public synchronized void stop() {
		if(!running) return;
		running = false;
		System.exit(0);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Keyboard.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Keyboard.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		screen.mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		screen.mouseMoved(e);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
				
	}

	@Override
	public void mouseEntered(MouseEvent e) {
				
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		screen.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		screen.mouseReleased(e);
	}
	
}
