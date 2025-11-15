package geometry;

import java.util.List;

/**
 * Represents a line segment in a 2D space, defined by two points (start and end).
 * This class provides methods to compute properties of the line and check for intersections with other lines.
 * Author: Afek Nuttman
 * Version: 14.7.2024
 */
public class Line {
    private Point start; // The start point of the line
    private Point end; // The end point of the line
    private boolean findInfinite = false; // Flag to indicate if the line is infinite
    private static final double THRESHOLD = 1e-10; // A small threshold for comparing doubles

    /**
     * Constructs a geometry.Line with the specified start and end points.
     *
     * @param start the start point of the line.
     * @param end   the end point of the line.
     */
    public Line(Point start, Point end) {
        this.start = new Point(start.getX(), start.getY());
        this.end = new Point(end.getX(), end.getY());
    }

    /**
     * Constructs a geometry.Line with the specified coordinates.
     *
     * @param x1 the x coordinate of the start point.
     * @param y1 the y coordinate of the start point.
     * @param x2 the x coordinate of the end point.
     * @param y2 the y coordinate of the end point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Returns the length of the line.
     *
     * @return the length of the line.
     */
    public double length() {
        return this.end.distance(this.start);
    }

    /**
     * Returns the middle point of the line.
     *
     * @return the middle point of the line.
     */
    public Point middle() {
        double mx = (this.start.getX() + this.end.getX()) / 2.0;
        double my = (this.start.getY() + this.end.getY()) / 2.0;
        return new Point(mx, my);
    }

    /**
     * Checks if a given point lies on the line segment.
     *
     * @param point the point to check.
     * @return true if the point is on the line, false otherwise.
     */
    public boolean isOnLine(Point point) {
        double epsilon = 1e-10;
        double lineLength = this.start.distance(this.end);
        double startToPoint = this.start.distance(point);
        double endToPoint = this.end.distance(point);

        // Check if the point is on the line segment considering a small threshold
        return Math.abs(lineLength - (startToPoint + endToPoint)) < epsilon;
    }

    /**
     * Returns the start point of the line.
     *
     * @return the start point of the line.
     */
    public Point start() {
        return new Point(this.start.getX(), this.start.getY());
    }

    /**
     * Returns the end point of the line.
     *
     * @return the end point of the line.
     */
    public Point end() {
        return new Point(this.end.getX(), this.end.getY());
    }

    /**
     * Checks if this line intersects with another line.
     *
     * @param other the other line to check intersection with.
     * @return true if the lines intersect, false otherwise.
     */
    public boolean isIntersecting(Line other) {
        boolean isInter = this.intersectionWith(other) != null;
        if (findInfinite) {
            return true; // Infinite lines are considered intersecting
        }
        return isInter;
    }

    /**
     * Checks if this line intersects with both other lines.
     *
     * @param other1 the first line to check intersection with.
     * @param other2 the second line to check intersection with.
     * @return true if all three lines intersect, false otherwise.
     */
    public boolean isIntersecting(Line other1, Line other2) {
        boolean isInter = (this.intersectionWith(other1) != null && this.intersectionWith(other2) != null);
        if (findInfinite) {
            return true; // Infinite lines are considered intersecting
        }
        return isInter;
    }

    /**
     * Checks if a number is within a specified range, considering a small threshold.
     *
     * @param numIn the number to check.
     * @param num1  the lower bound of the range.
     * @param num2  the upper bound of the range.
     * @return true if numIn is within the range, false otherwise.
     */
    public boolean checkInLimit(double numIn, double num1, double num2) {
        return (almostEqual(numIn, num1) || almostEqual(numIn, num2)
                || (numIn < num2 && numIn > num1) || (numIn > num2 && numIn < num1));
    }

    /**
     * Checks if two numbers are approximately equal within a small threshold.
     *
     * @param num1 the first number.
     * @param num2 the second number.
     * @return true if the numbers are approximately equal, false otherwise.
     */
    public boolean almostEqual(double num1, double num2) {
        return Math.abs(num1 - num2) < THRESHOLD;
    }

