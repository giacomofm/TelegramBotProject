package bot.services;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.MessageEntity;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.stickers.Sticker;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.vdurmont.emoji.EmojiParser;

public class DetailsDetector extends TelegramLongPollingBot {

	private static final Character end_line = '\n';

	@Override
	public String getBotUsername() {
		return "DetailsDetectorBot";
	}

	@Override
	public String getBotToken() {
		return "611087370:AAFzNWbftyE0vo6jmmBcN6C2wcj_z6xyt08";
	}

	@Override
	public void onUpdateReceived(final Update update) {
		long chatId = 0;
		if (update.hasMessage())
			chatId = update.getMessage().getChatId();
		try {
			String details = detect(update.getMessage());
			execute(new SendMessage().setChatId(chatId).setText(details));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private static String detect(final Message message) {
		StringBuilder sb = new StringBuilder("");
		if (message.hasText())
			detectText(sb, message.getText());

		if (message.hasEntities())
			message.getEntities().stream().forEach(e -> detectEntity(sb, e));

		if (message.getSticker() != null)
			detectSticker(sb, message.getSticker());

		if (sb.length() > 0)
			return sb.toString();
		return "empty text";
	}

	private static void detectText(final StringBuilder sb, final String text) {
		sb.append("-- message text --").append(end_line);
		sb.append(EmojiParser.parseToAliases(text)).append(end_line);
	}

	private static void detectEntity(final StringBuilder sb, final MessageEntity entity) {
		sb.append("-- entity info --").append(end_line);
		sb.append("entity type: ").append(entity.getType()).append(end_line);
		sb.append("entity text: ").append(entity.getText()).append(end_line);
	}

	private static void detectSticker(final StringBuilder sb, final Sticker sticker) {
		sb.append("-- sticker info --").append(end_line);
		sb.append("sticker set: ").append(sticker.getSetName()).append(end_line);
		sb.append("file id: ").append(sticker.getFileId()).append(end_line);
		sb.append("file size: ").append(sticker.getFileSize()).append(end_line);
		sb.append("file height: ").append(sticker.getHeight()).append(end_line);
		sb.append("file width: ").append(sticker.getWidth()).append(end_line);
		sb.append("emoji: ").append(EmojiParser.parseToAliases(sticker.getEmoji())).append(end_line);
	}

}