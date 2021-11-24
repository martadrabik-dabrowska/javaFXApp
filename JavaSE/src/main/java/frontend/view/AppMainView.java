package frontend.view;

import frontend.business.CompanyBD;
import frontend.business.EmployeeBD;
import frontend.model.CompanyVO;
import frontend.model.EmployeeVO;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;


public class AppMainView extends Application {

    private TableView<CompanyVO> companyTableView;

    private TableView<EmployeeVO> employeesTableView;

    private Styles styles;

    @Autowired
    private CompanyBD companyBD;

    @Autowired
    private EmployeeBD employeeBD;

    private String searchValue;

    public AppMainView(){
        this.companyBD = new CompanyBD();
        this.employeeBD = new EmployeeBD();
        this.styles = new Styles();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("App");
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        primaryStage.setScene(createScene());
        primaryStage.show();
    }

    private Scene createScene() {
        StackPane stackPane = createStackPane();
        stackPane.getStyleClass().add("stack");
        styles.setBorderStyle(stackPane);
        Scene scene = new Scene(stackPane, 800, 800);
        return scene;
    }

    private StackPane createStackPane() {
        StackPane stackPane = new StackPane();
        VBox topVbox = addTopVBox();
        Pane deleteBtnPane = addDeleteBtnPane();
        TextField search = getSearchTextField();
        companyTableView = getCompanyTableView();
        refreshDataCompanyTable();
        employeesTableView = getEmployeesTableView();

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.getChildren().addAll(topVbox,deleteBtnPane, search, companyTableView, employeesTableView);

        stackPane.getChildren().add(vBox);
        return stackPane;
    }

