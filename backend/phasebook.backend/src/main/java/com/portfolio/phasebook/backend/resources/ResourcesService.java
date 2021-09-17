package com.portfolio.phasebook.backend.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResourcesService {
	
	//get resources
	
	public InputStream getResource(String fileName, String fileExtension) throws IOException {
		
		if(fileExtension=="mp4") {
			ClassPathResource imgFile = new ClassPathResource("static/video/"+fileName+"."+fileExtension);
			return imgFile.getInputStream();

		}else if(fileExtension=="jpg") {
			ClassPathResource imgFile = new ClassPathResource("static/image/"+fileName+"."+fileExtension);
			return imgFile.getInputStream();
		}
		return null;
	}

	//create resources
	
	public String createResource(MultipartFile file, String fileExtension) throws IOException, InterruptedException {

		UUID uuid = UUID.randomUUID();
		String fileName = uuid.toString();
		
		if(fileExtension=="mp4") {
			String fileLocation = new File("src\\main\\resources\\static\\video").getAbsolutePath() + "\\" + fileName+".mp4";

			FileOutputStream output = new FileOutputStream(fileLocation);
			output.write(file.getBytes());
			output.flush();
			output.close();
			boolean a=true;
			ClassPathResource uploadedFile = new ClassPathResource("static/video/"+fileName+"."+"mp4");

			while(!uploadedFile.exists()) {
				Thread.sleep(1000);
			}
			
			
			return fileName;
			
		}else if(fileExtension=="jpg") {
			String fileLocation = new File("src\\main\\resources\\static\\image").getAbsolutePath() + "\\" + fileName+".jpg";
			FileOutputStream output = new FileOutputStream(fileLocation);
			output.write(file.getBytes());
			output.flush();
			output.close();
			
			ClassPathResource uploadedFile = new ClassPathResource("static/image/"+fileName+"."+"jpg");

			while(!uploadedFile.exists()) {
				Thread.sleep(1000);
			}

			
			return fileName;
		}
		return null;
	}

	//delete resources
	public void deleteResource(String fileName, String fileExtension) throws IOException {
		
		if(fileExtension=="mp4") {
			String deletingFileName = new File("src\\main\\resources\\static\\video").getAbsolutePath() + "\\" + fileName+".mp4";
			Files.delete(Paths.get(deletingFileName));
			System.out.println("video deleted");
			
		}else if(fileExtension=="jpg") {
			String deletingFileName = new File("src\\main\\resources\\static\\image").getAbsolutePath() + "\\" + fileName+".jpg";
			Files.delete(Paths.get(deletingFileName));
			System.out.println("image deleted");
			
		}

	}
	
	public String setDefaultImage() throws IOException {
		UUID uuid = UUID.randomUUID();
		String fileName = uuid.toString();
		ClassPathResource imgFile = new ClassPathResource("static/Default/dafault.jpg");
		FileOutputStream output = new FileOutputStream(new File("src\\main\\resources\\static\\image").getAbsolutePath() + "\\" + fileName+".jpg");
		output.write(imgFile.getInputStream().readAllBytes());
		output.flush();
		output.close();
		System.out.println("image uuid name is "+fileName);
		return fileName;
	}
	
	public String setDefaultVideo() throws IOException {
		UUID uuid = UUID.randomUUID();
		String fileName = uuid.toString();
		
		ClassPathResource videoFile = new ClassPathResource("static/Default/default.mp4");
		FileOutputStream output = new FileOutputStream(new File("src\\main\\resources\\static\\video").getAbsolutePath() + "\\" + fileName+".mp4");
		output.write(videoFile.getInputStream().readAllBytes());
		output.flush();
		output.close();
		System.out.println("video uuid name is "+fileName);
		return fileName;
	}
	
	public void deleteAllImages() throws IOException {
		FileUtils.cleanDirectory(new File("src/main/resources/static/image"));
		
	}
	
	public void deleteAllVideos() throws IOException {
		FileUtils.cleanDirectory(new File("src/main/resources/static/video"));
		
	}

}
