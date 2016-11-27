package home.mechanixlab.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
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
	
	private final Logger logger = Logger.getLogger("VideoControllerLogger");

	@RequestMapping(value="/upload", method= RequestMethod.POST)
	public @ResponseBody VideoModel upload(HttpServletRequest request, HttpServletResponse response, @RequestPart(name="file") MultipartFile file, @RequestPart(name="video") VideoModel video) {
		logger.info("Upload video receive video information: " + video.getVideodescription());
		OAuth2Authentication oAuth2Authentication = (OAuth2Authentication)request.getUserPrincipal();
		User user = (User)oAuth2Authentication.getPrincipal();
		logger.log(Level.INFO, "Start upload file with user " + user.getUsername());

		if (!file.isEmpty()) {
			String fullName = file.getOriginalFilename();
			String name = fullName.substring(0, fullName.length() - 4);
			String ext = fullName.substring(fullName.length() - 3, fullName.length());
			try {
//				byte[] bytes = file.getBytes();
//				BufferedOutputStream stream = 
//						new BufferedOutputStream(new FileOutputStream(new File(name)));
//				stream.write(bytes);
//				stream.close();
				InputStream in = file.getInputStream();
				File fileToSave = Paths.get(uploadLocation).resolve(fullName).toFile();
				if (fileToSave.exists()) {
					int i = 1;
					while(fileToSave.exists()) {
						String temp = name + "-" + i;
						i++;
						fullName = temp+"."+ext;
						fileToSave = Paths.get(uploadLocation).resolve(fullName).toFile();
					}
				}
				Files.copy(in, Paths.get(uploadLocation).resolve(fullName));
				in.close();
				VideoModel videoModel = new VideoModel();
				videoModel.setVideotitle(fullName.substring(0, fullName.length() - 4));
				videoModel.setFilename(fullName);
				videoModel.setFiletype(ext);
				videoModel.setFilepath(Paths.get(uploadLocation).resolve(fullName).toString());
				videoModel.setRef_uuid(user.getUuid());
				String thumbnail = fullName.substring(0, fullName.length() - 4) + ".png";
				VideoUtils.getThumbnail(fileToSave, Paths.get(thumbnailLocation).resolve(thumbnail).toString());
				videoModel.setThumbnailpath(Paths.get(thumbnailLocation).resolve(thumbnail).toString());
				VideoModel savedVideo = videoService.save(videoModel);
				return savedVideo;
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage());
				VideoModel model = new VideoModel();
				model.setErrorMsg("You failed to upload file with error: " + e.getMessage());
				return model;
			}
		} else {
			VideoModel model = new VideoModel();
			model.setErrorMsg("You failed to upload file because the file was empty.");
			return model;
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
		long expires = System.currentTimeMillis() + 604800000L;
		
//		File file = new File("D:/AndroidCommercial.3gp");
		
		InputStream fis = new BufferedInputStream( new FileInputStream(file));
		
		String fileName = file.getName();
        long length = file.length();
        long lastModified = file.lastModified();
        String eTag = fileName + "_" + length + "_" + lastModified;
        
	    response.setContentType("video/3gpp");
	    response.setContentLength((int)length);
	    response.setHeader("ETag", eTag);
	    response.setHeader("Connection", "keep-alive");
	    response.setHeader("Accept-Ranges", "bytes");
	    response.setDateHeader("Last-Modified", lastModified);
        response.setDateHeader("Expires", expires);
        
	    response.setHeader("Content-Disposition", "attachment; filename="+fileName);
	    
	   /* OutputStream out = response.getOutputStream();
	    
	    byte[] buffer = new byte[4096];
        int bytesRead;

        while((bytesRead = fis.read(buffer)) != -1)
            out.write(buffer, 0, bytesRead);

        fis.close();
        out.close();*/
        
	    IOUtils.copy(fis, response.getOutputStream());
		
		
	}
	
	/*@RequestMapping(value="/play", method= RequestMethod.GET)
	@ResponseBody public void play(@RequestParam("videoId") Integer videoId, HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
//	        String path = repositoryService.findVideoLocationById(id);
			File file = new File("D:/AndroidCommercial.3gp");
	        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
	        response.setHeader("Content-Disposition", "attachment; filename="+file.getName().replace(" ", "_"));
//	        InputStream iStream = new FileInputStream(file);
//	        IOUtils.copy(iStream, response.getOutputStream());
//	        response.flushBuffer();
	        InputStream fis = new BufferedInputStream( new FileInputStream(file));
	        
	        OutputStream out = response.getOutputStream();
		    
		    byte[] buffer = new byte[20480];
	        int bytesRead;

	        long length = file.length();
//	        response.setContentType("video/3gpp");
		    response.setContentLength((int)length);
//		    response.setHeader("ETag", eTag);
		    response.setHeader("Connection", "keep-alive");
		    response.setHeader("Accept-Ranges", "bytes");
		    
	        while((bytesRead = fis.read(buffer)) != -1)
	            out.write(buffer, 0, bytesRead);

	        fis.close();
	        out.close();
	        
	    } catch (java.nio.file.NoSuchFileException e) {
	        response.setStatus(HttpStatus.NOT_FOUND.value());
	    } catch (Exception e) {
	        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
	    }
	}*/
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ResponseEntity<List<VideoModel>> getAllVideoUploadedByUser(HttpServletRequest request, HttpServletResponse response) {
		OAuth2Authentication oAuth2Authentication = (OAuth2Authentication)request.getUserPrincipal();
		User user = (User)oAuth2Authentication.getPrincipal();
		logger.log(Level.INFO, "List all videos of user : " + user.getUsername());
		List<VideoModel> model = videoService.findVideoByCurrentUser(user.getUuid());
		return new ResponseEntity<List<VideoModel>>(model, HttpStatus.OK);
	}
	
}
