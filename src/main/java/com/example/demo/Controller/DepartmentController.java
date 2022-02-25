package com.example.demo.Controller;

import com.example.demo.Connection.ConnectionClass;
import com.example.demo.Model.Department;
import com.example.demo.Repository.DepartmentRepository;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


public class DepartmentController {
    final DepartmentRepository repository = new DepartmentRepository();

    @FXML
    public TableView<Department> departmentTable;
    @FXML
    public TableColumn<Department, String> dNameColumn;
    @FXML
    public TableColumn<Department, Integer> dNumberColumn;
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
//        dNumberColumn.setCellValueFactory(cellData -> cellData.getValue().dnumberProperty());
        ssnColumn.setCellValueFactory(cellData -> cellData.getValue().mgr_ssnProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().mgr_start_dateProperty());
        actionColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));

        initializeTableValues();


        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(Department department, boolean empty) {
                super.updateItem(department, empty);

                if (department == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);

                deleteButton.setOnAction(event -> removeDepartment(department.getMgr_ssn()));
            }
        });
        departmentTable.setOnMouseClicked(mouseEvent -> {
            System.out.println(departmentTable.getSelectionModel().getSelectedItem().getMgr_ssn());
        });

    }

    public void removeDepartment(String ssn) {
        try {
            Statement statement=connection.createStatement();
            String sql = "DELETE FROM department WHERE ssn = '" + ssn + "'";
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




    /**
     * Данный метод делает запрос -SELECT- в базу данных и из полученных данных формирует список
     * типа -ObservableList<Employee>-, с помощью которого заполняет таблицу -personTable-
     */
    public void initializeTableValues(){
        ObservableList<Department> departmentsList = repository.getList();
        if(departmentsList.size() > 0){
            departmentTable.setItems(departmentList);
        }
    }
}

