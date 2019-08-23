package com.bridgelabz.additional.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bridgelabz.additional.model.Image;
import com.bridgelabz.additional.rabbitmq.RabbitmqProducer;
import com.bridgelabz.additional.status.ResponseCode;
import com.bridgelabz.additional.status.ResponseStatus;

@Service("ServiceInterface")
public class AmazonS3ServiceImpl implements AmazonS3ServiceInterface {

	private AmazonS3 s3client;

	@Value("${amazonProperties.endpointurl}")
	private String endpointurl;

	@Value("${amazonProperties.bucketName}")
	private String bucketName;

	@Value("${amazonProperties.accessKey}")
	private String accessKey;

	@Value("${amazonProperties.secretKey}")
	private String secretKey; 

	@Autowired
	private ResponseCode responsecode;
	
	@Autowired
	private RabbitmqProducer rabbitmqProducer;

	private ResponseStatus response;

	@Override
	public ResponseStatus uploadFile(MultipartFile imagefile) {

		Image image = new Image();

		String fileUrl = "";
		try {
			File file = convertMultiPartToFile(imagefile);
			String filename = generateFileName(imagefile);
			fileUrl = endpointurl + "/" + bucketName + "/" + filename;
			System.out.println("\nBefore Rabbitmq :");
			System.out.println("File : "+file);
			System.out.println("Filename : "+filename);
			image.setFilename(filename);
			image.setFile(file);
			System.out.println("image : "+image.toString());
			rabbitmqProducer.produce(image);
			uploadFileTos3bucket(filename, file);
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		response = responsecode.getResponse(200, "Successfull", fileUrl);
		return response;
	}
	
	@SuppressWarnings("deprecation")
	@Bean
	private void initializeAmazon() {
		AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
		this.s3client = new AmazonS3Client(credentials);
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}
	
	public void uploadFileTos3bucket(String fileName, File file) {
		s3client.putObject(
				new PutObjectRequest(bucketName, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
	}

	private String generateFileName(MultipartFile multiPart) {
		return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
	}
}
