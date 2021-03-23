package com.zubaray.appweb.universidad.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zubaray.appweb.universidad.models.Subject;


@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long>{

}
