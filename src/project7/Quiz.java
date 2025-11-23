/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project7;

import java.util.ArrayList;

/**
 *
 * @author youssef
 */
public class Quiz {
    private String quizId;
    private String lessonId;
    private String title;
    private ArrayList<Question> questions;
    private int maxRetries;
    
    public Quiz(String quizId, String title, ArrayList<Question> questions, int maxRetries,String lessonId) {
        this.quizId = quizId;
        this.title = title;
        this.lessonId=lessonId;
        this.questions = questions;
        this.maxRetries = maxRetries;
    }

    public String getQuizId() {
        return quizId;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public String getTitle() {
        return title;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public String getLessonId() {
        return lessonId;
    }
    
    
    public void addQuestion(Question question) {
        if (this.questions == null) {
            this.questions = new ArrayList<>();
        }
        this.questions.add(question);
    }
}
