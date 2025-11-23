/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project7;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author 20114
 */
public class Student extends User {

        private ArrayList<String> enrolledCourses; 
        private HashMap<String, Integer> quizResults;
        private HashMap<String, Boolean> lessonCompleted;
    private HashMap<String, ArrayList<String>> progress;
    private ArrayList<Certificate> certificates = new ArrayList<>();

    public Student(String id, String name, String email, String passwordHash) {
        super(id, name, email, passwordHash, "student");
        this.quizResults = new HashMap<>();
        this.lessonCompleted = new HashMap<>();
        this.progress = new HashMap<>();
        this.enrolledCourses = new ArrayList<>();  
    }
    
    public void recordQuizResult(String courseId, int lessonId, int score, boolean passed) {
    String key = courseId + "_" + lessonId;
    quizResults.put(key, score);
    lessonCompleted.put(key, passed);

    if (passed) {
        markLessonCompleted(courseId, String.valueOf(lessonId));
    }
}
    
// --- ENROLLMENT ---
    public void enrollCourse(String courseId) {
        if (!enrolledCourses.contains(courseId)) {
            enrolledCourses.add(courseId);
        }
    }
    public void markLessonCompleted(String courseId, String lessonId) {
        progress.putIfAbsent(courseId, new ArrayList<>());
        ArrayList<String> completed = progress.get(courseId);

        if (!completed.contains(lessonId)) {
            completed.add(lessonId);
        }
    }

    public boolean hasCompletedLesson(String courseId, String lessonId) {
        return progress.containsKey(courseId) 
                && progress.get(courseId).contains(lessonId);
    }
    
    public boolean hasCompletedCourse(Course c) {
    if (!progress.containsKey(c.getCourseId())) return false;

    ArrayList<String> completedLessons = progress.get(c.getCourseId());

    return completedLessons.size() == c.getLessons().size();
}


    public String getName() {
        return name;
    }

    
    
    public boolean hasCompletedLesson(String courseId, int lessonId) {
    String key = courseId + "_" + lessonId;
    return lessonCompleted.getOrDefault(key, false);
    }
    
    public ArrayList<String> getEnrolledCourses() {return enrolledCourses;}
    public HashMap<String, Integer> getQuizResults() { return quizResults; }
    public HashMap<String, Boolean> getLessonCompleted() { return lessonCompleted; }
    public HashMap<String,ArrayList<String>> getProgress() {return progress;}
    public String getId() {return id;}
    
    public static Student getStudentById(String id) {
        Databasef db =new Databasef();
        ArrayList<User> users = db.readUsers();

        for (User u : users) {
            if (u instanceof Student && u.getId().equals(id)) {
                return (Student) u;
            }
        }

        return null;  
}

    
    public ArrayList<Certificate> getCertificates() {return certificates;}
            
}

