package home.mechanixlab.controller;

import java.io.IOException;
import java.util.ArrayList;
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

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;


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
	public String sendSingleDevice(HttpServletRequest request, HttpServletResponse response, @RequestBody home.mechanixlab.model.Message message) {
		try {
			Sender sender = new Sender("AIzaSyDSoriyUarDnLGRZy4rG-ZswnwzwkwqWF4");

	        ArrayList<String> devicesList = new ArrayList<String>();
	        devicesList.add(message.getDeviceToken());

	        Message m = new Message.Builder().timeToLive(30)
	                .delayWhileIdle(true)
	                .addData("title", message.getTitle())
	                .addData("message", message.getMessage())
	                .build();

	        MulticastResult result = sender.send(m, devicesList, 1);
	        sender.send(m, devicesList, 1);
	        System.out.println(result.toString());
	        
			return result.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Send message get error: " + e.getMessage();
		}
	}
	
	@RequestMapping(value="/topic", method= RequestMethod.POST)
	public String sendTopic(HttpServletRequest request, HttpServletResponse response, @RequestBody home.mechanixlab.model.Message message) {
		try {
			Sender sender = new Sender("AIzaSyDSoriyUarDnLGRZy4rG-ZswnwzwkwqWF4");

	        ArrayList<String> devicesList = new ArrayList<String>();
	        devicesList.add("/topic/"+message.getDeviceToken());

	        Message m = new Message.Builder().timeToLive(30)
	                .delayWhileIdle(true)
	                .addData("title", message.getTitle())
	                .addData("message", message.getMessage())
	                .build();

	        MulticastResult result = sender.send(m, devicesList, 1);
	        sender.send(m, devicesList, 1);
	        System.out.println(result.toString());
	        
			return result.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Send message to topic get error: " + e.getMessage();
		}
	}
}