    /**
     * Returns the intersection point if the lines intersect, and null otherwise.
     *
     * @param other the other line to check intersection with.
     * @return the intersection point if the lines intersect, and null otherwise.
     */
    public Point intersectionWith(Line other) {
        boolean found = false;
        boolean foundOtherX = false;
        boolean foundThisX = false;
        double xIntersect = Double.MAX_VALUE;
        double yIntersect = Double.MAX_VALUE;
        double inclineL1;
        double inclineL2;

        // Check for vertical lines
        if (almostEqual(other.start.getX(), other.end.getX())) {
            foundOtherX = true;
            found = true;
            xIntersect = other.start.getX();
        }
        if (almostEqual(this.start.getX(), this.end.getX())) {
            foundThisX = true;
            found = true;
            xIntersect = this.start.getX();
        }

        // Check for horizontal lines
        if (almostEqual(this.start.getY(), this.end.getY())) {
            yIntersect = this.start.getY();
        }
        if (almostEqual(other.start.getY(), other.end.getY())) {
            yIntersect = other.start.getY();
        }

        if (!found) {
            // Calculate slopes and y-intercepts for non-vertical lines
            inclineL1 = (this.start.getY() - this.end.getY()) / (this.start.getX() - this.end.getX());
            inclineL2 = (other.start.getY() - other.end.getY()) / (other.start.getX() - other.end.getX());

            double b1 = this.start.getY() - (inclineL1 * this.start.getX());
            double b2 = other.start.getY() - (inclineL2 * other.start.getX());

            // Check for parallel lines
            if (almostEqual(inclineL1, inclineL2)) {
                if (almostEqual(b1, b2)) {
                    this.findInfinite = true;
                }
                return null;
            }

            // Calculate intersection point
            xIntersect = (b2 - b1) / (inclineL1 - inclineL2);
            yIntersect = xIntersect * inclineL1 + b1;
        } else if (foundOtherX && foundThisX) {
            if (almostEqual(other.start.getX(), this.end.getX())) {
                // Check if the y-coordinate intersections are within bounds
                if (checkInLimit(other.start.getY(), this.start.getY(), this.end.getY())) {
                    this.findInfinite = true;
                    return null;
                }
                if (checkInLimit(other.end.getY(), this.start.getY(), this.end.getY())) {
                    this.findInfinite = true;
                    return null;
                }
            } else {
                return null;
            }
        } else if (foundOtherX) {
            // If only other line is vertical, calculate y-intersection
            inclineL1 = (this.start.getY() - this.end.getY()) / (this.start.getX() - this.end.getX());
            double b1 = this.start.getY() - (inclineL1 * this.start.getX());
            yIntersect = xIntersect * inclineL1 + b1;
        } else {
            // If only this line is vertical, calculate y-intersection
            inclineL2 = (other.start.getY() - other.end.getY()) / (other.start.getX() - other.end.getX());
            double b2 = other.start.getY() - (inclineL2 * other.start.getX());
            yIntersect = xIntersect * inclineL2 + b2;
        }

        // Check if the intersection point is within the bounds of both lines
        if ((checkInLimit(yIntersect, this.end.getY(), this.start.getY())
                && checkInLimit(yIntersect, other.end.getY(), other.start.getY()))) {
            if (checkInLimit(xIntersect, other.end.getX(), other.start.getX())
                    && checkInLimit(xIntersect, this.end.getX(), this.start.getX())) {
                return new Point(xIntersect, yIntersect);
            }
        }
        return null;
    }

    /**
     * Checks if the line is vertical (has the same x-coordinates for start and end).
     *
     * @return true if the line is vertical, false otherwise.
     */
    public boolean isVertical() {
        return this.start.getY() == this.end.getY();
    }

    /**
     * Returns the closest intersection point to the start of the line with a given rectangle.
     *
     * @param rect the rectangle to check for intersection.
     * @return the closest intersection point to the start of the line, or null if no intersection occurs.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> points = rect.intersectionPoints(this);
        if (points.isEmpty()) {
            return null;
        }
        Point closest = points.get(0);
        for (Point point : points) {
            if (this.start.distance(point) < this.start.distance(closest)) {
                closest = point;
            }
        }
        return closest;
    }

    /**
     * Checks if the line is horizontal (has the same y-coordinates for start and end).
     *
     * @return true if the line is horizontal, false otherwise.
     */
    public boolean isHorizontal() {
        return this.start.getX() == this.end.getX();
    }

    /**
     * Checks if this line is equal to another line.
     *
     * @param other the other line to compare with.
     * @return true if the lines are equal, false otherwise.
     */
    public boolean equals(Line other) {
        return ((almostEqual(other.start.getY(), this.start.getY())
                && almostEqual(other.start.getX(), this.start.getX()))
                && (almostEqual(this.end.getY(), other.end.getY())
                && almostEqual(this.end.getX(), other.end.getX())))
                || ((almostEqual(this.start.getY(), other.end.getY())
                && almostEqual(this.start.getX(), other.end.getX()))
                && (almostEqual(other.start.getY(), this.end.getY())
                && almostEqual(other.start.getX(), this.end.getX())));
    }
}
