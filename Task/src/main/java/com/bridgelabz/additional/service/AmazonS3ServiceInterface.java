package com.bridgelabz.additional.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.additional.status.ResponseStatus;

@Service
public interface AmazonS3ServiceInterface {

	public ResponseStatus uploadFile(MultipartFile imagefile);

}
