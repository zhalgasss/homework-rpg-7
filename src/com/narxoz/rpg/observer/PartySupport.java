package com.narxoz.rpg.observer;

import com.narxoz.rpg.combatant.Hero;

import java.util.List;
import java.util.Random;

public class PartySupport implements GameObserver {

    private final List<Hero> heroes;
    private final Random random = new Random();

    public PartySupport(List<Hero> heroes) {
        this.heroes = heroes;
    }

    public void onEvent(GameEvent event) {

        if (event.getType() == GameEventType.HERO_LOW_HP) {

            Hero target = null;

            int attempts = 0;
            while (attempts < 10) {
                Hero h = heroes.get(random.nextInt(heroes.size()));
                if (h.isAlive()) {
                    target = h;
                    break;
                }
                attempts++;
            }

            if (target != null) {
                target.heal(20);
                System.out.println("PartySupport healed " + target.getName() + " for 20 HP");
            }
        }
    }
}