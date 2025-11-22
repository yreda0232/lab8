/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project7;

import java.util.ArrayList;

public class Lesson {

    private String lessonId;
    private String title;
    private String content;
    private ArrayList<String> resources;

    // Constructor بدون resources
    public Lesson(String lessonId, String title, String content) {
        this.lessonId = lessonId;
        this.title = title;
        this.content = content;
        this.resources = new ArrayList<>();
    }

    // Constructor بالresources
    public Lesson(String lessonId, String title, String content, ArrayList<String> resources) {
        this.lessonId = lessonId;
        this.title = title;
        this.content = content;
        this.resources = resources;
    }

    // Getters
    public String getLessonId() {
        return lessonId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<String> getResources() {
        return resources;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // Manage Resources
    public void addResource(String resource) {
        if (!resources.contains(resource)) {
            resources.add(resource);
        }
    }

    public void removeResource(String resource) {
        resources.remove(resource);
    }
    
    public void setResources(ArrayList<String> resources) {
        this.resources = resources;
    }


}
