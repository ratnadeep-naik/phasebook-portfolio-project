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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class AWSResourcesService {
	
	@Autowired
	private AmazonS3Client awsS3Client;

	//create resources
	public String createResource(MultipartFile file, String fileExtension) throws IOException, InterruptedException {

		String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
		String key = UUID.randomUUID().toString()+"."+extension;
		
		System.out.println("@@@@@@@@uuid key is "+key);
		
		ObjectMetadata metaData = new ObjectMetadata();
		metaData.setContentLength(file.getSize());
		metaData.setContentType(file.getContentType());
		
		try {
			awsS3Client.putObject("phasebookfilebucket", key, file.getInputStream(), metaData);
		}catch(IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error occured while uploading file");
		}
		awsS3Client.setObjectAcl("phasebookfilebucket", key, CannedAccessControlList.PublicRead);
		return key;
	}

	//get resources
	public InputStream getResource(String fileName, String fileExtension) throws IOException {
		return  awsS3Client.getObject("phasebookfilebucket", fileName).getObjectContent().getDelegateStream();
	}
	
	//delete resources
	public void deleteResource(String fileName, String fileExtension) throws IOException {
		awsS3Client.deleteObject("phasebookfilebucket", fileName);		

	}

}
