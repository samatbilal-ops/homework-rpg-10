package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.council.CouncilEngine;
import com.narxoz.rpg.council.CouncilRunResult;
import com.narxoz.rpg.guild.Captain;
import com.narxoz.rpg.guild.GuildHall;
import com.narxoz.rpg.guild.Healer;
import com.narxoz.rpg.guild.Loremaster;
import com.narxoz.rpg.guild.Quartermaster;
import com.narxoz.rpg.guild.Scout;
import com.narxoz.rpg.quest.Quest;
import com.narxoz.rpg.quest.QuestIterator;
import com.narxoz.rpg.quest.QuestLog;
import com.narxoz.rpg.quest.QuestPriority;
import com.narxoz.rpg.quest.RewardSortedQuestIterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Homework 10 Demo: Iterator + Mediator ===");

        Hero aragorn = new Hero("Aragorn", 120, 40, 80, 25, 300);
        Hero legolas = new Hero("Legolas", 90, 60, 35, 15, 150);

        QuestLog log = new QuestLog();
        log.add(new Quest("Escort the merchant", QuestPriority.LOW, 50, false));
        log.add(new Quest("Clear the mines", QuestPriority.NORMAL, 120, false));
        log.add(new Quest("Recover the stolen relic", QuestPriority.HIGH, 250, false));
        log.add(new Quest("Slay the dragon", QuestPriority.URGENT, 1000, true));
        log.add(new Quest("Scout the northern pass", QuestPriority.HIGH, 200, true));

        GuildHall hall = new GuildHall();
        Quartermaster qm = new Quartermaster("Boromir", hall);
        Scout scout = new Scout("Legolas", hall);
        Healer healer = new Healer("Elrond", hall);
        Captain captain = new Captain("Gandalf", hall);
        Loremaster loremaster = new Loremaster("Saruman", hall);

        System.out.println("\n--- Pre-council coordination ---");
        captain.issueOrder("mission", "All officers report to the war table");
        scout.reportRoute("recon", "Northern pass is clear");
        healer.prepareAid("healing", "Potions stocked for 10 days");
        qm.requestSupplies("supplies", "Requesting rations for 5 days");
        loremaster.shareKnowledge("lore", "Dragon lair confirmed in ancient texts");

        System.out.println("\n--- Iterator: Ordered traversal ---");
        QuestIterator ordered = log.ordered();
        while (ordered.hasNext()) {
            System.out.println("  [ordered] " + ordered.next().getTitle());
        }

        System.out.println("\n--- Iterator: Reward-sorted traversal (Open/Closed proof) ---");
        QuestIterator byReward = new RewardSortedQuestIterator(log);
        while (byReward.hasNext()) {
            Quest q = byReward.next();
            System.out.println("  " + q.getRewardGold() + "g  " + q.getTitle());
        }

        System.out.println("\n--- CouncilEngine run ---");
        CouncilEngine engine = new CouncilEngine();
        CouncilRunResult result = engine.runCouncil(List.of(aragorn, legolas), log, hall);

        System.out.println("\n" + result);
    }
}