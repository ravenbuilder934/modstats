package com.modstats.main;

import net.minecraftforge.common.config.Config;

import static com.modstats.main.util.Constants.MOD_ID;

@Config(modid = MOD_ID)
public class Configurations
{
    @Config.Comment("Should the Block placement count as Mod Activity? [Default: true]")
    public static boolean blockPlaceEvent = true;

    @Config.Comment("Should the Block breaking count as Mod Activity? [Default: true]")
    public static boolean blockBreakEvent = true;

    @Config.Comment("Should the Block harvesting count as Mod Activity? [Default: true]")
    public static boolean blockHarvestEvent = true;

    @Config.Comment("Should the Right click block count as Mod Activity? [Default: true]")
    public static boolean rightClickBlockEvent = true;

    @Config.Comment("Should the crop growth count as Mod Activity? [Default: false]")
    public static boolean blockGrowEvent = false;

    @Config.Comment("Should the item crafting count as Mod Activity? [Default: true]")
    public static boolean itemCraft = true;

    @Config.Comment("Should the item smelting count as Mod Activity? [Default: true]")
    public static boolean itemSmelt = true;

    @Config.Comment("Should the item right click count as Mod Activity? [Default: true]")
    public static boolean rightClickItem = true;

    @Config.Comment("Should the item toss count as Mod Activity? [Default: true]")
    public static boolean itemToss = true;

    @Config.Comment("Should the item pickup count as Mod Activity? [Default: true]")
    public static boolean itemPickUp = true;

    @Config.Comment("Should the entity interact count as Mod Activity? [Default: true]")
    public static boolean entityInteract = true;

    @Config.Comment("Should the entity attack count as Mod Activity? [Default: true]")
    public static boolean entityAttack = true;

    @Config.Comment("Should the item enchantment count as Mod Activity? [Default: true]")
    public static boolean itemEnchantment = true;

    @Config.Comment("Should the item fished count as Mod Activity? [Default: true]")
    public static boolean itemFished = true;

    @Config.Comment("Should the bucket fill count as Mod Activity? [Default: true]")
    public static boolean bucketFilled = true;

    @Config.Comment("Should the arrow nock count as Mod Activity? [Default: false]")
    public static boolean arrowNock = false;

    @Config.Comment("Should the arrow loose count as Mod Activity? [Default: false]")
    public static boolean arrowLoose = false;

    @Config.Comment("Should the anvil repair count as Mod Activity? [Default: true]")
    public static boolean anvilRepair = true;

    @Config.Comment("Should mounting entities count as Mod Activity? [Default: true]")
    public static boolean entityMount = true;
}
