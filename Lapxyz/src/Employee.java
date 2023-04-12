
import java.io.*;
import java.util.*;

class Employee {
    private String name;
    private int id;
    private String contact;
    private String email;

    // Constructor to initialize object
    public Employee(String name, int id, String contact, String email) {
        this.name = name;
        this.id = id;
        this.contact = contact;
        this.email = email;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("%-20s %-10s %-20s %-20s", name, id, contact, email);
    }
}

class EmployeeManagementSystem {
    private static final String FILENAME = "employeeData.txt";
    private static List<Employee> employees = new ArrayList<>();

    // Display menu and get user's choice
    private static int getMenuChoice() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n*Welcome to the Employee Management System*");
        System.out.println("1). Add Employee to the DataBase");
        System.out.println("2). Search for Employee");
        System.out.println("3). Edit Employee details");
        System.out.println("4). Delete Employee Details");
        System.out.println("5). Display all Employees working in this company");
        System.out.println("6). EXIT");

        int choice = 0;
        boolean validChoice = false;
        while (!validChoice) {
            System.out.print("Enter your choice (1-6): ");
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice >= 1 && choice <= 6) {
                    validChoice = true;
                } else {
                    System.out.println("Invalid choice! Please enter a number between 1 and 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice! Please enter a number between 1 and 6.");
            }
        }
        return choice;
    }

    // Add a new employee to the list
    private static void addEmployee() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter employee name: ");
        String name = sc.nextLine();
        int id = getUniqueId();
        System.out.print("Enter contact number: ");
        String contact = sc.nextLine();
        System.out.print("Enter email address: ");
        String email = sc.nextLine();

        Employee employee = new Employee(name, id, contact, email);
        employees.add(employee);
        System.out.println("Employee added with ID: " + id);
        saveData();
    }

    // Get a unique ID for the new employee
    private static int getUniqueId() {
        int maxId = 0;
        for (Employee employee : employees) {
            if (employee.getId() > maxId) {
                maxId = employee.getId();
            }
        }
        return maxId + 1;
    }

    // Search for an employee by name or ID
    private static void searchEmployee() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Search by:");
        System.out.println("1). Employee Name");
        System.out.println("2). Employee ID");

        int searchBy = 0;
        boolean validSearchBy = false;
        while (!validSearchBy) {
            System.out.print("Enter your choice (1-2): ");
            try {
                searchBy = Integer.parseInt(sc.nextLine());
                if (searchBy >= 1 && searchBy <= 2) {
                    validSearchBy = true;
                } else {
                    System.out.println("Invalid choice! Please enter a number between 1 and 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice! Please enter a number between 1 and 2.");
            }
        }

        String searchTerm = "";
        if (searchBy == 1) {
            System.out.print("Enter employee name to search for: ");
            searchTerm = sc.nextLine();
        } else if (searchBy == 2) {
            System.out.print("Enter employee ID to search for: ");
            searchTerm = sc.nextLine();
        }

        boolean found = false;
        for (Employee employee : employees) {
            if ((searchBy == 1 && employee.getName().equalsIgnoreCase(searchTerm)) || (searchBy == 2 && Integer.toString(employee.getId()).equals(searchTerm))) {
                System.out.println("Employee found:");
                System.out.println(String.format("%-20s %-10s %-20s %-20s", "Name", "ID", "Contact", "Email"));
                System.out.println(employee.toString());
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No employee found with the specified search term.");
        }
    }

    // Edit employee details
    private static void editEmployee() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter ID of employee to edit: ");
        int id = Integer.parseInt(sc.nextLine());

        boolean found = false;
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                System.out.println("Employee found:");
                System.out.println(String.format("%-20s %-10s %-20s %-20s", "Name", "ID", "Contact", "Email"));
                System.out.println(employee.toString());
                found = true;

                System.out.println("Enter new details (leave blank if you don't want to change the value):");
                System.out.print("Name: ");
                String name = sc.nextLine().trim();
                if (!name.equals("")) {
                    employee.setName(name);
                }
                System.out.print("Contact: ");
                String contact = sc.nextLine().trim();
                if (!contact.equals("")) {
                    employee.setContact(contact);
                }
                System.out.print("Email: ");
                String email = sc.nextLine().trim();
                if (!email.equals("")) {
                    employee.setEmail(email);
                }

                System.out.println("Employee details updated!");
                saveData();
                break;
            }
        }

        if (!found) {
            System.out.println("No employee found with the specified ID.");
        }
    }

    // Delete an employee from the list
    private static void deleteEmployee() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter ID of employee to delete: ");
        int id = Integer.parseInt(sc.nextLine());

        boolean found = false;
        Iterator<Employee> iterator = employees.iterator();
        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            if (employee.getId() == id) {
                iterator.remove();
                System.out.println("Employee deleted!");
                saveData();
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No employee found with the specified ID.");
        }
    }

    // Display all employees in the list
    private static void displayEmployees() {
        if (employees.size() > 0) {
            System.out.println(String.format("%-20s %-10s %-20s %-20s", "Name", "ID", "Contact", "Email"));
            for (Employee employee : employees) {
                System.out.println(employee.toString());
            }
        } else {
            System.out.println("No employees found!");
        }
    }

    // Load data from file into the list
    private static void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String name = data[0];
                int id = Integer.parseInt(data[1]);
                String contact = data[2];
                String email = data[3];
                Employee employee = new Employee(name, id, contact, email);
                employees.add(employee);
            }
        } catch (IOException e) {
            System.out.println("Error loading data from file: " + e.getMessage());
        }
    }

    // Save data from the list into the file
    private static void saveData() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILENAME))) {
            for (Employee employee : employees) {
                pw.println(employee.getName() + "," + employee.getId() + "," + employee.getContact() + "," + employee.getEmail());
            }
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }

    // Main method that displays menu and handles user actions
    public static void main(String[] args) {
        loadData();
        int choice = getMenuChoice();

        while (choice != 6) {
            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    searchEmployee();
                    break;
                case 3:
                    editEmployee();
                    break;
                case 4:
                    deleteEmployee();
                    break;
                case 5:
                    displayEmployees();
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
            choice = getMenuChoice();
        }

        System.out.println("Exiting...");
    }
}
