package com.narxoz.rpg.guild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GuildHall implements GuildMediator {

    private final Map<String, List<GuildMember>> membersByTopic = new HashMap<>();
    private int lastDispatchNotified = 0;

    @Override
    public void register(GuildMember member) {
        if (member instanceof Quartermaster) {
            addSubscriber("supplies", member);
            addSubscriber("mission", member);
        } else if (member instanceof Scout) {
            addSubscriber("recon", member);
            addSubscriber("mission", member);
        } else if (member instanceof Healer) {
            addSubscriber("healing", member);
            addSubscriber("mission", member);
        } else if (member instanceof Captain) {
            addSubscriber("mission", member);
            addSubscriber("recon", member);
            addSubscriber("supplies", member);
            addSubscriber("healing", member);
        } else if (member instanceof Loremaster) {
            addSubscriber("lore", member);
            addSubscriber("recon", member);
            addSubscriber("mission", member);
        }
    }

    @Override
    public void dispatch(String topic, GuildMember from, String payload) {
        Set<GuildMember> notified = new HashSet<>();
        for (GuildMember member : subscribersFor(topic)) {
            if (member != from) {
                member.receive(topic, from, payload);
                notified.add(member);
            }
        }
        lastDispatchNotified = notified.size();
    }

    public int getLastDispatchNotified() {
        return lastDispatchNotified;
    }

    protected void addSubscriber(String topic, GuildMember member) {
        membersByTopic.computeIfAbsent(topic, key -> new ArrayList<>()).add(member);
    }

    protected List<GuildMember> subscribersFor(String topic) {
        return membersByTopic.getOrDefault(topic, List.of());
    }
}