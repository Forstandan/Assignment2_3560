package UI;

import javax.swing.*;

public class UserObjectWithIcon {
    private String username;
    private Icon icon;

    public UserObjectWithIcon(String username, Icon icon) {
        this.username = username;
        this.icon = icon;
    }

    public String getUsername() {
        return username;
    }

    public Icon getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return username;
    }
}

