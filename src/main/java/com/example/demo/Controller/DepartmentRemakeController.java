package com.example.demo.Controller;

import com.example.demo.Connection.ConnectionClass;
import com.example.demo.Model.Department;
import com.example.demo.Repository.DepartmentRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.demo.Controller.DepartmentController.ssn;

public class DepartmentRemakeController {
    final DepartmentRepository repository = new DepartmentRepository();

    @FXML
    private TextField Dname;

    @FXML
    private TextField Dnumber;

    @FXML
    private TextField Ssn;

    @FXML
    private DatePicker date;

    @FXML
    private Button updateButton;

    @FXML
    void updateDepartment(ActionEvent event) {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        try{
            Statement statement=connection.createStatement();
            String sql = "UPDATE department SET Dname = '" + Dname.getText() + "', Mgr_ssn = '" + Ssn.getText() + "', Mgr_start_date = '" + date.getValue() + "' WHERE Dnumber = " + Dnumber.getText();
            statement.executeUpdate(sql);

            System.out.println("Success!");
         } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize(){
        Department department = repository.getDepartment(ssn);
        Dname.setText(department.getDname());
        Dnumber.setText(Integer.toString(department.getDnumber()));
        Ssn.setText(department.getMgr_ssn());
//        date.setValue(department.getMgr_start_date());
    }

}
