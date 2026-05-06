package com.narxoz.rpg.guild;

public class Healer extends GuildMember {

    public Healer(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void prepareAid(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        String sender = from != null ? from.getName() : "Council";
        System.out.println("[Healer:" + getName() + "] <" + topic + "> from " + sender + ": " + payload);
    }
}