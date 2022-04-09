
//@author Mustafa Yencilek @since 18.04.2021

import java.util.ArrayList;

public class Assignment02_20190808038 {

    public static void main(String[] args) {
        Department department = new Department("CSE", "Computer Enginnering");
        Department department1 = new Department("CSE1", "Computer Enginnering1");
        Teacher teacher = new Teacher("Joseph LEDET", "josephledet@akdeniz.edu.tr", 123L, department, 1);
        System.out.println(teacher);
        Course course1 = new Course(department, 101, "Programming 1", 6, teacher);
        Course course2 = new Course(department, 102, "Programming 2", 4, teacher);

        System.out.println(course1.courseCode() + " - " + course1.getTitle());
        System.out.println(course2);
        department.setChair(teacher);
        Student student = new Student("Test Student", "me@somewhere.com", 456L, department);

        student.addCourse(course1, 78);
        student.addCourse(course2, 25);
        System.out.println(student.getGPA());
        System.out.println(student.getAkts());
        System.out.println(student.getAttemptedAkts());
        System.out.println(student);
    }
}

class Department {

    private String id;
    private String name;
    private Teacher chair;

    public Department(String id, String name) {
        if (id.length() <= 4 && id.length() >= 3) {
            this.id = id;
        } else {
            throw new MyException(id, this);
        }

        this.name = name;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id.length() <= 4 && id.length() >= 3) {
            this.id = id;
        } else {
            throw new MyException(id, this);
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getChair() {
        return chair;
    }

    public void setChair(Teacher chair) {
        if (chair.getDepartment() != this) {
            throw new DepartmentMismatchException(this, chair);
        } else {
            this.chair = chair;
        }
    }
}

class Course {

    private Department department;
    private Teacher teacher;
    private int akts;
    private String title;
    private int number;

    public Course(Department department, int number, String title, int akts, Teacher teacher) {
        this.department = department;
        if ((number <= 499 && number >= 100) || (number <= 5999 && number >= 5000) || (number <= 7999 && number >= 7000)) {
            this.number = number;
        } else {
            throw new MyException(number, this, 6);
        }
        this.title = title;
        if (akts > 0) {
            this.akts = akts;
        } else {
            throw new MyException(akts, this);
        }
        this.teacher = teacher;


        if (teacher.getDepartment() != this.getDepartment()) {
            throw new DepartmentMismatchException(teacher, this);
        }


    }


    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Teacher getTeacher() {


        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        if (teacher.getDepartment() != this.getDepartment()) {
            throw new DepartmentMismatchException(department, teacher);
        } else {
            this.teacher = teacher;
        }

    }


    public String courseCode() {
        return department.getId() + " " + number;
    }

    public int getAkts() {
        return akts;
    }

    public void setAkts(int akts) {
        if (akts > 0) {
            this.akts = akts;
        } else {
            throw new MyException(akts, this);
        }

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        if ((number <= 499 && number >= 100) || (number <= 5999 && number >= 5000) | (number <= 7999 && number >= 7000)) {
            this.number = number;
        } else {
            throw new MyException(number, this, 6);
        }


    }

    @Override
    public String toString() {
        return department.getId() + " " + number + " - " + title + " " + "(" + akts + ")";
    }
}

abstract class Person {
    private Department department;
    private String name;
    private String email;
    private long id;

