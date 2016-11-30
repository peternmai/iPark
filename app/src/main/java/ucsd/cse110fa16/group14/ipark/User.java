package ucsd.cse110fa16.group14.ipark;

import android.widget.EditText;

/**
 * User class holds information of the app users which consists of their name, email, license,
 * username, password, and a flag if they are owner or not.
 */
public class User {

    private Name name;
    private String email;
    private String license;
    private String username;
    private String password;
    private boolean owner;


    /**
     * Creates a non-owner user
     */
    public User() {
        this.owner = false;
    }

    /**
     * Creates a user depending on the input type
     * @param userType String that determines whether they are owner or not
     */
    public User(String userType) {
        userType = userType.toLowerCase();
        if (userType == "owner") {
            owner = true;
        } else {
            owner = false;
        }
    }

    /**
     * Create a user with the following details
     * @param firstName first name
     * @param lastName last name
     * @param email email
     * @param license license
     * @param username username
     * @param password password
     * @param owner ownership flag
     */
    public User(String firstName, String lastName, String email, String license, String username, String password, boolean owner) {
        Name name = new Name(firstName, lastName);
        this.name = name;
        this.email = email;
        this.license = license;
        this.username = username;
        this.password = password;
        this.owner = owner;
    }


    /**
     * Inner class to store name
     */
    protected class Name {
        String firstName;
        String lastName;

        /**
         * Name constructor
         * @param firstName first name
         * @param lastName last name
         */
        protected Name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        /**
         * Name created using first name and last name of edittext type
         * @param firstName first name
         * @param lastName last name
         */
        protected Name(EditText firstName, EditText lastName) {
            this.firstName = firstName.getText().toString();
            this.lastName = lastName.getText().toString();
        }

        /**
         * Gets first name
         * @return firstname
         */
        protected String getFirstName() {
            return this.firstName;
        }

        /**
         * Gets last name
         * @return last name
         */
        protected String getLastName() {
            return this.lastName;
        }

        /**
         * Gets full name
         * @return first name last name
         */
        protected String fullName() {
            return (this.firstName + " " + this.lastName);
        }
    }

    /**
     * Sets the name
     * @param firstName first name
     * @param lastName last name
     */
    protected void setStringName(String firstName, String lastName) {
        this.name = new Name(firstName, lastName);
    }

    /**
     * Sets the name
     * @param firstName first name
     * @param lastName last name
     */
    protected void setName(EditText firstName, EditText lastName) {
        this.name = new Name(firstName.getText().toString(), lastName.getText().toString());
    }

    /**
     * Gets name
     * @return full name
     */
    protected String getName() {
        return name.fullName();
    }

    /**
     * Gets the email
     * @return email
     */
    protected String getEmail() {
        return this.email;
    }

    /**
     * Sets emails
     * @param email email to be set
     */
    protected void setStringEmail(String email) {
        this.email = email;
    }

    /**
     * sets email
     * @param email email
     */
    protected void setEmail(EditText email) {
        this.email = email.getText().toString().toLowerCase();
    }

    /**
     * Gets the license
     * @return license
     */
    protected String getLicense() {
        return this.license;
    }

    /**
     * Sets license
     * @param license license
     */
    protected void setStringLicense(String license) {
        this.license = license;
    }

    /**
     * Sets license
     * @param license license
     */
    protected void setLicense(EditText license) {
        this.license = license.getText().toString();
    }

    /**
     * Gets username
     * @return username
     */
    protected String getUsername() {
        return this.username;
    }


    /**
     * Sets username
     * @param username     username
     */
    protected void setStringUsername(String username) {
        this.username = username;
    }

    /**
     * Sets username
     * @param username     username
     */
    protected void setUsername(EditText username) {
        String userNameString = username.getText().toString();
        this.username = userNameString.toLowerCase();
    }

    /**
     * Gets password
     * @return password
     */
    protected String getPassword() {
        return this.password;
    }

    /**
     * Sets password
     * @param password password
     */
    protected void setPassword(EditText password) {
        this.password = password.getText().toString();
    }

    /**
     * Checks if its an owner or not
     * @return boolean whether the user is an owner or not
     */
    protected boolean isOwner() {
        if (this.owner)
            return true;
        else
            return false;
    }
}
