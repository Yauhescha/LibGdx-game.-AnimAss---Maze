package com.hescha.game.maz.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LevelType implements Serializable {
    FOR_KIDS(10),
    EASY(20),
    MEDIUM(30),
    HARD(40);

    public final int size;
}
