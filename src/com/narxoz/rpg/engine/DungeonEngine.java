package com.narxoz.rpg.engine;

import com.narxoz.rpg.combatant.DungeonBoss;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.observer.*;

import java.util.List;

public class DungeonEngine {

    private final List<Hero> heroes;
    private final DungeonBoss boss;
    private final GameEventPublisher publisher;

    private final int MAX_ROUNDS = 50;

    public DungeonEngine(List<Hero> heroes, DungeonBoss boss, GameEventPublisher publisher) {
        this.heroes = heroes;
        this.boss = boss;
        this.publisher = publisher;
    }

    public EncounterResult run() {

        int round = 0;

        while (round < MAX_ROUNDS && boss.isAlive() && anyHeroAlive()) {
            round++;

            for (Hero hero : heroes) {
                if (!hero.isAlive()) continue;

                int damage =
                        hero.getStrategy().calculateDamage(hero.getAttackPower()) -
                                boss.getStrategy().calculateDefense(boss.getDefense());

                if (damage < 1) damage = 1;

                boss.takeDamage(damage);

                publisher.notifyObservers(
                        new GameEvent(GameEventType.ATTACK_LANDED, hero.getName(), damage)
                );

                if (!boss.isAlive()) break;
            }

            if (!boss.isAlive()) break;

            for (Hero hero : heroes) {
                if (!hero.isAlive()) continue;

                int damage =
                        boss.getStrategy().calculateDamage(boss.getAttackPower()) -
                                hero.getStrategy().calculateDefense(hero.getDefense());

                if (damage < 1) damage = 1;

                int oldHp = hero.getHp();
                hero.takeDamage(damage);

                publisher.notifyObservers(
                        new GameEvent(GameEventType.ATTACK_LANDED, boss.getName(), damage)
                );

                if (oldHp > hero.getMaxHp() * 0.3 &&
                        hero.getHp() <= hero.getMaxHp() * 0.3 &&
                        hero.getHp() > 0) {

                    publisher.notifyObservers(
                            new GameEvent(GameEventType.HERO_LOW_HP, hero.getName(), hero.getHp())
                    );
                }

                if (hero.getHp() == 0) {
                    publisher.notifyObservers(
                            new GameEvent(GameEventType.HERO_DIED, hero.getName(), 0)
                    );
                }
            }
        }

        boolean heroesWon = !boss.isAlive();
        int survivingHeroes = countAliveHeroes();

        return new EncounterResult(heroesWon, round, survivingHeroes);
    }

    private boolean anyHeroAlive() {
        for (Hero hero : heroes) {
            if (hero.isAlive()) return true;
        }
        return false;
    }

    private int countAliveHeroes() {
        int count = 0;
        for (Hero hero : heroes) {
            if (hero.isAlive()) count++;
        }
        return count;
    }
}