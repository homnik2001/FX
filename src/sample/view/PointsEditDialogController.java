package sample.view;

import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Main;
import sample.functions.ArrayTabulatedFunction;
import sample.util.DocUtil;

public class PointsEditDialogController {
    @FXML
    private Spinner spinner;
    @FXML
    private TextField leftdomainb;
    @FXML
    private TextField rightdomainb;

    private Main mainApp;
    private Stage dialogStage;
    private boolean okClicked = false;
    private boolean cancelClicked = false;
    private DocUtil doc;


    public void setMainApp (Main mainApp){
        this.mainApp = mainApp;
    }
    public void setDoc (DocUtil doc){this.doc = doc;}
    @FXML
    private void initialize() {
        final int initialValue  = 11;
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000000, initialValue);
        spinner.setValueFactory(valueFactory);
        Integer.parseInt(spinner.getValue().toString());

    }

    public void setDialogStage (Stage dialogStage){
        this.dialogStage = dialogStage;

    }

    @FXML
    private void handleOk (){

        if(isInputValid()){
            // работа с основным окном
            okClicked = true;
            dialogStage.close();
            mainApp.clearPointData();
           // mainApp.setPointData(DocUtil.newFunction (getLeftDomainBorder(),getRightDomainBorder(),getPointsCount()));
            doc.newFunction (getLeftDomainBorder(),getRightDomainBorder(),getPointsCount());
            mainApp.setPointData(doc);
        }
    }

    @FXML
    private void handleCancel(){
        dialogStage.hide();
    }
    public boolean isOkClicked(){
        return okClicked;
    }

    private boolean isInputValid (){
        String errMessage ="";
        if (leftdomainb.getText().length() == 0 || leftdomainb == null){
            errMessage += "No valid left domain border!\n";
        }
        else
        {
            try{
                Double.parseDouble(leftdomainb.getText());
            }
            catch (NumberFormatException e){
                errMessage += "No valid left domain border (must be double)!\n";
            }
        }
        if (rightdomainb.getText().length() == 0 || rightdomainb == null){
            errMessage += "No valid right domain border!\n";
        }
        else {
            try{
                Double.parseDouble(rightdomainb.getText());
            }
            catch (NumberFormatException e){

                errMessage += "No valid right domain border (must be double)!\n";
            }
        }
        if ( spinner.getValue() == null){
            errMessage += "Invalid left domain border!\n";
        }
        else {
            try {
                Integer.parseInt(spinner.getValue().toString());
                System.out.println(Integer.parseInt(spinner.getValue().toString()));

            }
            catch (NumberFormatException e){
                System.out.println("wtf");
                errMessage += "Invalid points count (must be integer)\n";
            }
        }

        if( errMessage.length() == 0){
            return true;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errMessage);

            alert.showAndWait();

            return false;
        }
    }

    public double getLeftDomainBorder (){
        return Double.parseDouble(leftdomainb.getText());
    }
    public double getRightDomainBorder(){
        return Double.parseDouble(rightdomainb.getText());
    }
    public int getPointsCount() {
        return Integer.parseInt(spinner.getValue().toString());
    }
}
