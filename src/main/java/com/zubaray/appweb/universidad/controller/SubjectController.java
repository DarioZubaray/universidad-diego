package com.zubaray.appweb.universidad.controller;


import static com.zubaray.appweb.universidad.constants.PageConstants.EDIT_PAGES;
import static com.zubaray.appweb.universidad.constants.PageConstants.LIST_PAGES;
import static com.zubaray.appweb.universidad.constants.PageConstants.REDIRECT_LIST_PAGES;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zubaray.appweb.universidad.models.ListView;
import com.zubaray.appweb.universidad.models.StandardResponseDto;
import com.zubaray.appweb.universidad.models.Subject;
import com.zubaray.appweb.universidad.services.SubjectService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/subject")
public class SubjectController {
	
	@Autowired
	private SubjectService service;
	
	 @GetMapping("/list")
	    public String list(Model model,
	            @RequestParam(required = false) Integer page,
	            @RequestParam(required = false) Integer size) {
	        log.info("listing all subjects");
	        ListView<Subject> subjects = service.findAll(page, size);
	        model.addAttribute("subjects", subjects.getData());
	        model.addAttribute("subjectPages", subjects);
	        return LIST_PAGES;
	 }
	 
	 @GetMapping("/edit/{id}")
	    public String editView(@PathVariable String id, Model model, RedirectAttributes flash) {
	        log.info("edit view, id: {}", id);
	        model.addAttribute("id", id);
	        try {
	        	Subject subject = service.findSubjectById(id);
	            model.addAttribute("subject", subject);
	            return EDIT_PAGES;
	        } catch (NumberFormatException nfe) {
	            log.error("error in editView: {}", nfe.getMessage());
	            flash.addFlashAttribute("error", nfe.getMessage());
	            return REDIRECT_LIST_PAGES;
	        } catch(NoSuchElementException nsee) {
	            String errorMessage = "There is no Subject with this ID: ".concat(id);
	            log.error("error: {}", errorMessage);
	            flash.addFlashAttribute("error", errorMessage);
	            return REDIRECT_LIST_PAGES;
	        }
	  }
	 
	 @PostMapping(path = "/save",
	            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	    public String save(@Valid @ModelAttribute Subject subject, BindingResult result, RedirectAttributes flash) {
	        log.info("save, subject: {}", subject);
	        if (result.hasErrors()) { 
	            return EDIT_PAGES;
	        }
	        service.save(subject);
	        flash.addFlashAttribute("success", "subject saved successfully");
	        return REDIRECT_LIST_PAGES;
	  }
	 
	 @DeleteMapping("/delete/{id}")
	    @ResponseBody
	    public StandardResponseDto<Object> delete(@PathVariable String id) {
	        try {
	        	Subject subject = service.deleteSubjectById(id);
	            return StandardResponseDto.builder()
	                    .status(true)
	                    .code(HttpStatus.OK.value())
	                    .datetime(LocalDateTime.now())
	                    .data(subject)
	                    .build();
	        } catch (NumberFormatException e) {
	            String message = "Number format exception: ".concat(e.getMessage());
	            log.error("error in delete: {}", message, e);
	            return StandardResponseDto.builder()
	                    .status(false)
	                    .code(HttpStatus.BAD_REQUEST.value())
	                    .datetime(LocalDateTime.now())
	                    .data(message)
	                    .build();
	        } catch(NoSuchElementException nsee) {
	            String message = "There is no Matter with this id.: ".concat(id);
	            log.error("error: {}", message);
	            return StandardResponseDto.builder()
	                    .status(false)
	                    .code(HttpStatus.NOT_FOUND.value())
	                    .datetime(LocalDateTime.now())
	                    .data(message)
	                    .build();
	        }
	    }

	
	
	

}
