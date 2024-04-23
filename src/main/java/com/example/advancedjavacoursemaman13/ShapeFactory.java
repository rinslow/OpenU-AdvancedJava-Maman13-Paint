package com.example.advancedjavacoursemaman13;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class ShapeFactory {
    public static Shape createShape(HandMode type, Position start, Position end, String color, boolean fill) {
        Color strokeColor = Color.web(color);
        Color fillColor = fill ? strokeColor : Color.TRANSPARENT;

        Shape shape = getUnstyledShape(type, start, end);
        if (shape == null) {
            return null;
        }

        shape.setStroke(strokeColor);
        shape.setFill(fillColor);
        return shape;
    }

    private static Shape getUnstyledShape(HandMode type, Position start, Position end) {
        return switch (type) {
            case HandMode.LINE_MODE -> new Line(start.getX(), start.getY(), end.getX(), end.getY());
            case HandMode.RECTANGLE_MODE -> new Rectangle(start.getX(), start.getY(), end.getX() - start.getX(), end.getY() - start.getY());
            case HandMode.CIRCLE_MODE -> {
                double radius = Math.sqrt(Math.pow(end.getX() - start.getX(), 2) + Math.pow(end.getY() - start.getY(), 2));
                yield new Circle(start.getX(), start.getY(), radius);
            }
            default -> null;
        };
    }
}
