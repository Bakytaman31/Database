package com.example.demo.Repository;

import com.example.demo.Connection.ConnectionClass;
import com.example.demo.Model.Department;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}
