package com.example.demo.Repository;

import com.example.demo.Connection.ConnectionClass;
import com.example.demo.Model.Department;
import com.example.demo.Model.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class EmployeeRepository {
    ConnectionClass connectionClass = new ConnectionClass();
    Connection connection = connectionClass.getConnection();

    public ObservableList<Employee> getList(){
        ObservableList<Employee> list = FXCollections.observableArrayList();
        try {
            Statement statement=connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            String sql="SELECT * FROM employee;";

            ResultSet resultSet=statement.executeQuery(sql);

            if (resultSet.next()){
                resultSet.previous();
                while (resultSet.next()) {
                    Employee employee = new Employee(
                            resultSet.getString("Fname"),
                            resultSet.getString("Lname"),
                            resultSet.getString("Ssn"),
                            resultSet.getDate("Bdate"),
                            resultSet.getString("Address"),
                            resultSet.getString("Sex"),
                            resultSet.getDouble("Salary"),
                            resultSet.getString("Super_ssn"),
                            resultSet.getInt("Dnumber")
                    );
                    list.add(employee);
                }
            }else {
                System.out.println("no data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Employee getEmployee(String ssn) {
        Date date = new Date();
        Employee employee = new Employee("", "", "", date, "", "", 11.0, "", 1);
        try {
            Statement statement=connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT `Fname`, `Lname`, `Ssn`, `Bdate`, `Address`, `Sex`, `Salary` FROM `employee` WHERE `Ssn` = "+ ssn +";";
            ResultSet resultSet=statement.executeQuery(sql);
            if (resultSet.next()){
                resultSet.previous();
                while (resultSet.next()) {
                    employee.setFname( resultSet.getString("Fname"));
                    employee.setLname(resultSet.getString("Lname"));
                    employee.setSsn(resultSet.getString("Ssn"));
                    employee.setBdate(resultSet.getDate("Bdate"));
                    employee.setAddress(resultSet.getString("Address"));
                    employee.setSex(resultSet.getString("Sex"));
                    employee.setSalary(resultSet.getDouble("Salary"));
                }
            }else {
                System.out.println("no data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }
}
