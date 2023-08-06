package com.hescha.game.maz.util;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.hescha.game.maz.model.Game;
import com.hescha.game.maz.model.Level;
import com.hescha.game.maz.model.LevelType;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LevelUtil {

    public static ArrayList<Level> loadLevels() {
        return scanLevels();
//        FileHandle file = Gdx.files.internal("levels/levels.json");
//        String jsonData = file.readString();
//        Json json = new Json();
//        return json.fromJson(ArrayList.class, Level.class, jsonData);
    }

    public static ArrayList<Level> scanLevels() {
        ArrayList<Level> levels = new ArrayList<>();
        FileHandle levelsFolder = Gdx.files.internal("levels");
        FileHandle[] levelTypeFolders = levelsFolder.list();
        for (FileHandle levelTypeFolder : levelTypeFolders) {
            if (levelTypeFolder.isDirectory()) {
                LevelType levelType = LevelType.valueOf(levelTypeFolder.file().getName());
                FileHandle[] categoryFolders = levelTypeFolder.list();
                if (categoryFolders != null) {
                    for (FileHandle categoryFolder : categoryFolders) {
                        if (categoryFolder.isDirectory()) {
                            String category = categoryFolder.file().getName();
                            FileHandle[] levelFiles = categoryFolder.list();
                            if (levelFiles != null) {
                                for (FileHandle levelFile : levelFiles) {
                                    if (!levelFile.isDirectory()) {
                                        String name = levelFile.file().getName();
                                        Level level = new Level();
                                        level.setName(name.replace(".jpg", "").replace(".png", ""));
                                        level.setCategory(category);
                                        level.setLevelType(levelType);
                                        level.setGame(new Game(levelType.getSize()));
                                        levels.add(level);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Collections.sort(levels, new Comparator<Level>() {
            @Override
            public int compare(Level level, Level t1) {
                return Integer.compare(new Integer(level.getName()), new Integer(t1.getName()));
            }
        });
        return levels;
    }
}