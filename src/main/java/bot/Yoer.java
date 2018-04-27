package bot;

import java.util.Arrays;
import java.util.List;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import bot.util.BotUtil;

public class Yoer extends TelegramLongPollingBot {

	public static final String ADDITIONAL_TEXT = " c'e'! Chi si aggiunge?";

	public static final String START_ROUTINE_COMMAND = "yoexe";

	private static final KeyboardButton keyboardButtonA = new KeyboardButton("yo!");
	private static final KeyboardButton keyboardButtonB = new KeyboardButton("nope");
	private static final KeyboardRow keyboardRow = new KeyboardRow();
	{
		keyboardRow.add(keyboardButtonA);
		keyboardRow.add(keyboardButtonB);
	}
	private static final List<KeyboardRow> keyboard = Arrays.asList(keyboardRow);

	@Override
	public String getBotUsername() {
		return "YoerBot";
	}

	@Override
	public String getBotToken() {
		return "568627206:AAGOwDRsevB7L2nvVCIP19u84HF9b-Dxa-g";
	}

	@Override
	public void onUpdateReceived(final Update update) {
		Long chatId = 0L;
		if (BotUtil.checkMessage(update.getMessage(), START_ROUTINE_COMMAND)) {
			chatId = update.getMessage().getChatId();
			startRoutine(chatId, update.getMessage());
		}
	}

	private void startRoutine(final Long chatId, final Message message) {
		try {
			execute(new SendMessage().setChatId(chatId)//
					.setText(message.getFrom().getFirstName() + ADDITIONAL_TEXT)//
					.setReplyMarkup(new ReplyKeyboardMarkup().setKeyboard(keyboard).setOneTimeKeyboard(true)));
		} catch (final TelegramApiException e) {
			e.printStackTrace();
		}
	}

}
