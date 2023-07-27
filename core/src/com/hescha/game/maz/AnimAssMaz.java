package com.hescha.game.maz;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.hescha.game.maz.screen.GameScreen;
import com.hescha.game.maz.screen.LoadingScreen;
import com.hescha.game.maz.screen.MainMenuScreen;

public class AnimAssMaz extends Game {

    public static float WORLD_WIDTH;
    public static float WORLD_HEIGHT;
    public static final String PREFERENCE_SAVING_PATH = "AnimAss_Maze";
    public static Color BACKGROUND_COLOR =  new Color(242f/255,231f/255,216f/255,1);

    public static final AssetManager assetManager = new AssetManager();
    public static AnimAssMaz launcher;
    @Override
    public void create() {
        this.launcher = this;


        WORLD_WIDTH = Gdx.graphics.getWidth();
        WORLD_HEIGHT = Gdx.graphics.getHeight();


        setScreen(new LoadingScreen());
    }
}