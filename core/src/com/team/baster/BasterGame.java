package com.team.baster;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team.baster.screen.menu.MenuScreen;

public class BasterGame extends Game {

	public SpriteBatch batch;
	public BitmapFont font;
	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		setScreen(new MenuScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	//	@Override
//	public void render () {
//		Gdx.gl.glClearColor(1, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
//	}

	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
		font.dispose();
	}
}
