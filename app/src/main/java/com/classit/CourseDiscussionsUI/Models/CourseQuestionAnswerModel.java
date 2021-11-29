package com.classit.CourseDiscussionsUI.Models;

public class CourseQuestionAnswerModel {

    String answerId, userName, userPhoto;
    String answer, numberOfUpvotes;
    Boolean isUpvoted;

    public CourseQuestionAnswerModel() {
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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
}
