package project7;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a student's attempt for a specific quiz.
 * Each attempt can store multiple scores and answers.
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
        this.scores = new ArrayList<>();
        this.answers = new HashMap<>();
    }

    // ==========================
    // Getters & Setters
    // ==========================
    public String getAttemptId() {
        return attemptId;
    }

    public String getQuizId() {
        return quizId;
    }

    public String getStudentId() {
        return studentId;
    }

    public ArrayList<Integer> getScores() {
        return scores;
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

    // ==========================
    // Methods
    // ==========================
    public void addScore(int score) {
        this.scores.add(score);
        this.attemptNumber = scores.size();

    }

    public void addAnswer(String questionId, String chosenOption) {
        answers.put(questionId, chosenOption);
    }
    

}