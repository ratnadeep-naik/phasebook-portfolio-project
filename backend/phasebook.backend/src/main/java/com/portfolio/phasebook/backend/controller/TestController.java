package com.portfolio.phasebook.backend.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.phasebook.backend.repository.CommentsRepository;
import com.portfolio.phasebook.backend.repository.FriendChatRepository;
import com.portfolio.phasebook.backend.repository.FriendRepository;
import com.portfolio.phasebook.backend.repository.FriendRequestRepository;
import com.portfolio.phasebook.backend.repository.GroupChatRepository;
import com.portfolio.phasebook.backend.repository.GroupJoinRequestRepository;
import com.portfolio.phasebook.backend.repository.GroupMemberRepository;
import com.portfolio.phasebook.backend.repository.GroupRepository;
import com.portfolio.phasebook.backend.repository.LikesRepository;
import com.portfolio.phasebook.backend.repository.PostRepository;
import com.portfolio.phasebook.backend.repository.UserRepository;
import com.portfolio.phasebook.backend.resources.ResourcesService;
import com.portfolio.phasebook.backend.service.CommentsService;
import com.portfolio.phasebook.backend.service.FriendChatService;
import com.portfolio.phasebook.backend.service.FriendRequestService;
import com.portfolio.phasebook.backend.service.FriendService;
import com.portfolio.phasebook.backend.service.GroupChatService;
import com.portfolio.phasebook.backend.service.GroupJoinRequestService;
import com.portfolio.phasebook.backend.service.GroupMemberService;
import com.portfolio.phasebook.backend.service.GroupService;
import com.portfolio.phasebook.backend.service.LikesService;
import com.portfolio.phasebook.backend.service.PostService;
import com.portfolio.phasebook.backend.service.UserService;


@RestController
@RequestMapping(value = "test")
public class TestController {

	@Autowired
	ResourcesService resourcesService;
	
	@Autowired
	CommentsService commentsService;

	@Autowired
	FriendChatService friendChatService;

	@Autowired
	FriendRequestService friendRequestService;

	@Autowired
	FriendService friendService;

	@Autowired
	GroupChatService groupChatService;

	@Autowired
	GroupJoinRequestService groupJoinRequestService;

	@Autowired
	GroupMemberService groupMemberService;

	@Autowired
	GroupService groupService;

	@Autowired
	LikesService likesService;

	
	@Autowired
	PostService postService;

	@Autowired
	UserService userService;


	@Autowired
	CommentsRepository commentsRepository;

	@Autowired
	FriendChatRepository friendChatRepository;

	@Autowired
	FriendRequestRepository friendRequestRepository;

	@Autowired
	FriendRepository friendRepository;

	@Autowired
	GroupChatRepository groupChatRepository;

	@Autowired
	GroupJoinRequestRepository groupJoinRequestRepository;

	@Autowired
	GroupMemberRepository groupMemberRepository;

	@Autowired
	GroupRepository groupRepository;

	@Autowired
	LikesRepository likesRepository;

	
	@Autowired
	PostRepository postRepository;

	@Autowired
	UserRepository userRepository; 
	
	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;

	//serve video
	@GetMapping(value = "/videosrc", produces = "video/mp4")
	@ResponseBody
	public FileSystemResource videoSource() throws IOException {//@RequestParam(value="id", required=true) int id
		ClassPathResource videoFile = new ClassPathResource("static/video/98638a74-2d21-494d-b005-fe8ab339388e.mp4");
	    return new FileSystemResource(videoFile.getFile());
	}
	
