package be.febscap.listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ButtonListener implements EventListener {
    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof ButtonInteractionEvent e) {
            switch (e.getButton().getId()) {
                case "endgame":
                    e.getChannel().delete().queue();
                    break;

                case "join":
                    MessageEmbed embed = e.getMessage().getEmbeds().get(0);
                    List<String> value = new LinkedList<>(Arrays.asList(embed.getFields().get(0).getValue()
                            .replaceFirst("-", "").replace(" ", "")
                            .replace("\n", "").split("-")));
                    boolean wasRegistered = false;
                    for (int i = 0; i < value.size(); i++) {
                        if (value.get(i).equals(e.getMember().getAsMention())) {
                            value.remove(i);
                            wasRegistered = true;
                        }
                    }
                    if (!wasRegistered)
                        value.add(e.getMember().getAsMention());
                    StringBuilder newValue = new StringBuilder();
                    for (String s: value) {
                        System.out.println(s);
                        newValue.append("- ").append(s).append("\n");
                    }
                    MessageEmbed.Field field = new MessageEmbed.Field(":pen_ballpoint: Joueurs inscrits :", newValue.toString(), true);
                    EmbedBuilder eb = new EmbedBuilder()
                            .setTitle(embed.getTitle())
                            .setDescription(embed.getDescription())
                            .addField(field)
                            .setColor(Color.CYAN);
                    e.getGuild().getTextChannelById(e.getChannelId()).editMessageEmbedsById(e.getMessageId(), eb.build()).queue();
                    break;

                case "start":

                    break;
            }
        }
    }
}
