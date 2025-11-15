package geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a rectangle defined by an upper-left corner point, width, and height.
 * Provides methods to compute intersection points with lines, get rectangle edges,
 * check if a point is within the rectangle, and compare rectangles for equality.
 * Author: Afek Nuttman
 * Version: 14.7.2024
 */
public class Rectangle {
    private Point upperLeft; // The upper-left corner point of the rectangle
    private double width;    // The width of the rectangle
    private double height;   // The height of the rectangle

    /**
     * Constructs a geometry.Rectangle object with the specified upper-left corner point,
     * width, and height.
     *
     * @param upperLeft the upper-left corner point of the rectangle.
     * @param width     the width of the rectangle.
     * @param height    the height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Computes the intersection points of this rectangle with a given line.
     *
     * @param line the line to intersect with this rectangle.
     * @return a list of intersection points (may be empty if no intersections).
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> intersectionPoints = new ArrayList<>();
        Line[] edges = getEdges(); // Get the edges of the rectangle

        // Check intersection with each edge of the rectangle
        for (Line edge : edges) {
            Point intersection = edge.intersectionWith(line);
            if (intersection != null) {
                intersectionPoints.add(intersection);
            }
        }
        return intersectionPoints;
    }

    /**
     * Computes the edges of this rectangle.
     *
     * @return an array of geometry.Line objects representing the edges of the rectangle.
     */
    public Line[] getEdges() {
        // Calculate the points for the other corners of the rectangle
        Point upperRight = new Point(upperLeft.getX() + width, upperLeft.getY());
        Point lowerLeft = new Point(upperLeft.getX(), upperLeft.getY() + height);
        Point lowerRight = new Point(upperLeft.getX() + width, upperLeft.getY() + height);

        // Create and return the edges as geometry.Line objects
        return new Line[]{
                new Line(upperLeft, upperRight),
                new Line(upperRight, lowerRight),
                new Line(lowerRight, lowerLeft),
                new Line(lowerLeft, upperLeft)
        };
    }

    /**
     * Gets the width of this rectangle.
     *
     * @return the width of the rectangle.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets the height of this rectangle.
     *
     * @return the height of the rectangle.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Gets the upper-left corner point of this rectangle.
     *
     * @return the upper-left corner point.
     */
    public Point getUpperLeft() {
        return upperLeft;
    }

    /**
     * Checks if a given point is contained within this rectangle.
     *
     * @param point the point to check.
     * @return true if the point is within the rectangle, false otherwise.
     */
    public boolean contains(Point point) {
        // Check if the point's coordinates are within the bounds of the rectangle
        return point.getX() >= upperLeft.getX()
                && point.getX() <= upperLeft.getX() + width
                && point.getY() >= upperLeft.getY()
                && point.getY() <= upperLeft.getY() + height;
    }

    /**
     * Checks if this rectangle is equal to another rectangle.
     *
     * @param other the other rectangle to compare with.
     * @return true if the rectangles are equal (same upper-left corner, width, and height), false otherwise.
     */
    public boolean equals(Rectangle other) {
        // Check if both the upper-left corner points, width, and height are the same
        return this.upperLeft.equals(other.upperLeft)
                && this.width == other.width
                && this.height == other.height;
    }
}
