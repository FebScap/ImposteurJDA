package be.febscap.game;

import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class ImposteurLobby {
    private static int id = 0;
    private static final String idCatGaming = "1177271063479078973";
    public static void createLobby(SlashCommandInteractionEvent e) {
        if (e.getGuild() != null) {
            id++;
            for (Channel channel : e.getGuild().getCategoryById(idCatGaming).getTextChannels()) {
                int forId = Integer.parseInt(channel.getName().split("-")[2]);
                if (forId == id)
                    id = forId+1;
            }
            e.getGuild().createTextChannel("imposteur-lobby-" + id, e.getGuild().getCategoryById(idCatGaming))
                    .queue(channel -> {
                        e.reply("Lobby correctement créé ! " + channel.getAsMention()).queue();
                    });
        }
    }

    public static void deleteLobby(SlashCommandInteractionEvent e) {
        if (e.getGuild() != null) {
            for (Channel channel : e.getGuild().getCategoryById(idCatGaming).getTextChannels()) {
                int forId = Integer.parseInt(channel.getName().split("-")[2]);
                if (forId == e.getOption("id", OptionMapping::getAsInt)) {
                    channel.delete().queue();
                    e.reply("Lobby correctement supprimé !").queue();
                }
            }
        }
    }

    public static void deleteAllLobby(SlashCommandInteractionEvent e) {
        for (Channel channel : e.getGuild().getCategoryById(idCatGaming).getTextChannels()) {
            channel.delete().queue();
        }
        e.reply("Tout les Lobbys ont correctement été supprimés !").queue();
    }
}
