package com.WizGuys.eStudent.model;

public class TeachersObj {
    private String id;
    private String name;
    private String address;
    private String contact;
    private String description;
    private String email;
    private String qualification;
    private String salary;

    public TeachersObj(String id, String name, String address, String contact, String description, String email, String qualification, String salary) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.description = description;
        this.email = email;
        this.qualification = qualification;
        this.salary = salary;
    }

    public TeachersObj() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "TeachersObj{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", qualification='" + qualification + '\'' +
                ", salary='" + salary + '\'' +
                '}';
    }
}
