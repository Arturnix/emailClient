package com.emailclientjavafx.emailclient.view;

import com.emailclientjavafx.emailclient.EmailManager;
import com.emailclientjavafx.emailclient.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ViewFactory {
    private EmailManager emailManager;
    private ArrayList<Stage> activeStages;
    private boolean mainViewInitialized = false; //to close extra pop up window when add new account in main view

    public ViewFactory(EmailManager emailManager) {
        this.emailManager = emailManager;
        activeStages = new ArrayList<Stage>();
    }

    public boolean isMainViewInitialized() {
        return mainViewInitialized;
    }

    //view options handling:
    private ColorTheme colorTheme = ColorTheme.DARK;
    private FontSize fontSize = FontSize.MEDIUM;

    public ColorTheme getColorTheme() {
        return colorTheme;
    }

    public FontSize getFontSize() {
        return fontSize;
    }

    public void setColorTheme(ColorTheme colorTheme) {
        this.colorTheme = colorTheme;
    }

    public void setFontSize(FontSize fontSize) {
        this.fontSize = fontSize;
    }

    public void showLoginWindow() {
        System.out.println("Show login window called");

        BaseController controller = new LoginWindowController(emailManager, this, "LoginWindow.fxml");
        initializeStage(controller);
    }

    public void showMainWindow() {
        System.out.println("Show main window called");

        BaseController controller = new MainWindowController(emailManager, this, "MainWindow.fxml");
        initializeStage(controller);

        mainViewInitialized = true;
    }

    public void showOptionsWindow() {
        System.out.println("Show options window called");

        BaseController controller = new OptionsWindowController(emailManager, this, "OptionsWindow.fxml");
        initializeStage(controller);
    }

    public void showComposeMessageWindow() { //when I add new window I must let know ViewFactory to show this new window
        System.out.println("Compose message window called");

        BaseController controller = new ComposeMessageController(emailManager, this, "ComposeMessageWindow.fxml");
        initializeStage(controller);
    }

    private void initializeStage(BaseController baseController) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(baseController.getFxmlName()));
        fxmlLoader.setController(baseController);
        Parent parent;

        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        // add new stage to the list of active stages when new stage is initialized
        activeStages.add(stage);
    }

    public void closeStage(Stage stageToClose) {

        stageToClose.close();
        //remove closed stage from the list of active stages
        activeStages.remove(stageToClose);
    }

    public void updateStyles() {
        // I need here list of opened scenes/stages because styles - css applies to scenes
        for(Stage stage : activeStages) {
            Scene scene = stage.getScene();
            //handle the css
            //before applying new css first I need to clear preset one
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource(ColorTheme.getCssPath(colorTheme)).toExternalForm());
            //from ViewFactory I have only font size and themeColor but I need to link it somehow with css files.
            //so in these enums I need to make connection with appropriete css files (using switch statemens corressponding with options selected in options window.
            //In enums create static method which return path to the css.
            scene.getStylesheets().add(getClass().getResource(FontSize.getCssPath(fontSize)).toExternalForm());
        }
    }
}
