package com;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;


  public class MyAmazingBot extends TelegramLongPollingBot {
      private static final String API_KEY = "e8024a81ac61e929b25e57016a5bbe14";
      private static final String API_URL_BASE = "http://food2fork.com/api/search?key=" + API_KEY + "&q=";

      @Override //se si sbaglia a scrivere l override.
      public void onUpdateReceived(Update update) {

          // We check if the update has a message and the message has text
          if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText() == "/recipe") {
              // Set variables
              long chat_id = update.getMessage().getChatId();

              SendMessage message = new SendMessage() // Create a message object object
                  .setChatId(chat_id)
                  .setText(this.getRecipe());
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

      private String getRecipe() throws IOException{
        URL url = new URL(API_URL_BASE + URLEncoder.encode("polLo", "UTF-8") + "&page=2");
        URL sec_url = new  URL ("https://www.food2fork.com/api/get?key=e8024a81ac61e929b25e57016a5bbe14&rId=35382");

  	    HttpURLConnection con = (HttpURLConnection) sec_url.openConnection();
  		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB;     rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)");

  		int status = con.getResponseCode();
  		if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM) {
  			String location = con.getHeaderField("Location");
  			URL newUrl = new URL(location);

  			con = (HttpURLConnection) newUrl.openConnection();
  			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB;     rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)");
  	    }

  	    System.out.println("Qui laggo");
  	    System.out.println("Guarda quanto ci ho messo! " + con.getInputStream());
  		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

  		String inputLine;
  		StringBuffer content = new StringBuffer();

  		while ((inputLine = in.readLine()) != null) {
  			content.append(inputLine);
  		}
  		in.close();

  		JSONObject ricettina = new JSONObject (content.toString());
  		JSONObject recipe = ricettina.getJSONObject("recipe");
  		String link = recipe.getString("f2f_url");

  		return link;
  	}
}
