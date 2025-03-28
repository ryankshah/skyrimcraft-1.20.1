package com.ryankshah.skyrimcraft.data.lang;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.data.block.BlockData;
import com.ryankshah.skyrimcraft.data.item.ItemData;
import com.ryankshah.skyrimcraft.registry.CreativeTabRegistry;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class SkyrimcraftLanguageProvider extends LanguageProvider
{
    public SkyrimcraftLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);

    }
    @Override
    protected void addTranslations() {
        add(CreativeTabRegistry.SKYRIMCRAFT_INGREDIENT_TAB_TITLE, "Skyrimcraft Ingredients");
        add(CreativeTabRegistry.SKYRIMCRAFT_BLOCK_TAB_TITLE, "Skyrimcraft Blocks");
        add(CreativeTabRegistry.SKYRIMCRAFT_FOOD_TAB_TITLE, "Skyrimcraft Food");
        add(CreativeTabRegistry.SKYRIMCRAFT_COMBAT_TAB_TITLE, "Skyrimcraft Combat");
        add(CreativeTabRegistry.SKYRIMCRAFT_JEWELLERY_TAB_TITLE, "Skyrimcraft Jewellery");
        add(CreativeTabRegistry.SKYRIMCRAFT_ALL_TITLE, "Skyrimcraft");
        add(CreativeTabRegistry.SKYRIMCRAFT_MAGIC_TITLE, "Skyrimcraft Magic");
        add(CreativeTabRegistry.SKYRIMCRAFT_SKILLING_TITLE, "Skyrimcraft Skilling");

        add("effect.skyrimcraft.ethereal", "Ethereal");
        add("effect.skyrimcraft.frozen", "Frozen");
        add("effect.skyrimcraft.magicka_regen", "Magicka Regen");
        add("effect.skyrimcraft.spectral", "Spectral");
        add("effect.skyrimcraft.undead_flee", "Undead Flee");
        add("effect.skyrimcraft.dismay", "Dismay");
        add("effect.skyrimcraft.water_walking", "Water Walking");
        add("effect.skyrimcraft.paralysis", "Paralysis");
        add("effect.skyrimcraft.cure_disease", "Cure Disease");
        add("effect.skyrimcraft.cure_poison", "Cure Poison");
        add("effect.skyrimcraft.flame_cloak", "Flame Cloak");
        add("effect.skyrimcraft.hist", "Hist");
        add("effect.skyrimcraft.adrenaline_rush", "Adrenaline Rush");
        add("effect.skyrimcraft.battle_cry", "Battle Cry");
        add("effect.skyrimcraft.calm", "Calm");
        add("effect.skyrimcraft.armor_rating", "Increased Armor Rating");


        // Spellbook
        add("spellbook.tooltip.name", "Grants you use of the %s spell!");
        add("spellbook.tooltip.difficulty", "Spell Difficulty: %s");
        add("spellbook.learn", "You have just learnt %s!");
        add("spellbook.known", "You already know this spell!");

        add("skillbook.tooltip", "Improves %s by one level");
        add("skillbook.learn", "You have just learnt how to improve %s!");
        add("skillbook.fail", "Something has gone wrong with this Skill Book");

        // Shout block
        add("shoutblock.allshoutsknown", "You have no more shouts to learn!");
        add("shoutblock.used", "The power which once resonated within this wall has since departed...");
        add("skyrimcraft.shoutblock.used", "The power which once resonated within this wall has since departed...");

        // Info
        add("skyrimcraft.spell.noselect", "No spell/shout selected");
        add("skyrimcraft.menu.skills", "Skills");
        add("skyrimcraft.menu.map", "Map");
        add("skyrimcraft.menu.quests", "Quests");
        add("skyrimcraft.menu.items", "Items");
        add("skyrimcraft.menu.magic", "Magic");
        add("skyrimcraft.menu.option.unavailable", "This option is currently unavailable!");
        add("skyrimcraft.menu.option.invalid", "Invalid Option!");
        add("skyrimcraft.menu.option.magic.none", "You have not yet learned any spells/shouts!");
        add("skyrimcraft.shout.cooldown", "This shout is currently on cooldown!");

        add("key.categories.skyrimcraft", "Skyrimcraft Keys");
        add("key.skyrimcraft.toggle_menu", "Toggle Menu");
        add("key.skyrimcraft.toggle_spell_1", "Cast Spell 1");
        add("key.skyrimcraft.toggle_spell_2", "Cast Spell 2");
        add("key.skyrimcraft.toggle_pickpocket", "Toggle Pickpocket");
        add("key.skyrimcraft.menu.enter", "Menu Enter");
        add("key.skyrimcraft.menu.north", "Menu North");
        add("key.skyrimcraft.menu.south", "Menu South");
        add("key.skyrimcraft.menu.east", "Menu East");
        add("key.skyrimcraft.menu.west", "Menu West");
        add("key.skyrimcraft.menu_button_1.click", "Menu Mouse Button 1");


        // Skills
        add("skill.pickpocket.fail", "You fail to pick the %s's pockets!");
        add("skill.pickpocket.success", "You successfully pick the %s's pockets and get some loot!");

        // Mobs
        add("entity.minecraft.villager.skyrimcraft.skyrim_blacksmith", "Blacksmith");
        add("entity.minecraft.villager.skyrimcraft.alchemist", "Alchemist");
        add("entity.minecraft.villager.skyrimcraft.cook", "Food Merchant");
        add("entity.skyrimcraft.sabre_cat", "Sabre Cat");
        add("entity.skyrimcraft.vale_sabre_cat", "Vale Sabre Cat");
        add("entity.skyrimcraft.skeever", "Skeever");
        add("entity.skyrimcraft.venomfang_skeever", "Venomfang Skeever");
        add("entity.skyrimcraft.vampire", "Vampire");
        add("entity.skyrimcraft.snowy_sabre_cat", "Snowy Sabre Cat");
        add("entity.skyrimcraft.giant", "Giant");
        add("entity.skyrimcraft.mammoth", "Mammoth");
        add("entity.skyrimcraft.blue_butterfly", "Blue Butterfly");
        add("entity.skyrimcraft.monarch_butterfly", "Monarch Butterfly");
        add("entity.skyrimcraft.blue_dartwing", "Blue Dartwing");
        add("entity.skyrimcraft.orange_dartwing", "Orange Dartwing");
        add("entity.skyrimcraft.lunar_moth", "Lunar Moth");
        add("entity.skyrimcraft.torchbug", "Torchbug");
        add("entity.skyrimcraft.dragon", "Dragon");
        add("entity.skyrimcraft.dwarven_spider", "Dwarven Spider");
        add("entity.skyrimcraft.draugr", "Draugr");
        add("entity.skyrimcraft.slaughterfish", "Slaughterfish");

        add("entity.skyrimcraft.abecean_longfin", "Abecean Longfin");
        add("entity.skyrimcraft.cyrodilic_spadetail", "Cyrodilic Spadetail");

        add("skyrimcraft.spell.no_magicka", "You have no magicka!");

        add("entity.skyrimcraft.khajiit", "Khajiit");
        add("entity.skyrimcraft.khajiit.armorer", "Khajiit Armorer");
        add("entity.skyrimcraft.khajiit.butcher", "Khajiit Butcher");
        add("entity.skyrimcraft.khajiit.cartographer", "Khajiit Cartographer");
        add("entity.skyrimcraft.khajiit.cleric", "Khajiit Cleric");
        add("entity.skyrimcraft.khajiit.farmer", "Khajiit Farmer");
        add("entity.skyrimcraft.khajiit.fisherman", "Khajiit Fisherman");
        add("entity.skyrimcraft.khajiit.fletcher", "Khajiit Fletcher");
        add("entity.skyrimcraft.khajiit.leatherworker", "Khajiit Leatherworker");
        add("entity.skyrimcraft.khajiit.librarian", "Khajiit Librarian");
        add("entity.skyrimcraft.khajiit.mason", "Khajiit Mason");
        add("entity.skyrimcraft.khajiit.nitwit", "Khajiit Nitwit");
        add("entity.skyrimcraft.khajiit.none", "Khajiit");
        add("entity.skyrimcraft.khajiit.shepherd", "Khajiit Shepherd");
        add("entity.skyrimcraft.khajiit.toolsmith", "Khajiit Toolsmith");
        add("entity.skyrimcraft.khajiit.weaponsmith", "Khajiit Weaponsmith");

        add("entity.skyrimcraft.falmer", "Falmer");
        add("entity.skyrimcraft.falmer.armorer", "Falmer Armorer");
        add("entity.skyrimcraft.falmer.butcher", "Falmer Butcher");
        add("entity.skyrimcraft.falmer.cartographer", "Falmer Cartographer");
        add("entity.skyrimcraft.falmer.cleric", "Falmer Cleric");
        add("entity.skyrimcraft.falmer.farmer", "Falmer Farmer");
        add("entity.skyrimcraft.falmer.fisherman", "Falmer Fisherman");
        add("entity.skyrimcraft.falmer.fletcher", "Falmer Fletcher");
        add("entity.skyrimcraft.falmer.leatherworker", "Falmer Leatherworker");
        add("entity.skyrimcraft.falmer.librarian", "Falmer Librarian");
        add("entity.skyrimcraft.falmer.mason", "Falmer Mason");
        add("entity.skyrimcraft.falmer.nitwit", "Falmer Nitwit");
        add("entity.skyrimcraft.falmer.none", "Falmer");
        add("entity.skyrimcraft.falmer.shepherd", "Falmer Shepherd");
        add("entity.skyrimcraft.falmer.toolsmith", "Falmer Toolsmith");
        add("entity.skyrimcraft.falmer.weaponsmith", "Falmer Weaponsmith");

        // Damage Source
        add("death.attack.death.skyrimcraft.conjuredfamiliar", "Your conjured %1$s familiar has vanished!");
        add("skyrimcraft.conjuredfamiliar.exists", "You have already conjured a familiar!");

        add("death.attack.death.skyrimcraft.conjuredzombie", "Your conjured zombie has vanished!");
        add("skyrimcraft.conjuredzombie.exists", "You have already conjured a zombie!");

        ItemData.addItemTranslations(this);
        BlockData.addBlockTranslations(this);

        add("curios.identifier.circlet", "Circlet");

        // Quests
        add("advancements.skyrimcraft.quest.root.title", "Skyrimcraft");
        add("advancements.skyrimcraft.quest.root.description", "Skyrimcraft Quests");
        add("advancements.skyrimcraft.quest.dragon_slayer.title", "Dragon Slayer");
        add("advancements.skyrimcraft.quest.dragon_slayer.description", "Kill your first Skyrim Dragon");
        add("advancements.skyrimcraft.quest.ebony_dreams.title", "Ebony Dreams");
        add("advancements.skyrimcraft.quest.ebony_dreams.description", "Smelt an Ebony Ingot");
        add("advancements.skyrimcraft.quest.goodbye_webs.title", "Goodbye, Webs");
        add("advancements.skyrimcraft.quest.goodbye_webs.description", "Kill a Spider");

        add("sound." + Constants.MODID + ".swamp_ambient", "Skyrim Swamp Ambience");
        add("sound." + Constants.MODID + ".skyrim_caves", "Skyrim Caves Ambience");
        add("sound." + Constants.MODID + ".fire_spell_cast", "Fire Spell Cast");
        add("sound." + Constants.MODID + ".unrelenting_force_cast", "Unrelenting Force");
        add("sound." + Constants.MODID + ".freeze_spell_hit", "Freeze Spell Hit");
        add("sound." + Constants.MODID + ".draugr_grunt", "Draugr Grunts");
        add("sound." + Constants.MODID + ".draugr_attack", "Draugr Attacks");


        add("language.skyrimcraft.translation_author", "<Your name here>");

        add("itemGroup.skyrimcraft", "Locks");

        add("item.skyrimcraft.spring", "Spring");
        add("item.skyrimcraft.wood_lock_mechanism", "Wood Lock Mechanism");
        add("item.skyrimcraft.iron_lock_mechanism", "Iron Lock Mechanism");
        add("item.skyrimcraft.steel_lock_mechanism", "Steel Lock Mechanism");
        add("item.skyrimcraft.wood_lock", "Wood Lock");
        add("item.skyrimcraft.iron_lock", "Iron Lock");
        add("item.skyrimcraft.steel_lock", "Steel Lock");
        add("item.skyrimcraft.gold_lock", "Gold Plated Lock");
        add("item.skyrimcraft.diamond_lock", "Diamond Plated Lock");
        add("item.skyrimcraft.wood_lock_pick", "Bobby Pin Lock Pick");
        add("item.skyrimcraft.iron_lock_pick", "Iron Lock Pick");
        add("item.skyrimcraft.steel_lock_pick", "Steel Lock Pick");
        add("item.skyrimcraft.gold_lock_pick", "Gold Lock Pick");
        add("item.skyrimcraft.diamond_lock_pick", "Diamond Lock Pick");
        add("item.skyrimcraft.key", "Key");
        add("item.skyrimcraft.master_key", "Master Key");
        add("item.skyrimcraft.key_blank", "Key Blank");
        add("item.skyrimcraft.key_ring", "Key Ring");

        add("enchantment.skyrimcraft.shocking", "Shocking");
        add("enchantment.skyrimcraft.shocking.desc", "Shocks thieves that fail to pick the lock");
        add("enchantment.skyrimcraft.sturdy", "Sturdy");
        add("enchantment.skyrimcraft.sturdy.desc", "Reduces the strength of lock picks");
        add("enchantment.skyrimcraft.complexity", "Complexity");
        add("enchantment.skyrimcraft.complexity.desc", "Makes the lock too complex to pick with lower tier lock picks");

        add("death.attack.skyrimcraft.shock", "%1$s was electrocuted after failing to pick an enchanted lock");
        add("death.attack.skyrimcraft.shock.player", "%1$s was electrocuted by an enchanted lock whilst simultaneously picking it and fighting off %2$s");

        add("skyrimcraft.gui.lockpicking.title", "Lock picking");
        add("skyrimcraft.gui.lockpicking.open", "Press `ESC` to open");

        add("skyrimcraft.subtitle.key_ring", "Keys jingle");
        add("skyrimcraft.subtitle.lock.close", "Lock clicks");
        add("skyrimcraft.subtitle.lock.open", "Key turned in lock");
        add("skyrimcraft.subtitle.lock.rattle", "Lock rattles");
        add("skyrimcraft.subtitle.pin.fail", "Pin clacks hollowly");
        add("skyrimcraft.subtitle.pin.match", "Pin clicks lightly");
        add("skyrimcraft.subtitle.shock", "Sudden electric zap");

        add("skyrimcraft.tooltip.id", "ID, %s");
        add("skyrimcraft.tooltip.length",  "Length, %s");
        add("skyrimcraft.tooltip.strength", "Strength, %s");

        add("skyrimcraft.status.locked", "This block is locked");
        add("skyrimcraft.status.too_complex", "This lock is too complex for this lock pick");
    }
}