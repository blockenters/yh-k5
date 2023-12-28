package com.block.employer.model;

public class Employee {

    public int id;
    public String name;
    public int salary;
    public int age;
    public String profileImage;


    public Employee(int id, String name, int salary, int age, String profileImage) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.age = age;
        this.profileImage = profileImage;
    }
}
