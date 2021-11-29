package com.classit.CoursesHelper.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class CoursesModel implements Serializable {

    String courseName, courseId, courseFaculty;
    ArrayList<String> courseDays;
    String courseSlot, courseTime;

    public CoursesModel() {
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseFaculty() {
        return courseFaculty;
    }

    public void setCourseFaculty(String courseFaculty) {
        this.courseFaculty = courseFaculty;
    }

    public ArrayList<String> getCourseDays() {
        return courseDays;
    }

    public void setCourseDays(ArrayList<String> courseDays) {
        this.courseDays = courseDays;
    }

    public String getCourseSlot() {
        return courseSlot;
    }

    public void setCourseSlot(String courseSlot) {
        this.courseSlot = courseSlot;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }
}
