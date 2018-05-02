package bot.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.telegram.telegrambots.api.objects.Message;

public class BotUtil {

	public static Boolean checkMessage(final Message message, final String txt) {
		return message != null && message.hasText() && message.getText().contains(txt);
	}

	public static LocalDateTime now() {
		return LocalDateTime.now(ZoneId.of("Europe/Rome"));
	}

}
