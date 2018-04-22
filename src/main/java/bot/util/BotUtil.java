package bot.util;

import java.time.LocalTime;
import java.time.ZoneId;

import org.telegram.telegrambots.api.objects.Message;

public class BotUtil {

	public static Boolean checkMessage(final Message message, final String txt) {
		return message != null && message.hasText() && message.getText().equals(txt);
	}

	public static LocalTime now() {
		return LocalTime.now(ZoneId.of("Europe/Rome"));
	}

}
