package Moblima;

import java.io.FileNotFoundException;

/**
 * Controller of the admin module. Creates and delegates tasks to other classes.
 * @author Nghia Nguyen
 * @version 1.0
 */
public class Admin implements LoginObserver {
    private static Admin instance = null;
    private LoginPage loginUI;
    private AdminForm adminUI;
    private SettingsController settingsController;
    private BookMyShowInterface showManager;

    /**
     * Constructor for Admin.
     */
    private Admin() {
        adminUI = new AdminForm(this);
        settingsController = new SettingsController(new SettingsForm());
    }

    /**
     * Get the single instance of the Admin class.
     * @return the instance of the Admin class.
     */
    public static Admin getInstance() {
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }

    /**
     * Start the admin module.
     */
    public void start() {
        //adminUI.show();
        login();
    }

    /**
     * Launch the login function.
     * @return <code>true</code> if the login is successful, <code>false</code> otherwise.
     */
    private void login() {
        IDandPasswords idandPasswords = new IDandPasswords();
		loginUI = new LoginPage(idandPasswords.getLoginInfo());
        loginUI.addLoginObserver(this);
    }

    /**
     * Launch the admin UI when the login is successful.
     */
    public void loginSuccess() {
        adminUI.show();
    }

    /**
     * Exit the admin module.
     */
    public void exit() {
        instance = null;
        loginUI = null;
        adminUI = null;
        settingsController = null;
        BookMyShowApp.main(null);
    }

    /**
     * Inject a BookMyShow object into the admin module.
     * @param bookMyShow the <code>BookMyShow</code> object to be injected.
     */
    public void attachBookMyShow(BookMyShowInterface bookMyShow) {
        this.showManager = bookMyShow;
    }

    /**
     * Launch the settings function.
     */
    public void manageSettings() {
        settingsController.launch();
    }

    public void createMovie() {
        showManager.createMovie("MovieList.txt");
    }

    public void updateMovie() {
        showManager.updateMovie("MovieList.txt");
    }

    public void removeMovie() {
        showManager.removeMovie("MovieList.txt");
    }

    public void showAllMovies() {
        showManager.showAllMovies();
    }

    public void createShow() {
        showManager.createShow();
    }
}
