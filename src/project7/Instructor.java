package project7;

import java.util.ArrayList;
import java.util.List;

public class Instructor extends User {
    private ArrayList<Course> createdCourses;
    //private Databasef db = new Databasef();

    public Instructor(String id, String name, String email, String passwordHash) {
        super(id, name, email, passwordHash, "instructor");
        //this.createdCourses = db.loadCoursesByInstructor(id); 
        this.createdCourses = new ArrayList<>(); 
    }

    public void setCreatedCourses(ArrayList<Course> createdCourses) {
        this.createdCourses = createdCourses;
    }
    
    

    public ArrayList<Course> getCreatedCourses() {
        return createdCourses;
    }

    public void createCourse(String courseId, String title, String description, String instructorId) {
         Databasef db = new Databasef();
        Course c = new Course(courseId, title, description, instructorId);

        createdCourses.add(c);

        db.addCourse(c);
    }

    public Course getCourseById(String courseId) {
        for (Course c : createdCourses) {
            if (c.getCourseId().equals(courseId)) return c;
        }
        return null;
    }

    public void editCourse(String courseId, String newTitle, String newDescription, String newInstructorId) {
        Databasef db = new Databasef();
        Course c = getCourseById(courseId);
        if (c != null) {
            c.editCourse(newTitle, newDescription, newInstructorId);
            db.updateCourse(c);
        }
    }

    public void deleteCourse(String courseId) {
        Databasef db = new Databasef();
        createdCourses.removeIf(c -> c.getCourseId().equals(courseId));
        db.deleteCourse(courseId);
    }

    public void addLessonToCourse(String courseId, Lesson lesson) {
        Databasef db = new Databasef();
        Course c = getCourseById(courseId);
        if (c != null) {
            c.getLessons().add(lesson);
            db.updateCourse(c);
        }
    }

    public void editLessonInCourse(String courseId, String lessonId, String newTitle, String newContent) {
        Databasef db = new Databasef();
        Course c = getCourseById(courseId);
        if (c != null) {
            for (Lesson l : c.getLessons()) {
                if (l.getLessonId().equals(lessonId)) {
                    l.setTitle(newTitle);
                    l.setContent(newContent);
                }
            }
            db.updateCourse(c);
        }
    }

    public void deleteLessonFromCourse(String courseId, String lessonId) {
        Databasef db = new Databasef();
        Course c = getCourseById(courseId);
        if (c != null) {
            c.getLessons().removeIf(l -> l.getLessonId().equals(lessonId));
            db.updateCourse(c);
        }
    }
}
    

