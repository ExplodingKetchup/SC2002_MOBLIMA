package Moblima;

import java.util.Scanner;

/**
 * The <code>SettingsForm</code> class is the UI for managing settings of the admin module.
 * @author Nghia Nguyen
 * @version 1.0
 */
public final class SettingsForm extends Form {

    private SettingsController controller;
    
    /**
     * Constructor for SettingsForm.
     */
    public SettingsForm() {
        super();
    }

    /**
     * The controller is the only object that can change the settings. The controller delegates this form to collect user inputs.
     * @param controller    a <code>SettingsController</code> object to manage the settings based on inputs provided from this class.
     */
    public void setController(SettingsController controller) {
        this.controller = controller;
    }
    
    /**
     * Show the form.
     * @return <code>null</code>.
     */
    public Object show() {
        exitFlag = false;
        while (!exitFlag) {
            System.out.println("Settings");
            System.out.println("========");
            controller.print();
            System.out.println("To edit a setting or add new setting, enter the name of the setting, follow by its value");
            System.out.println("E.g. property_name property_value");
            //System.out.println("To delete an entry, enter the name of the setting, followed by a dash (-)");
            //System.out.println("E.g. property_name -");
            System.out.println("To exit, enter exit");
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            if (input.equals("exit")) {
                exit();
            } else {
                controller.manageSettings(input);
            }
        }
        return null;
    }
}
