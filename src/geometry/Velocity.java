package geometry;

/**
 * The geometry.Velocity class represents a change in position on the x and y axes.
 * It provides methods to create a velocity from an angle and speed, calculate the speed,
 * apply the velocity to a point, and get or set the changes in position on each axis.
 * Author: Afek Nuttman
 * Version: 14.7.2024
 */
public class Velocity {
    private double dx; // Change in position on the x-axis
    private double dy; // Change in position on the y-axis

    /**
     * Constructs a geometry.Velocity object with the specified changes in position.
     *
     * @param dx The change in position on the x-axis.
     * @param dy The change in position on the y-axis.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Creates a geometry.Velocity object from an angle and speed.
     * The angle is assumed to be in degrees and is converted to radians
     * for trigonometric calculations. The speed determines the magnitude
     * of the velocity vector.
     *
     * @param angle The angle in degrees.
     * @param speed The speed.
     * @return A geometry.Velocity object corresponding to the angle and speed.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double radians = Math.toRadians(angle); // Convert angle to radians
        double dx = speed * Math.cos(radians); // Calculate change in x based on speed and angle
        double dy = speed * Math.sin(radians); // Calculate change in y based on speed and angle
        return new Velocity(dx, dy);
    }

    /**
     * Gets the speed of the velocity vector.
     * The speed is the magnitude of the velocity vector, calculated using
     * the Pythagorean theorem.
     *
     * @return The speed (magnitude of the velocity vector).
     */
    public double getSpeed() {
        // Calculate the magnitude of the velocity vector using the Pythagorean theorem
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Applies the velocity to a given point.
     * This method calculates the new position of a point after applying the velocity.
     *
     * @param p The point to which the velocity is applied.
     * @return A new geometry.Point with the updated position after applying the velocity.
     */
    public Point applyToPoint(Point p) {
        double newX = p.getX() + this.dx; // Calculate new x-coordinate
        double newY = p.getY() + this.dy; // Calculate new y-coordinate
        return new Point(newX, newY);
    }

    /**
     * Gets the change in position on the x-axis.
     *
     * @return The change in position on the x-axis (dx).
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Gets the change in position on the y-axis.
     *
     * @return The change in position on the y-axis (dy).
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Sets the change in position on the x-axis.
     *
     * @param dx The new change in position on the x-axis.
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * Sets the change in position on the y-axis.
     *
     * @param dy The new change in position on the y-axis.
     */
    public void setDy(double dy) {
        this.dy = dy;
    }
}
