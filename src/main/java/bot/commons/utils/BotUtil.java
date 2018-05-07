package bot.commons.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.telegram.telegrambots.api.objects.Message;

import bot.commons.BotConstants;

public class BotUtil {

	public static Boolean checkMessage(final Message message, final String txt) {
		return message != null && message.hasText() && message.getText().contains(txt);
	}

	public static LocalTime timeNow() {
		return LocalTime.now(BotConstants.ZONE_ID);
	}

	public static LocalDateTime dateTimeNow() {
		return LocalDateTime.now(BotConstants.ZONE_ID);
	}

}
