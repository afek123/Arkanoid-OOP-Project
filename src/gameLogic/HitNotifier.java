package gameLogic;

/**
 * The gameLogic.HitNotifier interface defines methods for managing listeners that
 * respond to hit events. Classes implementing this interface can notify
 * registered listeners when a hit event occurs.
 */
public interface HitNotifier {

    /**
     * Adds a gameLogic.HitListener to the list of listeners that will be notified of hit events.
     *
     * @param hl The gameLogic.HitListener to be added. This listener will be notified whenever a hit event occurs.
     */
    void addHitListener(HitListener hl);

    /**
     * Removes a gameLogic.HitListener from the list of listeners.
     * The specified listener will no longer be notified of hit events.
     * @param hl The gameLogic.HitListener to be removed.
     * This listener will no longer receive notifications of hit events.
     */
    void removeHitListener(HitListener hl);
}
