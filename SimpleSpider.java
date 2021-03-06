import java.io.InputStream;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;




public class SimpleSpider {
	//The page number at which the program is trying to start.
	private static final int page =2376 ;
	public static void main(String[] args) {
		//HttpClient Comfigurations for time out
		RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).setConnectionRequestTimeout(6000).setConnectTimeout(6000).build();
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig).build();
		System.out.println("Begin searching in 5 seconds……");
		for (int i = page; i > 0; i--) {
			//Creating a GET request
			HttpGet httpGet = new HttpGet("http://jandan.net/ooxx/page-" + i);
			httpGet.addHeader("User-Agent","Chrome/56.0.2924.87");
			httpGet.addHeader("Cookie","_gat=1; nsfw-click-load=off; gif-click-load=on; _ga=GA1.2.1861846600.1423061484");
			try {
				
				Thread.sleep(5000);
				//Sending the request
				CloseableHttpResponse response = httpClient.execute(httpGet);
				InputStream in = response.getEntity().getContent();
				String html = Utils.convertStreamToString(in);
				//Analyzing the web contents
				new Thread(new JianDanHtmlParser(html, i)).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
