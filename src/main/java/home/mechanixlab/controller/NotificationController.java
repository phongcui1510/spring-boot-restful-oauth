package home.mechanixlab.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import home.mechanixlab.model.Message;

@RestController
@RequestMapping("/notif")
public class NotificationController {

	@Value("${FIREBASE_API_KEY}")
	private String FIREBASE_API_KEY;
	
	@Value("${ANDROID_NOTIFICATION_ICON}")
	private String ANDROID_NOTIFICATION_ICON;
	
	@Value("${ANDROID_NOTIFICATION_COLOR}")
	private String ANDROID_NOTIFICATION_COLOR;
	
	@Value("${API_URL_FCM}")
	public String API_URL_FCM;
	
	private final Logger logger = Logger.getLogger("NotificationController");
	
	@RequestMapping(value="/single", method= RequestMethod.POST)
	public String sendSingleDevice(HttpServletRequest request, HttpServletResponse response, @RequestBody Message message) {
		try {
			sendAndroidNotification(message.getDeviceToken(), message.getMessage(), message.getTitle());
			return "Send message successfully";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Send message get error: " + e.getMessage();
		}
	}
	
	@RequestMapping(value="/topic", method= RequestMethod.POST)
	public String sendTopic(HttpServletRequest request, HttpServletResponse response, @RequestBody Message message) {
		try {
			sendAndroidNotification("/topics/"+message.getDeviceToken(), message.getMessage(), message.getTitle());
			return "Send message to topic successfully";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Send message to topic get error: " + e.getMessage();
		}
	}
	
	private void sendAndroidNotification(String deviceToken, String message, String title) throws IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		HttpPost httppost = new HttpPost(API_URL_FCM);
		
		JSONObject obj = new JSONObject();
        JSONObject msgObject = new JSONObject();
        
        msgObject.put("title", title);
        msgObject.put("message", message);
        msgObject.put("icon", ANDROID_NOTIFICATION_ICON);
//        msgObject.put("color", ANDROID_NOTIFICATION_COLOR);
        
        obj.put("to", deviceToken);
        obj.put("data", msgObject);
		
        httppost.addHeader("Content-Type", "application/json");
        httppost.addHeader("Authorization", "key="+FIREBASE_API_KEY);
        
        HttpResponse response = httpclient.execute(httppost);
		HttpEntity resEntity = response.getEntity();

		System.out.println(response.getStatusLine());
		if (resEntity != null) {
			logger.info("Notification response >>>" + EntityUtils.toString(resEntity));
		}
		httpclient.close();
    }
}
