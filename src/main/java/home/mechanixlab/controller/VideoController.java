package home.mechanixlab.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import home.mechanixlab.data.User;
import home.mechanixlab.model.VideoModel;
import home.mechanixlab.service.VideoService;
import home.mechanixlab.util.VideoUtils;


@RestController
@RequestMapping("/video")
public class VideoController {

	@Value("${uploadLocation}")
	private String uploadLocation;
	
	@Value("${thumbnailLocation}")
	private String thumbnailLocation;

	@Autowired
	private VideoService videoService;

	@Autowired
	ServletContext context;
	
	@Value("${ANDROID_NOTIFICATION_KEY}")
	private String ANDROID_NOTIFICATION_KEY;
	
	@Value("${ANDROID_NOTIFICATION_ICON}")
	private String ANDROID_NOTIFICATION_ICON;
	
	@Value("${ANDROID_NOTIFICATION_COLOR}")
	private String ANDROID_NOTIFICATION_COLOR;
	
	@Value("${API_URL_FCM}")
	public String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

	private final Logger logger = Logger.getLogger("VideoControllerLogger");

	@RequestMapping(value="/upload", method= RequestMethod.POST)
	public @ResponseBody String upload(HttpServletRequest request, HttpServletResponse response, @RequestBody MultipartFile file) {
		OAuth2Authentication oAuth2Authentication = (OAuth2Authentication)request.getUserPrincipal();
		User user = (User)oAuth2Authentication.getPrincipal();
		logger.log(Level.INFO, "Start upload file with user " + user.getUsername());

		if (!file.isEmpty()) {
			String name = file.getOriginalFilename();
			try {
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = 
						new BufferedOutputStream(new FileOutputStream(new File(name)));
				stream.write(bytes);
				stream.close();
				Files.copy(file.getInputStream(), Paths.get(uploadLocation).resolve(name));
				String ext = name.substring(name.length() - 3, name.length());
				VideoModel videoModel = new VideoModel();
				videoModel.setVideotitle(name.substring(0, name.length() - 4));
				videoModel.setFilename(name);
				videoModel.setFiletype(ext);
				videoModel.setFilepath(Paths.get(uploadLocation).resolve(name).toString());
				videoModel.setRef_uuid(user.getUuid());
				File savedFile = Paths.get(uploadLocation).resolve(name).toFile();
				String thumbnail = name.substring(0, name.length() - 4) + ".png";
				VideoUtils.getThumbnail(savedFile, Paths.get(thumbnailLocation).resolve(thumbnail).toString());
				videoModel.setThumbnailpath(Paths.get(thumbnailLocation).resolve(thumbnail).toString());
				VideoModel savedVideo = videoService.save(videoModel);
				return savedVideo.getVideoId().toString();
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage());
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload file because the file was empty.";
		}
	}
	
	@RequestMapping(value="/thumbnail", method= RequestMethod.GET, produces=MediaType.IMAGE_PNG_VALUE)
	public void getThumbnail (@RequestParam("videoId") Integer videoId, HttpServletRequest request, HttpServletResponse response) throws IOException {
		VideoModel model = videoService.findVideoById(videoId);
		logger.log(Level.INFO, "Video found: " + model.getVideoId());
		File file = new File(model.getThumbnailpath());
		FileInputStream is = new FileInputStream(file);
		response.setContentType("image/png");
		IOUtils.copy(is, response.getOutputStream());
	}
	
	@RequestMapping(value="/info", method= RequestMethod.POST, consumes="application/json")
	public @ResponseBody VideoModel videoInfo(@RequestBody VideoModel videoModel) {
		if (videoModel != null) {
			logger.log(Level.INFO, "Start update information file: " + videoModel.getVideoId().toString());
			VideoModel returnVideo = videoService.update(videoModel);
			return returnVideo;
		} else {
			VideoModel returnVideo = new VideoModel();
			returnVideo.setErrorMsg("Input video cannot null");
			return returnVideo;
		}
	}

	@RequestMapping(value="/play", method= RequestMethod.GET, produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void play(@RequestParam("videoId") Integer videoId, HttpServletRequest request, HttpServletResponse response) throws IOException {
		VideoModel model = videoService.findVideoById(videoId);
		File file = new File(model.getFilepath());
		long length = file.length();
		FileInputStream is = null;
		is = new FileInputStream(file);
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentLength(length);
	    response.setContentType("application/octect-stream");
	    response.setContentLength((int)length);
	    response.setHeader("Connection", "keep-alive");
	    response.setHeader("Content-Disposition", "attachment; filename="+model.getFilename());
	    IOUtils.copy(is, response.getOutputStream());
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ResponseEntity<List<VideoModel>> getAllVideoUploadedByUser(HttpServletRequest request, HttpServletResponse response) {
		OAuth2Authentication oAuth2Authentication = (OAuth2Authentication)request.getUserPrincipal();
		User user = (User)oAuth2Authentication.getPrincipal();
		logger.log(Level.INFO, "List all videos of user : " + user.getUsername());
		List<VideoModel> model = videoService.findVideoByCurrentUser(user.getUuid());
		return new ResponseEntity<List<VideoModel>>(model, HttpStatus.OK);
	}
	
	private void sendAndroidNotification(String deviceToken, String message, String title) throws IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		HttpPost httppost = new HttpPost(API_URL_FCM);
		
		JSONObject obj = new JSONObject();
        JSONObject msgObject = new JSONObject();
        
        msgObject.put("body", message);
        msgObject.put("title", title);
        msgObject.put("icon", ANDROID_NOTIFICATION_ICON);
        msgObject.put("color", ANDROID_NOTIFICATION_COLOR);
        
        obj.put("to", deviceToken);
        obj.put("notification", msgObject);
		
        httppost.addHeader("Content-Type", "application/json");
        httppost.addHeader("Authorization", "key="+ANDROID_NOTIFICATION_KEY);
        
        HttpResponse response = httpclient.execute(httppost);
		HttpEntity resEntity = response.getEntity();

		System.out.println(response.getStatusLine());
		if (resEntity != null) {
			logger.info("Notification response >>>" + EntityUtils.toString(resEntity));
		}
		httpclient.close();
    }
}
