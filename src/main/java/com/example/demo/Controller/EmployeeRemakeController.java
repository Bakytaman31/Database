package com.example.demo.Controller;

import com.example.demo.Connection.ConnectionClass;
import com.example.demo.Model.Employee;
import com.example.demo.Repository.EmployeeRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.demo.Controller.Controller.ssn;

public class EmployeeRemakeController {
    final EmployeeRepository repository = new EmployeeRepository();

    @FXML
    private TextField Address;

    @FXML
    private DatePicker Bdate;

    @FXML
    private TextField Fname;

    @FXML
    private TextField Lname;

    @FXML
    private TextField Salary;

    @FXML
    private TextField Sex;

    @FXML
    private TextField Ssn;

    @FXML
    private Button submitButton;

    @FXML
    void updateEmployee(ActionEvent event) {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        try{
            Statement statement=connection.createStatement();
//            String sql = "UPDATE department SET Fname = '" + Fname.getText() + "', Mgr_ssn = '" + Ssn.getText() + "', Mgr_start_date = '" + date.getValue() + "' WHERE Dnumber = " + Dnumber.getText();
            String sql = "UPDATE employee SET Fname = '" + Fname.getText() + "', Lname = '" + Lname.getText() + "', Bdate = '" + Bdate.getValue() + "', Address = '" + Address.getText() + "', Sex = '" + Sex.getText() + "', Salary = '" + Double.parseDouble(Salary.getText()) + "'";
            statement.executeUpdate(sql);

            System.out.println("Success!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        Employee employee = repository.getEmployee(ssn);
        Fname.setText(employee.getFname());
        Lname.setText(employee.getLname());
        Ssn.setText(employee.getSsn());
        Address.setText(employee.getAddress());
        Sex.setText(employee.getSex());
        Salary.setText(Double.toString(employee.getSalary()));
    }

}
