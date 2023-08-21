package org.tokeiito.wurmunlimited.mods.statistics;

import java.util.logging.Logger;

import com.wurmonline.server.Items;
import com.wurmonline.server.creatures.CreatureTemplateIds;
import com.wurmonline.server.creatures.Creatures;

class StatisticsReport {
	private static Logger logger = Logger.getLogger(StatisticsMod.class.getName());
	private static StatisticsReport instance;
	 	
	public static StatisticsReport getInstance() {
		if (instance == null) {
			instance = new StatisticsReport();
		}
		return instance;
	}
	
	public String getOpenTelemetryResponse() {
		String response = "";
		response += buildCreaturesStatistics();
		response += buildItemsStatistics();
		
		return response;
	}
	
	private String buildCreaturesStatistics() {
		return "# HELP wu_number_of_creatures_total The total number of creatures.\n"
				+ "# TYPE wu_number_of_creatures_total counter\n"
				+ "wu_number_of_creatures_total{type=\"agro\"} " + Creatures.getInstance().getNumberOfAgg() + "\n"
				+ "wu_number_of_creatures_total{type=\"nice\"} " + Creatures.getInstance().getNumberOfNice()+"\n"
			    + "wu_number_of_creatures_total{type=\"unique\"} " + getNumberOfUnique()+"\n";
	}
	
	private String buildItemsStatistics() {
		return "# HELP wu_number_of_items_total The total number of items.\n"
				+ "# TYPE wu_number_of_items_total counter\n"
				+ "wu_number_of_items_total " + Items.getNumberOfNormalItems() + "\n";
	}
	
	private int getNumberOfUnique() {
		int count = 0;				
		int[] uniqueIds = {
				CreatureTemplateIds.DRAGON_BLACK_CID,
				CreatureTemplateIds.DRAGON_BLUE_CID,
				CreatureTemplateIds.DRAGON_GREEN_CID,
				CreatureTemplateIds.DRAGON_RED_CID,
				CreatureTemplateIds.DRAGON_WHITE_CID,
				CreatureTemplateIds.DRAKE_BLACK_CID,
				CreatureTemplateIds.DRAKE_BLUE_CID,
				CreatureTemplateIds.DRAKE_GREEN_CID,
				CreatureTemplateIds.DRAKE_RED_CID,
				CreatureTemplateIds.DRAKE_WHITE_CID,
				CreatureTemplateIds.CYCLOPS_CID,
				CreatureTemplateIds.FOREST_GIANT_CID,
				CreatureTemplateIds.GOBLIN_LEADER_CID,
				CreatureTemplateIds.TROLL_KING_CID
				};

		for(int id: uniqueIds) {
			count += Creatures.getInstance().getNumberOfCreaturesWithTemplate(id);
		}
		
		return count;
	}
}
