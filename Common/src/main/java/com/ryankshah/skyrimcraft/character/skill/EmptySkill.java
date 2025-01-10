package com.ryankshah.skyrimcraft.character.skill;

import java.util.AbstractMap;
import java.util.List;

public class EmptySkill extends Skill
{
    @Override
    public AbstractMap.SimpleEntry<Integer, Integer> getIconUV() {
        return null;
    }

    @Override
    public String getDescription() {
        return "";
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
