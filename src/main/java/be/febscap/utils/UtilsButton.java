package be.febscap.utils;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.List;

public class UtilsButton {
    public final static Button btnConfirmEnd = Button.primary("confirmend", Emoji.fromUnicode("✅"));
    public final static Button btnCancelEnd = Button.secondary("cancelend", Emoji.fromUnicode("❎"));
    public final static Button btnClaim = Button.primary("claim", Emoji.fromUnicode("✉"));
    public final static Button btnNext = Button.secondary("next", Emoji.fromUnicode("⏭"));
    public final static Button btnEndGame = Button.danger("endgame", Emoji.fromUnicode("\uD83D\uDED1"));
    public final static Button btnJoinGame = Button.primary("join", Emoji.fromUnicode("\uD83D\uDD8A\uFE0F"));
    public final static Button btnStartGame = Button.primary("start", Emoji.fromUnicode("▶"));
    public final static String[] numbersEmojis = {":one:", ":two:", ":three:", ":four:", ":five:", ":six:", ":seven:", ":eight:", ":nine:", ":keycap_ten:"};

}
