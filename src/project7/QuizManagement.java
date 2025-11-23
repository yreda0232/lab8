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
public class QuizManagement {
    private ArrayList<Quiz> quizzes;
    public QuizManagement() {
        this.quizzes = new ArrayList<>();
    }
    
    public void addQuiz(Quiz quiz) {
        if (!quizzes.contains(quiz)) {
            quizzes.add(quiz);
        }
    }
    
    public void removeQuiz(Quiz quiz) {
        quizzes.remove(quiz);
    }
    public Quiz findQuizById(String quizId) {
        for (Quiz q : quizzes) {
            if (q.getQuizId().equals(quizId)) {
                return q;
            }
        }
        return null;
    }
    
    public ArrayList<Quiz> getQuizzesForLesson(Lesson lesson) {
        ArrayList<Quiz> lessonQuizzes = new ArrayList<>();
        for (Quiz q : quizzes) {
            if (q.getLessonId().equals(lesson.getLessonId())) {
                lessonQuizzes.add(q);
            }
        }
        return lessonQuizzes;
    }
}
