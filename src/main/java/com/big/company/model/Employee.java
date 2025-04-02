package com.big.company.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Employee {
    private Integer id;
    private String firstName;
    private String lastName;
    private double salary;
    private Integer managerId;
    private List<Employee> peers;


    public Employee(Integer id, String firstName, String lastName, double salary, Integer managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId;
        this.peers = new CopyOnWriteArrayList<>();
    }

    public void addPeers(Employee subordinate) {
        peers.add(subordinate);
    }



    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
    public Integer getManagerId() {
        return managerId;
    }
    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }
    public List<Employee> getPeers() {
        return peers;
    }
    @Override
    public String toString() {
        return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", salary=" + salary
                + ", managerId=" + managerId + "]";
    }




}
