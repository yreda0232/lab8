/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*package project7;

import java.time.LocalDate;
import java.util.ArrayList;


public class CourseCompletion {
    
    private ArrayList<Certificate> certRepo;

    public CourseCompletion(ArrayList<Certificate> certRepo) {
        certRepo = new ArrayList<>();
    }
    
    
    
public static boolean isCourseCompleted(String studentId, String courseId) {
        Course course = Course.getCourseById(courseId);
        Student student = Student.getStudentById(studentId);
    ArrayList<Lesson> lessons = course.getLessons();
   

        for (Lesson lesson : lessons) {
            // check all quizzes in this lesson
           
                String lessonId =lesson.getLessonId(); // assuming lessonId is integer string
                if (!student.hasCompletedLesson(course.getCourseId(), lessonId)) {
                    return false; // if any quiz is not passed, course is not completed
                }
            
        }

        // all lessons' quizzes passed
        return true;
    }


public Certificate generateCertificate(String studentId, String courseId) {
        if (!isCourseCompleted(studentId, courseId)) {
            throw new RuntimeException("Course not completed yet!");
        }
        
        String date = java.time.LocalDate.now().toString();
        String certId = "CERT-" + System.currentTimeMillis();
       Certificate cert = new Certificate(certId, studentId, courseId, date);
        // ممكن تضيف رقم شهادة فريد هنا
        certRepo.add(cert);
        return cert;
    }

}*/
