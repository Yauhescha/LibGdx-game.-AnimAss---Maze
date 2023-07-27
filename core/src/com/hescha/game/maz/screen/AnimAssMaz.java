package com.hescha.game.maz.screen;


import com.badlogic.gdx.Game;

public class AnimAssMaz extends Game {
    public static AnimAssMaz launcher;

    @Override
    public void create() {
        this.launcher = this;
        setScreen(new GameScreen());
    }
}