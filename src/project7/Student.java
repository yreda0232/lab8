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
        
        private HashMap<String, Boolean> lessonCompleted;
    private HashMap<String, ArrayList<String>> progress;
    private ArrayList<Certificate> certificates = new ArrayList<>();
    private HashMap<String, QuizResults> quizResults = new HashMap<>();

    public Student(String id, String name, String email, String passwordHash) {
        super(id, name, email, passwordHash, "student");
        this.quizResults = new HashMap<>();
        this.lessonCompleted = new HashMap<>();
        this.progress = new HashMap<>();
        this.enrolledCourses = new ArrayList<>();  
    }
    
    
    public void recordQuizResult(String courseId, String lessonId, int score, boolean passed) {

    String key = courseId + "_" + lessonId;

    // Create QuizResults object
    QuizResults result = new QuizResults(lessonId, score, passed);

    quizResults.put(key, result);
    lessonCompleted.put(key, passed);

    if (passed) {
        markLessonCompleted(courseId, lessonId);
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

    // ✅ سطر مهم جدًا للشهادة
    String key = courseId + "_" + lessonId;
    lessonCompleted.put(key, true);
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

    
    public HashMap<String, QuizResults> getQuizResults() {
        return quizResults;
    }


    
    public void addCertificate(Certificate cert)
    {
        if(cert != null)
            this.certificates.add(cert);
    }
   
    public ArrayList<Certificate> getCertificates()
    {
        return certificates;
    }
    
    public String getName() {
        return name;
    }

    
    
   
    
    public ArrayList<String> getEnrolledCourses() {return enrolledCourses;}
    
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
            






public void addQuizResult(String lessonId, QuizResults result) {
    quizResults.put(lessonId, result);
}
}
