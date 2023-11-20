package UI;

import Admin.Admin;
import User.User;
import User.UserGroup;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUIListener extends AdminUIFields{
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

            if (AdminUI.currentComponent == null) {
                newGroup = Admin.getInstance().createGroup(AdminUI.getInstance().getRoot(), groupname);
                parent = AdminUI.getInstance().getTreeRoot();
            }
            else if (AdminUI.currentComponent instanceof UserGroup) {
                newGroup = Admin.getInstance().createGroup(((UserGroup)AdminUI.currentComponent), groupname);
                parent = AdminUI.currentNode;
            }
            else {
                newGroup = Admin.getInstance().createGroup(((User)AdminUI.currentComponent).getParentGroup(), groupname);
                parent = (DefaultMutableTreeNode)AdminUI.currentNode.getParent();
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

            if (AdminUI.currentComponent == null) {
                newUser = Admin.getInstance().createUser(AdminUI.getInstance().getRoot(), username);
                parent = AdminUI.getInstance().getTreeRoot();
            }
            else if (AdminUI.currentComponent instanceof UserGroup) {
                newUser = Admin.getInstance().createUser((UserGroup) AdminUI.currentComponent, username);
                parent = AdminUI.currentNode;
            }
            else {
                newUser = Admin.getInstance().createUser(((User) AdminUI.currentComponent).getParentGroup(), username);
                parent = (DefaultMutableTreeNode)AdminUI.currentNode.getParent();
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
            JFrame frame = new JFrame("popup");
            JLabel label = new JLabel("User Total: " + Admin.getInstance().getNumOfUsers());
            GridBagConstraints constraints = new GridBagConstraints();

            // set constraints
            constraints.anchor = GridBagConstraints.CENTER;
            GridBagLayout gridBagLayout = new GridBagLayout();
            gridBagLayout.setConstraints(label, constraints);
            frame.setLayout(gridBagLayout);

            // add components
            frame.add(label);
            frame.setBounds(230, 130, 100, 100);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }
    };

    public final ActionListener actShowGroupTotal = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
        }

        public void buttonActionPerformed(ActionEvent e) {
            JFrame frame = new JFrame("popup");
            JLabel label = new JLabel("Group Total: " + Admin.getInstance().getNumOfGroups());
            GridBagConstraints constraints = new GridBagConstraints();

            // set constraints
            constraints.anchor = GridBagConstraints.CENTER;
            GridBagLayout gridBagLayout = new GridBagLayout();
            gridBagLayout.setConstraints(label, constraints);
            frame.setLayout(gridBagLayout);

            // add components
            frame.add(label);
            frame.setBounds(430, 130, 100, 100);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }
    };

    public final ActionListener actShowTotalMessages = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
        }

        public void buttonActionPerformed(ActionEvent e) {
            JFrame frame = new JFrame("popup");
            JLabel label = new JLabel("Message Total: " + Admin.getInstance().getTotalTweets());
            GridBagConstraints constraints = new GridBagConstraints();

            // set constraints
            constraints.anchor = GridBagConstraints.CENTER;
            GridBagLayout gridBagLayout = new GridBagLayout();
            gridBagLayout.setConstraints(label, constraints);
            frame.setLayout(gridBagLayout);

            // add components
            frame.add(label);
            frame.setBounds(230, 160, 100, 100);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }
    };

    public final ActionListener actShowPositiveMessagePercentage = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
        }

        public void buttonActionPerformed(ActionEvent e) {
            JFrame frame = new JFrame("popup");
            Admin admin = Admin.getInstance();
            int totalMessages = admin.getTotalTweets();
            JLabel label;

            if (totalMessages != 0) {
                label = new JLabel("Percentage of Positive Messages: " +
                        ((double) admin.getPositiveTweetTotal()/(double) admin.getTotalTweets()) * 100 + "%");
            }
            else {
                label = new JLabel("Percentage of Positive Messages: 0%");
            }
            GridBagConstraints constraints = new GridBagConstraints();

            // set constraints
            constraints.anchor = GridBagConstraints.CENTER;
            GridBagLayout gridBagLayout = new GridBagLayout();
            gridBagLayout.setConstraints(label, constraints);
            frame.setLayout(gridBagLayout);

            // add components
            frame.add(label);
            frame.setBounds(430, 160, 400, 100);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }
    };

    public final ActionListener actOpenUserView = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
        }

        public void buttonActionPerformed(ActionEvent e) {
            if (AdminUI.currentComponent instanceof User) {
                UserUI instance = new UserUI();
                instance.createUserUI();
            }
        }
    };
}
