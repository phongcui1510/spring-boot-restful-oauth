package home.phong.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import home.phong.model.VideoModel;
import home.phong.service.VideoService;

@RestController
@RequestMapping("/video")
public class VideoController {
	
	@Value("${uploadLocation}")
	private String uploadLocation;
	
	@Autowired
	private VideoService videoService;
	
	private final Logger logger = Logger.getLogger("VideoControllerLogger");
	
	@RequestMapping(value="/upload", method= RequestMethod.POST)
	public @ResponseBody String upload(@RequestParam("file") MultipartFile file, @RequestParam(name="videotitle", required=false) String videotitle) {
//		MultipartFile file = videoModel.getFile();
		logger.log(Level.INFO, "Start upload file");
		if (!file.isEmpty()) {
			String name = file.getOriginalFilename();
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = 
                        new BufferedOutputStream(new FileOutputStream(new File(name)));
                stream.write(bytes);
                stream.close();
                Files.copy(file.getInputStream(), Paths.get(uploadLocation).resolve(name));
                VideoModel videoModel = new VideoModel();
                videoModel.setFilepath(Paths.get(uploadLocation).resolve(name).toString());
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
	
	@RequestMapping(value="/info", method= RequestMethod.POST, consumes="application/json")
	public @ResponseBody VideoModel videoInfo(@RequestBody VideoModel videoModel) {
		logger.log(Level.INFO, "Start update information file: " + videoModel.getVideoId().toString());
		VideoModel returnVideo = videoService.save(videoModel);
		return returnVideo;
	}
	
	@RequestMapping(value="/play", method= RequestMethod.GET)
	public ResponseEntity<Resource> play(@RequestParam("file") String fileName) throws IOException {
	    File file = Paths.get(uploadLocation).resolve("SampleVideo.mp4").toFile();
	    FileInputStream is = null;
		is = new FileInputStream(file);
	    InputStreamResource inputStreamResource = new InputStreamResource(is);
	    OutputStream os = new OutputStream() {
			
			@Override
			public void write(int b) throws IOException {
				// TODO Auto-generated method stub
				
			}
		};
//	    ResponseEntity<File> response = new ResponseEntity<>(file, HttpStatus.OK);
//	    response.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//	    headers.setContentLength(inputStreamResource.contentLength());
//	    FileCopyUtils.copy(is, out);
	    return new ResponseEntity<Resource>(inputStreamResource, headers, HttpStatus.OK);
	}
}
