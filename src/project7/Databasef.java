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
    private static final String QUIZ_ATTEMPTS_FILE = "quizAttempts.json";
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
                
                if(o.has("certificates"))
            {
                JSONArray certArr = o.getJSONArray("certificates");
                for(int k=0; k<certArr.length(); k++)
                {
                    JSONObject certObj = certArr.getJSONObject(k);
                    
                    Certificate cert = new Certificate(
                            certObj.getString("certificateId"),
                            o.getString("userId"),
                            certObj.getString("courseId"),
                            certObj.getString("issueDate")
                    );
                    s.getCertificates().add(cert);
                }
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
            else if(o.getString("role").equals("admin"))
            {
                Admin a =new Admin(
                        o.getString("userId"),
                        o.getString("username"),
                        o.getString("email"),
                        o.getString("passwordHash")
                );
                users.add(a);
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
                
                JSONArray certArr = new JSONArray();
                for(Certificate cert : s.getCertificates())
                {
                    JSONObject certObj = new JSONObject();
                    certObj.put("certificateId", cert.getCertificateId());
                    certObj.put("courseId", cert.getCourseId());
                    certObj.put("issueDate", cert.getIssueDate());
                    
                    certArr.put(certObj); 
                }
                o.put("certificates", certArr);
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

            String statusStr = "PENDING";
            if (o.has("status") && !o.isNull("status")) {
                statusStr = String.valueOf(o.get("status"));
            }

            Course.Status status = Course.Status.valueOf(statusStr);

            Course c = new Course(
                    o.getString("courseId"),
                    o.getString("title"),
                    o.getString("description"),
                    o.getString("instructorId"),
                    status
            );
            String currentStatus = o.optString("status", "PENDING");
            String lastMod = o.optString("lastModifiedBy", "");
            String lastChange = o.optString("lastStatusChange", "");

            c.setStatus(Course.Status.valueOf(currentStatus));
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
                JSONArray quizzesArr = L.optJSONArray("quizzes");  // لو موجود
    if (quizzesArr != null) {
        for (int k = 0; k < quizzesArr.length(); k++) {
            JSONObject Qz = quizzesArr.getJSONObject(k);
            Quiz quiz = new Quiz(
                Qz.getString("quizId"),
                Qz.getString("title"),
                new ArrayList<>(),  // questions هيتضافوا بعدين
                Qz.optInt("maxRetries", 1),
                l.getLessonId()
            );
            // قراءة الـ Questions
            JSONArray questionsArr = Qz.optJSONArray("questions");
            if (questionsArr != null) {
                for (int q = 0; q < questionsArr.length(); q++) {
                    JSONObject qs = questionsArr.getJSONObject(q);
                    ArrayList<String> options = new ArrayList<>();
                    JSONArray opts = qs.optJSONArray("options");
                    if (opts != null) {
                        for (int m = 0; m < opts.length(); m++) {
                            options.add(opts.getString(m));
                        }
                    }
                    Question question = new Question(
                        qs.getString("questionId"),
                        qs.getString("text"),
                        options,
                        qs.getString("correctAnswer")
                    );
                    quiz.addQuestion(question);
                }
            }
            l.addQuiz(quiz);
        }
    }
                
                c.addLesson(l);
            }

           /* JSONArray st = o.getJSONArray("students");

for (int j = 0; j < st.length(); j++) {
    JSONObject stuObj = st.getJSONObject(j);
    String id = stuObj.getString("id");   // ✔ دا الصحيح
    Student s = Student.getStudentById(id);
    if (s != null) {
        c.addStudent(s);
    }
} */
           
           JSONArray st = o.getJSONArray("students");

            for (int j = 0; j < st.length(); j++) {
                String id = st.getString(j);
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


                JSONArray quizzesArray = new JSONArray();
                if (ls.getQuizzes() != null) {
                    for (Quiz q : ls.getQuizzes()) {
                        JSONObject quizObj = new JSONObject();
                        quizObj.put("quizId", q.getQuizId());
                        quizObj.put("title", q.getTitle());
                        quizObj.put("lessonId", q.getLessonId());
                        quizObj.put("maxRetries", q.getMaxRetries());

                        // إضافة الأسئلة داخل الـ Quiz
                        JSONArray questionsArray = new JSONArray();
                        if (q.getQuestions() != null) {
                            for (Question ques : q.getQuestions()) {
                                JSONObject quesObj = new JSONObject();
                                quesObj.put("questionId", ques.getQuestionId());
                                quesObj.put("text", ques.getText());
                                quesObj.put("options", ques.getOptions());
                                quesObj.put("correctAnswer", ques.getCorrectAnswer());
                                questionsArray.put(quesObj);
                            }
                        }
                        quizObj.put("questions", questionsArray);
                        quizzesArray.put(quizObj);
                    }
                }
                obj.put("quizzes", quizzesArray);
                L.put(obj);
                }
            o.put("lessons", L);

            // بدلاً من: o.put("students", c.getStudents());
            JSONArray stuArr = new JSONArray();
            for (Student s : c.getStudents()) {
                stuArr.put(s.getId());
            }
            o.put("students", stuArr);


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
    
    public ArrayList<Course> getPendingCourses()
    {
        ArrayList<Course> all =readCourses();
        ArrayList<Course> pending =new ArrayList<>();
        
        for(Course c : all)
        {
            if(c.getStatus() == Course.Status.PENDING)
                pending.add(c);
        }
        
        return pending;
    }
    
    public boolean approveCourse(String courseId)
    {
        ArrayList<Course> courses = readCourses();
        boolean changed = false;
        
        for(Course c : courses)
        {
            if(c.getCourseId().equals(courseId))
            {
                c.setStatus(Course.Status.APPROVED);
                changed = true;
                break;
            }
        }
        if(changed) writeCourses(courses);
        return changed;
    }
    
    public boolean rejectCourse(String courseId)
    {
        ArrayList<Course> courses = readCourses();
        boolean changed = false;
        
        for(Course c : courses)
        {
            if(c.getCourseId().equals(courseId))
            {
                c.setStatus(Course.Status.REJECTED);
                changed = true;
                break;
            }
        }
        if(changed) writeCourses(courses);
        return changed;
    }
    
    // hena beygenerate Certificate Automatically
    public Certificate generateCertificate(String studentId, String courseId) {
    String certId = "CERT-" + System.currentTimeMillis();
    String date = java.time.LocalDate.now().toString();

    return new Certificate(certId, studentId, courseId, date);
}


    public static ArrayList<QuizAttempt> readQuizAttempts() {
    ArrayList<QuizAttempt> attempts = new ArrayList<>();
    JSONArray arr = new JSONArray(readFile(QUIZ_ATTEMPTS_FILE));
    for (int i = 0; i < arr.length(); i++) {
        JSONObject o = arr.getJSONObject(i);
        QuizAttempt attempt = new QuizAttempt(
            o.getString("attemptId"),
            o.getString("quizId"),
            o.getString("studentId"),
            o.getInt("attemptNumber")
        );
        JSONObject ans = o.getJSONObject("answers");
        for (String qId : ans.keySet()) {
            attempt.addAnswer(qId, ans.getString(qId));
        }
       // attempt.setScore(o.getInt("score"));
        attempts.add(attempt);
    }
    return attempts;
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
    
}
