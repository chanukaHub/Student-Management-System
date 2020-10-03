package com.WizGuys.eStudent.model;

public class Subject {
    private String Id;
    private String name;
    private String  chapters;
    private String info;

    public Subject() { }

    public Subject(String name, String chapters, String info) {
        this.name = name;
        this.chapters = chapters;
        this.info = info;
    }

    public Subject(String id, String name, String chapters, String info) {
        Id = id;
        this.name = name;
        this.chapters = chapters;
        this.info = info;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChapters(String chapters) {
        this.chapters = chapters;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getChapters() {
        return chapters;
    }

    public String getInfo() {
        return info;
    }
}
