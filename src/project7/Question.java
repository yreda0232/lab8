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
public class Question {
    private String questionId;
    private String text;
    private ArrayList<String> options; 
    private String correctAnswer; 
    
    //no options
    public Question(String questionId, String text, String correctAnswer) {
        this.questionId = questionId;
        this.text = text;
        this.correctAnswer = correctAnswer;
        this.options = new ArrayList<>();
    }
    
    //with options
    public Question(String questionId, String text, ArrayList<String> options, String correctAnswer) {
        this.questionId = questionId;
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getText() {
        return text;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
    
    

    public void setText(String text) {
        this.text = text;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    
    public void addOption(String option) {
        if (!options.contains(option)) {
            options.add(option);
        }
    }

    public void removeOption(String option) {
        options.remove(option);
    }
}
