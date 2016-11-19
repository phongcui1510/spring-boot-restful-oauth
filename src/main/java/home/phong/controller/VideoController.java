package home.phong.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import home.phong.model.VideoModel;
import home.phong.service.VideoService;

@RestController
@RequestMapping("/video")
public class VideoController {

	@Value("${uploadLocation}")
	private String uploadLocation;

	@Autowired
	private VideoService videoService;

	@Autowired
	ServletContext context;

	private final Logger logger = Logger.getLogger("VideoControllerLogger");

	@RequestMapping(value="/upload", method= RequestMethod.POST)
	public @ResponseBody String upload(@RequestBody MultipartFile file) {
		//	public @ResponseBody String upload(HttpServletRequest request, HttpServletResponse response) {
		//				MultipartFile file = videoModel.getFile();
		logger.log(Level.INFO, "Start upload file");
		/*boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		List fileNames = new ArrayList();
		if (isMultipart) {
			// Create a factory for disk-based file items
			FileItemFactory factory = new DiskFileItemFactory();

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			try {
				// Parse the request
				List items = upload.parseRequest(request);
				logger.log(Level.INFO, "Found: " + items.size() + " files");
				Iterator iterator = items.iterator();
				while (iterator.hasNext()) {
					FileItem item = (FileItem) iterator.next();
					if (!item.isFormField() && !item.getName().equals("")) {
						String fileName = item.getName();
						String root = context.getRealPath("/");
						File path = new File(Paths.get(uploadLocation).toString());
						logger.log(Level.INFO, "Store file path: " + path);
						if (!path.exists()) {
							boolean status = path.mkdirs();
						}

						File uploadedFile = new File(path + "/" + fileName);
						fileNames.add(fileName);
						System.out.println("File Path:-"
								+ uploadedFile.getAbsolutePath());

						item.write(uploadedFile);
					}
				} 
			}catch (FileUploadException e) {
				System.out.println("FileUploadException:- " + e.getMessage());
			} catch (Exception e) {
				System.out.println("Exception:- " + e.getMessage());
			}
			return "successfully";
		}
		return "fail";	*/


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
		//				return "failed";
	}
	@RequestMapping(value="/info", method= RequestMethod.POST, consumes="application/json")
	public @ResponseBody VideoModel videoInfo(@RequestBody VideoModel videoModel) {
		logger.log(Level.INFO, "Start update information file: " + videoModel.getVideoId().toString());
		VideoModel returnVideo = videoService.save(videoModel);
		return returnVideo;
	}

	@RequestMapping(value="/play", method= RequestMethod.GET)
	public ResponseEntity<Resource> play(@RequestParam("videoId") Integer videoId) throws IOException {
		VideoModel model = videoService.findVideoById(videoId);
//		File file = Paths.get(uploadLocation).resolve("SampleVideo.mp4").toFile();
		File file = new File(model.getFilepath());
		FileInputStream is = null;
		is = new FileInputStream(file);
		InputStreamResource inputStreamResource = new InputStreamResource(is);
//		OutputStream os = new OutputStream() {
//
//			@Override
//			public void write(int b) throws IOException {
//				// TODO Auto-generated method stub
//
//			}
//		};
		//	    ResponseEntity<File> response = new ResponseEntity<>(file, HttpStatus.OK);
		//	    response.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		//	    headers.setContentLength(inputStreamResource.contentLength());
		//	    FileCopyUtils.copy(is, out);
		return new ResponseEntity<Resource>(inputStreamResource, headers, HttpStatus.OK);
	}

	//	public @ResponseBody List<VideoModel> getAllVideo () {
	//		
	//	}
}
