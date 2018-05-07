package bot.services;

import static bot.commons.utils.BotUtil.checkMessage;

import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import org.quartz.*;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import bot.commons.BotConstants;

public class Pausatron extends TelegramLongPollingBot implements Job {

	public static final String START_COMMAND = "start";
	public static final String STOP_COMMAND = "stop";

	private static final Set<Long> chatIDs = new HashSet<>();

	private static final String MESSAGE_TEXT = "_IT'S TIME TO REST_";

	@Override
	public String getBotUsername() {
		return "PausatronBot";
	}

	@Override
	public String getBotToken() {
		return "570584773:AAGqtNaqlXnIGAz5q2GjejbwFwYExhuv3aI";
	}

	@Override
	public void onUpdateReceived(final Update update) {
		long chatId = 0;
		if (update.hasMessage())
			chatId = update.getMessage().getChatId();

		if (checkMessage(update.getMessage(), START_COMMAND)) {
			chatIDs.add(chatId);
		}

		if (checkMessage(update.getMessage(), STOP_COMMAND)) {
			chatIDs.remove(chatId);
		}
	}

	@Override
	public void execute(final JobExecutionContext jobContext) throws JobExecutionException {
		if (!chatIDs.isEmpty()) {
			for (final Long chatId : chatIDs) {
				try {
					execute(new SendMessage().setChatId(chatId).setText(MESSAGE_TEXT).enableMarkdown(true));
				} catch (final TelegramApiException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void schedule(final Scheduler sched) throws SchedulerException {
		final JobDetail job = JobBuilder.newJob(Pausatron.class).withIdentity("PausatronJob", BotConstants.QUARTZ_GROUP).build();

		final Trigger triggerA = TriggerBuilder.newTrigger().withIdentity("PausatronTriggerA", "group1")//
				.startNow()//
				.withSchedule(CronScheduleBuilder.cronSchedule("0 0 11 ? * MON,TUE,WED,THU,FRI *")//
						.inTimeZone(TimeZone.getTimeZone(ZoneId.of("Europe/Rome"))))//
				.build();

		final Trigger triggerB = TriggerBuilder.newTrigger().withIdentity("PausatronTriggerB", "group1")//
				.startNow()//
				.withSchedule(CronScheduleBuilder.cronSchedule("0 0 16 ? * MON,TUE,WED,THU,FRI *")//
						.inTimeZone(TimeZone.getTimeZone(ZoneId.of("Europe/Rome"))))//
				.build();

		sched.scheduleJob(job, triggerA);
		sched.scheduleJob(job, triggerB);
	}

}
