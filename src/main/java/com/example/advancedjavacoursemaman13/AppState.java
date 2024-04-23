package com.example.advancedjavacoursemaman13;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

import java.util.Stack;

public class AppState {
    private static AppState instance;
    private HandMode handMode = HandMode.FREE_HAND_MODE;
    private FillMode fillMode = FillMode.OPAQUE;
    private Position lastDragPosition = null;
    private Position lastDropPosition = null;
    private String fgColor = "#000000";
    private Pane pane;

    private Stack<Shape> shapes = new Stack<>();

    private Shape virtualShape; // This shape shows the drawer what they are about to draw, hence the word virtual

    private AppState() {

    }

    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }

        return instance;
    }

    public HandMode getHandMode() {
        return handMode;
    }

    public void setHandMode(HandMode handMode) {
        Util.log("Hand mode set to: " + handMode.toString());
        this.handMode = handMode;
    }

    public Position getLastDragPosition() {
        return lastDragPosition;
    }

    public void setLastDragPosition(Position lastDragPosition) {
        this.lastDragPosition = lastDragPosition;
        this.lastDropPosition = null; // Reset the drop position
    }

    public Position getLastDropPosition() {
        return lastDropPosition;
    }

    public void onDrag(Position position) {
        assert lastDragPosition != null; // This couldn't be, if we reach here it's a bug

        if (virtualShape != null) {
            pane.getChildren().remove(virtualShape); // Remove the previous virtual shape
        }

        // Redraw the shape
        virtualShape = ShapeFactory.createShape(handMode, lastDragPosition, position, fgColor, fillMode == FillMode.OPAQUE);
        if (virtualShape != null) {
            pane.getChildren().add(virtualShape);
        }
    }
    public void setLastDropPosition(Position lastDropPosition) {
        this.lastDropPosition = lastDropPosition;

        // We remove the virtual shape
        if (virtualShape != null) {
            pane.getChildren().remove(virtualShape);
            virtualShape = null;
        }

        // we get the latest shape to draw
        Shape shape = ShapeFactory.createShape(handMode, lastDragPosition, lastDropPosition, fgColor, fillMode == FillMode.OPAQUE);

        if (shape == null) {
            return; // We might be in free-hand mode, no need to draw anything
        }

        // we draw it
        pane.getChildren().add(shape);

        // Then we create an undo event for it
        shapes.push(shape);

        // Then we clear the drag-and-drop positions
        this.lastDragPosition = null;
        this.lastDropPosition = null;
    }

    public void undoLastDraw() {
        if (!shapes.isEmpty()) {
            Shape shape = shapes.pop();
            pane.getChildren().remove(shape);
        }
    }

    public void clearAllShapes() {
        pane.getChildren().clear();
        shapes.clear();
    }

    public FillMode getFillMode() {
        return fillMode;
    }

    public void setFillMode(FillMode fillMode) {
        Util.log("Setting fill mode to: " + fillMode.toString());
        this.fillMode = fillMode;
    }

    public String getFgColor() {
        return fgColor;
    }

    public void setFgColor(String fgColor) {
        Util.log("Setting fg color to: " + fgColor);
        this.fgColor = fgColor;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }
}
