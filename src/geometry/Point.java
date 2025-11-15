package geometry;

/**
 * Represents a point in a 2-dimensional space with coordinates (x, y).
 * Provides methods to get the coordinates, calculate the distance to another point,
 * and check for approximate equality with another point.
 * Author: Afek Nuttman
 * Version: 14.7.2024
 */
public class Point {
    private double x; // The x-coordinate of the point
    private double y; // The y-coordinate of the point
    private static final double THRESHOLD = 1e-10; // A small threshold for comparing doubles

    /**
     * Constructs a geometry.Point object with the specified coordinates.
     *
     * @param x the x-coordinate of the point.
     * @param y the y-coordinate of the point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x-coordinate of this point.
     *
     * @return the x-coordinate of the point.
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of this point.
     *
     * @return the y-coordinate of the point.
     */
    public double getY() {
        return y;
    }

    /**
     * Calculates the Euclidean distance between this point and another point.
     *
     * @param other the other point to calculate the distance to.
     * @return the Euclidean distance between this point and the other point.
     */
    public double distance(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    /**
     * Checks if this point is approximately equal to another point within a small threshold.
     *
     * @param other the other point to compare.
     * @return true if the points are approximately equal, false otherwise.
     */
    public boolean equals(Point other) {
        // Check if both x and y coordinates are approximately equal
        return almostEqual(this.x, other.x) && almostEqual(this.y, other.y);
    }

    /**
     * Checks if two numbers are approximately equal within a small threshold.
     *
     * @param num1 the first number.
     * @param num2 the second number.
     * @return true if the numbers are approximately equal, false otherwise.
     */
    private boolean almostEqual(double num1, double num2) {
        // Compare the absolute difference with the threshold
        return Math.abs(num1 - num2) < THRESHOLD;
    }
}
