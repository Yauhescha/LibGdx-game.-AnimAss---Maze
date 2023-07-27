package com.hescha.game.maz.screen;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.hescha.game.maz.generator.MazeGenerator;
import com.hescha.game.maz.model.Maze;
import com.hescha.game.maz.model.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AnimAssMaz extends ApplicationAdapter {
	public static int TEXTURE_SIZE=100;

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Player player;
	private Maze maze;
	public static Texture playerTexture;
	public static Texture wallTexture;

	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		camera.update();

		batch = new SpriteBatch();

		String[][] generate = MazeGenerator.generate(5);
		List<String[]> list = Arrays.asList(generate);
		Collections.reverse(list);

		for(int i=0; i<generate.length; i++){
			generate[i]=list.get(i);
		}

//		String[] strings = list.toArray(new String[]);

		maze = new Maze(generate);
		player = new Player(maze.getStartX(), maze.getStartY());

		playerTexture = new Texture(Gdx.files.internal("1.png"));
		wallTexture = new Texture(Gdx.files.internal("2.png"));
	}

	@Override
	public void render() {
		handleTouch();

		ScreenUtils.clear(Color.WHITE);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		maze.draw(batch);
		player.draw(batch);
		batch.end();
	}

	private void handleTouch() {
		if (Gdx.input.justTouched()) {
			float touchX = Gdx.input.getX();
			float touchY = Gdx.graphics.getHeight() - Gdx.input.getY(); // инвертируем ось Y

			player.moveTo(touchX, touchY, maze);
		}
	}


	@Override
	public void dispose() {
		batch.dispose();
	}
}