package org.tokeiito.wurmunlimited.mods.statistics;

import org.tokeiito.wurmunlimited.mods.statistics.cache.InMemoryCache;

import com.wurmonline.server.Items;
import com.wurmonline.server.creatures.Creatures;

class StatisticsReport {
	private static StatisticsReport instance;
	
	private final InMemoryCache<String, Integer> cache = new InMemoryCache<>(5, 2);
	 	
	public static StatisticsReport getInstance() {
		if (instance == null) {
			instance = new StatisticsReport();
		}
		return instance;
	}

	public void configure(long cacheTimeOut, long cacheCheckInterval) {
		this.cache.configure(cacheTimeOut, cacheCheckInterval);
	}		
	
	public String getOpenTelemetryResponse() {
		String response = "";
		response += buildCreaturesStatisticsTotal();
		response += buildItemsStatistics();
		response += buildCreaturesStatisticsByTypeTotal();
		
		return response;
	}
	
	private String buildCreaturesStatisticsTotal() {
		return "# HELP wu_number_of_creatures_total The total number of creatures.\n"
				+ "# TYPE wu_number_of_creatures_total counter\n"
				+ "wu_number_of_creatures_total{type=\"agro\"} " + this.getNumberOfAggroCreatures() + "\n"
				+ "wu_number_of_creatures_total{type=\"nice\"} " + this.getNumberOfNiceCreatures()+"\n"
			    + "wu_number_of_creatures_total{type=\"unique\"} " + this.getNumberOfUnique()+"\n";
	}
	
	private String buildCreaturesStatisticsByTypeTotal() {
		String returnString = "# HELP wu_number_of_specific_creature_total The total number of creatures.\n"
				+ "# TYPE wu_number_of_specific_creature_total counter\n";
		for (CreatureTemplateIds cti: CreatureTemplateIds.values()) {
			returnString += "wu_number_of_specific_creature_total{name=\""+cti.name+"\"} " + this.getSpecificCreatureStatistics(cti.id) + "\n";
		}
		
		return returnString;
	}
	
	private int getSpecificCreatureStatistics(int id) {
		Integer cachedValue = cache.get("creature_" + id);
		
		if (cachedValue != null) {
			return cachedValue;
		} else {
			Integer value = Creatures.getInstance().getNumberOfCreaturesWithTemplate(id);
			cache.put("creature_" + id, value);
			
			return value;
		}
	}

	private String buildItemsStatistics() {
		return "# HELP wu_number_of_items_total The total number of items.\n"
				+ "# TYPE wu_number_of_items_total counter\n"
				+ "wu_number_of_items_total " + this.getNumberOfNormalItems() + "\n";
	}
	
	private int getNumberOfNormalItems() {
		Integer cachedValue = cache.get("items_normal");

		if(cachedValue != null) {
			return cachedValue;
		} else {
			Integer value = Items.getNumberOfNormalItems();
			cache.put("items_normal", value);

			return value;
		}
	}

	private int getNumberOfUnique() {
		Integer cachedValue = cache.get("creatures_unique");

		if(cachedValue != null) {
			return cachedValue;
		} else {

			int count = 0;				
			int[] uniqueIds = {
					CreatureTemplateIds.DRAGON_BLACK_CID.id,
					CreatureTemplateIds.DRAGON_BLUE_CID.id,
					CreatureTemplateIds.DRAGON_GREEN_CID.id,
					CreatureTemplateIds.DRAGON_RED_CID.id,
					CreatureTemplateIds.DRAGON_WHITE_CID.id,
					CreatureTemplateIds.DRAKE_BLACK_CID.id,
					CreatureTemplateIds.DRAKE_BLUE_CID.id,
					CreatureTemplateIds.DRAKE_GREEN_CID.id,
					CreatureTemplateIds.DRAKE_RED_CID.id,
					CreatureTemplateIds.DRAKE_WHITE_CID.id,
					CreatureTemplateIds.CYCLOPS_CID.id,
					CreatureTemplateIds.FOREST_GIANT_CID.id,
					CreatureTemplateIds.GOBLIN_LEADER_CID.id,
					CreatureTemplateIds.TROLL_KING_CID.id
					};

			for(int id: uniqueIds) {
				count += Creatures.getInstance().getNumberOfCreaturesWithTemplate(id);
			}
			cache.put("creatures_unique", count);

			return count;
		}
	}

	private int getNumberOfAggroCreatures() {
		Integer cachedValue = cache.get("creatures_agro");

		if (cachedValue != null) {
			return cachedValue;
		} else {
			Integer value = Creatures.getInstance().getNumberOfAgg();
			cache.put("creatures_agro", value);
			return value;
		}
	}

	private int getNumberOfNiceCreatures() {
		Integer cachedValue = cache.get("creatures_nice");

		if (cachedValue != null) {
			return cachedValue;
		} else {
			Integer value = Creatures.getInstance().getNumberOfNice();
			cache.put("creatures_nice", value);
			return value;
		}
	}
}
