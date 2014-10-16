package com.crazydudes.viewpagerexample.data;

/**
 * Simple grade record class
 */
public class Record {

    private int grade;
    private String name;

    public Record(int grade, String name) {
        this.grade = grade;
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public String getName() {
        return name;
    }
}
