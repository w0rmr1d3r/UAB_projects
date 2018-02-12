package polimorfism;

import java.util.ArrayList;

/**
 * Class Student, extends Person
 * @author ramonguimera, davidcuadrado
 *
 */
public class Student extends Person{
    
    private ArrayList<String> subjects;
    
    /**
     * Constructor
     * @param name of this student
     * @param age of this student
     */
    public Student(final String name, final int age) {
        super(name, age);
        this.subjects = new ArrayList<>();
    }
    
    /**
     * Constructor of Student
     * @param name of the student
     * @param age of the student
     * @param subjects the student already has
     */
    public Student(final String name, final int age, final ArrayList<String> subjects) {
        this(name, age);
        this.subjects = subjects;
    }
    
    /**
     * Adds a subject to this student
     * @param subject to add
     */
    public void addSubject(final String subject) {
        this.subjects.add(subject);
    }
    
    /**
     * Prints student
     */
    @Override
    public String toString() {
        String result = super.toString() + "\r\n";
        for(String subject : this.subjects) {
            result += subject + "\r\n";
        }
        return result;
    }
}
