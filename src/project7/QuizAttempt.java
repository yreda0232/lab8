/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project7;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author youssef
 */
public class QuizAttempt {
    private String attemptId;
    private String quizId;
    private String studentId;
    private ArrayList<Integer> scores;
    private HashMap<String, String> answers;
    private int attemptNumber;
    
     public QuizAttempt(String attemptId, String quizId, String studentId, int attemptNumber) {
        this.attemptId = attemptId;
        this.quizId = quizId;
        this.studentId = studentId;
        this.attemptNumber = attemptNumber;
        this.answers = new HashMap<>();
        this.scores = new ArrayList<>();
    }

    public String getAttemptId() {
        return attemptId;
    }

    public String getQuizId() {
        return quizId;
    }

    public String getStudentId() {
        return studentId;
    }


    public HashMap<String, String> getAnswers() {
        return answers;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(int attemptNumber) {
        this.attemptNumber = attemptNumber;
    }
     
    public void addScore(int score) {
        this.scores.add(score);
        this.attemptNumber = scores.size();
    }

    public ArrayList<Integer> getScores() {
        return scores;
    }
     

     
     public void addAnswer(String questionId, String chosenOption) {
        answers.put(questionId, chosenOption);
    }
     
}
