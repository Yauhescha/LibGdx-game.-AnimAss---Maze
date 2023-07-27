package com.hescha.game.maz;


import com.badlogic.gdx.Game;
import com.hescha.game.maz.screen.GameScreen;

public class AnimAssMaz extends Game {
    public static AnimAssMaz launcher;

    @Override
    public void create() {
        this.launcher = this;
        setScreen(new GameScreen());
    }
}