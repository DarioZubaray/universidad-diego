package com.zubaray.appweb.universidad.services;

import com.zubaray.appweb.universidad.models.ListView;
import com.zubaray.appweb.universidad.models.Teacher;

public interface TeacherService {

    ListView<Teacher> findAll(Integer page, Integer size);
}
