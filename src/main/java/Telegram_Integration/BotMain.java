package Telegram_Integration;

import javax.inject.Inject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class BotMain extends TelegramLongPollingBot {

    private String token;
    private String username;

    private ArrayList<TelegramUser> users;

    public BotMain(){
        Properties props = Config.getAppProps();
        this.token = props.getProperty("telegram.token");
        this.username = props.getProperty("telegram.username");
        this.users = new ArrayList<TelegramUser>();
    }

    @Override
    public void onUpdateReceived(Update update) {
        String currentChat = update.getMessage().getChatId().toString();
        TelegramUser currentUser = findUserByChatId(currentChat);

        if(currentUser == null){
            String username = update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName() + " ";
            currentUser = new TelegramUser(username, currentChat);
            users.add(currentUser);
        }

        String[] botResponse = currentUser.handleMessage(update.getMessage().getText());
        sendMessage(currentChat, botResponse);
    }

    @Override
    public String getBotUsername() {
        return this.username;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }

    private boolean sendMessage(String chatId, String[] Messages){

        boolean success = true;
        for (int i = 0; i < Messages.length; i++) {
            SendMessage response = new SendMessage();
            response.setChatId(chatId);
            response.setText(Messages[i]);

            try {
                execute(response);
            } catch (TelegramApiException e) {
                System.out.println(e);
                success = false;
            }
        }
        return success;
    }

    private TelegramUser findUserByChatId(String chatId){
        for (int i = 0; i < users.size(); i++) {
            if( users.get(i).getChatId().equals(chatId)){
                return users.get(i);
            }
        }
        return null;
    }
}
