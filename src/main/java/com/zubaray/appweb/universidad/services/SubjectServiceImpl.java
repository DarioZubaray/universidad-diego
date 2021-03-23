package com.zubaray.appweb.universidad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.zubaray.appweb.universidad.models.ListView;
import com.zubaray.appweb.universidad.models.Subject;
import com.zubaray.appweb.universidad.repositories.SubjectRepository;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectRepository repository;

	@Override
	public ListView<Subject> findAll(Integer page, Integer size) {
		if (page == null) {
			page = 0;
		}
		if (size == null) {
			size = 5;
		}

		Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
		Page<Subject> pageSubject = repository.findAll(pageable);
		Long totalPage = Long.valueOf(pageSubject.getTotalPages());

		return ListView.<Subject>builder().totalPages(totalPage).currentPage(page).sizeShowed(size)
				.firstPage(page.equals(0)).lastPage(page.equals(totalPage)).data(pageSubject.getContent()).build();
	}

	@Override
	public Subject findSubjectById(String id) {
		Long subjectId = Long.valueOf(id);
		if (subjectId.equals(0L)) {
			return new Subject();
		} else {
			return repository.findById(subjectId).get();
		}
	}

	@Override
	public void save(Subject subject) {
		repository.save(subject);
	}

	@Override
	public Subject deleteSubjectById(String id) {
		Long SubjectId = Long.valueOf(id);
		Subject subject = repository.findById(SubjectId).get();
		repository.deleteById(SubjectId);
		return subject;
	}

}
