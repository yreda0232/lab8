/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StudentDashboardFrame extends JFrame {

    private Student student;
    private StudentService studentService;

    private JList<String> allCoursesList;
    private JList<String> enrolledCoursesList;

    private DefaultListModel<String> allCoursesModel;
    private DefaultListModel<String> enrolledCoursesModel;

    public StudentDashboardFrame(Student student, StudentService service) {
        this.student = student;
        this.studentService = service;

        setTitle("Student Dashboard - " + student.getName());
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panels
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 10));
        JPanel bottomPanel = new JPanel();

        // Lists Models
        allCoursesModel = new DefaultListModel<>();
        enrolledCoursesModel = new DefaultListModel<>();

        // Lists
        allCoursesList = new JList<>(allCoursesModel);
        enrolledCoursesList = new JList<>(enrolledCoursesModel);

        centerPanel.add(new JScrollPane(allCoursesList));
        centerPanel.add(new JScrollPane(enrolledCoursesList));

        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Buttons
        JButton enrollBtn = new JButton("Enroll");
        JButton viewLessonsBtn = new JButton("View Lessons");

        bottomPanel.add(enrollBtn);
        bottomPanel.add(viewLessonsBtn);

        // Load data
        loadCourses();

        // Actions
        enrollBtn.addActionListener(e -> enrollInCourse());
        viewLessonsBtn.addActionListener(e -> viewLessons());

        setVisible(true);
    }

    private void loadCourses() {
        ArrayList<Course> list = studentService.browseCourses();

        allCoursesModel.clear();
        enrolledCoursesModel.clear();

        for (Course c : list) {
            allCoursesModel.addElement(c.getCourseId() + " - " + c.getTitle());
        }

        for (String id : student.getEnrolledCourses()) {
            enrolledCoursesModel.addElement(id);
        }
    }

    private void enrollInCourse() {
        String selected = allCoursesList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a course.");
            return;
        }

        String courseId = selected.split(" - ")[0];
        Course c = studentService.getLessons(courseId) != null
                ? Course.getCourseById(courseId)
                : null;

        if (c == null) return;

        boolean enrolled = studentService.enroll(student, c);

        if (enrolled) {
            JOptionPane.showMessageDialog(this, "Enrolled Successfully!");
            loadCourses();
        } else {
            JOptionPane.showMessageDialog(this, "Already enrolled!");
        }
    }

    private void viewLessons() {
        String selected = enrolledCoursesList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a course.");
            return;
        }

        new LessonsViewerFrame(student, selected, studentService);
    }
}

