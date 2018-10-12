package com;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;


  public class MyAmazingBot extends TelegramLongPollingBot {
              //Riscrittura di un metodo ereditato.La clausola Override permette di
              //ottenere errori in fase do compilazione invece che in fase di esecuzione
      @Override//se si sbaglia a scrivere l override.
      public void onUpdateReceived(Update update) {

          // We check if the update has a message and the message has text
          if (update.hasMessage() && update.getMessage().hasText()) {
              // Set variables
              String message_text = update.getMessage().getText();
              long chat_id = update.getMessage().getChatId();

              SendMessage message = new SendMessage() // Create a message object object
                  .setChatId(chat_id)
                  .setText(message_text);
              try {
                  execute(message); // Sending our message object to user
              } catch (TelegramApiException e) {
                  e.printStackTrace();
              }
          }
      }

      @Override
      public String getBotUsername() {
          // Return bot username
          // If bot username is @MyAmazingBot, it must return 'MyAmazingBot'
          return "Recipe_Machine_Gun_bot";
      }

      @Override
      public String getBotToken() {
          // Return bot token from BotFather
          return "487365523:AAGyY9FrLZK_Mo42MQwTCMgzd_L6KVit0bI";
      }
  }
