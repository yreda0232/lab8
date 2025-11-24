/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project7;

/**
 *
 * @author DELL
 */
import java.util.HashMap;




public class QuizResults {
    private String quizId;
    private String lessonId;
    private int score;
    private boolean passed;
    private int attempts;

    public QuizResults(String quizId, String lessonId, int score, boolean passed, int attempts) {
        this.quizId = quizId;
        this.lessonId = lessonId;
        this.score = score;
        this.passed = passed;
        this.attempts = attempts;
    }

    public String getQuizId() { return quizId; }
    public String getLessonId() { return lessonId; }
    public int getScore() { return score; }
    public boolean isPassed() { return passed; }
    public int getAttempts() { return attempts; }

    public void setScore(int score) { this.score = score; }
    public void setPassed(boolean passed) { this.passed = passed; }
    public void setAttempts(int attempts) { this.attempts = attempts; }
}
