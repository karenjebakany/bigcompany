package com.big.company.services;

import com.big.company.model.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class EmployeeAnalyzer {

    public Map<Integer, Employee> readCSV(String fileName) {
        // Read the csv file and create a list of employees
        // Use BufferedReader to read the file line by line
        // Use String.split() method to split the line by comma
        // Create an Employee object for each line and add it to the list
        // Use try-with-resources to automatically close the BufferedReader

        Map<Integer, Employee> employeeMap = new HashMap<>();
        Employee ceo = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String headerLine = br.readLine(); // Read the header line
            if (headerLine == null) {
                throw new IOException("Empty file");
            }
            // Read the rest of the lines
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                // Check if the line has the correct number of values
                if (values[0].isEmpty() || values[1].isEmpty() || values[2].isEmpty() || values[3].isEmpty()) {
                    throw new IOException("Invalid data in file");
                }

                if (values.length < 5) {
                    // add a default value for managerId
                    values = Arrays.copyOf(values, 5);
                    values[4] = "0";
                }
                // Create an Employee object and add it to the list
                Employee employee = new Employee
                        (Integer.parseInt(values[0]),
                                values[1], values[2],
                                Double.parseDouble(values[3]), Integer.parseInt(values[4]));
                employeeMap.put(employee.getId(), employee);
                if (Integer.parseInt(values[4]) == 0) {
                    ceo = employee;
                }
            }
            System.out.println(employeeMap);
            // Build the hierarchy
            for (Employee employee : employeeMap.values()) {
                if (employee.getManagerId() != 0) {
                    Employee manager = employeeMap.get(employee.getManagerId());
                    manager.addPeers(employee);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("CEO is " + ceo);
        return employeeMap;
    }

    public List<Employee> findManagersEarnLess(Map<Integer,Employee> employeeMap) {
        // Find all employees who are managers and earn less than their subordinates
        // Use a List to store the employees who meet the criteria
        List<Employee> managers = new ArrayList<>();
        for (Employee employee : employeeMap.values()) {
            if (!employee.getPeers().isEmpty()) {
                double avgSubordinateSalary = employee.getPeers().stream()
                        .mapToDouble(Employee::getSalary)
                        .average().orElse(0);
                double minManagerSalary = 1.2 * avgSubordinateSalary;
                if(employee.getSalary() < minManagerSalary) {
                    managers.add(employee);
                    System.out.printf("Manager %s %s earns less than their subordinates by %.2f%n",
                            employee.getFirstName(), employee.getLastName(),
                            minManagerSalary - employee.getSalary());
                }
            }

        }
        return managers;
    }

    public List<Employee> findManagersEarnMore(Map<Integer,Employee> employeeMap) {
        // Find all employees who are managers and earn more than their subordinates
        // Use a List to store the employees who meet the criteria
        List<Employee> managers = new ArrayList<>();
        for (Employee employee : employeeMap.values()) {
            if (!employee.getPeers().isEmpty()) {
                double avgSubordinateSalary = employee.getPeers().stream()
                        .mapToDouble(Employee::getSalary)
                        .average().orElse(0);
                double maxManagerSalary = 1.5 * avgSubordinateSalary;
                if(employee.getSalary() > maxManagerSalary) {
                    managers.add(employee);
                    System.out.printf("Manager %s %s earns more than their subordinates by %.2f%n",
                            employee.getFirstName(), employee.getLastName(),
                            employee.getSalary() - maxManagerSalary);
                }
            }

        }
        return managers;
    }



    public Map<Employee, Integer> analyzeReportingLines(Map<Integer,Employee> employeeMap) {
        Map<Employee, Integer> managerMap = new HashMap<>();
        for (Employee employee : employeeMap.values()) {
            // Check the height of the reporting line
            int depth = getReportingLineDepth(employee,employeeMap);

            if (depth > 4) {
                // Print a message indicating the employee's reporting line is too long
                System.out.printf("Employee %s %s has a reporting line too long by %d managers%n",
                        employee.getFirstName(), employee.getLastName(),
                        depth - 4);
                // add the employee to the managerMap
                managerMap.put(employee, depth);
            }
        }
        return managerMap;
    }

    private int getReportingLineDepth(Employee employee, Map<Integer,Employee> employeeMap) {
        int depth = 0;
        while (employee.getManagerId() != 0) {
            depth++;
            employee = employeeMap.get(employee.getManagerId());
        }
        return depth;
    }
}
