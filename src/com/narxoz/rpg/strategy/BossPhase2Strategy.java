package com.narxoz.rpg.strategy;

public class BossPhase2Strategy implements CombatStrategy {

    public int calculateDamage(int basePower) {
        return (int)(basePower * 1.4);
    }

    public int calculateDefense(int baseDefense) {
        return (int)(baseDefense * 0.9);
    }

    public String getName() {
        return "Boss Phase 2";
    }
}