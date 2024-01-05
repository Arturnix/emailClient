package com.emailclientjavafx.emailclient.controller;

import com.emailclientjavafx.emailclient.EmailManager;
import com.emailclientjavafx.emailclient.view.ColorTheme;
import com.emailclientjavafx.emailclient.view.FontSize;
import com.emailclientjavafx.emailclient.view.ViewFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class OptionsWindowController extends BaseController implements Initializable {

    public OptionsWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    private Slider fontSizePicker;

    @FXML
    private ChoiceBox<ColorTheme> themePicker;

    @FXML
    void applyBtnAction() {

    }

    @FXML
    void cancelBtnAction() {

    }

    //this method is called right after object is created
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        setUpThemePicker();
        setUpSizePicker();

    }

    private void setUpSizePicker() {

        //convert enum into numbers on slider to pick the font size
        fontSizePicker.setMin(0);
        fontSizePicker.setMax(FontSize.values().length - 1);
        fontSizePicker.setValue(viewFactory.getFontSize().ordinal()); //ordinal() gets numerical value of the enum element
        fontSizePicker.setMajorTickUnit(1);
        fontSizePicker.setMinorTickCount(0);
        fontSizePicker.setBlockIncrement(1);
        fontSizePicker.setSnapToTicks(true);
        fontSizePicker.setShowTickMarks(true);
        fontSizePicker.setShowTickLabels(true);
        fontSizePicker.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {

                int i = object.intValue();
                return FontSize.values()[i].toString();
            }

            @Override
            public Double fromString(String string) {
                return null;
            }
        });
        fontSizePicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            fontSizePicker.setValue(newVal.intValue());
        });
    }

    private void setUpThemePicker() {
        themePicker.setItems(FXCollections.observableArrayList(ColorTheme.values())); //FXCollections make observable list
        themePicker.setValue(viewFactory.getColorTheme()); //set default value - take vale from viewFactory field
    }
}