    private VBox addTopVBox() {
        VBox topVbox = new VBox();
        HBox radioBtnHbox = new HBox();
        ToggleGroup radioBtnToggleGroup = new ToggleGroup();
        styles.setTopVBoxStyle(topVbox);

        RadioButton companyRadioBtn = new RadioButton("Company");
        companyRadioBtn.setToggleGroup(radioBtnToggleGroup);
        styles.setRadioButtonStyle(companyRadioBtn);
        companyRadioBtn.setSelected(true);

        RadioButton employeeRadioBtn = new RadioButton("Employee");
        employeeRadioBtn.setToggleGroup(radioBtnToggleGroup);

        styles.setRadioButtonStyle(employeeRadioBtn);

        radioBtnHbox.getChildren().addAll(companyRadioBtn, employeeRadioBtn);

        Pane addCompanyPane = getAddCompanyPane();
        Pane addEmployeePane = getAddEmployeePane();

        addEmployeePane.setVisible(false);
        addEmployeePane.setManaged(false);
        radioBtnToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {

            RadioButton radioButton = (RadioButton) radioBtnToggleGroup.getSelectedToggle();
            if (radioButton != null){
                String selectedRadioBtnName = radioButton.getText();
                if (selectedRadioBtnName.equals("Employee")){
                    addEmployeePane.setVisible(true);
                    addEmployeePane.setManaged(true);
                    addCompanyPane.setVisible(false);
                    addCompanyPane.setManaged(false);
                }else if (selectedRadioBtnName.equals("Company")){
                    addCompanyPane.setVisible(true);
                    addCompanyPane.setManaged(true);
                    addEmployeePane.setVisible(false);
                    addEmployeePane.setManaged(false);
                }
            }
        });
        topVbox.getChildren().addAll(radioBtnHbox, addCompanyPane, addEmployeePane);
        return topVbox;
    }

    private Pane getAddEmployeePane(){
        HBox employeeHBox = new HBox();
        TextField firstName = new TextField();
        firstName.setPromptText("First name");
        styles.getTextFieldStyle(firstName);
        TextField lastName = new TextField();
        lastName.setPromptText("Last name");
        styles.getTextFieldStyle(lastName);
        TextField position = new TextField();
        position.setPromptText("Position");
        styles.getTextFieldStyle(position);
        TextField email = new TextField();
        email.setPromptText("Email");
        styles.getTextFieldStyle(email);

        Button addBtn = new Button("Add");
        styles.setAddBtnStyle(addBtn);
        addBtn.setOnAction(p->{
            EmployeeVO employeeVO = createNewEmployee(firstName, lastName, position, email);
            employeeBD.saveEmployee(employeeVO);
            clearTextField(firstName, lastName, position, email);
            refreshDataEmployeesTable();
        });
        employeeHBox.getChildren().addAll(firstName, lastName, position, email, addBtn);

        VBox employeeVBox = new VBox();
        styles.setVBoxStyle(employeeVBox);
        employeeVBox.getChildren().addAll(employeeHBox,addBtn);
        return employeeVBox;
    }

    private EmployeeVO createNewEmployee(TextField firstName, TextField lastName, TextField position, TextField email){
        EmployeeVO employeeVO = new EmployeeVO();
        employeeVO.setFirstName(firstName.getText());
        employeeVO.setLastName(lastName.getText());
        employeeVO.setPosition(position.getText());
        employeeVO.setEmail(email.getText());

        employeeVO.setCompany(getSelectedCompany().getId());
        return employeeVO;
    }

    private CompanyVO getSelectedCompany(){
        return companyTableView.getSelectionModel().getSelectedItem();
    }

    private void clearTextField(TextField ...textFields){
        for (TextField fields : textFields) {
            fields.clear();
        }
    }

    private void refreshDataEmployeesTable(){
        CompanyVO selectedCompany = getSelectedCompany();
        if (selectedCompany != null){
            ObservableList<EmployeeVO> employeeVOObservableList = employeeBD.getEmployeesByCompanyIdAndSearchValue(selectedCompany.getId(), searchValue);
            employeesTableView.setItems(employeeVOObservableList);
        }
    }

    private Pane getAddCompanyPane(){
        HBox companyHBox = new HBox();
        TextField name = new TextField();

        name.setPromptText("Name");
        styles.getTextFieldStyle(name);

        TextField address = new TextField();
        address.setPromptText("Address");
        styles.getTextFieldStyle(address);
        TextField nip = new TextField();
        nip.setPromptText("Nip");
        nip.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches("([1-9][0-9]*)?")) ? change : null));
        styles.getTextFieldStyle(nip);

        Button addBtn = new Button("Add");
        styles.setAddBtnStyle(addBtn);

        addBtn.setOnAction(p-> {
            CompanyVO companyVO = createNewCompany(name, address, nip);
            companyBD.saveCompany(companyVO);
            refreshDataCompanyTable();
            clearTextField(name, address, nip);

        });
        companyHBox.getChildren().addAll(name, address, nip);

        VBox companyVBox = new VBox();
        companyVBox.getChildren().addAll(companyHBox, addBtn);
        styles.setVBoxStyle(companyVBox);
        return companyVBox;
    }

    private CompanyVO createNewCompany(TextField name, TextField address, TextField nip){
        CompanyVO companyVO = new CompanyVO();
        companyVO.setName(name.getText());
        companyVO.setAddress(address.getText());
        companyVO.setNip(nip.getText());
        return companyVO;
    }

    private void refreshDataCompanyTable(){
        if (companyBD != null){
            ObservableList<CompanyVO> companiesBySearch = companyBD.getCompaniesBySearch(searchValue);
            companyTableView.setItems(companiesBySearch);
        }
    }

    private Pane addDeleteBtnPane() {
        HBox deleteBtnHbox = new HBox();
        deleteBtnHbox.setSpacing(50);
        Button deleteBtn = new Button("Delete");
        deleteBtn.setOnAction(p->removeAction());
        deleteBtnHbox.getChildren().add(deleteBtn);
        styles.setDeleteBtnStyle(deleteBtn);
        return deleteBtnHbox;
    }

    private void removeAction(){
        EmployeeVO selectedEmployee = getSelectedEmployee();
        if (selectedEmployee != null){
            employeeBD.remove(selectedEmployee);
            refreshDataEmployeesTable();
        }else {
            CompanyVO selectedCompany = getSelectedCompany();
            if (selectedCompany != null){
                companyBD.remove(selectedCompany);
                refreshDataEmployeesTable();
                refreshDataCompanyTable();

            }
        }
    }
    private EmployeeVO getSelectedEmployee(){
        return employeesTableView.getSelectionModel().getSelectedItem();
    }

    private TextField getSearchTextField(){
      TextField search = new TextField();
        search.setPromptText("Search");

        search.textProperty().addListener((observable,oldValue,newValue)->{
            searchValue = newValue;
            refreshDataEmployeesTable();
            refreshDataCompanyTable();
        });
        return search;
    }

    private TableView getCompanyTableView(){
        TableView companyTableView = new TableView();
        companyTableView.setEditable(true);

        TableColumn idTableColumn = new TableColumn();
        idTableColumn.setVisible(false);
        TableColumn nameTableColumn = new TableColumn("Name");
        TableColumn addressTableColumn = new TableColumn("Address");
        TableColumn nipTableColumn = new TableColumn("Nip");

        idTableColumn.setCellValueFactory(new PropertyValueFactory<CompanyVO, String>("id"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<CompanyVO, String>("name"));

        nameTableColumn.setEditable(true);
        nameTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameTableColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<CompanyVO, String>>) event -> {
            CompanyVO companyVO = event.getRowValue();
            companyVO.setName(event.getNewValue());
            companyBD.updateCompany(companyVO);
        });

        addressTableColumn.setCellValueFactory(new PropertyValueFactory<CompanyVO, String>("address"));
        addressTableColumn.setEditable(true);
        addressTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        addressTableColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<CompanyVO, String>>) event -> {
            CompanyVO companyVO = event.getRowValue();
            companyVO.setAddress(event.getNewValue());
            companyBD.updateCompany(companyVO);
        });

        nipTableColumn.setCellValueFactory(new PropertyValueFactory<CompanyVO, String>("nip"));
        nipTableColumn.setEditable(true);
        nipTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nipTableColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<CompanyVO, String>>) event -> {
            CompanyVO companyVO = event.getRowValue();
            companyVO.setNip(event.getNewValue());
            companyBD.updateCompany(companyVO);
        });

        companyTableView.getColumns().addAll(nameTableColumn, addressTableColumn, nipTableColumn);
        companyTableView.setOnMouseClicked(e->{
            CompanyVO selectionModel = getSelectedCompany();
            if (selectionModel != null){
                refreshDataEmployeesTable();
                employeesTableView.setVisible(true);
            }else {
                employeesTableView.setVisible(false);
            }
        });
        return companyTableView;
    }

    private TableView getEmployeesTableView(){
        TableView employeesTableView = new TableView();
        TableColumn idTableColumn = new TableColumn();
        idTableColumn.setVisible(false);
        TableColumn firstNameTableColumn = new TableColumn("First name");
        TableColumn lastNameTableColumn =new TableColumn("Last name");
        TableColumn positionTableColumn = new TableColumn("Position");
        TableColumn emailTableColumn = new TableColumn("Email");

        idTableColumn.setCellValueFactory(new PropertyValueFactory<EmployeeVO, String>("id"));
        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<EmployeeVO, String>("firstName"));
        lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<EmployeeVO, String>("lastName"));
        positionTableColumn.setCellValueFactory(new PropertyValueFactory<EmployeeVO, String>("position"));
        emailTableColumn.setCellValueFactory(new PropertyValueFactory<EmployeeVO, String>("email"));

        employeesTableView.getColumns().addAll(firstNameTableColumn, lastNameTableColumn, positionTableColumn, emailTableColumn);
        employeesTableView.setVisible(false);
        return employeesTableView;
    }
}
