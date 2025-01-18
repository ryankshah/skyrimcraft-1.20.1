package com.ryankshah.skyrimcraft.character.skill;

import java.util.AbstractMap;
import java.util.List;

public class EmptySkill extends Skill
{
    @Override
    public int getID() {
        return 10000;
    }

    @Override
    public String getName() {
        return "Empty Spell";
    }

    @Override
    public AbstractMap.SimpleEntry<Integer, Integer> getIconUV() {
        return new AbstractMap.SimpleEntry<>(0,0);
    }

    @Override
    public String getDescription() {
        return "Empty Spell";
    }

    @Override
    public float getSkillImproveMultiplier() {
        return 0;
    }

    @Override
    public float getSkillUseMultiplier() {
        return 0;
    }

    @Override
    public int getSkillImproveOffset() {
        return 0;
    }

    @Override
    public int getSkillUseOffset() {
        return 0;
    }

    @Override
    public List<Perk> getSkillPerks() {
        return List.of();
    }
}
