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

            JSONArray L = new JSONArray();
            for (Lesson ls : c.getLessons()) {
                JSONObject obj = new JSONObject();
                obj.put("lessonId", ls.getLessonId());
                obj.put("title", ls.getTitle());
                obj.put("content", ls.getContent());
                L.put(obj);
            }
            o.put("lessons", L);

            o.put("students", c.getStudents());

            arr.put(o);
        }

        writeFile(COURSES_FILE, arr.toString(4));
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


public static JSONObject getCourseStatistics(String courseId) {

    // Read database
    ArrayList<Course> courses = Databasef.readCourses();
    ArrayList<User> users = Databasef.readUsers();

    Course selected = null;

    // Find course
    for (Course c : courses) {
        if (c.getCourseId().equals(courseId)) {
            selected = c;
            break;
        }
    }

    if (selected == null) {
        JSONObject error = new JSONObject();
        error.put("error", "Course not found");
        return error;
    }

    // ============================
    // COURSE LEVEL STATISTICS
    // ============================

    int totalStudents = selected.getStudents().size();
    int totalLessons = selected.getLessons().size();

    // Count completed lessons by all students
    int completedCount = 0;

    for (User u : users) {
        if (u instanceof Student s) {
            if (s.getProgress().containsKey(courseId)) {
                completedCount += s.getProgress().get(courseId).size();
            }
        }
    }

    double avgCompletion = 0.0;
    if (totalStudents > 0 && totalLessons > 0) {
        avgCompletion = (completedCount * 100.0) / (totalStudents * totalLessons);
    }

    // ============================
    // LESSON LEVEL STATISTICS
    // ============================

    JSONArray lessonsArray = new JSONArray();

    for (Lesson lesson : selected.getLessons()) {

        int lessonCompleted = 0;

        for (User u : users) {
            if (u instanceof Student s) {
                if (s.getProgress().containsKey(courseId)) {
                    if (s.getProgress().get(courseId).contains(lesson.getLessonId())) {
                        lessonCompleted++;
                    }
                }
            }
        }

        JSONObject lessonObj = new JSONObject();
        lessonObj.put("lessonId", lesson.getLessonId());
        lessonObj.put("title", lesson.getTitle());
        lessonObj.put("completedBy", lessonCompleted);

        lessonsArray.put(lessonObj);
    }

    // ============================
    // FINAL RESULT JSON
    // ============================

    JSONObject result = new JSONObject();
    result.put("courseId", courseId);
    result.put("title", selected.getTitle());
    result.put("totalStudents", totalStudents);
    result.put("totalLessons", totalLessons);
    result.put("averageCompletion", avgCompletion);
    result.put("lessonsPerformance", lessonsArray);

    return result;
}




public static void synchronizeData(ArrayList<User> users, ArrayList<Course> courses) {

    // 1. مسح الـ enrolledCourses لكل students
    for (User u : users) {
        if (u instanceof Student s) {
            s.getEnrolledCourses().clear();
        }
    }

    // 2. مسح الطلاب من كل course
    for (Course c : courses) {
        c.getStudents().clear();
    }

    // 3. ربط البيانات من الأول: based on progress
    for (User u : users) {
        if (u instanceof Student s) {

            for (Course c : courses) {

                // لو الطالب عنده progress في الكورس
                if (s.getProgress().containsKey(c.getCourseId())) {

                    // أضف courseId للـ student
                    if (!s.getEnrolledCourses().contains(c.getCourseId())) {
                        s.getEnrolledCourses().add(c.getCourseId());
                    }

                    // أضف student للـ course
                    if (!c.getStudents().contains(s)) {
                        c.getStudents().add(s);
                    }
                }
            }
        }
    }

    // 4. حفظ الملفات بعد التزامن
    writeUsers(users);
    writeCourses(courses);
}








}
