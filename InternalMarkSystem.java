import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Student {
    private int id;
    private String name;
    private int assignmentMarks;
    private int quizMarks;
    private int attendanceMarks;
    private int totalMarks;

    public Student(int id, String name, int assignmentMarks, int quizMarks, int attendanceMarks) {
        this.id = id;
        this.name = name;
        this.assignmentMarks = assignmentMarks;
        this.quizMarks = quizMarks;
        this.attendanceMarks = attendanceMarks;
        this.totalMarks = calculateTotalMarks();
    }

    private int calculateTotalMarks() {
        return (int) (assignmentMarks * 0.5 + quizMarks * 0.3 + attendanceMarks * 0.2);
    }

    public String toCSV() {
        return id + "," + name + "," + assignmentMarks + "," + quizMarks + "," + attendanceMarks + "," + totalMarks;
    }

    public void displayStudentInfo() {
        System.out.println("ID: " + id + ", Name: " + name + ", Assignment: " + assignmentMarks 
                + ", Quiz: " + quizMarks + ", Attendance: " + attendanceMarks 
                + ", Total Marks: " + totalMarks);
    }
}

public class InternalMarkSystem {
    private static ArrayList<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String FILE_PATH = "students.csv";

    public static void main(String[] args) {
        loadFromFile();
        int choice;
        do {
            System.out.println("\n--- Internal Mark Calculation System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Save and Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    displayAllStudents();
                    break;
                case 3:
                    saveToFile();
                    System.out.println("Data saved successfully. Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }

    private static void addStudent() {
        System.out.print("Enter Student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Assignment Marks (out of 100): ");
        int assignmentMarks = scanner.nextInt();

        System.out.print("Enter Quiz Marks (out of 100): ");
        int quizMarks = scanner.nextInt();

        System.out.print("Enter Attendance Marks (out of 100): ");
        int attendanceMarks = scanner.nextInt();

        students.add(new Student(id, name, assignmentMarks, quizMarks, attendanceMarks));
        System.out.println("Student added successfully!");
    }

    private static void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
        } else {
            System.out.println("\n--- Student Details ---");
            for (Student student : students) {
                student.displayStudentInfo();
            }
        }
    }

    private static void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Student student : students) {
                writer.write(student.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error while saving to file: " + e.getMessage());
        }
    }

    private static void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                int assignmentMarks = Integer.parseInt(data[2]);
                int quizMarks = Integer.parseInt(data[3]);
                int attendanceMarks = Integer.parseInt(data[4]);
                students.add(new Student(id, name, assignmentMarks, quizMarks, attendanceMarks));
            }
        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        } catch (IOException e) {
            System.out.println("Error while loading data: " + e.getMessage());
        }
    }
}
