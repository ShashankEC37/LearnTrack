package com.airtribe.learntrack.ui;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.EnrollmentStatus;
import com.airtribe.learntrack.entity.Person;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.entity.Trainer;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;
import com.airtribe.learntrack.util.InputValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();
    private static final EnrollmentService enrollmentService =
            new EnrollmentService(studentService, courseService);

    private static final List<Trainer> trainers = new ArrayList<>();

    public static void main(String[] args) {
        loadSampleData();
        System.out.println("Welcome to LearnTrack");

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readMenuChoice();
            switch (choice) {
                case 1:
                    studentMenu();
                    break;
                case 2:
                    courseMenu();
                    break;
                case 3:
                    enrollmentMenu();
                    break;
                case 4:
                    showAllPeople();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose from the menu.");
            }
        }
        System.out.println("Goodbye!");
    }

    private static void printMainMenu() {
        System.out.println();
        System.out.println("----- MAIN MENU -----");
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Enrollment Management");
        System.out.println("4. View All People (students + trainers)");
        System.out.println("0. Exit");
    }

    private static void studentMenu() {
        int choice;
        do {
            System.out.println();
            System.out.println("----- STUDENT MANAGEMENT -----");
            System.out.println("1. Add new student");
            System.out.println("2. View all students");
            System.out.println("3. Search student by ID");
            System.out.println("4. Update student email/batch");
            System.out.println("5. Deactivate a student");
            System.out.println("0. Back to main menu");
            choice = readMenuChoice();
            try {
                switch (choice) {
                    case 1: addStudent(); break;
                    case 2: viewAllStudents(); break;
                    case 3: searchStudentById(); break;
                    case 4: updateStudent(); break;
                    case 5: deactivateStudent(); break;
                    case 0: break;
                    default: System.out.println("Invalid option. Please choose from the menu.");
                }
            } catch (EntityNotFoundException | InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (choice != 0);
    }

    private static void courseMenu() {
        int choice;
        do {
            System.out.println();
            System.out.println("----- COURSE MANAGEMENT -----");
            System.out.println("1. Add new course");
            System.out.println("2. View all courses");
            System.out.println("3. Activate a course");
            System.out.println("4. Deactivate a course");
            System.out.println("0. Back to main menu");
            choice = readMenuChoice();
            try {
                switch (choice) {
                    case 1: addCourse(); break;
                    case 2: viewAllCourses(); break;
                    case 3: changeCourseStatus(true); break;
                    case 4: changeCourseStatus(false); break;
                    case 0: break;
                    default: System.out.println("Invalid option. Please choose from the menu.");
                }
            } catch (EntityNotFoundException | InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (choice != 0);
    }

    private static void enrollmentMenu() {
        int choice;
        do {
            System.out.println();
            System.out.println("----- ENROLLMENT MANAGEMENT -----");
            System.out.println("1. Enroll a student in a course");
            System.out.println("2. View enrollments for a student");
            System.out.println("3. View all enrollments");
            System.out.println("4. Mark enrollment as COMPLETED");
            System.out.println("5. Mark enrollment as CANCELLED");
            System.out.println("0. Back to main menu");
            choice = readMenuChoice();
            try {
                switch (choice) {
                    case 1: enrollStudent(); break;
                    case 2: viewEnrollmentsForStudent(); break;
                    case 3: viewAllEnrollments(); break;
                    case 4: changeEnrollmentStatus(EnrollmentStatus.COMPLETED); break;
                    case 5: changeEnrollmentStatus(EnrollmentStatus.CANCELLED); break;
                    case 0: break;
                    default: System.out.println("Invalid option. Please choose from the menu.");
                }
            } catch (EntityNotFoundException | InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (choice != 0);
    }

    private static void addStudent() throws InvalidInputException {
        String firstName = InputValidator.requireNonEmpty(readLine("First name: "), "First name");
        String lastName = InputValidator.requireNonEmpty(readLine("Last name: "), "Last name");
        String email = readLine("Email (press Enter to skip): ").trim();
        String batch = InputValidator.requireNonEmpty(readLine("Batch: "), "Batch");

        Student student = email.isEmpty()
                ? studentService.addStudent(firstName, lastName, batch)
                : studentService.addStudent(firstName, lastName, email, batch);
        System.out.println("Student added with ID " + student.getId() + ".");
    }

    private static void viewAllStudents() {
        printList("students", studentService.listStudents());
    }

    private static void searchStudentById() throws InvalidInputException, EntityNotFoundException {
        int id = readId("Student ID: ");
        System.out.println(studentService.findStudentById(id));
    }

    private static void updateStudent() throws InvalidInputException, EntityNotFoundException {
        int id = readId("Student ID: ");
        studentService.findStudentById(id);
        String email = readLine("New email (press Enter to keep current): ").trim();
        String batch = readLine("New batch (press Enter to keep current): ").trim();
        studentService.updateStudent(id, email, batch);
        System.out.println("Student #" + id + " updated.");
    }

    private static void deactivateStudent() throws InvalidInputException, EntityNotFoundException {
        int id = readId("Student ID to deactivate: ");
        studentService.deactivateStudent(id);
        System.out.println("Student #" + id + " is now inactive.");
    }

    private static void addCourse() throws InvalidInputException {
        String name = InputValidator.requireNonEmpty(readLine("Course name: "), "Course name");
        String description = readLine("Description: ").trim();
        int weeks = InputValidator.parsePositiveInt(readLine("Duration in weeks: "));
        Course course = courseService.addCourse(name, description, weeks);
        System.out.println("Course added with ID " + course.getId() + ".");
    }

    private static void viewAllCourses() {
        printList("courses", courseService.listCourses());
    }

    private static void changeCourseStatus(boolean active) throws InvalidInputException, EntityNotFoundException {
        int id = readId("Course ID: ");
        courseService.setCourseActive(id, active);
        System.out.println("Course #" + id + " is now " + (active ? "active." : "inactive."));
    }

    private static void enrollStudent() throws InvalidInputException, EntityNotFoundException {
        int studentId = readId("Student ID: ");
        int courseId = readId("Course ID: ");
        Enrollment enrollment = enrollmentService.enrollStudent(studentId, courseId);
        System.out.println("Enrollment created with ID " + enrollment.getId() + ".");
    }

    private static void viewEnrollmentsForStudent() throws InvalidInputException, EntityNotFoundException {
        int studentId = readId("Student ID: ");
        printList("enrollments", enrollmentService.getEnrollmentsForStudent(studentId));
    }

    private static void viewAllEnrollments() {
        printList("enrollments", enrollmentService.listEnrollments());
    }

    private static void changeEnrollmentStatus(EnrollmentStatus status)
            throws InvalidInputException, EntityNotFoundException {
        int enrollmentId = readId("Enrollment ID: ");
        enrollmentService.updateStatus(enrollmentId, status);
        System.out.println("Enrollment #" + enrollmentId + " marked as " + status + ".");
    }

    private static void showAllPeople() {
        List<Person> people = new ArrayList<>();
        people.addAll(studentService.listStudents());
        people.addAll(trainers);
        System.out.println();
        for (Person person : people) {
            System.out.println("- " + person.getDisplayName());
        }
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            System.out.println();
            System.out.println("Input closed. Exiting LearnTrack.");
            System.exit(0);
        }
        return scanner.nextLine();
    }

    private static int readMenuChoice() {
        try {
            return InputValidator.parseInt(readLine("Choose an option: "));
        } catch (InvalidInputException e) {
            return -1;
        }
    }

    private static int readId(String prompt) throws InvalidInputException {
        return InputValidator.parsePositiveInt(readLine(prompt));
    }

    private static <T> void printList(String label, List<T> items) {
        if (items.isEmpty()) {
            System.out.println("No " + label + " found.");
            return;
        }
        for (T item : items) {
            System.out.println(item);
        }
    }

    private static void loadSampleData() {
        try {
            studentService.addStudent("Asha", "Verma", "asha.verma@example.com", "B1");
            studentService.addStudent("Rohit", "Mehta", "B1");
            courseService.addCourse("Core Java", "Java fundamentals and OOP", 8);
            courseService.addCourse("Spring Boot", "Building REST APIs", 6);
            trainers.add(new Trainer(1, "Neha", "Kapoor", "neha.kapoor@example.com", "Java"));
        } catch (InvalidInputException e) {
            System.out.println("Could not load sample data: " + e.getMessage());
        }
    }
}
