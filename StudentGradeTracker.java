
import java.util.*;

// Class representing a single student
class Student {
    private int rollNo;
    private double grade;
    private String name;

    // Constructor to initialize student details
    public Student(String name, int rollNo, double grade) {
        this.rollNo = rollNo;
        this.grade = grade;
        this.name = name;
    }

    // Setter methods (used in update)
    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
    // Here Roll number acts as the primary key so it is no changable for updating
    // an exiting student information

    // Getter methods
    public int getRollNo() {
        return rollNo;
    }

    public double getGrade() {
        return grade;
    }

    public String getName() {
        return name;
    }
}

public class StudentGradeTracker {
    // creating ArrayList of student type
    static ArrayList<Student> students = new ArrayList<>();
    // static to use outside of main method
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int choice = 0;
        // Menu-driven loop
        do {
            try {
                System.out.println("Enter 1 for Add new student record");
                System.out.println("Enter 2 for Delete existing student record");
                System.out.println("Enter 3 for update existing student record");
                System.out.println("Enter 4 for display statistics ");
                System.out.println("Enter 5 for display all records ");
                System.out.println("Enter 6 for display a specific records ");
                System.out.println("Enter 7 to exit ");
                System.out.println("Enter your choice:");
                choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        addStudent(); // Add new student
                        break;
                    case 2:
                        deleteStudent(); // Delete student
                        break;
                    case 3:
                        updateStudent(); // Update details
                        break;
                    case 4:
                        displaySummary(); // Show statistics
                        break;
                    case 5:
                        displayAllStudents(); // Show all records
                        break;
                    case 6:
                        try {
                            System.out.println("Enter the roll of the student you want to see :");
                            int sroll = sc.nextInt();
                            displaySpecificStudent(sroll);
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Roll number must be an integer");
                            sc.nextLine();
                        } // Show specific student
                        break;
                    case 7:
                        System.out.println("Exiting program...");
                        break;
                    default:
                        System.out.println("Your choice is incorrect");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number from 1 to 7.");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Unable to process menu choice: " + e.getMessage());
                sc.nextLine();
            }
        } while (choice != 7);

    }

    static void addStudent() {
        try {
            System.out.println("Enter the name of the student:");
            sc.nextLine();
            String name = sc.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Student name cannot be empty.");
                return;
            }

            // rollno must be unique
            System.out.println("Enter the roll no:");
            int rollNo = sc.nextInt();

            if (rollNo <= 0) {
                System.out.println("Roll number must be greater than 0.");
                return;
            }

            // Check if roll number already exists
            if (searchStudentByRollNo(rollNo) != null) {
                System.out.println("Roll number already exits !!");
                return;
            }

            System.out.println("Enter the grade(1 to 100):");
            double grade = sc.nextDouble();

            // Validate grade input
            if (grade < 0 || grade > 100) {
                System.out.println("Invalid grade !! Try again..");
                return;
            }

            // Create and store student
            Student s = new Student(name, rollNo, grade);
            students.add(s);
            System.out.println("Student added successfully.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Roll number must be an integer and grade must be numeric.");
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("Unable to add student: " + e.getMessage());
            sc.nextLine();
        }
    }

    static void deleteStudent() {
        try {
            if (students.isEmpty()) {
                System.out.println("No student records available.");
                return;
            }
            System.out.println("Enter the roll of the student you want to remove :");
            int sroll = sc.nextInt();

            // Search student

            Student studentToDelete = searchStudentByRollNo(sroll);
            if (studentToDelete != null) {
                students.remove(studentToDelete);
                System.out.println("Student removed successfully.");
                return;
            }
            System.out.println("Student not found");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Roll number must be an integer ");
            sc.nextLine();
        }catch (Exception e) {
            System.out.println("Unable to delete student: " + e.getMessage());
            sc.nextLine();
        }

    }

    static void displaySummary() {
        if (students.isEmpty()) {
            System.out.println("No student records available.");
            return;
        }

        double sum = 0;
        // Assume first student as initial highest & lowest
        Student lowestStudent = students.get(0);
        Student highestStudent = students.get(0);

        for (Student s : students) {
            sum += s.getGrade();

            if (s.getGrade() > highestStudent.getGrade()) {
                highestStudent = s;
            }

            if (s.getGrade() < lowestStudent.getGrade()) {
                lowestStudent = s;
            }
        }
        // Display results

        double average = sum / students.size();
        System.out.println("_________SUMMARY_________");
        System.out.println("Highest grade is " + highestStudent.getGrade() + " by " + highestStudent.getName());
        System.out.println("___________________________");
        System.out.println("Lowest grade is " + lowestStudent.getGrade() + " by " + lowestStudent.getName());
        System.out.println("___________________________");

        System.out.println("Average grade is " + average);
        System.out.println("___________________________");

    }

    static void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records available.");
            return;
        }
        System.out.println("_________STUDENTS_________");
        System.out.println("TOTAL NUMBER OF STUDENT IS " + students.size());
        System.out.println("__________________________");

        for (Student s : students) {
            System.out.println("NAME :" + s.getName());
            System.out.println("ROLL NO  :" + s.getRollNo());
            System.out.println("GRADE :" + s.getGrade());
            
            System.out.println("__________________________");

        }
    }

    static void displaySpecificStudent(int sroll) {
        if (students.isEmpty()) {
            System.out.println("No student records available.");
            return;
        }
        // System.out.println("Enter the roll of the student you want to see :");
        // int sroll = sc.nextInt();

        try {
            Student foundStudent = searchStudentByRollNo(sroll);
            if (foundStudent != null) {
                
                System.out.println("__________________");
                System.out.println("NAME :" + foundStudent.getName());
                System.out.println("ROLL NO  :" + foundStudent.getRollNo());
                System.out.println("GRADE :" + foundStudent.getGrade());
                System.out.println("__________________");
                
                return;
            }
            System.out.println("Student not found");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Roll number must be an integer");
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("Unable to display student: " + e.getMessage());
            sc.nextLine();
        }
    }

    // Helper method to search student by roll number
    static Student searchStudentByRollNo(int rollNo) {
        for (Student s : students) {
            if (s.getRollNo() == rollNo) {
                return s;
            }
        }
        return null;
    }

    static void updateStudent() {
        if (students.isEmpty()) {
            System.out.println("No student records available.");
            return;
        }
        try {
            System.out.println("Enter the roll of the student you want to update :");
            int sroll = sc.nextInt();
            int choice;
            boolean updated = false;

            Student studentToUpdate = searchStudentByRollNo(sroll);
            if (studentToUpdate != null) {
                do {
                    // Loop for update options
                    System.out.println("1 for update the name\n2 for update the grade\n3 to exit");
                    choice = sc.nextInt();

                    switch (choice) {
                        case 1:
                            System.out.println("Enter the new Name");
                            sc.nextLine();
                            String newName = sc.nextLine().trim();
                            if (newName.isEmpty()) {
                                System.out.println("Name cannot be empty.");
                                break;
                            }
                            studentToUpdate.setName(newName);
                            updated = true;
                            break;
                        case 2:
                            System.out.println("Enter new grade");
                            double newgrade = sc.nextDouble();
                            if (newgrade < 0 || newgrade > 100) {
                                System.out.println("Invalid grade !! Try again..");
                                break;
                            }
                            studentToUpdate.setGrade(newgrade);
                            updated = true;
                            break;
                        case 3:
                            System.out.println("Exiting from update method ...");
                            break;
                        default:
                            System.out.println("Invalid choice!!");
                    }
                } while (choice != 3);

                if (updated) {
                    System.out.println("Student updated successfully");
                    displaySpecificStudent(studentToUpdate.getRollNo());
                }
            } else {
                System.out.println("Record not found !!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Enter numeric values for roll number, menu choice, and grade.");
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("Unable to update student: " + e.getMessage());
            sc.nextLine();
        }

    }

}
