//author Mustafa Yencilek
//@since 30.03.2021

public class Assignment01_20190808038 {

    public static void main(String[] args) {
        Course c = new Course("CSE",102,"Programming 2",6);
        System.out.println(c.courseCode()+" - "+c.getTitle());
        System.out.println(c);

        Teacher t = new Teacher("Joseph Ledet","josephledet@gmail.com",123,"CSE",1);
       System.out.println(t);


  Student s = new Student("Mustafa Yencilek","mustafayencilek@gmail.com", 456L,"CSE",33);
     System.out.println(s);

        s.passCourse(c);
       System.out.println(s.getAkts());


    }
}

class Course {

    private String department;
    private int number;
    private String title;
    private int akts;

    public Course(String department, int number, String title, int akts) {
        if (((department.length() <= 4 && department.length() >= 3 ) &&
                ((number <= 499 && number >= 100) || (number <= 5999 && number >= 5000) ||
                        (number <= 7999 && number >= 7000)) && (akts > 0))) {
            this.department = department;
            this.number = number;
            this.title = title;
            this.akts = akts;
        } else {
            throw new ArithmeticException();
        }


    }

    @Override
    public String toString() {
        return  department +" "+ number + " - " + title + " (" + akts +")";
    }



    //getter
    public String getDepartment() {
        return department;
    }

    public int getNumber() {
        return number;
    }

    public int getAkts() {
        return akts;
    }

    public String getTitle() {
        return title;
    }


    //setter
    public void setDepartment(String department) {
        if (department.length() <= 4 && department.length() >= 3) {
            this.department = department;
        } else {
            throw new  RuntimeException();
        }


    }

    public void setNumber(int number) {
        if ((number <= 499 && number >= 100) || (number <= 5999 && number >= 5000) || (number <= 7999 && number >= 7000)) {
            this.number = number;
        } else {
            throw new RuntimeException();
        }

    }

    public void setAkts(int akts) {
        if (akts > 0) {
            this.akts = akts;
        } else {
            throw new RuntimeException();
        }

    }

    public void setTitle(String title) {
        this.title = title;
    }

    //methods
    public String courseCode() {

        return (  department+ " " + number);
    }


}

class Person {
    private String name;
    private String email;
    private long id;
    private String department;

    public Person(String name, String email, long id, String department) {

        if ((department.length() <= 4 && department.length() >= 3) &&
                (email.contains("@")) && (email.contains("."))) {
            this.name = name;
            this.email = email;
            this.id = id;
            this.department = department;
        } else {
            throw new RuntimeException();
        }

    }

    @Override
    public String toString() {
        return name+" " +"("+id+")"+" - "+email;
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
        if ((email.contains("@")) && (email.contains(".")) ){
            this.email = email;
        }
        else{
            throw new RuntimeException();
        }

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        if (department.length() <= 4 && department.length() >= 3) {
            this.department = department;
        } else {
            throw new RuntimeException();
        }

    }

}


class Teacher extends Person {
    private int rank;

    public Teacher(String name, String email, long id, String department, int rank) {
        super(name, email, id, department);

        if (rank >= 1 && rank <= 3) {
            this.rank = rank;
        } else {
            throw new RuntimeException();
        }

    }
    @Override
    public String toString() {
        return getTitle()+" "+super.toString();
    }


    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        if (rank >= 1 && rank <= 3) {
            this.rank = rank;
        } else {
            throw new RuntimeException();
        }

    }

    public String getTitle() {
        if (getRank() == 1) {
            return "Assistant Professor";
        } else if (getRank() == 2) {
            return "Associate Professor";
        } else {
            return "Professor";
        }
    }

    public void promote() {
        if (rank == 1 || rank == 2) {
            rank++;
        }
    }

    public void demote() {
        if (rank == 2 || rank == 3) {
            rank--;
        }
    }
}

class Student extends Person {
    private int akts;

    public Student(String name, String email, long id, String department, int akts) {
        super(name, email, id, department);
        if (akts >= 0) {
            this.akts= akts;
        } else {
            throw new RuntimeException();
        }

    }

    public int getAkts() {
        return akts;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void setAkts(int akts) {
        if (akts >= 0) {
            this.akts = akts;
        } else {
            throw new RuntimeException();
        }


    }

    public void passCourse(Course course) {
        akts += course.getAkts();


    }
}


class GradStudent extends Student {
    private String thesis;

    public GradStudent(String name, String email, long id, String department, int akts, String thesis) {
        super(name, email, id, department, akts);
        this.thesis = thesis;
    }

    public String getThesis() {
        return thesis;
    }

    public void setThesis(String thesis) {
        this.thesis = thesis;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}


