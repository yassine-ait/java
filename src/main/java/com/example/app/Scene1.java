package com.example.app;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

public class Scene1 {

    @FXML
    private void Login() throws IOException {
        HelloApplication.setRoot("Scene2");
    }
}
