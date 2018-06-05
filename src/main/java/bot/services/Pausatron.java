package bot.services;

import static bot.commons.utils.BotUtil.checkMessage;

import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import bot.commons.BotConstants;
import bot.commons.utils.BotUtil;

public class Pausatron extends TelegramLongPollingBot implements Job {

	public static final String START_RESPONSE = "Chat registrata";
	public static final String STOP_RESPONSE = "Chat rimossa";

	public static final String START_COMMAND = "start";
	public static final String STOP_COMMAND = "stop";

	private static Set<Long> chatIDs;

	private static final String MESSAGE_TEXT = "_IT'S TIME TO REST_";

	public Pausatron() {
		reload();
		System.out.println("Pausatron ids: " + chatIDs);
	}

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
			BotUtil.write(chatId, BotConstants.PAUSATRON_RESOURCE_FILE_PATH);
			reload();
			sendSimpleMessage(chatId, START_RESPONSE);
		}

		if (checkMessage(update.getMessage(), STOP_COMMAND)) {
			BotUtil.remove(chatId, BotConstants.PAUSATRON_RESOURCE_FILE_PATH);
			reload();
			sendSimpleMessage(chatId, STOP_RESPONSE);
		}
	}

	private void sendSimpleMessage(final Long chatId, final String text) {
		try {
			super.execute(new SendMessage().setChatId(chatId).setText(text));
		} catch (TelegramApiException e) {
			e.printStackTrace();
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

	private static void reload() {
		chatIDs = new HashSet<>(BotUtil.read(BotConstants.PAUSATRON_RESOURCE_FILE_PATH));
	}

	public static void schedule(final Scheduler sched) throws SchedulerException {
		final JobDetail jobA = JobBuilder.newJob(Pausatron.class).withIdentity("PausatronJob", "group1").build();
		final JobDetail jobB = JobBuilder.newJob(Pausatron.class).withIdentity("PausatronJob", "group2").build();

		final Trigger triggerA = TriggerBuilder.newTrigger().withIdentity("PausatronTriggerA", "group1")//
				.startNow()//
				.withSchedule(CronScheduleBuilder.cronSchedule("0 0 11 ? * MON,TUE,WED,THU,FRI *")//
						.inTimeZone(TimeZone.getTimeZone(ZoneId.of("Europe/Rome"))))//
				.build();

		final Trigger triggerB = TriggerBuilder.newTrigger().withIdentity("PausatronTriggerB", "group2")//
				.startNow()//
				.withSchedule(CronScheduleBuilder.cronSchedule("0 30 15 ? * MON,TUE,WED,THU,FRI *")//
						.inTimeZone(TimeZone.getTimeZone(ZoneId.of("Europe/Rome"))))//
				.build();

		sched.scheduleJob(jobA, triggerA);
		sched.scheduleJob(jobB, triggerB);
	}

}
