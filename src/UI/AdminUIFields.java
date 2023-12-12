package UI;

import User.UserComponent;
import User.UserGroup;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AdminUIFields {

    private static JTextField userID;
    private static JTextField groupID;
    private static JTree tree;
    private static DefaultTreeModel treeModel;
    private static DefaultMutableTreeNode treeRoot;
    private static UserGroup root;
    private static Map<DefaultMutableTreeNode, UserComponent> nodeMap = new HashMap<>();

    // group button
    public final GridBagConstraints constraintsGroupButton = new GridBagConstraints(100, 0, 200,
            0, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE,
            new Insets(60, 0, 0, 20), 0, 0);
    // user button
    public final GridBagConstraints constraintsUserButton = new GridBagConstraints(100, 200, 300,
            400, 0.8, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE,
            new Insets(20, 0, 0, 20), 0, 0);
    // user view button
    public final GridBagConstraints constraintsUserViewButton = new GridBagConstraints(100, 100, 200,
            0, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL,
            new Insets(10, 40, 10, 60), 0, 0);
    // total users button
    public final GridBagConstraints constraintsUserTotalButton = new GridBagConstraints(100, 200, 200,
            100, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL,
            new Insets(10, 10, 10, 10), 0, 0);
    // total messages button
    public final GridBagConstraints constraintsTotalMessagesButton = new GridBagConstraints(100, 200, 200,
            400, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
            new Insets(10, 10, 10, 10), 0, 0);
    // user view button
    public final GridBagConstraints constraintsGroupTotalButton = new GridBagConstraints(100, 200, 200,
            400, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL,
            new Insets(10, 10, 10, 13), 0, 0);
    // total users button
    public final GridBagConstraints constraintsPositiveMessagePercentButton = new GridBagConstraints(100, 200, 200,
            400, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
            new Insets(10, 10, 10, 13), 0, 0);
    // user ID
    public final GridBagConstraints constraintsUserID = new GridBagConstraints(100, 200, 300,
            400, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL,
            new Insets(10, 40, 45, 0), 0, 0);
    // group ID
    public final GridBagConstraints constraintsGroupID = new GridBagConstraints(100, 200, 200,
            400, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL,
            new Insets(45, 40, 0, 0), 0, 0);
    // tree view
    public final GridBagConstraints constraintsTreeView = new GridBagConstraints(100, 200, 200,
            400, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 0, 0), 0, 0);
    // validate button
    public final GridBagConstraints constraintsValidateButton = new GridBagConstraints(100, 200, 200,
            400, 1, 1, GridBagConstraints.PAGE_END, GridBagConstraints.HORIZONTAL,
            new Insets(10, 10, 10, 10), 0, 0);
    // latest updated user button
    public final GridBagConstraints constraintsLatestUpdated = new GridBagConstraints(100, 200, 200,
            400, 1, 1, GridBagConstraints.PAGE_END, GridBagConstraints.HORIZONTAL,
            new Insets(10, 10, 10, 13), 0, 0);

    public JTree getTree() {
        return tree;
    }

    public void setTree(JTree tree) { this.tree = tree; }

    public DefaultTreeModel getTreeModel() { return treeModel; }

    public void setTreeModel(DefaultTreeModel treeModel) { this.treeModel = treeModel; }

    public JTextField getUserID() { return userID; }

    public void setUserID(JTextField userID) { this.userID = userID; }

    public JTextField getGroupID() { return groupID; }

    public void setGroupID(JTextField groupID) { this.groupID = groupID; }

    public UserGroup getRoot() { return root; }

    public void setRoot(UserGroup root) { this.root = root; }

    public DefaultMutableTreeNode getTreeRoot() { return treeRoot; }

    public void setTreeRoot(DefaultMutableTreeNode treeRoot) { this.treeRoot = treeRoot; }

    public static Map<DefaultMutableTreeNode, UserComponent> getNodeMap() { return nodeMap; }

    public void setNodeMap(Map<DefaultMutableTreeNode, UserComponent> nodeMap) { AdminUIFields.nodeMap = nodeMap; }
}
