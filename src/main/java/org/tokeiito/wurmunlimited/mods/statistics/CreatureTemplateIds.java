package org.tokeiito.wurmunlimited.mods.statistics;

import java.util.HashMap;
import java.util.Map;

public enum CreatureTemplateIds {
		COW_BROWN_CID(3, "Cow Brown"),
		WOLF_BLACK_CID(10, "Wolf"),
		TROLL_CID(11, "Troll"),
		BEAR_BROWN_CID(12, "Bear brown"),
		RAT_LARGE_CID(13, "Rat"),
		LION_MOUNTAIN_CID(14, "Lion"),
		CAT_WILD_CID(15, "Cat"),
		DRAGON_RED_CID(16, "Dragon Red"),
		DRAKE_GREEN_CID(17, "Drake Green"),
		DRAKE_BLACK_CID(18, "Drake Black"),
		DRAKE_WHITE_CID(19, "Drake White"),
		FOREST_GIANT_CID(20, "Forest Giant"),
		UNICORN_CID(21, "Unicorn"),
		CYCLOPS_CID(22, "Cyclops"),
		GOBLIN_CID(23, "Goblin"),
		SPIDER_CID(25, "Spider"),
		GOBLIN_LEADER_CID(26, "Goblin Leader"),
		TROLL_KING_CID(27, "Troll King"),
		BOAR_FO_CID(37, "Boar"),
		ANACONDA_CID(38, "Anaconda"),
		GORILLA_MAGRANON_CID(39, "Gorilla"),
		HYENA_LIBILA_CID(40, "Hyena"),
		BEAR_BLACK_CID(42, "Bear Black"),
		CAVE_BUG_CID(43, "Cave Bug"),
		PIG_CID(44, "Pig"),
		HEN_CID(45, "Hen"),
		CHICKEN_CID(48, "Chicken"),
		BULL_CID(49, "Bull"),
		CALF_CID(50, "Calf"),
		DOG_CID(51, "Dog"),
		ROOSTER_CID(52, "Rooster"),
		DEER_CID(54, "Deer"),
		PHEASANT_CID(55, "Pheasant"),
		LAVA_SPIDER_CID(56, "Lava Spider"),
		CROCODILE_CID(58, "Crocodile"),
		SCORPION_CID(59, "Scorpion"),
		HORSE_CID(64, "Horse"),
		FOAL_CID(65, "Foal"),
		SEA_SERPENT_CID(70, "Sea Serpent"),
		SHARK_HUGE_CID(71, "Shark"),
		DEMON_SOL_CID(72, "Demon Sol"),
		DEATHCRAWLER_MINION_CID(73, "Death crawler"),
		SPAWN_UTTACHA_CID(74, "Spawn Uttah"),
		SON_OF_NOGUMP_CID(75, "Son og Nogump"),
		BISON_CID(82, "Bison"),
		HELL_HORSE_CID(83, "Hell Horse"),
		HELL_HOUND_CID(84, "Hell Hound"),
		HELL_SCORPION_CID(85, "Hell Scorpion"),
		WORG_CID(86, "Worg"),
		DRAGON_BLACK_CID(89, "Dragon Black"),
		DRAGON_GREEN_CID(90, "Dragon Green"),
		DRAGON_BLUE_CID(91, "Dragon Blue"),
		DRAGON_WHITE_CID(92, "Dragon White"),
		SEAL_CID(93, "Seal"),
		TORTOISE_CID(94, "Tortoise"),
		CRAB_CID(95, "Crab"),
		SHEEP_CID(96, "Sheep"),
		BLUE_WHALE_CID(97, "Whale"),
		SEAL_CUB_CID(98, "Seal Cub"),
		DOLPHIN_CID(99, "Dolphin"),
		OCTOPUS_CID(100, "Octopus"),
		LAMB_CID(101, "Lamb"),
		RAM_CID(102, "Ram"),
		DRAKE_RED_CID(103, "Drake Red"),
		DRAKE_BLUE_CID(104, "Drake Blue"),
		SPIDER_FOG_CID(105, "Spider Fog"),
		RIFT_JACKAL_ONE_CID(106, "Jackal One"),
		RIFT_JACKAL_TWO_CID(107, "Jackal Two"),
		RIFT_JACKAL_THREE_CID(108, "Jackal Three"),
		RIFT_JACKAL_FOUR_CID(109, "Jackal Four"),
		RIFT_JACKAL_CASTER_CID(110, "Jackal Caster"),
		RIFT_OGRE_MAGE_CID(111, "Ogre Mage"),
		RIFT_JACKAL_SUMMONER_CID(112, "Jackal Summoner"),
		HELL_FOAL_CID(117, "Hell Foal"),
		UNICORN_FOAL_CID(118, "Unicorn Foal"),
		FISH_CID(119, "Fish");
		
		public final int id;
		public final String name;
		
		private CreatureTemplateIds(int id, String name) {
			this.id = id;
			this.name = name;
		}
		
		private static final Map<String, CreatureTemplateIds> BY_NAME = new HashMap<>();
		private static final Map<Integer, CreatureTemplateIds> BY_ID = new HashMap<>();
		
		static {
			for (CreatureTemplateIds cti: values()) {
				BY_NAME.put(cti.name, cti);
				BY_ID.put(cti.id, cti);
			}
		}
		
		public static CreatureTemplateIds byName(String name) {
			return BY_NAME.get(name);
		}
		
		public static CreatureTemplateIds byId(int id) {
			return BY_ID.get(id);
		}
}
