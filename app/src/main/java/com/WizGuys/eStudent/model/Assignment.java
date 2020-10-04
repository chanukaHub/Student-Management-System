package com.WizGuys.eStudent.model;

public class Assignment {
    private String index;
    private Integer mark1;
    private Integer mark2;

    public Assignment() {
    }

    public Assignment(String index, Integer mark1, Integer mark2) {
        this.index = index;
        this.mark1 = mark1;
        this.mark2 = mark2;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Integer getMark1() {
        return mark1;
    }

    public void setMark1(Integer mark1) {
        this.mark1 = mark1;
    }

    public Integer getMark2() {
        return mark2;
    }

    public void setMark2(Integer mark2) {
        this.mark2 = mark2;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "index='" + index + '\'' +
                ", mark1=" + mark1 +
                ", mark2=" + mark2 +
                '}';
    }
}
