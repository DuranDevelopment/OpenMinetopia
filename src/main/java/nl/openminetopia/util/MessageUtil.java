package nl.openminetopia.util;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.Component;

@UtilityClass
public class MessageUtil {

    public Component format(String message) {
        return MiniMessage.miniMessage().deserialize(message);
    }
}
