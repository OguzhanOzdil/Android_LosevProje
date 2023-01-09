package com.ozzyozdil.flappydeneme;

import com.badlogic.gdx.Game;

public class FlappyDeneme extends Game {

	public static final int WIDTH = 300;
	public static final int HEIGHT = 480;
	public static final int CENTER_X = WIDTH/2;
	public static final int CENTER_Y = HEIGHT/2;
	public static final int GROUND_LEVEL = 96;

	@Override
	public void create () {
		Assets.load();
		SavedDataManager.getInstance().load();
		setScreen(new Reklam(this));
	}

	@Override
	public void pause() {
		super.pause();
		SavedDataManager.getInstance().save();
	}

	@Override
	public void dispose() {
		super.dispose();
		Assets.dispose();
	}
}
