package ru.example.telegrambot.service;

import com.vdurmont.emoji.EmojiParser;

import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.example.telegrambot.config.TelegramBotConfiguration;
import ru.example.telegrambot.controller.MessageController;


import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.awt.SystemColor.text;

@Component
public class TelegramBotService  extends TelegramLongPollingBot {
    final TelegramBotConfiguration telegramBotConfiguration;
    final TreeService treeService;
    final MessageController messageController;
    final AddCategory addCategory;
    final AddElement addElement;
    final RemoveTree removeTree;
    private final Pattern pattern = Pattern.compile("(([А-я\\d\\s.,!?:]+))");


    public TelegramBotService(TelegramBotConfiguration telegramBotConfiguration, TreeService treeService, MessageController messageController, AddCategory addCategory, AddElement addElement, RemoveTree removeTree) {
        this.telegramBotConfiguration = telegramBotConfiguration;
        this.treeService = treeService;
        this.messageController = messageController;

        this.addCategory = addCategory;
        this.addElement = addElement;
        this.removeTree = removeTree;
    }
    @PostConstruct
    public void command(){
        List<BotCommand> command = new ArrayList<>();
        command.add(new BotCommand("/start", "Начать"));
        command.add(new BotCommand("/help", "Помощь"));
        command.add(new BotCommand("/addCategory", "добавить категорию"));
        command.add(new BotCommand("/addElement", "добавить элемент"));
        command.add(new BotCommand("/treeView", "просмотр дерева категорий"));
        command.add(new BotCommand("/removeTree", "удаление дерева категорий"));
    }

    // обработка сообщений от пользователя
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            Matcher matcher = pattern.matcher(messageText);
            switch ((messageText)) {
                case "/start":
                    try {
                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                    break;

                case "/help":
                    sendMessage(chatId, HelpCommand.HELP_TEXT);
                    break;

                case "/addCategory":

                   sendMessage(chatId, "Введите категорию" );
                    //перейти на метод
                    addCategory.addCategory(messageText);

                   sendMessage(chatId, "Ваша категория успешно записана ");

                    break;

                case "/addElement":
                    sendMessage(chatId, "Введите элемент");
                     //  String text = matcher.group(1);
                      addElement.addElement(messageText,messageText);
                    sendMessage(chatId, "Ваш элемент успешно записан ");
                    break;

                case "/treeView":

                    Map<String, Set<Set<String>>> treeView = messageController.treeView();
                    sendMessage(chatId, formatTreeView(treeView));
                    break;

                case "/removeTree":
                    sendMessage(chatId, "Введите категорию для удаления ");

                    if (messageText != null) {
                        matcher = pattern.matcher(messageText);
                        if (matcher.find()) {
                            // Извлекаем данные из группы
                            String txt = matcher.group(1);         // Дальнейшая обработка
                        } else {          // Не найдено совпадений, обрабатываем ситуацию
                            throw new IllegalStateException("No match found");
                        }

                        try {
                            removeTree.deleteCategoryByName(messageText);
                        } catch (ChangeSetPersister.NotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        sendMessage(chatId, "Ваша категория успешно удалена ");

                    }

                    break;

            }


        }

    }

    public void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    public void startCommandReceived(long chatId, String name) throws TelegramApiException {
        String answer = EmojiParser.parseToUnicode("Hi," + name + " nice to meet you! " + " :blush:");

        sendMessage(chatId, answer);

    }



    @Override
    public String getBotToken() {

        return telegramBotConfiguration.getToken();
    }

    @Override
    public String getBotUsername() {
        return telegramBotConfiguration.getBotName();
    }

    private String formatTreeView(Map<String, Set<Set<String>>> treeView) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Set<Set<String>>> entry : treeView.entrySet()) {
            sb.append(entry.getKey()).append(":\n");
            for (Set<String> set : entry.getValue()) {
                sb.append("  - ");
                for (String item : set) {
                    sb.append(item).append(", ");
                }
                sb.delete(sb.length() - 2, sb.length()); // remove trailing ", "
                sb.append("\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }



}







