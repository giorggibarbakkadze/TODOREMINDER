package com.example.todoreminder;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class TODO  implements Serializable {
    @Exclude private String ide;

    private String title;
    private String todo;
    private String date;




    public TODO() {
    }

    public TODO(String title, String todo, String date) {
        this.title = title;
        this.todo = todo;
        this.date = date;

    }

    public String getIde() {
        return ide;
    }

    public void setIde(String ide) {
        this.ide = ide;
    }

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getTodo() {
        return todo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }
}
