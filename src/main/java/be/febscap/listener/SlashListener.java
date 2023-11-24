package be.febscap.listener;

import be.febscap.game.ImposteurLobby;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Objects;

public class SlashListener extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        switch (e.getName()) {
            case "imposteur":
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.GREEN);
                eb.setTitle("Liste des commandes disponibles pour imposteur :")
                        .setDescription("» Les chevrons <> indiquent un paramètre obligatoire et ne doivent pas être mis," +
                                " idem pour les parenthèses () qui indiquent un paramètre optionnel," +
                                " un @ indique que le paramètre doit être entré en tant que mention s'il " +
                                "s'agit d'un membre ou s'il s'agit d'un rôle.");
                eb.addField(":small_orange_diamond: /imposteur", "  Liste des commandes disponibles pour imposteur", false);
                eb.addField(":small_orange_diamond: /newlobby", " Crée un lobby imposteur", false);
                eb.addField(":small_orange_diamond: /endlobby <IdLobby>", "  Supprime un lobby existant", false);

                e.reply("").addEmbeds(eb.build()).queue();
                break;

            case "newlobby":
                ImposteurLobby.createLobby(e);
                break;

            case "endlobby":
                ImposteurLobby.deleteLobby(e);
                break;

            case "endalllobby":
                ImposteurLobby.deleteAllLobby(e);
                break;

            default:
                System.out.printf("Commande inconnue %s utilisée par %#s%n", e.getName(), e.getUser());
        }
    }
}
