package com.example.demo.Controller;

import com.example.demo.Connection.ConnectionClass;
import com.example.demo.Main;
import com.example.demo.Model.Department;
import com.example.demo.Repository.DepartmentRepository;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
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


public class DepartmentController {
    final DepartmentRepository repository = new DepartmentRepository();
    public static String ssn;

    @FXML
    public TableView<Department> departmentTable;
    @FXML
    public TableColumn<Department, String> dNameColumn;
    @FXML
    public TableColumn<Department, Number> dNumberColumn;
    @FXML
    public TableColumn<Department, String> ssnColumn;
    @FXML
    public TableColumn<Department, Date> dateColumn;
    @FXML
    public TableColumn<Department, Department> actionColumn;

    @FXML
    public TextField Dname;
    @FXML
    public TextField Dnumber;
    @FXML
    public TextField Ssn;
    @FXML
    public DatePicker date;


    @FXML
    private void initialize(){

        dNameColumn.setCellValueFactory(cellData -> cellData.getValue().dnameProperty());
        dNumberColumn.setCellValueFactory(cellData -> cellData.getValue().dnumberProperty());
        ssnColumn.setCellValueFactory(cellData -> cellData.getValue().mgr_ssnProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().mgr_start_dateProperty());
        actionColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));

        initializeTableValues();


        actionColumn.setCellFactory(param -> new TableCell<Department, Department>() {
            private final Button deleteButton = new Button("Delete");
            private final Button updateButton = new Button("Update");
            private final HBox pane = new HBox(deleteButton, updateButton);

            @Override
            protected void updateItem(Department department, boolean empty) {
                super.updateItem(department, empty);

                if (department == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(pane);

                deleteButton.setOnAction(event -> removeDepartment(department.getMgr_ssn()));
                updateButton.setOnAction(event -> {
                    openDepartmentRemakePage(event);
                    ssn = department.getMgr_ssn();
                });
            }
        });
    }

    public void removeDepartment(String ssn) {
        try {
            Statement statement=connection.createStatement();
            String sql = "DELETE FROM department WHERE Mgr_ssn = '" + ssn + "'";
            statement.executeUpdate(sql);

            System.out.println("Success!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        initializeTableValues();
    }


    ConnectionClass connectionClass = new ConnectionClass();
    Connection connection = connectionClass.getConnection();

    public ObservableList<Department> departmentList = FXCollections.observableArrayList();

    public void insertDepartment(ActionEvent actionEvent) {
        try {
            Statement statement=connection.createStatement();
            String sql = "INSERT INTO " +
                    "department(Dname, Dnumber, Mgr_ssn, Mgr_start_date) " +
                    "VALUES ('"+Dname.getText()+"','" +
                    ""+Dnumber.getText()+"','" +
                    ""+Ssn.getText()+"','" +
                    ""+date.getValue()+"')";
            statement.executeUpdate(sql);

            System.out.println("Success!");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Dname.setText("");
        Dnumber.setText("");
        Ssn.setText("");


        initializeTableValues();
    }


    public void initializeTableValues(){
        ObservableList<Department> departmentsList = repository.getList();
        if(departmentsList.size() > 0){
            departmentTable.setItems(departmentsList);
        }
    }

    public void openDepartmentRemakePage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("departmentRemake.fxml")));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

