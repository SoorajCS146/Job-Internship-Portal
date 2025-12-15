package com.jobportal.student_server.model;

import jakarta.persistence.*;

@Entity
@Table
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String usn;

    private String name;
    private String email;
    private String branch;
    private int semester;
    private double cgpa;

    private String resumeUrl; // Link to uploaded resume

    // Constructors
    public Student() {}

    public Student(String usn, String name, String email, String branch, int semester, double cgpa, String resumeUrl) {
        this.usn = usn;
        this.name = name;
        this.email = email;
        this.branch = branch;
        this.semester = semester;
        this.cgpa = cgpa;
        this.resumeUrl = resumeUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }
}

