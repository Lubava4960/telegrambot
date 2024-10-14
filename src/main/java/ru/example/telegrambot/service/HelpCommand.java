package ru.example.telegrambot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
@Component
public class HelpCommand  {
    private TelegramBotService telegramBotService;
    static final String HELP_TEXT="This bot add category tree, delete element category, shows category tree \n\n"+
            "you can execute commands from the main menu on the left or by typing a command \n\n"+
            "Type /start to see a welcome message \n\n"+
            "Type /help to see info \n\n"+
            "Type /addCategory to see a add Category of category \n\n"+
            "Type /addElement to see a add Element of category \n\n"+
            "Type /treeView to see a category tree with elements \n\n" +
            "Type /removeTree you mast  a remove all category" ;



//    public void execute(Update update) throws TelegramApiException {
//
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            String messageText = update.getMessage().getText();
//            long chatId = update.getMessage().getChatId();
//            switch (messageText) {
//                case "/help":
//                    telegramBotService.sendMessage(chatId, HELP_TEXT);
//
//
//                    break;
//                default:
//                    telegramBotService.sendMessage(chatId, "Sorry, command was not recognized! ");
//
//            }

        }



 //   }



//    @Override
//    public String getCommand() {
//        return "/help";
//    }





//}
