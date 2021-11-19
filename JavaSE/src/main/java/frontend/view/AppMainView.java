package frontend.view;

import frontend.business.CompanyBD;
import frontend.business.EmployeeBD;
import frontend.model.CompanyVO;
import frontend.model.EmployeeVO;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;


public class AppMainView extends Application {

    private TableView<CompanyVO> companyTableView;

    private TableView<EmployeeVO> employeesTableView;

    //
//
//    private Pane addEmployeePane;
//    private Pane addCompanyPane;
//    private ToggleGroup radioBtnToggleGroup;
    //



    @Autowired
    private CompanyBD companyBD;

    @Autowired
    private EmployeeBD employeeBD;

    private String searchValue;

    public AppMainView(){
        this.companyBD = new CompanyBD();
        this.employeeBD = new EmployeeBD();
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
        //configureBorder(stackPane);
        return new Scene(stackPane, 800,800);
    }

    private StackPane createStackPane() {
        StackPane stackPane = new StackPane();
        VBox topVbox = addTopVBox();
        Pane deleteBtnPane = addDeleteBtnPane();

        //TextField searchTextField = getSearchTextField();
        companyTableView = getCompanyTableView();

        employeesTableView = getEmployeesTableView();
        TextField search = new TextField();
        search.setPromptText("Wyszukaj");
        search.textProperty().addListener((observable,oldValue,newValue)->{
            searchValue = newValue;
            //refreshDataEmployersTable();
            refreshDataCompanyTable();
        });
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

        RadioButton employeeRadioBtn = new RadioButton("Employee");
        employeeRadioBtn.setToggleGroup(radioBtnToggleGroup);
        employeeRadioBtn.setSelected(true);

        RadioButton companyRadioBtn = new RadioButton("Company");
        companyRadioBtn.setToggleGroup(radioBtnToggleGroup);


        radioBtnHbox.getChildren().addAll(employeeRadioBtn, companyRadioBtn);
        Pane addEmployeePane = getAddEmployeePane();
        Pane addCompanyPane = getAddCompanyPane();
        addCompanyPane.setVisible(false);
        addCompanyPane.setManaged(false);
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



        topVbox.getChildren().addAll(radioBtnHbox, addEmployeePane, addCompanyPane);


        return topVbox;

    }





    private Pane getAddEmployeePane(){
        HBox employeeHBox = new HBox();
        TextField firstName = new TextField();
        firstName.setPromptText("First name");
        TextField lastName = new TextField();
        lastName.setPromptText("Last name");
        TextField position = new TextField();
        position.setPromptText("Position");
        TextField email = new TextField();
        email.setPromptText("Email");
        employeeHBox.getChildren().addAll(firstName, lastName, position, email);

        Button addBtn = new Button("Add");
        addBtn.setOnAction(p->{
            EmployeeVO employeeVO = createNewEmployee(firstName, lastName, position, email);
            employeeBD.saveEmployee(employeeVO);
            clearTextField(firstName, lastName, position, email);
            refreshDataEmployeesTable();
        });
        VBox employeeVBox = new VBox();
        employeeVBox.getChildren().addAll(employeeHBox,addBtn);
        return employeeVBox;
    }

    private EmployeeVO createNewEmployee(TextField firstName, TextField lastName, TextField position, TextField email){
        EmployeeVO employeeVO = new EmployeeVO();
        employeeVO.setFirstName(firstName.getText());
        employeeVO.setLastName(lastName.getText());
        employeeVO.setPosition(position.getText());
        employeeVO.setEmail(email.getText());
        CompanyVO companyVO = getSelectedCompany();
        if(companyVO==null){
            throw new RuntimeException("No company selected");
        }
        employeeVO.setCompany(companyVO.getId());
        return employeeVO;
    }

    private CompanyVO getSelectedCompany(){
        return companyTableView.getSelectionModel().getSelectedItem();


    }

    private void clearTextField(TextField ...textFields){
        for (TextField textField: textFields) {
            textField.clear();
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
        TextField address = new TextField();
        address.setPromptText("Address");
        TextField nip = new TextField();
        nip.setPromptText("Nip");
        companyHBox.getChildren().addAll(name, address, nip);

        Button addBtn = new Button("Add");
        addBtn.setOnAction(p-> {
            CompanyVO companyVO = createNewCompany(name, address, nip);
            refreshDataCompanyTable();
            companyBD.saveCompany(companyVO);


            clearTextField(name, address, nip);
        });
        VBox companyVBox = new VBox();
        companyVBox.getChildren().addAll(companyHBox, addBtn);
        //companyVBox.setVisible(true);
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
        Button deleteBtn = new Button("Delete");
        deleteBtn.setOnAction(p->removeAction());
        deleteBtnHbox.getChildren().add(deleteBtn);
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
                refreshDataCompanyTable();
            }
        }
    }
    private EmployeeVO getSelectedEmployee(){
        return employeesTableView.getSelectionModel().getSelectedItem();



    }


//    private TextField getSearchTextField(){ // do poprawki
//        TextField searchTextField = new TextField();
//        searchTextField.setPromptText("search");
//        searchTextField.textProperty().addListener((observable, oldValue, newValue)->{
//            searchValue = newValue;
//            refreshDataEmployeesTable();
//            refreshDataCompanyTable();
//        });
//        return searchTextField;
//    }

    private TableView getCompanyTableView(){
        TableView companyTableView = new TableView();

        TableColumn idTableColumn = new TableColumn();
        idTableColumn.setVisible(false);
        TableColumn nameTableColumn = new TableColumn("Name");
        TableColumn addressTableColumn = new TableColumn("Address");
        TableColumn nipTableColumn = new TableColumn("Nip");

        idTableColumn.setCellValueFactory(new PropertyValueFactory<CompanyVO, String>("id"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<CompanyVO, String>("name"));
        addressTableColumn.setCellValueFactory(new PropertyValueFactory<CompanyVO, String>("address"));
        nipTableColumn.setCellValueFactory(new PropertyValueFactory<CompanyVO, String>("nip"));

        companyTableView.getColumns().addAll(nameTableColumn, addressTableColumn, nipTableColumn);
        companyTableView.setOnMouseClicked(e->{
            CompanyVO selectionModel = getSelectedCompany();
            if (selectionModel != null){
                refreshDataCompanyTable();
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
        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<EmployeeVO, String>("lastName"));
        lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<EmployeeVO, String>("position"));
        emailTableColumn.setCellValueFactory(new PropertyValueFactory<EmployeeVO, String>("email"));

        employeesTableView.getColumns().addAll(firstNameTableColumn, lastNameTableColumn, positionTableColumn, emailTableColumn);
        employeesTableView.setVisible(false);
        return employeesTableView;
    }


    private static void configureBorder(final Region region){
        region.setStyle("-fx-background-color: white;"
                + "-fx-border-color: black;"
                + "-fx-border-width: 1;"
                + "-fx-padding: 6;");
    }
}
