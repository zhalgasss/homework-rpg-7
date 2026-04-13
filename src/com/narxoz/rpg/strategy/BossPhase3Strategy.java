package com.narxoz.rpg.strategy;

public class BossPhase3Strategy implements CombatStrategy {

    public int calculateDamage(int basePower) {
        return (int)(basePower * 1.8);
    }

    public int calculateDefense(int baseDefense) {
        return (int)(baseDefense * 0.5);
    }

    public String getName() {
        return "Boss Phase 3";
    }
}