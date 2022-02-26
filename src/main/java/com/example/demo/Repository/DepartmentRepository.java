package com.example.demo.Repository;

import com.example.demo.Connection.ConnectionClass;
import com.example.demo.Model.Department;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Date;

public class DepartmentRepository {
    ConnectionClass connectionClass = new ConnectionClass();
    Connection connection = connectionClass.getConnection();

    public ObservableList<Department> getList(){
        ObservableList<Department> list = FXCollections.observableArrayList();
        try {
            Statement statement=connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            String sql="SELECT * FROM department;";

            ResultSet resultSet=statement.executeQuery(sql);

            if (resultSet.next()){
                resultSet.previous();
                while (resultSet.next()) {
                    Department department = new Department(
                            resultSet.getString("Dname"),
                            resultSet.getInt("Dnumber"),
                            resultSet.getString("Mgr_ssn"),
                            resultSet.getDate("Mgr_start_date")
                    );
                    list.add(department);
                }
            }else {
                System.out.println("no data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Department getDepartment(String ssn) {
        Date date = new Date();
        Department department = new Department("", 1, "", date);
        try {
            Statement statement=connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT `Dname`, `Dnumber`, `Mgr_ssn`, `Mgr_start_date` FROM `department` WHERE `Mgr_ssn` = "+ ssn +";";
            ResultSet resultSet=statement.executeQuery(sql);
            if (resultSet.next()){
                resultSet.previous();
                while (resultSet.next()) {
                    department.setDname(resultSet.getString("Dname"));
                    department.setDnumber(resultSet.getInt("Dnumber"));
                    department.setMgr_ssn(resultSet.getString("Mgr_ssn"));
                    department.setMgr_start_date(resultSet.getDate("Mgr_start_date"));
                }
            }else {
                System.out.println("no data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(department.getDname());
        return department;
    }
}
