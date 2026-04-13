package com.narxoz.rpg.observer;

public class BattleLogger implements GameObserver {

    public void onEvent(GameEvent event) {
        System.out.println("[LOG] " + event.getType() +
                " | Source: " + event.getSourceName() +
                " | Value: " + event.getValue());
    }
}