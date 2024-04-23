package com.example.advancedjavacoursemaman13;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class AppController {
    @FXML
    private Pane pane; // Our canvas

    @FXML
    private ScrollPane leftPane; // Our left pane

    // region Work Mode Buttons

    @FXML
    private Button freeHandBtn;

    @FXML
    private Button rectBtn;

    @FXML
    private Button circleBtn;

    @FXML
    private Button lineBtn;

    // endregion Work Mode Buttons

    @FXML
    private Button clearAllBtn;

    @FXML
    private Button undoBtn;

    @FXML
    private CheckBox fillBtn;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    protected void initialize() {
        // Set the default mode to free hand
        AppState state = AppState.getInstance();
        state.setHandMode(HandMode.FREE_HAND_MODE);
        state.setFillMode(fillBtn.isSelected() ? FillMode.OPAQUE : FillMode.TRANSPARENT);
        state.setFgColor(colorPicker.getValue().toString());
        state.setPane(pane);
        pane.toBack();

        this.clearAllModeButtons();
        this.highlightButton(freeHandBtn);

    }

    @FXML
    protected void onFreeHandBtnClicked() {
        AppState.getInstance().setHandMode(HandMode.FREE_HAND_MODE);
        this.clearAllModeButtons();
        this.highlightButton(freeHandBtn);
    }

    @FXML
    protected void onRectBtnClicked() {
        AppState.getInstance().setHandMode(HandMode.RECTANGLE_MODE);
        this.clearAllModeButtons();
        this.highlightButton(rectBtn);
    }

    @FXML
    protected void onCircleBtnClicked() {
        AppState.getInstance().setHandMode(HandMode.CIRCLE_MODE);
        this.clearAllModeButtons();
        this.highlightButton(circleBtn);
    }

    @FXML
    protected void onLineBtnClicked() {
        AppState.getInstance().setHandMode(HandMode.LINE_MODE);
        this.clearAllModeButtons();
        this.highlightButton(lineBtn);
    }

    @FXML
    protected void onPaneDragStart(MouseEvent event) {
        Util.log("started drag:" + event.getX() + "," + event.getY());
        Position position = new Position(event.getX(), event.getY());
        AppState.getInstance().setLastDragPosition(position);
    }

    @FXML
    protected void onPaneDragMove(MouseEvent event) {
        Util.log("dragging:" + event.getX() + "," + event.getY());
        Position position = new Position(event.getX(), event.getY());
        AppState.getInstance().onDrag(position);
    }

    @FXML
    protected void onPaneDragEnd(MouseEvent event) {
        // First, we log a drop
        Util.log("ended drag:" + event.getX() + "," + event.getY());
        Position position = new Position(event.getX(), event.getY());
        AppState.getInstance().setLastDropPosition(position);
    }

    @FXML
    protected void onClearAllBtnClicked() {
        AppState state = AppState.getInstance();
        state.clearAllShapes();
    }

    @FXML
    protected void onUndoBtnClicked() {
        AppState state = AppState.getInstance();
        state.undoLastDraw();
    }

    @FXML
    protected void onFillBtnClicked() {
        AppState state = AppState.getInstance();
        state.setFillMode(fillBtn.isSelected() ? FillMode.OPAQUE : FillMode.TRANSPARENT);
    }

    @FXML
    protected void onColorPickerChanged() {
        AppState state = AppState.getInstance();
        state.setFgColor(colorPicker.getValue().toString());
    }


    protected void clearAllModeButtons() {
        freeHandBtn.setStyle("-fx-background-color: #lightgray");
        rectBtn.setStyle("-fx-background-color: #lightgray");
        circleBtn.setStyle("-fx-background-color: #lightgray");
        lineBtn.setStyle("-fx-background-color: #lightgray");
    }

    protected void highlightButton(Button button) {
        button.setStyle("-fx-background-color: #a1cad6");
    }
}