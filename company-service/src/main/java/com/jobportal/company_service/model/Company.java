package com.jobportal.company_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String companyName;

    private String industry;
    private String description;
    private String website;

    @Column(nullable = false)
    private String contactEmail;

    private String contactPhone;

    public Company() {}

    public Company(Long id, String companyName, String industry, String description, String website, String contactEmail, String contactPhone) {
        this.id = id;
        this.companyName = companyName;
        this.industry = industry;
        this.description = description;
        this.website = website;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
    }

    public Long getId() {        return id;    }
    public String getCompanyName() {        return companyName;    }
    public String getIndustry() {        return industry;    }
    public String getDescription() {        return description;    }
    public String getWebsite() {        return website;    }
    public String getContactEmail() {        return contactEmail;    }
    public String getContactPhone() {        return contactPhone;    }

    public void setId(Long id) {        this.id = id;    }
    public void setCompanyName(String companyName) {        this.companyName = companyName;    }
    public void setIndustry(String industry) {        this.industry = industry;    }
    public void setDescription(String description) {        this.description = description;    }
    public void setWebsite(String website) {        this.website = website;    }
    public void setContactEmail(String contactEmail) {        this.contactEmail = contactEmail;    }
    public void setContactPhone(String contactPhone) {        this.contactPhone = contactPhone;    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", industry='" + industry + '\'' +
                ", description='" + description + '\'' +
                ", website='" + website + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                '}';
    }
}