	//serve video
	@GetMapping(value = "videos")	//videos/{id}/{name}
	@ResponseBody
	public final ResponseEntity<InputStreamResource>
	retrieveResource() throws Exception {
		ClassPathResource videoFile = new ClassPathResource("static/video/003f6e22-66be-46da-850d-cedd102d4767.mp4");
		InputStream targetStream  = videoFile.getInputStream();
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.valueOf("video/mp4"));
	    headers.set("Accept-Ranges", "bytes");
	    headers.set("Expires", "0");
	    headers.set("Cache-Control", "no-cache, no-store");
	    headers.set("Connection", "keep-alive");
	    headers.set("Content-Transfer-Encoding", "binary");
	    return new ResponseEntity<>(new InputStreamResource(targetStream), headers, HttpStatus.OK);
	}
	
	//serve an image
	@GetMapping(value = "/serveimages", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getImage(HttpServletResponse response) throws IOException {
//		ClassPathResource imgFile = new ClassPathResource("static/image/journey.jpg");
//        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
		
		String fileName = "journey";
		String fileExtension = "jpg";
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		InputStream data = resourcesService.getResource(fileName, fileExtension);
		
		if(data==null) {
			response.sendError(400, "image not found");
	
		}else {
			StreamUtils.copy(data, response.getOutputStream());
		}
    }

	//download an file
	@GetMapping("/resource")
	public ResponseEntity<Object> resourcs() throws IllegalStateException, IOException {
		String fileName= "journey.jpg";
		File file = new File("src\\main\\resources\\static\\image\\"+fileName);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);
		return responseEntity;
	}
	
	@PostMapping(value = "uploadFile1", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String uploadFile1(
			@RequestParam(value = "file", required = true) MultipartFile file,
			@RequestParam(value = "fileType", required = true) String fileType
			) 
	{
		String fileName = Long.toString(System.currentTimeMillis()) ;
		 if(fileType.equalsIgnoreCase("image")) {
			 try {
		            Path path=Paths.get("src/main/resources/static/image/"+fileName+".jpg");
		            Files.write(path,file.getBytes());
		            
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			 
		 }else if(fileType.equalsIgnoreCase("video")) {
			 try {
		            Path path=Paths.get("src/main/resources/static/video/"+fileName+".mp4");
		            Files.write(path,file.getBytes());
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		 }
		return fileName;
	}
	

	@GetMapping("/downloadFile1")
	public ResponseEntity<Object> downloadFile1(
			@RequestParam(value = "fileName", required = true) String fileName,
			@RequestParam(value = "fileType", required = true) String fileType
			) throws IllegalStateException, IOException {
		
		String requestedFileFolder = "";
		String requestedFileExtension = "";
		
		if(fileType.equalsIgnoreCase("video")) {
			requestedFileFolder = "video";
			requestedFileExtension = "mp4";
			
		}else if(fileType.equalsIgnoreCase("image")) {
			requestedFileFolder = "image";
			requestedFileExtension = "jpg";
		}

		File file = new File("src/main/resources/static/"+requestedFileFolder+"/"+fileName+"."+requestedFileExtension);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);
		return responseEntity;
	}

	
	@GetMapping(value = "/serveImage1", produces = MediaType.IMAGE_JPEG_VALUE)
    public void serveImage1(HttpServletResponse response,
			@RequestParam(value = "fileName", required = true) String fileName
    		) throws IOException {

		String fileExtension = "jpg";
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		InputStream data = resourcesService.getResource(fileName, fileExtension);
		
		if(data==null) {
			response.sendError(400, "image not found");
	
		}else {
			StreamUtils.copy(data, response.getOutputStream());
		}
    }

	
	//serve video
	@GetMapping(value = "serveVideo1")	//videos/{id}/{name}
	@ResponseBody
	public final ResponseEntity<InputStreamResource> serveVideo1(
			@RequestParam(value = "fileName", required = false) String fileName
			) throws Exception {
		ClassPathResource videoFile = new ClassPathResource("static/video/horror.mp4");
		InputStream targetStream  = videoFile.getInputStream();
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.valueOf("video/mp4"));
	    headers.set("Accept-Ranges", "bytes");
	    headers.set("Expires", "0");
	    headers.set("Cache-Control", "no-cache, no-store");
	    headers.set("Connection", "keep-alive");
	    headers.set("Content-Transfer-Encoding", "binary");
	    return new ResponseEntity<>(new InputStreamResource(targetStream), headers, HttpStatus.OK);
	}

}
