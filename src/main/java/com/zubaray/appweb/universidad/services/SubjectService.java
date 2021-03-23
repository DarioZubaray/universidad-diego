package com.zubaray.appweb.universidad.services;

import com.zubaray.appweb.universidad.models.ListView;
import com.zubaray.appweb.universidad.models.Subject;

public interface SubjectService {

	ListView<Subject> findAll(Integer page, Integer size);

	Subject findSubjectById(String id);

	void save(Subject Subject);

	Subject deleteSubjectById(String id);

}
