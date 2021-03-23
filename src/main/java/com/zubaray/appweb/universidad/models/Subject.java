package com.zubaray.appweb.universidad.models;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    
    @Temporal(TemporalType.TIMESTAMP) 
	@DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date schedule;

    @ManyToOne()
    @JoinColumn(name="teacher_id", nullable=false)
    private Teacher teacher;

    @Column(name = "maximun_student")
    private int maximunStudent;
}
