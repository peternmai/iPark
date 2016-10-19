package ucsd.cse110fa16.group14.ipark;

/**
 * Created by Abhigya Ghimire on 10/19/2016.
 */

public class Driver {
    private Name name;
    private String email;
    private String license;
    private String username;
    private String password;

    class Name {
        String firstName;
        String lastName;

        Name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return this.firstName;
        }

        public String getLastName() {
            return this.getLastName();
        }

    }

    public void setName(Name name) {
        this.name = name;
    }

    public Name getName() {
        return name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getLicense() {
        return this.license;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

}
