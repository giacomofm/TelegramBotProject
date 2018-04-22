package bot.util;

import org.telegram.telegrambots.api.objects.Message;

public class BotUtil {

	public static Boolean checkMessage(final Message message, final String txt) {
		return message != null && message.hasText() && message.getText().equals(txt);
	}

}
