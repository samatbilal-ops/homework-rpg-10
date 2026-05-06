package com.narxoz.rpg.council;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.guild.GuildHall;
import com.narxoz.rpg.guild.GuildMediator;
import com.narxoz.rpg.quest.Quest;
import com.narxoz.rpg.quest.QuestIterator;
import com.narxoz.rpg.quest.QuestLog;
import com.narxoz.rpg.quest.QuestPriority;
import java.util.List;

public class CouncilEngine {

    public CouncilRunResult runCouncil(List<Hero> party, QuestLog questLog, GuildMediator hall) {
        int questsTraversed = 0;
        int messagesRouted = 0;
        int membersNotified = 0;

        System.out.println("--- Ordered traversal ---");
        QuestIterator ordered = questLog.ordered();
        while (ordered.hasNext()) {
            Quest q = ordered.next();
            questsTraversed++;
            hall.dispatch("mission", null, "Planning: " + q.getTitle());
            messagesRouted++;
            if (hall instanceof GuildHall gh) {
                membersNotified += gh.getLastDispatchNotified();
            }
        }

        System.out.println("--- High+ priority traversal ---");
        QuestIterator priority = questLog.priorityAtLeast(QuestPriority.HIGH);
        while (priority.hasNext()) {
            Quest q = priority.next();
            questsTraversed++;
            hall.dispatch("recon", null, "Scouting for: " + q.getTitle());
            messagesRouted++;
            if (hall instanceof GuildHall gh) {
                membersNotified += gh.getLastDispatchNotified();
            }
        }

        System.out.println("--- Reverse traversal ---");
        QuestIterator reverse = questLog.reverse();
        while (reverse.hasNext()) {
            Quest q = reverse.next();
            questsTraversed++;
            hall.dispatch("supplies", null, "Supplies for: " + q.getTitle());
            messagesRouted++;
            if (hall instanceof GuildHall gh) {
                membersNotified += gh.getLastDispatchNotified();
            }
        }

        return new CouncilRunResult(questsTraversed, messagesRouted, membersNotified);
    }
}