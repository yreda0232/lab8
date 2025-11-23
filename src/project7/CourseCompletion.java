/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project7;

import java.util.ArrayList;

/**
 *
 * @author cs
 */
public class CourseCompletion {
public static boolean isCourseCompleted(Student student, Course course) {
        ArrayList<Lesson> lessons = course.getLessons();

        for (Lesson lesson : lessons) {
            // check all quizzes in this lesson
            for (Quiz quiz : lesson.getQuizzes()) {
                int lessonId = Integer.parseInt(lesson.getLessonId()); // assuming lessonId is integer string
                if (!student.hasCompletedLesson(course.getCourseId(), lessonId)) {
                    return false; // if any quiz is not passed, course is not completed
                }
            }
        }

        // all lessons' quizzes passed
        return true;
    }

}
