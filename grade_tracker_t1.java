import java.util.ArrayList;
import java.util.Scanner;

class Student {
    String name;
    double grade;

    Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }
}

public class grade_tracker_t1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        System.out.print("Enter the number of students: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // consume newline

        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for student " + (i + 1));
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Grade (0-100): ");
            double grade = scanner.nextDouble();
            scanner.nextLine(); // consume newline

            students.add(new Student(name, grade));
        }

        if (students.isEmpty()) {
            System.out.println("No students entered.");
            return;
        }

        // Calculate average, highest, and lowest
        double total = 0;
        double highest = Double.MIN_VALUE;
        double lowest = Double.MAX_VALUE;
        String topStudent = "", lowStudent = "";

        for (Student s : students) {
            total += s.grade;
            if (s.grade > highest) {
                highest = s.grade;
                topStudent = s.name;
            }
            if (s.grade < lowest) {
                lowest = s.grade;
                lowStudent = s.name;
            }
        }

        double average = total / students.size();

        // Summary report
        System.out.println("\n=== Student Grade Report ===");
        for (Student s : students) {
            System.out.println("Name: " + s.name + ", Grade: " + s.grade);
        }

        System.out.println("\nTotal Students: " + students.size());
        System.out.println("Average Grade: " + average);
        System.out.println("Highest Grade: " + highest + " (by " + topStudent + ")");
        System.out.println("Lowest Grade: " + lowest + " (by " + lowStudent + ")");
    }
}