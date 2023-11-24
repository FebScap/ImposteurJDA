package be.febscap;

import be.febscap.listener.GeneralListener;
import be.febscap.listener.SlashListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        final String token = "MTE3NzIyMzgzNDMzMjU4MTg4OQ.GnwWRo.oLy24whe5uuWJuy7n84dw3OdOlUC_0D59kDUkU";

        JDABuilder builder = JDABuilder.createDefault(token);
        builder.setActivity(Activity.playing("à l'imposteur"));
        builder.addEventListeners(new GeneralListener());
        builder.addEventListeners(new SlashListener());

        JDA jda = builder.build();

        jda.updateCommands().addCommands(
                Commands.slash("imposteur", "Menu d'aide principal"),
                Commands.slash("newlobby", "Crée un nouveau lobby imposteur"),
                Commands.slash("endlobby", "Supprime le lobby avec l'identifiant défini")
                        .addOption(OptionType.INTEGER, "id", "L'ID du lobby à supprimer", true),
                Commands.slash("endalllobby", "Supprime le lobby avec l'identifiant défini")
        ).queue();
        jda.awaitReady();
    }
}