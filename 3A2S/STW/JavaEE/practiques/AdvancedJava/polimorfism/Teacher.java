package polimorfism;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Teacher
 * @author ramonguimera, davidcuadrado
 *
 */
public class Teacher extends Person{

    private List<Student> students;
    
    /**
     * Constructor
     * @param name of this teacher
     * @param age of this teacher
     */
    public Teacher(final String name, final int age) {
        super(name, age);
        this.students = new ArrayList<>();
    }
    
    /**
     * Constructor
     * @param name of this teacher
     * @param age of this teacher
     * @param students this teacher already has
     */
    public Teacher(final String name, final int age, final ArrayList<Student> students) {
        this(name, age);
        this.students = students;
    }
    
    /**
     * Adds an student to this teacher
     * @param student to add
     */
    public void addStudent(final Student student) {
        this.students.add(student);
    }
    
    /**
     * Prints teacher and its students
     */
    @Override
    public String toString() {
        String result = super.toString() + "\r\n";
        for(Student student : this.students) {
            result += student.toString() + "\r\n";
        }
        return result;
    }
}
