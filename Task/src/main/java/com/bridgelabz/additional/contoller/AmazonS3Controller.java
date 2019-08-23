package com.bridgelabz.additional.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.additional.service.AmazonS3ServiceImpl;
import com.bridgelabz.additional.service.AmazonS3ServiceInterface;
import com.bridgelabz.additional.status.ResponseStatus;

@RestController
@RequestMapping("/image")
public class AmazonS3Controller {
	
	@Autowired
	private AmazonS3ServiceInterface iAmazonService;
	

    @Autowired
    AmazonS3Controller(AmazonS3ServiceImpl amazonClient) {
        this.iAmazonService = amazonClient;
    }
	
	@PostMapping(value = "/upload")
	public ResponseEntity<ResponseStatus> uploadFile(@RequestParam(value = "imagefile") MultipartFile imagefile) {
		System.out.println("Upload Picture");
		ResponseStatus response = iAmazonService.uploadFile(imagefile);
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

}
