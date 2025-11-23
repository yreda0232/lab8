/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project7;

import java.util.ArrayList;

public class Lesson {

    private String lessonId;
    private String title;
    private String content;
    private ArrayList<String> resources;
    private Quiz quiz;
    private ArrayList<Quiz> quizzes;

    // Constructor بدون resources
    public Lesson(String lessonId, String title, String content) {
        this.lessonId = lessonId;
        this.title = title;
        this.content = content;
        this.resources = new ArrayList<>();
        this.quizzes = new ArrayList<>();
    }

    // Constructor بالresources
    public Lesson(String lessonId, String title, String content, ArrayList<String> resources) {
        this.lessonId = lessonId;
        this.title = title;
        this.content = content;
        this.resources = resources;
        this.quizzes = new ArrayList<>();

    }

    
    // Getters
    
    public ArrayList<Quiz> getQuizzes() {
        return quizzes;
    }

    public String getLessonId() {
        return lessonId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<String> getResources() {
        return resources;
    }
    
    public Quiz getQuiz() { return quiz; }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public void setQuiz(Quiz quiz) { this.quiz = quiz; }

    // Manage Resources
    public void addResource(String resource) {
        if (!resources.contains(resource)) {
            resources.add(resource);
        }
    }

    public void removeResource(String resource) {
        resources.remove(resource);
    }
    
    public void setResources(ArrayList<String> resources) {
        this.resources = resources;
    }
    
    public void addQuiz(Quiz quiz) {
    if (this.quizzes == null) {
        this.quizzes = new ArrayList<>();
    }
    this.quizzes.add(quiz);
    }


}
