package com.example.demo.Controller;

import com.example.demo.Connection.ConnectionClass;
import com.example.demo.Main;
import com.example.demo.Model.Employee;
import com.example.demo.Repository.EmployeeRepository;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Objects;


public class Controller {
    final EmployeeRepository repository = new EmployeeRepository();
    public static String ssn;

    @FXML
    public TableView<Employee> employeeTable;
    @FXML
    public TableColumn<Employee, String> fNameColumn;
    @FXML
    public TableColumn<Employee, String> lNameColumn;
    @FXML
    public TableColumn<Employee, String> ssnColumn;
    @FXML
    public TableColumn<Employee, Date> bdateColumn;
    @FXML
    public TableColumn<Employee, String> addressColumn;
    @FXML
    public TableColumn<Employee, String> sexColumn;
    @FXML
    public TableColumn<Employee, Employee> actionColumn;

    @FXML
    public TextField Fname;
    @FXML
    public TextField Lname;
    @FXML
    public TextField Ssn;
    @FXML
    public DatePicker Bdate;
    @FXML
    public TextField Address;
    @FXML
    public TextField Sex;
    @FXML
    public TextField Salary;

    @FXML
    private void initialize(){
        fNameColumn.setCellValueFactory(cellData -> cellData.getValue().fnameProperty());
        lNameColumn.setCellValueFactory(cellData -> cellData.getValue().lnameProperty());
        ssnColumn.setCellValueFactory(cellData -> cellData.getValue().ssnProperty());
        bdateColumn.setCellValueFactory(cellData -> cellData.getValue().bdateProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        sexColumn.setCellValueFactory(cellData -> cellData.getValue().sexProperty());
        actionColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));


        initializeTableValues();

        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");
            private final Button updateButton = new Button("Update");
            private final HBox pane = new HBox(deleteButton, updateButton);

            @Override
            protected void updateItem(Employee employee, boolean empty) {
                super.updateItem(employee, empty);

                if (employee == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(pane);

                deleteButton.setOnAction(event -> removePerson(employee.getSsn()));
                updateButton.setOnAction(event -> {
                    openEmployeeRemakePage(event);
                    ssn = employee.getSsn();
                });
            }
        });
    }

    public void removePerson(String ssn) {
        try {
            Statement statement=connection.createStatement();
            String sql = "DELETE FROM employee WHERE ssn = '" + ssn + "'";
            statement.executeUpdate(sql);

            System.out.println("Success!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        initializeTableValues();
    }

    ConnectionClass connectionClass = new ConnectionClass();
    Connection connection = connectionClass.getConnection();

    public void insertPerson(ActionEvent actionEvent) {
        try {
            Statement statement=connection.createStatement();
            String sql = "INSERT INTO " +
                    "employee(Fname, Lname, Ssn, Bdate, Address, Sex, Salary, Dnumber) " +
                    "VALUES ('"+Fname.getText()+"','" +
                    ""+Lname.getText()+"','" +
                    ""+Ssn.getText()+"','" +
                    ""+Bdate.getValue()+"','" +
                    ""+Address.getText()+"','" +
                    ""+Sex.getText()+"','" +
                    ""+Salary.getText()+"', '1')";
            statement.executeUpdate(sql);

            System.out.println("Success!");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Fname.setText("");
        Lname.setText("");
        Ssn.setText("");
        Address.setText("");
        Sex.setText("");
        Salary.setText("");

        initializeTableValues();
    }

    public void initializeTableValues(){
        ObservableList<Employee> personList = repository.getList();
        System.out.println(personList);
        if(personList.size() > 0){
            employeeTable.setItems(personList);
        }
    }

    public void openEmployeeRemakePage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("employeeRemake.fxml")));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

