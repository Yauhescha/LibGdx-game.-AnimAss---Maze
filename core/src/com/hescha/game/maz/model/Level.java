package com.hescha.game.maz.model;

import lombok.Data;

@Data
public class Level {
    private String category;
    private String name;
    private LevelType levelType;
    private Game game;

    public String texturePath() {
        return "levels/" + levelType.name() + "/" + category + "/" + name + ".jpg";
    }
}
