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
    private ArrayList<String> choices; 
    private String correctAnswer; 
    
    public Question(String questionId, String text, ArrayList<String> options, String correctAnswer) {
        this.questionId = questionId;
        this.text = text;
        this.choices = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getText() {
        return text;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
    
    

    public void setText(String text) {
        this.text = text;
    }

    public void setOptions(ArrayList<String> options) {
        this.choices = options;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    
    public void addOption(String option) {
        if (!choices.contains(option)) {
            choices.add(option);
        }
    }

    public void removeOption(String option) {
        choices.remove(option);
    }
}
