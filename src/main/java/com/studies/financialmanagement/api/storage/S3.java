package com.studies.financialmanagement.api.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.studies.financialmanagement.api.config.property.ApiProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@Component
public class S3 {
	
	private static final Logger logger = LoggerFactory.getLogger(S3.class);
	private static final String agnosticProtocol = "\\\\";
	
	@Autowired
	private ApiProperty property;
	
	@Autowired
	private AmazonS3 amazonS3;
	
	public String tmpSave(MultipartFile arquivo) {
		AccessControlList acl = new AccessControlList();
		acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
		
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(arquivo.getContentType());
		objectMetadata.setContentLength(arquivo.getSize());
		
		String nomeUnico = generateUniqueName(arquivo.getOriginalFilename());
		
		try {
			PutObjectRequest putObjectRequest = new PutObjectRequest(
					property.getS3().getBucket(),
					nomeUnico,
					arquivo.getInputStream(), 
					objectMetadata)
					.withAccessControlList(acl);
			
			putObjectRequest.setTagging(new ObjectTagging(
					Arrays.asList(new Tag("expire", "true"))));
			
			amazonS3.putObject(putObjectRequest);
			
			if (logger.isDebugEnabled())
				logger.debug("File {} successfully sent to S3.", arquivo.getOriginalFilename());
			
			return nomeUnico;

		} catch (IOException e) {
			throw new RuntimeException("An error has occurred trying to send the file to S3.", e);
		}
	}
	
	public String setUrl(String object) {
		return agnosticProtocol + property.getS3().getBucket() + ".s3.amazonaws.com/" + object;
	}

	private String generateUniqueName(String originalFilename) {
		return UUID.randomUUID() + "_" + originalFilename;
	}

}
