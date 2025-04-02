package com.big.company;

import com.big.company.model.Employee;
import com.big.company.services.EmployeeAnalyzer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeeAnalyzeTest

{
    EmployeeAnalyzer employeeAnalyzer;
    Map<Integer, Employee> employeeMap;
    @BeforeEach
    public void setUp()
    {
        employeeAnalyzer = new EmployeeAnalyzer();
        String filePath =  System.getProperty("user.dir")+ "/src/test/resources/employee.csv";
        employeeMap = employeeAnalyzer.readCSV(filePath);

    }
    @AfterEach
    public void tearDown()
    {
        employeeMap = null;
        employeeAnalyzer = null;

    }

    @Test
    public void testReadCSV() {
        // Test the readCSV method
        // You can use assertions to verify the expected behavior
        assertFalse(employeeMap.isEmpty());
        // Check if the map contains the expected number of employees
        assertTrue(employeeMap.containsKey(1));
        assertTrue(employeeMap.containsKey(2));
        assertTrue(employeeMap.containsKey(3));
        assertTrue(employeeMap.containsKey(4));
    }

    @Test
    public void testFindManagersEarnLess() {
        // Test the findManagersEarnLess method
        assertFalse(employeeAnalyzer.findManagersEarnLess(employeeMap).isEmpty());
    }

    @Test
    public void testFindManagersEarnMore() {
        // Test the findManagersEarnMore method
        assertFalse(employeeAnalyzer.findManagersEarnMore(employeeMap).isEmpty());
    }

    @Test
    public void testAnalyzeReportingLines() {
        // Test the analyzeReportingLines method
        assertFalse(employeeAnalyzer.analyzeReportingLines(employeeMap).isEmpty());
    }




}
