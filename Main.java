import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLEncoder;
import java.net.HttpURLConnection;

import org.json.JSONObject;

public class Main {
	private static final String API_KEY = "e8024a81ac61e929b25e57016a5bbe14";
	private static final String API_URL_BASE = "http://food2fork.com/api/search?key=" + API_KEY + "&q=";


    public static void main(String[] args) throws IOException{
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

		System.out.println(link);

	}
}
