package project7;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Databasef {

    private static final String USERS_FILE = "users.json";
    private static final String COURSES_FILE = "courses.json";
    private ArrayList<User> users;

    public Databasef() {
        this.users = readUsers();
    }

    // =====================================================================
    // ✅ READ FILE FROM src/project7/
    // =====================================================================
    private static String readFile(String filename) {
        try {
            InputStream is = Databasef.class.getResourceAsStream("/project7/" + filename);

            if (is == null) {
                System.out.println("File not found inside resources: " + filename);
                return "[]";
            }

            return new String(is.readAllBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return "[]";
        }
    }

    // =====================================================================
    // ✅ WRITE FILE INTO src/project7/
    // =====================================================================
    private static void writeFile(String filename, String content) {
        try {
            File file = new File("src/project7/" + filename);
            FileWriter fw = new FileWriter(file);
            fw.write(content);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =====================================================================
    // USERS HANDLING
    // =====================================================================
    public static ArrayList<User> readUsers() {
        ArrayList<User> users = new ArrayList<>();
        JSONArray arr = new JSONArray(readFile(USERS_FILE));

        for (int i = 0; i < arr.length(); i++) {
            JSONObject o = arr.getJSONObject(i);

            if (o.getString("role").equals("student")) {
                Student s = new Student(
                        o.getString("userId"),
                        o.getString("username"),
                        o.getString("email"),
                        o.getString("passwordHash")
                );

                JSONArray enrolled = o.getJSONArray("enrolledCourses");
                for (int j = 0; j < enrolled.length(); j++) {
                    s.getEnrolledCourses().add(enrolled.getString(j));
                }
JSONObject lessonCompletedObj = o.optJSONObject("lessonCompleted");
    if (lessonCompletedObj != null) {
        for (String key : lessonCompletedObj.keySet()) {
            s.getLessonCompleted().put(key, lessonCompletedObj.getBoolean(key));
        }
    }
    JSONObject quizResultsObj = o.optJSONObject("quizResults");
    if (quizResultsObj != null) {
        for (String key : quizResultsObj.keySet()) {
            s.getQuizResults().put(key, quizResultsObj.getInt(key));
        }
    }
                JSONObject prog = o.getJSONObject("progress");
                for (String courseId : prog.keySet()) {
                    JSONArray completedLessons = prog.getJSONArray(courseId);
                    ArrayList<String> lessonsList = new ArrayList<>();
                    for (int k = 0; k < completedLessons.length(); k++) {
                        lessonsList.add(completedLessons.getString(k));
                    }
                    s.getProgress().put(courseId, lessonsList);
                }

                users.add(s);
            } else if (o.getString("role").equals("instructor")) {
                Instructor ins = new Instructor(
                        o.getString("userId"),
                        o.getString("username"),
                        o.getString("email"),
                        o.getString("passwordHash")
                );

                JSONArray created = o.getJSONArray("createdCourses");
                for (int j = 0; j < created.length(); j++) {
                    String courseId = created.getString(j);

                    Course c = Course.getCourseById(courseId);
                    if (c != null) {
                        ins.getCreatedCourses().add(c);
                    }
                }

                users.add(ins);
            }
        }

        return users;
    }

    public static void writeUsers(ArrayList<User> users) {
        JSONArray arr = new JSONArray();

        for (User u : users) {
            JSONObject o = new JSONObject();
            o.put("userId", u.getId());
            o.put("role", u.getRole());
            o.put("username", u.getName());
            o.put("email", u.getEmail());
            o.put("passwordHash", u.getPasswordHash());

            if (u instanceof Student s) {
                o.put("enrolledCourses", s.getEnrolledCourses());
                o.put("progress", s.getProgress()); 
                JSONObject lessonCompletedObj = new JSONObject();
    for (String key : s.getLessonCompleted().keySet()) {
        lessonCompletedObj.put(key, s.getLessonCompleted().get(key));
    }
    o.put("lessonCompleted", lessonCompletedObj);
            
            JSONObject quizResultsObj = new JSONObject();
    for (String key : s.getQuizResults().keySet()) {
        quizResultsObj.put(key, s.getQuizResults().get(key));
    }
    o.put("quizResults", quizResultsObj);
            o.put("progress", s.getProgress());
            
            }

            if (u instanceof Instructor i) {
                JSONArray created = new JSONArray();
                for (Course c : i.getCreatedCourses()) {
                    created.put(c.getCourseId());
                }
                o.put("createdCourses", created);
            }

            arr.put(o);
        }

        writeFile(USERS_FILE, arr.toString(4));
    }

    // =====================================================================
    // COURSES HANDLING
    // =====================================================================
    public static ArrayList<Course> readCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        JSONArray arr = new JSONArray(readFile(COURSES_FILE));
        

        for (int i = 0; i < arr.length(); i++) {
            JSONObject o = arr.getJSONObject(i);

            Course c = new Course(
                    o.getString("courseId"),
                    o.getString("title"),
                    o.getString("description"),
                    o.getString("instructorId")
            );
            String status = o.optString("status", "PENDING");
            String lastMod = o.optString("lastModifiedBy", "");
            String lastChange = o.optString("lastStatusChange", "");

            c.setStatus(CourseStatus.valueOf(status));
            c.setLastModifiedBy(lastMod);
            c.setLastStatusChange(lastChange);

            JSONArray lessons = o.getJSONArray("lessons");
            for (int j = 0; j < lessons.length(); j++) {
                JSONObject L = lessons.getJSONObject(j);
                Lesson l = new Lesson(
                        L.getString("lessonId"),
                        L.getString("title"),
                        L.getString("content")
                );
                c.addLesson(l);
            }

            JSONArray st = o.getJSONArray("students");

for (int j = 0; j < st.length(); j++) {
    JSONObject stuObj = st.getJSONObject(j);
    String id = stuObj.getString("id");   // ✔ دا الصحيح
    Student s = Student.getStudentById(id);
    if (s != null) {
        c.addStudent(s);
    }
}

            courses.add(c);
        }

        return courses;
    }

    public static void writeCourses(ArrayList<Course> courses) {
        JSONArray arr = new JSONArray();

        for (Course c : courses) {
            JSONObject o = new JSONObject();

            o.put("courseId", c.getCourseId());
            o.put("title", c.getTitle());
            o.put("description", c.getDescription());
            o.put("instructorId", c.getInstructorId());
            o.put("status", c.getStatus().name());
            o.put("lastModifiedBy", c.getLastModifiedBy());
            o.put("lastStatusChange", c.getLastStatusChange());

            JSONArray L = new JSONArray();
            for (Lesson ls : c.getLessons()) {
                JSONObject obj = new JSONObject();
                obj.put("lessonId", ls.getLessonId());
                obj.put("title", ls.getTitle());
                obj.put("content", ls.getContent());
                obj.put("resources", ls.getResources());
                L.put(obj);
            }
            o.put("lessons", L);

            o.put("students", c.getStudents());

            arr.put(o);
        }

        writeFile(COURSES_FILE, arr.toString(4));
    }
    
    public ArrayList<Course> readCoursesForInstructor(String instructorId) {
        ArrayList<Course> allCourses = readCourses();
        ArrayList<Course> instructorCourses = new ArrayList<>();
        for (Course c : allCourses) {
            if (c.getInstructorId().equals(instructorId)) {
                instructorCourses.add(c);
            }
        }
        return instructorCourses;
    }

    // =====================================================================
    public void addCourse(Course newCourse) {
        ArrayList<Course> courses = readCourses();
        courses.add(newCourse);
        writeCourses(courses);
    }

    public void updateCourse(Course updated) {
        ArrayList<Course> courses = readCourses();

        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseId().equals(updated.getCourseId())) {
                courses.set(i, updated);
                break;
            }
        }

        writeCourses(courses);
    }
    
    public void updateStudent(Student student) {
    ArrayList<User> allUsers = readUsers();
    for (int i = 0; i < allUsers.size(); i++) {
        if (allUsers.get(i).getId().equals(student.getId())) {
            allUsers.set(i, student); // replace the old student with the updated one
            break;
        }
    }
    writeUsers(allUsers); // save changes to users.json
}

    public ArrayList<Course> loadCoursesByInstructor(String instructorId) {
        ArrayList<Course> all = readCourses();
        ArrayList<Course> result = new ArrayList<>();

        for (Course c : all) {
            if (c.getInstructorId().equals(instructorId)) {
                result.add(c);
            }
        }
        return result;
    }

    public void deleteCourse(String courseId) {
        ArrayList<Course> courses = readCourses();
        courses.removeIf(c -> c.getCourseId().equals(courseId));
        writeCourses(courses);
    }

    public boolean idExists(String id) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static boolean courseIdExists(String courseId) {
        return readCourses().stream()
                .anyMatch(c -> c.getCourseId().equals(courseId));
    }

    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean isValidUsername(String username) {
        return username != null && !username.trim().isEmpty() && username.length() >= 3;
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 3;
    }
}
