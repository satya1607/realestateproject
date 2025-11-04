package com.example.realestateproject.com.example.realestateproject.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
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
import com.example.realestateproject.entity.UserInfo;
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
	 public String home(PropertyDetails propertyDetails, Model model, String keyword,Integer price) {
	  if(keyword!=null) {
	   List<PropertyDetails> list = service.getByKeyword(keyword,price);
	   model.addAttribute("list", list);
	  }else {
	  List<PropertyDetails> list = service.getAllProperties();
	  List<Image> images = imageService.getAllImages();
	  
	  Map<String, String> imageMap = new HashMap<>();
	    for (Image img : images) {
	        imageMap.put(img.getId(), imageService.convertToBase64(img));
	    }

	    model.addAttribute("list", list);
	    model.addAttribute("imageMap", imageMap);
	  System.out.println(list);
	  System.out.println(imageMap);
	  }
	  return "index";
	 }
	
//	 @PostMapping(value = "/propertyDetails/add")
//	  public String handleProfileAdd(PropertyDetails propertyDetails, @RequestPart("file") MultipartFile file) {
//
////	    log.info("handling request parts: {}", file);	    
//		 service.save(propertyDetails, file);
//	    return "redirect:/search";
//	 }	  

	 
	 @GetMapping("/customer/create")
	    public String showaddPropertyForm(Model model){
	        model.addAttribute("propertyDetails",new PropertyDetails());
//	        model.addAttribute("images", imageService.getAllImages());
	        return "customerpost";
	    }

	 @PostMapping("/customer/create")
	 public String createProperty(
	         @ModelAttribute("propertyDetails") PropertyDetails propertyDetails,
	         @RequestParam("file") MultipartFile file) throws IOException {
	      
	    	 Image savedImage = imageService.save(new Image(), file);
	        propertyDetails.setImageId(savedImage.getId());
 
	     customerService.saveDetails(propertyDetails);

	     return "redirect:/search";
	 }

@GetMapping("/customer/display")
public List<PropertyDetails> displayProperties() {
	return customerService.getProperties();
}

@PutMapping("/customer/edit/{id}")
public String editProperty(@ModelAttribute PropertyDetails propertyDetails,@PathVariable String id) {
	customerService.editProperty(id,propertyDetails);
	return "redirect:/userdashboard";
}

@GetMapping("/customer/edit/{id}")
public String showEditForm(@PathVariable String id, Model model) throws Exception {
    PropertyDetails property = customerService.getPropertyById(id);
    model.addAttribute("property", property);
    return "editpropertyform";   // name of Thymeleaf template for editing
}

@GetMapping("/customer/delete/{id}")
public String deletePropertyForCustomer(@PathVariable String id) {
    customerService.deleteProperty(id);
    return "redirect:/userdashboard";
}

@GetMapping("/admin/delete/{id}")
public String deleteProperty(@PathVariable String id) {
    customerService.deleteProperty(id);
    return "redirect:/admindashboard";
}

}
