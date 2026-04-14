package com.narxoz.rpg;

import com.narxoz.rpg.combatant.DungeonBoss;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.engine.DungeonEngine;
import com.narxoz.rpg.engine.EncounterResult;
import com.narxoz.rpg.observer.*;
import com.narxoz.rpg.strategy.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        GameEventPublisher publisher = new GameEventPublisher();

        Hero hero1 = new Hero("Warrior", 120, 40, 15, new AggressiveStrategy());
        Hero hero2 = new Hero("Tank", 150, 25, 25, new DefensiveStrategy());
        Hero hero3 = new Hero("Rogue", 100, 35, 10, new BalancedStrategy());

        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero1);
        heroes.add(hero2);
        heroes.add(hero3);

        DungeonBoss boss = new DungeonBoss(
                "Dragon",
                400,
                30,
                10,
                new BossPhase1Strategy(),
                publisher
        );

        BattleLogger logger = new BattleLogger();
        AchievementTracker achievements = new AchievementTracker();
        PartySupport support = new PartySupport(heroes);
        HeroStatusMonitor monitor = new HeroStatusMonitor(heroes);
        LootDropper loot = new LootDropper();

        publisher.addObserver(logger);
        publisher.addObserver(achievements);
        publisher.addObserver(support);
        publisher.addObserver(monitor);
        publisher.addObserver(loot);
        publisher.addObserver(boss);

        DungeonEngine engine = new DungeonEngine(heroes, boss, publisher);

        EncounterResult result = engine.run();

        System.out.println("\n=== RESULT ===");
        System.out.println("Heroes won: " + result.isHeroesWon());
        System.out.println("Rounds: " + result.getRoundsPlayed());
        System.out.println("Survivors: " + result.getSurvivingHeroes());
    }
}