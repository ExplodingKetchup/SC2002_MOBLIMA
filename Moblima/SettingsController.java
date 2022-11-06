package Moblima;

/**
 * Controller of the settings function.  
 * <p>Owns a <code>SettingsForm</code> object to interact with users. Modify settings in accordance with user input from the <code>SettingsForm</code> object.</p>
 * @author Nghia Nguyen
 * @version 1.0
 */
public class SettingsController {

    private SettingsForm settingsForm;
    private Settings settings;

    /**
     * Constructor for SettingsController.
     * @param settingsForm  a <code>SettingsForm</code> object to interact with users.
     */
    public SettingsController(SettingsForm settingsForm) {
        this.settingsForm = settingsForm;
        settingsForm.setController(this);
        settings = Settings.getInstance();
    }

    /**
     * Launch the manage settings function.
     */
    public void launch() {
        settingsForm.show();
    }

    /**
     * Modify settings in accordance with user input.
     * @param command   The user input.
     */
    public void manageSettings(String command) {
        String[] inputTokens = command.split(" ");
        if (inputTokens.length == 2) {
            /*if (inputTokens[1].equals("-")) {
                settings.remove(inputTokens[0]);
            } else {
                settings.setProperty(inputTokens[0], inputTokens[1]);
            }*/
            settings.setProperty(inputTokens[0], inputTokens[1]);
            settings.save();
        } else {
            System.out.println("Invalid input.");
        }
    }

    /**
     * Print the current settings.
     */
    public void print() {
        settings.print();
    }
}
