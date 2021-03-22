package com.zubaray.appweb.universidad.controller;

import static com.zubaray.appweb.universidad.constants.TeacherConstants.EDIT_PAGE;
import static com.zubaray.appweb.universidad.constants.TeacherConstants.LIST_PAGE;
import static com.zubaray.appweb.universidad.constants.TeacherConstants.REDIRECT_LIST_PAGE;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zubaray.appweb.universidad.models.StandardResponseDto;
import com.zubaray.appweb.universidad.models.Teacher;
import com.zubaray.appweb.universidad.repositories.TeacherRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherRepository repository;

    @GetMapping("/list")
    public String list(Model model) {
        log.info("listing all teachers");
        model.addAttribute("teachers", repository.findAll());
        return LIST_PAGE;
    }

    @GetMapping("/edit/{id}")
    public String editView(@PathVariable String id, Model model, RedirectAttributes flash) {
        log.info("edit view, id: {}", id);
        model.addAttribute("id", id);
        Teacher teacher;
        try {
            Long teacherId = Long.valueOf(id);
            if (teacherId.equals(0L)) {
                teacher = new Teacher();
            } else {
                teacher = repository.findById(teacherId).get();
            }
        } catch (NumberFormatException nfe) {
            log.error("error in editView: {}", nfe.getMessage());
            flash.addFlashAttribute("error", nfe.getMessage());
            return REDIRECT_LIST_PAGE;
        } catch(NoSuchElementException nsee) {
            String errorMessage = "There is no Teacher with this id: ".concat(id);
            log.error("error: {}", errorMessage);
            flash.addFlashAttribute("error", errorMessage);
            return REDIRECT_LIST_PAGE;
        }
        model.addAttribute("teacher", teacher);
        return EDIT_PAGE;
    }

    @PostMapping(path = "/save",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String save(@ModelAttribute Teacher teacher, RedirectAttributes flash) {
        log.info("save, teacher: {}", teacher);
        repository.save(teacher);
        flash.addFlashAttribute("success", "teacher saved successfully");
        return REDIRECT_LIST_PAGE;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public StandardResponseDto<Object> delete(@PathVariable String id) {
        Teacher teacher = null;
        try {
            Long teacherId = Long.valueOf(id);
            teacher = repository.findById(teacherId).get();
            repository.deleteById(teacherId);
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
            String message = "There is no Teacher with this id: ".concat(id);
            log.error("error: {}", message);
            return StandardResponseDto.builder()
                    .status(false)
                    .code(HttpStatus.NOT_FOUND.value())
                    .datetime(LocalDateTime.now())
                    .data(message)
                    .build();
        }
        return StandardResponseDto.builder()
                    .status(true)
                    .code(HttpStatus.OK.value())
                    .datetime(LocalDateTime.now())
                    .data(teacher)
                    .build();
    }

}
