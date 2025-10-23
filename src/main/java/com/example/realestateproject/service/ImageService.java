package com.example.realestateproject.service;

import java.io.IOException;
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

	public Optional getImageById(String id) {
	    return imageRepository.findById(id);
	}
	
	public void save(Image image, MultipartFile file) {
        try {
            image.setData(file.getBytes());
            imageRepository.save(image);
        } catch (IOException ex) {
            Logger.getLogger(ImageService.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }

	
}
