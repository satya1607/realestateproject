package com.example.realestateproject.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.realestateproject.entity.Image;
import com.example.realestateproject.repository.ImageRepository;
@Service
public class ImageService {

	@Autowired
	private ImageRepository imageRepository;
	public List getAllImages() {
	    return imageRepository.findAll();
	}

	public Optional<Image> getImageById(Long id) {
	    return imageRepository.findById(id);
	}
	
	public Image save(Image image,MultipartFile file) throws IOException {
//		 Image image = new Image();
	        image.setImageName(file.getOriginalFilename());      // store original filename
	        image.setContentType(file.getContentType());    // store file type
	        image.setData(file.getBytes());                 // store file bytes

	        return imageRepository.save(image);
         
    }
	 public String convertToBase64(Image image) {
	        return Base64.getEncoder().encodeToString(image.getData());
	    }
	
}
