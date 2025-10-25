package com.example.realestateproject.com.example.realestateproject.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.realestateproject.entity.Image;
import com.example.realestateproject.entity.PropertyDetails;
import com.example.realestateproject.repository.ImageRepository;
import com.example.realestateproject.service.CustomerService;
import com.example.realestateproject.service.ImageService;
import com.example.realestateproject.service.PropertyDetailsService;

@Controller
public class CustomerController { 
	
	private static final String[] MULTIPART_FORM_DATA_VALUE = null;

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private PropertyDetailsService service;
	@Autowired
	private ImageService imageService;
	@Autowired
	private ImageRepository imageRepository;
	
	 @RequestMapping(path = {"/","/search"})
	 public String home(PropertyDetails propertyDetails, Model model, String keyword) {
	  if(keyword!=null) {
	   List<PropertyDetails> list = service.getByKeyword(keyword);
	   model.addAttribute("list", list);
	  }else {
	  List<PropertyDetails> list = service.getAllProperties();
	  System.out.println(list);
	  model.addAttribute("list", list);}
	  return "index";
	 }
	
	 @GetMapping(value = "/image/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
	  public Resource downloadImage(@PathVariable Long imageId) {
	    byte[] image = service.findById(imageId)
	        .orElseThrow()
	        .getImageData();

	    return new ByteArrayResource(image);
	  }
	 @PostMapping(value = "/propertyDetails/add")
	  public String handleProfileAdd(PropertyDetails propertyDetails, @RequestPart("file") MultipartFile file) {

//	    log.info("handling request parts: {}", file);	    
		 service.save(propertyDetails, file);
	    return "redirect:/index";
	  }

	 
	 @GetMapping("/customer/create")
	    public String showaddPropertyForm(Model model){
	        model.addAttribute("propertyDetails",new PropertyDetails());
//	        model.addAttribute("images", imageService.getAllImages());
	        return "customerpost";
	    }
	 

	 
@PostMapping("/customer/create")
public String createProperty(MultipartFile file,Image image,@ModelAttribute("propertyDetails") PropertyDetails propertyDetails) throws IOException {
	
//		Image image = new Image();
//        image.setName(file.getOriginalFilename());
//        image.setContentType(file.getContentType());
        image.setData(file.getBytes());
        imageService.save(image,file);
        customerService.saveDetails(propertyDetails);
        
//        model.addAttribute("message", "Image uploaded successfully!");
//    
//	customerService.saveDetails(propertyDetails,image);
//	customerService.saveDetails(propertyDetails);
	return "redirect:/";
}



@GetMapping("/customer/display")
public List<PropertyDetails> displayProperties() {
	return customerService.getProperties();
}

@PutMapping("/customer/edit/{id}")
public PropertyDetails editProperty(@RequestBody PropertyDetails propertyDetails,@PathVariable Long id) {
	return customerService.editProperty(id,propertyDetails);
}
	
@DeleteMapping("/customer/delete/{id}")
public void deleteProperty(@PathVariable Long id) {
    customerService.deleteProperty(id);
}

}
