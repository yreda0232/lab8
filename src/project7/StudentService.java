/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project7;

import java.util.ArrayList;

public class StudentService {

    private Databasef db;

    public StudentService() {
        this.db = new Databasef();
    }

    // ------------------------------------------------------------
    // 1) Browse all available courses (for student)
    // ------------------------------------------------------------
    public ArrayList<Course> getAllCourses() {
        return db.readCourses();
    }

    // ------------------------------------------------------------
    // 2) Enroll in a course
    // ------------------------------------------------------------
    public boolean enrollInCourse(Student student, String courseId) {

        ArrayList<Course> courses = db.readCourses();

        for (Course c : courses) {
            if (c.getCourseId().equals(courseId)) {
                
                // لو هو متسجل قبل كده
                if (student.getEnrolledCourses().contains(courseId))
                    return false;

                // سجل عند الطالب
                student.enrollCourse(courseId);

                // ضيف الطالب داخل الكورس
                c.addStudent(student);

                // حفظ
                ArrayList<User> users = db.readUsers();
for (int i = 0; i < users.size(); i++) {
    if (users.get(i).getId().equals(student.getId())) {
        users.set(i, student);
        break;
    }
}
db.writeUsers(users);
                db.updateCourse(c);
                return true;
            }
        }

        return false;
    }

    // ------------------------------------------------------------
    // 3) Get all enrolled courses for a student
    // ------------------------------------------------------------
    public ArrayList<Course> getEnrolledCourses(Student student) {

        ArrayList<Course> all = db.readCourses();
        ArrayList<Course> enrolled = new ArrayList<>();

        for (Course c : all) {
            if (student.getEnrolledCourses().contains(c.getCourseId())) {
                enrolled.add(c);
            }
        }

        return enrolled;
    }

    // ------------------------------------------------------------
    // 4) View lessons of a specific course
    // ------------------------------------------------------------
    public ArrayList<Lesson> getLessons(String courseId) {

        Course c = Course.getCourseById(courseId);

        if (c != null)
            return c.getLessons();

        return new ArrayList<>();
    }

    // ------------------------------------------------------------
    // 5) Mark lesson completed
    // ------------------------------------------------------------
    public void markLessonCompleted(Student student, String courseId, String lessonId) {

        student.markLessonCompleted(courseId, lessonId);

        // تحديث بيانات الطالب
        ArrayList<User> users = db.readUsers();

// ندور على الطالب اللي عايزين نعدّل بياناته
for (int i = 0; i < users.size(); i++) {
    if (users.get(i).getId().equals(student.getId())) {
        users.set(i, student);   // Replace the old student with the updated one
        break;
    }
}

// حفظ كل المستخدمين بعد التعديل
db.writeUsers(users);

    }

    // ------------------------------------------------------------
    // 6) Check if lesson is completed (for UI)
    // ------------------------------------------------------------
    public boolean isLessonCompleted(Student s, String courseId, String lessonId) {
        return s.getProgress()
                .getOrDefault(courseId, new ArrayList<>())
                .contains(lessonId);
    }
    
    public ArrayList<Course> getVisibleCoursesForStudent() {
    ArrayList<Course> courses = db.readCourses();
    ArrayList<Course> visible = new ArrayList<>();

    for (Course c : courses) {
        if (c.getStatus() == Course.Status.APPROVED) {
            visible.add(c);
        }
    }
    return visible;
}
    
}
