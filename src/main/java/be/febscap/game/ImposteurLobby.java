package be.febscap.game;

import be.febscap.listener.SlashListener;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ImposteurLobby {
    private static int id = 0;
    public static void createLobby(SlashCommandInteractionEvent e) {
        if (e.getGuild() != null) {
            id++;
            for (Channel channel : e.getGuild().getCategoryById(SlashListener.idCatGaming).getTextChannels()) {
                int forId = Integer.parseInt(channel.getName().split("-")[2]);
                if (forId == id)
                    id = forId+1;
            }

            List<MessageEmbed.Field> ruleFields = new ArrayList<>();
            ruleFields.add(new MessageEmbed.Field(":small_blue_diamond: Le jeu :", ":pushpin: À chaque tour," +
                    " chaque joueur reçois un mot secret. A tour de rôle," + "\n" +
                    " chaque joueur va partager un mot-indice en rapport avec le mot secret.",false));
            ruleFields.add(new MessageEmbed.Field("",
                    ":pushpin: Le but du jeu pour les joueurs est de découvrir qui est l'imposteur, tandis que l'imposteur" +
                            " doit lui essayer de passer inaperçu, en échappant aux suspicions et en tentant " +
                            "de découvrir le mot secret grâce aux indices donnés par les autres joueurs.", false));
            ruleFields.add(new MessageEmbed.Field("",
                    ":pushpin: Soyez trop évident et l’imposteur comprendra de quoi il s’agit et se fondra dans la masse." +
                            " Soyez trop subtil et vous risquez vous-même d'être accusé d’être l’imposteur.", false));
            ruleFields.add(new MessageEmbed.Field("","",false));
            ruleFields.add(new MessageEmbed.Field(":small_blue_diamond: Règles du Jeu :", ":one: Au départ, chaque" +
                    " joueur prend connaissance du mot secret en recevant une carte confidentielle," + "\n" +
                    "sauf un, qui recevra un deuxième mot secret, ressemblant à celui des autres.",false));
            ruleFields.add(new MessageEmbed.Field("",
                    ":two: Ce joueur sera donc l’imposteur.", false));
            ruleFields.add(new MessageEmbed.Field("",
                    ":three: Pendant 3 tours et à tour de rôle, chaque joueur va communiquer un mot-indice en rapport avec" +
                            " le mot secret pour prouver son innocence au groupe.", false));
            ruleFields.add(new MessageEmbed.Field("",
                    ":pushpin: L’objectif des joueurs est donc de démasquer l’imposteur, tandis que celui-ci doit se fondre" +
                            " dans la masse en faisant semblant d'avoir le même mot secret pour éviter d’éveiller les soupçons.\n" +
                            " \n" +
                            "Pourrez-vous passer inaperçu en tant qu’imposteur ou le groupe vous démasquera t'il ?", false));
            EmbedBuilder rules = createEmbed(Color.red, "Voici les règles du jeu :", null, ruleFields, null);


            List<MessageEmbed.Field> gameFields = new ArrayList<>();
            gameFields.add(new MessageEmbed.Field(":pen_ballpoint: Joueurs inscrits :", "- " + e.getMember().getAsMention() + "\n",false));
            EmbedBuilder game = createEmbed(Color.CYAN, "Jouer :", "Avant de lancer la partie, vérifiez bien que tout les participants" +
                    "soient inscrits.", gameFields,null);


            List<Button> buttons = new ArrayList<Button>();
            buttons.add(Button.danger("endgame", Emoji.fromUnicode("\uD83D\uDED1")));
            buttons.add(Button.primary("join", Emoji.fromUnicode("\uD83D\uDD8A\uFE0F")));
            buttons.add(Button.primary("start", Emoji.fromUnicode("▶")));

            e.getGuild().createTextChannel("imposteur-lobby-" + id, e.getGuild().getCategoryById(SlashListener.idCatGaming))
                    .queue(channel -> {
                        e.reply("Lobby correctement créé ! " + channel.getAsMention()).queue();
                        channel.sendMessage(e.getMember().getAsMention() + " Bienvenue dans une nouvelle partie d'imposteur ! :detective:").queue();
                        channel.sendMessageEmbeds(rules.build()).queue();
                        channel.sendMessageEmbeds(game.build()).addActionRow(buttons).queue();
                    });
        }
    }

    public static void deleteLobby(SlashCommandInteractionEvent e) {
        if (e.getGuild() != null) {
            for (Channel channel : e.getGuild().getCategoryById(SlashListener.idCatGaming).getTextChannels()) {
                int forId = Integer.parseInt(channel.getName().split("-")[2]);
                if (forId == e.getOption("id", OptionMapping::getAsInt)) {
                    channel.delete().queue();
                    e.reply("Lobby correctement supprimé !").queue();
                }
            }
        }
    }

    public static void deleteAllLobby(SlashCommandInteractionEvent e) {
        for (Channel channel : e.getGuild().getCategoryById(SlashListener.idCatGaming).getTextChannels()) {
            channel.delete().queue();
        }
        e.reply("Tout les Lobbys ont correctement été supprimés !").queue();
    }

    public static EmbedBuilder createEmbed(Color color, String title, String description, List<MessageEmbed.Field> fields, String imageUrl) {
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(color)
                .setTitle(title)
                .setDescription(description)
                .setImage(imageUrl);
        if (fields != null) {
            for (MessageEmbed.Field field: fields) {
                eb.addField(field);
            }
        }
        return eb;
    }
}