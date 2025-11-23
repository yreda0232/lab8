/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project7;

import java.util.ArrayList;

public class Course {
    
     public enum Status { PENDING, APPROVED, REJECTED }

    private String courseId;
    private String title;
    private String description;
    private String instructorId;
    private ArrayList<Lesson> lessons;
    private ArrayList<Student> students;
    private Status status = Status.PENDING;
    private String lastModifiedBy;
    private String lastStatusChange;


    public Course(String courseId, String title, String description, String instructorId) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
        this.lessons = new ArrayList<Lesson>();
        this.students = new ArrayList<Student>();
        this.status = Status.PENDING;
    }
    
     public Course(String courseId, String title, String description, String instructorId, Status status) {
        this(courseId, title, description, instructorId);
        if (status != null) this.status = status;
    }

    // ----------------- GETTERS -----------------
    public String getCourseId() {return courseId;}

    public ArrayList<Lesson> getLessons() {return lessons;}

    public ArrayList<Student> getStudents() {return students;}

    public String getTitle() {return title;}

    public String getDescription() {return description;}

    public void setCourseId(String courseId) {this.courseId = courseId;}

    public void setTitle(String title) {this.title = title;}

    public void setDescription(String description) {this.description = description;}
  
    public String getInstructorId() {return instructorId;}

    public void setInstructorId(String instructorId) {this.instructorId = instructorId;}
    
    public void setStatus(Status status){this.status=status;}
    
    public Status getStatus(){return status;}

    public String getLastModifiedBy() {return lastModifiedBy;}

    public String getLastStatusChange() {return lastStatusChange;}

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public void setLastStatusChange(String lastStatusChange) {
        this.lastStatusChange = lastStatusChange;
    }

    
    
    
    public void editCourse(String newTitle, String newDescription, String newInstructorId) {
        if (newTitle != null) this.title = newTitle;
        if (newDescription != null) this.description = newDescription;
        if (newInstructorId != null) this.instructorId = newInstructorId;
    }

    public void viewEnrolledStudents() {
        System.out.println("Enrolled Students in " + title + ":");
        if (students.isEmpty()) {
            System.out.println("No students enrolled yet.");
        } else {
            for (Student s : students) {
                System.out.println("- " + s.getName() + " (" + s.getId() + ")");
            }
        }
    }

   

    
    public void addLesson(Lesson lesson) {
        if (!lessons.contains(lesson)) {
            lessons.add(lesson);
        }
    }

    
    public void addStudent(Student s) {
        if (!students.contains(s)) {
            students.add(s);
        }
    }

    
    public ArrayList<String> getStudentIds() {
        ArrayList<String> ids = new ArrayList<>();
        for (Student s : students) {
            ids.add(s.getId());
        }
        return ids;
    }
    
    public void setLessons(ArrayList<Lesson> lessons) {
    this.lessons = lessons;
}
    
    public static Course getCourseById(String courseId) {
        Databasef db = new Databasef();
        ArrayList<Course> courses = db.readCourses();
        for (Course c : courses) {
            if (c.getCourseId().equals(courseId)) {
                return c;
            }
        }
        return null; 
}
    
    
    

}

