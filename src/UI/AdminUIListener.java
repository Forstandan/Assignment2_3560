package UI;

import Admin.Admin;
import UI.AdminUI;
import UI.AdminUIFields;
import UI.UserUI;
import User.User;
import User.UserGroup;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUIListener extends AdminUIFields {
    private void createPopup(String text, int width) {
        GridBagLayout gridBagLayout = new GridBagLayout();
        // create frame
        JFrame frame = new JFrame("popup");
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);

        // create label
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 8));
        label.setForeground(Color.white);
        GridBagConstraints constraints = new GridBagConstraints();

        // set constraints
        constraints.anchor = GridBagConstraints.CENTER;
        gridBagLayout.setConstraints(label, constraints);

        // add components
        frame.add(panel, BorderLayout.CENTER);
        panel.add(label);
        frame.setBounds(480, 230, width, 60);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        timer.start();
    }
    public final ActionListener actAddGroup = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
        }

        public void buttonActionPerformed(ActionEvent e) {
            UserGroup newGroup;
            DefaultMutableTreeNode parent;

            String groupname;

            if (AdminUI.getInstance().getGroupID().getText().equals("")) {
                groupname = "groupname";
            }
            else {
                groupname = AdminUI.getInstance().getGroupID().getText();
            }

            if (AdminUI.getCurrentComponent() == null) {
                newGroup = Admin.getInstance().createGroup(AdminUI.getInstance().getRoot(), groupname);
                parent = AdminUI.getInstance().getTreeRoot();
            }
            else if (AdminUI.getCurrentComponent() instanceof UserGroup) {
                newGroup = Admin.getInstance().createGroup(((UserGroup) AdminUI.getCurrentComponent()), groupname);
                parent = AdminUI.getCurrentNode();
            }
            else {
                newGroup = Admin.getInstance().createGroup(((User) AdminUI.getCurrentComponent()).getParentGroup(), groupname);
                parent = (DefaultMutableTreeNode) AdminUI.getCurrentNode().getParent();
            }

            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(groupname);
            getNodeMap().put(newNode, newGroup);
            AdminUI.getInstance().getTreeModel().insertNodeInto(newNode, parent, parent.getChildCount());
            AdminUI.getInstance().getTree().scrollPathToVisible(new TreePath(newNode.getPath()));

            AdminUI.getInstance().getGroupID().setText("");
        }
    };

    public final ActionListener actAddUser = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
        }

        public void buttonActionPerformed(ActionEvent e) {
            User newUser;
            DefaultMutableTreeNode parent;

            String username;

            if (AdminUI.getInstance().getUserID().getText().equals("")) {
                username = "username";
            }
            else {
                username = AdminUI.getInstance().getUserID().getText();
            }

            if (Admin.getInstance().nameIsTaken(username)) {
                return;
            }

            if (AdminUI.getCurrentComponent() == null) {
                newUser = Admin.getInstance().createUser(AdminUI.getInstance().getRoot(), username);
                parent = AdminUI.getInstance().getTreeRoot();
            }
            else if (AdminUI.getCurrentComponent() instanceof UserGroup) {
                newUser = Admin.getInstance().createUser((UserGroup) AdminUI.getCurrentComponent(), username);
                parent = AdminUI.getCurrentNode();
            }
            else {
                newUser = Admin.getInstance().createUser(((User) AdminUI.getCurrentComponent()).getParentGroup(), username);
                parent = (DefaultMutableTreeNode) AdminUI.getCurrentNode().getParent();
            }

            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(username);
            getNodeMap().put(newNode, newUser);
            AdminUI.getInstance().getTreeModel().insertNodeInto(newNode, parent, parent.getChildCount());
            AdminUI.getInstance().getTree().scrollPathToVisible(new TreePath(newNode.getPath()));

            AdminUI.getInstance().getUserID().setText("");
        }
    };

    public final ActionListener actShowUserTotal = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
        }

        public void buttonActionPerformed(ActionEvent e) {
            createPopup("User Total: " + Admin.getInstance().getNumOfUsers(), 100);
        }
    };

    public final ActionListener actShowGroupTotal = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
        }

        public void buttonActionPerformed(ActionEvent e) {
            createPopup("Group Total: " + Admin.getInstance().getNumOfGroups(), 100);
        }
    };

    public final ActionListener actShowTotalMessages = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
        }

        public void buttonActionPerformed(ActionEvent e) {
            createPopup("Message Total: " + Admin.getInstance().getTotalTweets(), 100);
        }
    };

    public final ActionListener actShowPositiveMessagePercentage = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
        }

        public void buttonActionPerformed(ActionEvent e) {
            Admin admin = Admin.getInstance();
            int totalMessages = admin.getTotalTweets();

            if (totalMessages != 0) {
                createPopup("Percentage of Positive Message: " +
                        ((double) admin.getPositiveTweetTotal()/(double) admin.getTotalTweets()) * 100 + "%", 200);
            }
            else {
                createPopup("Percentage of Positive Messages: 0%", 200);
            }
        }
    };

    public final ActionListener actOpenUserView = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
        }

        public void buttonActionPerformed(ActionEvent e) {
            if (AdminUI.getCurrentComponent() instanceof User) {
                UserUI instance = new UserUI();
                instance.createUserUI();
            }
        }
    };

    public final ActionListener actValidateNames = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPeformed(e);
        }

        public void buttonActionPeformed(ActionEvent e) {
            createPopup("User IDs valid: " + Admin.getInstance().checkValiditiy(), 100);
        }
    };

    public final ActionListener actCheckLatestUpdatedUser = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPeformed(e);
        }

        public void buttonActionPeformed(ActionEvent e) {
            createPopup("Latest updated user: " + Admin.getInstance().getLatestUpdatedUser(), 200);
        }
    };
}
