package com.narxoz.rpg.observer;

import com.narxoz.rpg.combatant.Hero;

import java.util.List;

public class HeroStatusMonitor implements GameObserver {

    private final List<Hero> heroes;

    public HeroStatusMonitor(List<Hero> heroes) {
        this.heroes = heroes;
    }

    public void onEvent(GameEvent event) {

        if (event.getType() == GameEventType.HERO_LOW_HP ||
                event.getType() == GameEventType.HERO_DIED) {

            System.out.println("=== HERO STATUS ===");

            for (Hero hero : heroes) {
                String status = hero.isAlive() ? "Alive" : "Dead";
                System.out.println(hero.getName() + " | HP: " +
                        hero.getHp() + "/" + hero.getMaxHp() +
                        " | " + status);
            }

            System.out.println("===================");
        }
    }
}