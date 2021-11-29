package com.classit.CourseDiscussionsUI.Models;

import java.io.Serializable;

public class CourseQuestionsModel implements Serializable {

    String id, question, numberOfAnswers, numberOfUpvotes, studentName, studentPhoto;
    Boolean isUpvoted;

    public CourseQuestionsModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getNumberOfUpvotes() {
        return numberOfUpvotes;
    }

    public void setNumberOfUpvotes(String numberOfUpvotes) {
        this.numberOfUpvotes = numberOfUpvotes;
    }

    public Boolean getUpvoted() {
        return isUpvoted;
    }

    public void setUpvoted(Boolean upvoted) {
        isUpvoted = upvoted;
    }

    public String getNumberOfAnswers() {
        return numberOfAnswers;
    }

    public void setNumberOfAnswers(String numberOfAnswers) {
        this.numberOfAnswers = numberOfAnswers;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentPhoto() {
        return studentPhoto;
    }

    public void setStudentPhoto(String studentPhoto) {
        this.studentPhoto = studentPhoto;
    }
}
