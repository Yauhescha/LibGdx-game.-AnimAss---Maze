package com.hescha.game.maz.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Level implements Serializable {
    private String category;
    private String name;
    private LevelType levelType;
    private Game game;

    public String texturePath() {
        return "levels/" + levelType.name() + "/" + category + "/" + name + ".jpg";
    }
}
