/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project7;

import java.util.HashMap;

/**
 *
 * @author youssef
 */
public class QuizAttempt {
    private String attemptId;
    private String quizId;
    private String studentId;
    private int score;
    private HashMap<String, String> answers;
    private int attemptNumber;
    
     public QuizAttempt(String attemptId, String quizId, String studentId, int attemptNumber) {
        this.attemptId = attemptId;
        this.quizId = quizId;
        this.studentId = studentId;
        this.attemptNumber = attemptNumber;
        this.answers = new HashMap<>();
        this.score = 0;
    }
     
     public void addAnswer(String questionId, String chosenOption) {
        answers.put(questionId, chosenOption);
    }
}
