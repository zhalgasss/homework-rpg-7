package com.narxoz.rpg.combatant;

import com.narxoz.rpg.observer.*;
import com.narxoz.rpg.strategy.CombatStrategy;

public class DungeonBoss implements GameObserver {

    private final String name;
    private int hp;
    private final int maxHp;
    private final int attackPower;
    private final int defense;

    private int phase = 1;

    private CombatStrategy strategy;

    private final GameEventPublisher publisher;

    public DungeonBoss(String name, int hp, int attackPower, int defense,
                       CombatStrategy initialStrategy,
                       GameEventPublisher publisher) {

        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.strategy = initialStrategy;
        this.publisher = publisher;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttackPower() { return attackPower; }
    public int getDefense() { return defense; }
    public CombatStrategy getStrategy() { return strategy; }
    public boolean isAlive() { return hp > 0; }

    public void takeDamage(int amount) {

        int oldHp = hp;
        hp = Math.max(0, hp - amount);

        checkPhaseTransition(oldHp, hp);

        if (hp == 0) {
            publisher.notifyObservers(
                    new GameEvent(GameEventType.BOSS_DEFEATED, name, 0)
            );
        }
    }

    private void checkPhaseTransition(int oldHp, int newHp) {

        double oldPercent = (double) oldHp / maxHp;
        double newPercent = (double) newHp / maxHp;

        if (oldPercent > 0.6 && newPercent <= 0.6) {
            publisher.notifyObservers(
                    new GameEvent(GameEventType.BOSS_PHASE_CHANGED, name, 2)
            );
        }

        if (oldPercent > 0.3 && newPercent <= 0.3) {
            publisher.notifyObservers(
                    new GameEvent(GameEventType.BOSS_PHASE_CHANGED, name, 3)
            );
        }
    }

    public void onEvent(GameEvent event) {

        if (event.getType() == GameEventType.BOSS_PHASE_CHANGED &&
                event.getSourceName().equals(name)) {

            int newPhase = event.getValue();

            if (newPhase == 2 && phase != 2) {
                phase = 2;
                strategy = new com.narxoz.rpg.strategy.BossPhase2Strategy();
                System.out.println(name + " switched to Phase 2 strategy");
            }

            if (newPhase == 3 && phase != 3) {
                phase = 3;
                strategy = new com.narxoz.rpg.strategy.BossPhase3Strategy();
                System.out.println(name + " switched to Phase 3 strategy");
            }
        }
    }
}