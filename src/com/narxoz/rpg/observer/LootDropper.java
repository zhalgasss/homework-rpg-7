package com.narxoz.rpg.observer;

import java.util.Random;

public class LootDropper implements GameObserver {

    private final Random random = new Random();

    private final String[] phaseLoot = {
            "Iron Sword",
            "Magic Ring",
            "Healing Potion"
    };

    private final String[] bossLoot = {
            "Legendary Armor",
            "Dragon Slayer Blade",
            "Epic Staff"
    };

    public void onEvent(GameEvent event) {

        if (event.getType() == GameEventType.BOSS_PHASE_CHANGED) {
            String loot = phaseLoot[random.nextInt(phaseLoot.length)];
            System.out.println("Loot dropped (phase): " + loot);
        }

        if (event.getType() == GameEventType.BOSS_DEFEATED) {
            String loot = bossLoot[random.nextInt(bossLoot.length)];
            System.out.println("Loot dropped (boss): " + loot);
        }
    }
}