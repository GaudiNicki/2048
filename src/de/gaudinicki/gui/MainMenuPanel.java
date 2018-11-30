package de.gaudinicki.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.gaudinicki.game.AudioHandler;
import de.gaudinicki.game.DrawUtils;
import de.gaudinicki.game.Game;

public class MainMenuPanel extends GuiPanel {
	
	private AudioHandler audio;
	private boolean muted = false;
	private Font titleFont = Game.mainFont.deriveFont(100f);
	private Font creatorFont = Game.mainFont.deriveFont(24f);
	private String title = "2048";
	private String creator = "Developed by Niklas Witzel";
	private int buttonWidth = 220;
	private int buttonHeight = 60;
	private int muteButtonWidth = 150;
	private int muteButtonHeight = 40;
	private int spacing = 90;
	
	public MainMenuPanel() {
		super();
		GuiButton playButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, 220, buttonWidth, buttonHeight);
		GuiButton scoresButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, playButton.getY() + spacing, buttonWidth, buttonHeight);
		GuiButton quitButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, scoresButton.getY() + spacing, buttonWidth, buttonHeight);
		GuiButton muteButton = new GuiButton(Game.WIDTH - muteButtonWidth - 20, Game.HEIGHT - muteButtonHeight - 10, muteButtonWidth, muteButtonHeight);
		
		playButton.setText("Play");
		scoresButton.setText("Scores");
		quitButton.setText("Quit");
		muteButton.setText("Mute Volume");
		
		muteButton.setReleaseColor(new Color(51, 102, 0));
		muteButton.setHoverColor(Color.RED);
		
		playButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GuiScreen.getInstance().setCurrentPanel("Play");			
			}
			
		});
		
		scoresButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GuiScreen.getInstance().setCurrentPanel("Leaderboards");			
			}
			
		});
		
		quitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);			
			}
			
		});
		
		muteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				audio = AudioHandler.getInstance();
				
				if(!muted) {
					audio.adjustVolume("BG", -80);
					muted = true;
					muteButton.setReleaseColor(Color.RED);
					muteButton.setHoverColor(new Color(51, 102, 0));
					muteButton.setText("Ummute Volume");
				}
				else {
					audio.adjustVolume("BG", -10);
					muted = false;
					muteButton.setReleaseColor(new Color(51, 102, 0));
					muteButton.setHoverColor(Color.RED);
					muteButton.setText("Mute Volume");
				}
			}
		});
		
		add(playButton);
		add(scoresButton);
		add(quitButton);
		add(muteButton);
	}
	
	public void render(Graphics2D g) {
		super.render(g);
		g.setFont(titleFont);
		g.setColor(Color.BLACK);
		g.drawString(title, Game.WIDTH / 2 - DrawUtils.getMessageWidth(title, titleFont, g) / 2, 150);
		g.setFont(creatorFont);
		g.drawString(creator, 20, Game.HEIGHT - 20);
	}
}
