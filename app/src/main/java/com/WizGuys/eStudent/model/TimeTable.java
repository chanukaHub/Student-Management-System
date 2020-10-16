package com.WizGuys.eStudent.model;

public class TimeTable {

    String id;
    String grade;
    String date;
    String time8_10;
    String time10_12;
    String time12_2;
    String time2_4;

    public TimeTable() {
    }

    public TimeTable(String grade, String date, String time8_10, String time10_12, String time12_2, String time2_4) {
        this.grade = grade;
        this.date = date;
        this.time8_10 = time8_10;
        this.time10_12 = time10_12;
        this.time12_2 = time12_2;
        this.time2_4 = time2_4;
    }

    @Override
    public String toString() {
        return "TimeTable{" +
                "grade='" + grade + '\'' +
                ", date='" + date + '\'' +
                ", time8_10='" + time8_10 + '\'' +
                ", time10_12='" + time10_12 + '\'' +
                ", time12_2='" + time12_2 + '\'' +
                ", time2_4='" + time2_4 + '\'' +
                '}';
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime8_10() {
        return time8_10;
    }

    public void setTime8_10(String time8_10) {
        this.time8_10 = time8_10;
    }

    public String getTime10_12() {
        return time10_12;
    }

    public void setTime10_12(String time10_12) {
        this.time10_12 = time10_12;
    }

    public String getTime12_2() {
        return time12_2;
    }

    public void setTime12_2(String time12_2) {
        this.time12_2 = time12_2;
    }

    public String getTime2_4() {
        return time2_4;
    }

    public void setTime2_4(String time2_4) {
        this.time2_4 = time2_4;
    }
}