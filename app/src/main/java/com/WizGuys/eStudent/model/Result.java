package com.WizGuys.eStudent.model;

public class Result {
    private String index;
    private Integer mark1;
    private Integer mark2;
    private Integer mark3;

    public Result() {
    }

    public Result(String index, Integer mark1, Integer mark2, Integer mark3) {
        this.index = index;
        this.mark1 = mark1;
        this.mark2 = mark2;
        this.mark3 = mark3;
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

    public Integer getMark3() {
        return mark3;
    }

    public void setMark3(Integer mark3) {
        this.mark3 = mark3;
    }

    @Override
    public String toString() {
        return "Result{" +
                "index='" + index + '\'' +
                ", mark1=" + mark1 +
                ", mark2=" + mark2 +
                ", mark3=" + mark3 +
                '}';
    }
}
