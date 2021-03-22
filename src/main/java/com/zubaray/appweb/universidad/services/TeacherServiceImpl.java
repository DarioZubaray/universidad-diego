package com.zubaray.appweb.universidad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.zubaray.appweb.universidad.models.ListView;
import com.zubaray.appweb.universidad.models.Teacher;
import com.zubaray.appweb.universidad.repositories.TeacherRepository;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository repository;

    @Override
    public ListView<Teacher> findAll(Integer page, Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<Teacher> pageTeacher = repository.findAll(pageable);
        Long totalPage = Long.valueOf(pageTeacher.getTotalPages());

        return ListView.<Teacher>builder()
                    .totalPages(totalPage)
                    .currentPage(page).sizeShowed(size)
                    .firstPage(page.equals(0))
                    .lastPage(page.equals(totalPage))
                    .data(pageTeacher.getContent())
                    .build();
    }


    @Override
    public Teacher findTeacherById(String id) {
        Long teacherId = Long.valueOf(id);
        if (teacherId.equals(0L)) {
            return new Teacher();
        } else {
            return repository.findById(teacherId).get();
        }
    }

    @Override
    public void save(Teacher teacher) {
        repository.save(teacher);
    }

    @Override
    public Teacher deleteTeacherById(String id) {
        Long teacherId = Long.valueOf(id);
        Teacher teacher = repository.findById(teacherId).get();
        repository.deleteById(teacherId);
        return teacher;
    }

}
