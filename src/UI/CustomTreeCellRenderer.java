package UI;

import User.*;
import sun.swing.DefaultLookup;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

import static UI.AdminUI.createImageIcon;

public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
    boolean isUser;

    CustomTreeCellRenderer(boolean isUser) {
        this.isUser = isUser;
    }

    public void setIsUser(boolean isUser) {
        this.isUser = isUser;
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel,
                                                  boolean expanded, boolean leaf, int row,
                                                  boolean hasFocus)
    {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        if (isUser) {
            setIcon(createImageIcon("Images/userIcon.png"));
        }
        else {
            setIcon(createImageIcon("Images/groupIcon.png"));
        }

        return this;
    }
}
