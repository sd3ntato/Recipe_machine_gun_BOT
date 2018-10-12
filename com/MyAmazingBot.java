package com;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;


import java.net.URL;
import java.net.URLEncoder;
import java.net.HttpURLConnection;

import org.json.JSONObject;
import org.json.JSONArray;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;


public class MyAmazingBot extends TelegramLongPollingBot {
    @Override //se si sbaglia a scrivere l override.
    public void onUpdateReceived(Update update)
    {
      //Genero un numero random di 5 cifre
      Random rand = new Random();
      int n = rand.nextInt((50000 - 40000) + 1) + 40000;
      //che diventera l'id della ricetta da restituire
      String id = String.valueOf(n); System.out.println(id);
        // We check if the update has a message and the message has text amd the text is a command
      System.out.println( update.getMessage().getText());
      if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().equals("/recipe"))
      {
          // Set variables
          long chat_id = update.getMessage().getChatId();
          SendMessage message = new SendMessage();
          try {
              message = new SendMessage() // Create a message object object
              .setChatId(chat_id)
              .setText(this.getRecipe(id));
          }
          catch (IOException e){
            e.printStackTrace();
          }
          try{
            execute(message); // Sending our message object to user
          }
          catch (TelegramApiException e){
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

    private String getRecipe(String id) throws IOException{
      URL sec_url = new  URL ("https://www.food2fork.com/api/get?key=e8024a81ac61e929b25e57016a5bbe14&rId="+ id);
	    HttpURLConnection con = (HttpURLConnection) sec_url.openConnection();
      con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB;     rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)");
  		int status = con.getResponseCode();//lo status e un numero di 3 cifre: 200 per ok, 301 moved permanently, altri per redirect, 404 notfound etc
  		if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM) {
			String location = con.getHeaderField("Location");            //in caso di redirect
			URL newUrl = new URL(location);
			con = (HttpURLConnection) newUrl.openConnection();
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB;     rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)");
	    }

	    System.out.println("Qui Antonio dice che laggo");
	    System.out.println("comincio a leggere la risposta ..." + con.getInputStream());
  		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
  		String inputLine;
  		StringBuffer content = new StringBuffer();
  		while ((inputLine = in.readLine()) != null) { //confronta inputline con null dopo aver assegnato il nuovo valore
  			content.append(inputLine);
  		}
  		in.close();
      //Abbiamo letto tutta la risposta di f2f, ora possiamo elaborarla
  		JSONObject ricettina = new JSONObject (content.toString());
  		JSONObject recipe = ricettina.getJSONObject("recipe");
  		String link = recipe.getString("source_url");
      JSONArray ingredients= recipe.getJSONArray("ingredients");
      String current_message =  recipe.getString("title") + "\n\nINGREDIENTI:\n";
      for (Object ing : ingredients)
          current_message += "- " + ing.toString() + "\n";
      current_message += link;
      return current_message;
	   }
}
