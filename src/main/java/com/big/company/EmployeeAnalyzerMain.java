package com.big.company;

import com.big.company.model.Employee;
import com.big.company.services.EmployeeAnalyzer;

import java.net.URISyntaxException;

import java.util.List;
import java.util.Map;

public class EmployeeAnalyzerMain
{
    public static void main( String[] args ) throws URISyntaxException {
        EmployeeAnalyzer employeeAnalyzer = new EmployeeAnalyzer();
        String filePath = ClassLoader.getSystemResource("employee.csv").getPath();
        System.out.println(filePath);
//        Path path = Paths.get(ClassLoader.getSystemResource("filename.txt").toURI());

        Map<Integer, Employee> map = employeeAnalyzer.readCSV(filePath);
        map.forEach((k,v) -> {
            System.out.println("Employee ID: " + k);
            System.out.println("Employee Name: " + v.getFirstName() + " " + v.getLastName());
            System.out.println("Employee Salary: " + v.getSalary());
            System.out.println("Employee Manager ID: " + v.getManagerId());
            System.out.println("Employee Peers: " + v.getPeers());
            System.out.println();
        });
        System.out.println("Finding managers who earn less than their subordinates...");
        List<Employee> lessEarningManagers = employeeAnalyzer.findManagersEarnLess(map);
        // Print the list of managers who earn less than their subordinates
        lessEarningManagers.forEach(System.out::println);
        System.out.println("Done.");
        System.out.println("Finding managers who earn more than their subordinates...");
        List<Employee> moreEarningManagers = employeeAnalyzer.findManagersEarnMore(map);
        moreEarningManagers.forEach(System.out::println);
        // Print the list of managers who earn more than their subordinates
        System.out.println("Done.");
        System.out.println("Analyzing reporting lines...");
        Map<Employee,Integer> depthLinesMap = employeeAnalyzer.analyzeReportingLines(map);
        // Print the list of managers who have a reporting line too long
        depthLinesMap.forEach((k,v) -> {
            System.out.println("Employee ID: " + k.getId());
            System.out.println("Employee Name: " + k.getFirstName() + " " + k.getLastName());
            System.out.println("Employee Salary: " + k.getSalary());
            System.out.println("Employee Manager ID: " + k.getManagerId());
            System.out.println("Employee Peers: " + k.getPeers());
            System.out.println("Reporting line depth: " + v);
            System.out.println();
        });
        System.out.println("Done.");
    }
}
