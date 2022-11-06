package Moblima;

/**
 * This interface is for the observers of the login status in the login UI. 
 * <p>All observers will be notified when there is a successful login.</p>
 * @author Nghia Nguyen
 * @version 1.0
 */
public interface LoginObserver {
    /**
     * Notify the observers that the login is successful.
     */
    public void loginSuccess();
}