    public Person(String name, String email, long id, Department department) {
        this.name = name;
        this.department = department;
        this.id = id;
        if (email.contains("@") && email.contains(".")) {
            this.email = email;
        } else {
            throw new MyException(email, this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email.contains("@") && email.contains(".")) {
            this.email = email;
        } else {
            throw new MyException(email, this);
        }

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String toString() {
        return name + " (" + id + ")" + " - " + email;
    }


}

class Teacher extends Person {
    private int rank;

    public Teacher(String name, String email, long id, Department department, int rank) {
        super(name, email, id, department);
        this.rank = rank;
    }

    @Override
    public void setDepartment(Department department) {
        if (department.getChair() == this) {
            department.setChair(null);
            super.setDepartment(department);
        }

    }

    public int getRank() {
        return rank;
    }

    public void promote() {
        if (rank == 1 || rank == 2 || rank == 3) {
            this.rank++;
        } else {
            throw new InvalidRankException();
        }
    }

    public void demote() {
        if (rank == 2 || rank == 3 || rank == 4) {
            this.rank--;
        } else {
            throw new InvalidRankException();
        }
    }


    public String getTitle() {
        if (getRank() == 1) {
            return "lecturer";
        } else if (getRank() == 2) {
            return "Assistant Professor";
        } else if (getRank() == 3) {
            return "Associate Professor";
        } else {
            return "Professor";
        }
    }


}

class Student extends Person {

    public Student(String name, String email, long id, Department department) {
        super(name, email, id, department);
    }


    protected ArrayList<Course> courses = new ArrayList<Course>();
    protected ArrayList<Double> grades = new ArrayList<Double>();

    public void addCourse(Course course, double grade) {

        if (courses.contains(course)) {
            int index = courses.indexOf(course);
            grades.set(index, grade);
        } else if (grade < 0 || grade > 100) {
            throw new InvalidGradeException();
        } else {
            courses.add(course);
            grades.add(grade);
        }


    }


    public double courseGPAPoints(Course course) {

        if (!(courses.contains(course))) {
            throw new CourseNotFoundException();
        }
        int index = courses.indexOf(course);
        double gpa = grades.get(index);
        if (gpa >= 88 && gpa <= 100) {
            return (4.0);
        } else if (gpa >= 81 && gpa <= 88) {
            return (3.5);
        } else if (gpa >= 74 && gpa <= 80) {
            return (3.0);
        } else if (gpa >= 67 && gpa <= 73) {
            return (2.5);
        } else if (gpa >= 60 && gpa <= 69) {
            return (2.0);
        } else if (gpa >= 53 && gpa <= 59) {
            return (1.5);
        } else if (gpa >= 46 && gpa <= 52) {
            return (1.0);
        } else if (gpa >= 35 && gpa <= 45) {
            return (0.5);
        } else
            return (0.0);


    }

    public String courseGradeLetter(Course course) {
        if (!(courses.contains(course))) {
            throw new CourseNotFoundException();
        }
        int index = courses.indexOf(course);
        Double gpa = grades.get(index);
        if (gpa >= 88 && gpa <= 100) {
            return "AA";
        } else if (gpa >= 81 && gpa <= 88) {
            return "BA";
        } else if (gpa >= 74 && gpa <= 80) {
            return "BB";
        } else if (gpa >= 67 && gpa <= 73) {
            return "CB";
        } else if (gpa >= 60 && gpa <= 69) {
            return "CC";
        } else if (gpa >= 53 && gpa <= 59) {
            return "DC";
        } else if (gpa >= 46 && gpa <= 52) {
            return "DD";
        } else if (gpa >= 35 && gpa <= 45) {
            return "FD";
        } else
            return "FF";


    }

    public String courseResult(Course course) {
        if (!(courses.contains(course))) {
            throw new CourseNotFoundException();
        }
        int index = courses.indexOf(course);
        Double gpa = grades.get(index);
        if (gpa >= 88 && gpa <= 100) {
            return "Passed";
        } else if (gpa >= 81 && gpa <= 88) {
            return "Passed";
        } else if (gpa >= 74 && gpa <= 80) {
            return "Passed";
        } else if (gpa >= 67 && gpa <= 73) {
            return "Passed";
        } else if (gpa >= 60 && gpa <= 69) {
            return "Passed";
        } else if (gpa >= 53 && gpa <= 59) {
            return "Conditionally Passed";
        } else if (gpa >= 46 && gpa <= 52) {
            return "Conditionally Passed";
        } else if (gpa >= 35 && gpa <= 45) {
            return "Failed";
        } else
            return "Failed";


    }

    public double getGPA() {
        double sum = 0;
        for (Course x : courses) {
            sum = sum + courseGPAPoints(x) * x.getAkts();
        }
        return sum / getAttemptedAkts();


    }

    public int getAkts() {

        int sum = 0;
        for (Course x : courses) {
            if (courseResult(x) == "Passed") {
                sum = sum + x.getAkts();
            }
        }
        return sum;


    }


    public int getAttemptedAkts() {

        int sum = 0;
        for (Course x : courses) {
            sum = sum + x.getAkts();
        }
        return sum;


    }

    @Override
    public String toString() {
        return super.toString() + " –GPA: " + getGPA();
    }


}

class GradStudent extends Student {
    private String thesis;

    public GradStudent(String name, String email, long id, Department department, String thesis) {
        super(name, email, id, department);
        this.thesis = thesis;
    }

    @Override
    public double courseGPAPoints(Course course) {
        if (!(courses.contains(course))) {
            throw new CourseNotFoundException();
        }
        int index = courses.indexOf(course);
        double gpa = grades.get(index);
        if (gpa >= 90 && gpa <= 100) {
            return (4.0);
        } else if (gpa >= 85 && gpa <= 89) {
            return (3.5);
        } else if (gpa >= 80 && gpa <= 84) {
            return (3.0);
        } else if (gpa >= 75 && gpa <= 79) {
            return (2.5);
        } else if (gpa >= 70 && gpa <= 74) {
            return (2.0);
        } else
            return (1.5);
    }

    @Override
    public String courseGradeLetter(Course course) {

        if (!(courses.contains(course))) {
            throw new CourseNotFoundException();
        }
        int index = courses.indexOf(course);
        Double gpa = grades.get(index);
        if (gpa >= 90 && gpa <= 100) {
            return "AA";
        } else if (gpa >= 85 && gpa <= 89) {
            return "BA";
        } else if (gpa >= 80 && gpa <= 84) {
            return "BB";
        } else if (gpa >= 75 && gpa <= 79) {
            return "CB";
        } else if (gpa >= 70 && gpa <= 74) {
            return "CC";
        } else
            return "DC";


    }

    @Override
    public String courseResult(Course course) {

        if (!(courses.contains(course))) {
            throw new CourseNotFoundException();
        }
        int index = courses.indexOf(course);
        Double gpa = grades.get(index);
        if (gpa >= 90 && gpa <= 100) {
            return "Passed";
        } else if (gpa >= 85 && gpa <= 89) {
            return "Passed";
        } else if (gpa >= 80 && gpa <= 84) {
            return "Passed";
        } else if (gpa >= 75 && gpa <= 79) {
            return "Passed";
        } else if (gpa >= 70 && gpa <= 74) {
            return "Passed";
        } else
            return "Failed";

    }

    @Override
    public String toString() {
        return super.toString();
    }
}

class CourseNotFoundException extends RuntimeException {
    private Student student;
    private Course course;

    @Override
    public String toString() {
        return "CourseNotFoundException: " + student.getId() +
                "has not yet taken" + course;
    }
}

class DepartmentMismatchException extends RuntimeException {
    private Department department;
    private Teacher person;
    private Course course;

    public DepartmentMismatchException(Teacher person, Course course) {
        this.person = person;
        this.course = course;
        department = null;
    }

    public DepartmentMismatchException(Department department, Teacher person) {
        this.department = department;
        this.person = person;
        course = null;
    }

    @Override
    public String toString() {
        if (course == null) {
            return "DepartmentMismatchException: " + person.getName()
                    + "(" + person.getId() + ") cannot be chair of " + department.getId() +
                    "because he/she is currently assigned to " + person.getDepartment().getId();
        } else {
            return "DepartmentMismatchException: " + person.getName() + "(" +
                    person.getId() + ") cannot teach " + course.courseCode() +
                    " because he/she is currently assigned to " + person.getDepartment().getId();
        }
    }
}

class InvalidGradeException extends RuntimeException {
    private double grade;

    @Override
    public String toString() {
        return "InvalidGradeException:" + grade;
    }
}

class InvalidRankException extends RuntimeException {
    private int rank;

    @Override
    public String toString() {
        return "InvalidRankException:" + rank;
    }
}

class MyException extends RuntimeException {
    private String email;
    private String id;
    private int akts;
    private int number;
    private Department department;
    private Course course;
    private Person person;
    private int x;


    public MyException(String id, Department department) {
        this.id = id;
        this.department = department;
        x = 1;

    }

    public MyException(int number, Course course, int rakam) {
        this.number = number;
        this.course = course;
        x = 2;
    }

    public MyException(int akts, Course course) {
        this.akts = akts;
        this.course = course;
        x = 3;
    }

    public MyException(String email, Person person) {
        this.email = email;
        this.person = person;
        x = 4;
    }

    @Override
    public String toString() {
        if (x == 1) {
            return "InvalidIDException: " + department.getClass() + " " + id + " must be 3 or 4 characters";
        } else if (x == 2) {
            return "InvalidNumberException: " + course.getClass() + " " + number +
                    " must be in the range 100-499 or 5000-5999 or 7000-7999";
        } else if (x == 3) {
            return "InvalidAKTSException: " + course.getClass() + " " + akts + " must be positive";
        } else {
            return "InvalidEmailException: " + person.getClass() + email +
                    "must be of the form {username}@{university name}.{domain}";
        }

    }
}





