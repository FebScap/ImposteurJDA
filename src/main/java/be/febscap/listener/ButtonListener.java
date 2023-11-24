package be.febscap.listener;

import be.febscap.game.ImposteurGame;
import be.febscap.utils.UtilsButton;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ButtonListener implements EventListener {
    ImposteurGame game;

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof ButtonInteractionEvent e) {
            switch (e.getButton().getId()) {
                case "endgame":
                    e.reply("Êtes-vous sur de vouloir arrêter la partie ?")
                            .setActionRow(UtilsButton.btnConfirmEnd, UtilsButton.btnCancelEnd).queue();
                    break;

                case "confirmend":
                    e.getChannel().delete().queue();
                    break;

                case "cancelend":
                    e.editMessage("Suppression annulée")
                            .setActionRow(Button.success("nice", Emoji.fromUnicode("♻"))).queue();
                    e.getMessage().delete().queueAfter(3, TimeUnit.SECONDS);
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
                        newValue.append("- ").append(s).append("\n");
                    }
                    MessageEmbed.Field field = new MessageEmbed.Field(":pen_ballpoint: Joueurs inscrits :", newValue.toString(), true);
                    EmbedBuilder eb = new EmbedBuilder()
                            .setTitle(embed.getTitle())
                            .setDescription(embed.getDescription())
                            .addField(field)
                            .setColor(Color.CYAN);
                    e.editMessageEmbeds(eb.build()).queue();
                    break;

                case "start":
                    List<String> players = new LinkedList<>(Arrays.asList(e.getMessage().getEmbeds().get(0)
                            .getFields().get(0).getValue()
                            .replaceFirst("-", "").replace(" ", "")
                            .replace("\n", "").replace("<", "").replace("@", "")
                            .replace(">", "").split("-")));
                    e.getMessage().delete().queue();
                    game = new ImposteurGame(
                            players,
                            Integer.parseInt(e.getChannel().getName().split("-")[2]),
                            e.getChannel()
                    );

                    EmbedBuilder ebGame = new EmbedBuilder()
                            .setTitle("La partie commence !")
                            .setDescription("Voici le récapitulatif des scores :")
                            .setColor(Color.CYAN);
                    for (int i = 0; i < game.getMembers().size(); i++) {
                        ebGame.addField(UtilsButton.numbersEmojis[i], "<@" + game.getMembers().get(i) + ">"
                                +"\nPoints : "+ game.getPoint().get(i)
                                +"\nNombre de fois imposteur : " + game.getTimesImpostor().get(i), true);
                    }
                    e.replyEmbeds(ebGame.build())
                            .setActionRow(UtilsButton.btnEndGame, UtilsButton.btnClaim, UtilsButton.btnNext).queue();
                    break;

                case "next":
                    break;

                case "claim":
                    int index = 0;
                    for (int i = 0; i < game.getMembers().size(); i++) {
                        if (game.getMembers().get(i).equals(e.getMember().getId())) {
                            index = i;
                        }
                    }
                    e.reply("Ton mot est :**" + game.getWords().get(index) + "**").setEphemeral(true).queue();
                    break;
            }
        }
    }
}
