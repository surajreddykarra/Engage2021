package com.classit.MyTimetableUI.Models;

public class TimetableModel {

    String courseId, courseName, courseFaculty, courseTime;
    int courseSlot;

    public TimetableModel() {
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseFaculty() {
        return courseFaculty;
    }

    public void setCourseFaculty(String courseFaculty) {
        this.courseFaculty = courseFaculty;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public int getCourseSlot() {
        return courseSlot;
    }

    public void setCourseSlot(int courseSlot) {
        this.courseSlot = courseSlot;
    }
}
