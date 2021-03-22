package com.zubaray.appweb.universidad.controller;

import static com.zubaray.appweb.universidad.constants.TeacherConstants.EDIT_PAGE;
import static com.zubaray.appweb.universidad.constants.TeacherConstants.LIST_PAGE;
import static com.zubaray.appweb.universidad.constants.TeacherConstants.REDIRECT_LIST_PAGE;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zubaray.appweb.universidad.models.Teacher;
import com.zubaray.appweb.universidad.repositories.TeacherRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ManageController {

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
        Optional<Teacher> opt;
        try {
            Long teacherId = Long.valueOf(id);
            if (teacherId.equals(0L)) {
                model.addAttribute("teacher", new Teacher());
                return EDIT_PAGE;
            }
            opt = repository.findById(Long.valueOf(id));
            if (opt.isEmpty()) {
                log.error("error: {}", "No existe teacher con ese id");
                flash.addFlashAttribute("error", "No existe teacher con ese id");
                return REDIRECT_LIST_PAGE;
            }
        } catch (NumberFormatException e) {
            log.error("error: {}", e.getMessage(), e);
            flash.addFlashAttribute("error", e.getMessage());
            return REDIRECT_LIST_PAGE;
        }
        model.addAttribute("teacher", opt.get());
        return EDIT_PAGE;
    }

    @PostMapping(path = "/save",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String edit(@ModelAttribute Teacher teacher) {
        log.info("edit, teacher: {}", teacher);
        repository.save(teacher);
        return REDIRECT_LIST_PAGE;
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, Model model, RedirectAttributes flash) {
        try {
            Long teacherId = Long.valueOf(id);
            repository.deleteById(teacherId);
        } catch (NumberFormatException e) {
            log.error("error: {}", e.getMessage(), e);
            flash.addFlashAttribute("error", e.getMessage());
        }
        return REDIRECT_LIST_PAGE;
    }

}
