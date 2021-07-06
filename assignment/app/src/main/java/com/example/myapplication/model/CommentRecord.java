package com.example.myapplication.model;

public class CommentRecord {

    private String comment;
    private String createUser;

    public CommentRecord(String comment, String createUser) {
        this.comment = comment;
        this.createUser = createUser;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Override
    public String toString() {
        return "CommentRecord{" +
                "comment='" + comment + '\'' +
                ", createUser='" + createUser + '\'' +
                '}';
    }
}
