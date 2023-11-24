package be.febscap.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.List;

public class UtilsEmbedBuilder {
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
