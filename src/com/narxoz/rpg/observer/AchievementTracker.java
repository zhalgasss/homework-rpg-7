package com.narxoz.rpg.observer;

public class AchievementTracker implements GameObserver {

    private int attackCount = 0;
    private boolean firstBloodUnlocked = false;

    public void onEvent(GameEvent event) {

        if (event.getType() == GameEventType.ATTACK_LANDED) {
            attackCount++;

            if (!firstBloodUnlocked) {
                System.out.println("Achievement unlocked: First Blood");
                firstBloodUnlocked = true;
            }

            if (attackCount == 10) {
                System.out.println("Achievement unlocked: Relentless");
            }
        }

        if (event.getType() == GameEventType.HERO_DIED) {
            System.out.println("Achievement unlocked: Fallen Hero");
        }

        if (event.getType() == GameEventType.BOSS_DEFEATED) {
            System.out.println("Achievement unlocked: Boss Slayer");
        }
    }
}