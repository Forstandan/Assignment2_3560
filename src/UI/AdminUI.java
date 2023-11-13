package UI;

import Admin.Admin;
import UI.Listeners.AppTreeModelListener;
import User.User;
import User.UserComponent;
import User.UserGroup;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class AdminUI {
//    private CustomTreeCellRenderer customTreeCellRenderer;
    private static JTree tree;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode treeRoot;
    private UserGroup root;
    private static AdminUI instance;
    private static Map<DefaultMutableTreeNode, UserComponent> nodeMap = new HashMap<>();
    private UserComponent currentComponent;
    private DefaultMutableTreeNode currentNode;

    private ActionListener actAddGroup = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
        }

        private void buttonActionPerformed(ActionEvent e) {
            UserGroup newGroup;
            DefaultMutableTreeNode parent;

            if (currentComponent == null) {
                newGroup = Admin.getInstance().createGroup(root);
                parent = treeRoot;
            }
            else if (currentComponent instanceof UserGroup) {
                newGroup = Admin.getInstance().createGroup((UserGroup)currentComponent);
                parent = currentNode;
            }
            else {
                newGroup = Admin.getInstance().createGroup(((User)currentComponent).getParentGroup());
                parent = (DefaultMutableTreeNode)currentNode.getParent();
            }

//            customTreeCellRenderer.setIsUser(false);

//            Icon groupIcon = createImageIcon("Images/groupIcon.png");
//            if (groupIcon != null) {
//                TreePath path = new TreePath(currentNode.getPath());
//                DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
//                renderer.setIcon(groupIcon);
//                tree.repaint(tree.getPathBounds(path));
//            } else {
//                System.err.println("Leaf icon missing; using default.");
//            }

            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("groupName");
            nodeMap.put(newNode, newGroup);
            treeModel.insertNodeInto(newNode, parent, parent.getChildCount());
            tree.scrollPathToVisible(new TreePath(newNode.getPath()));
        }
    };

    private ActionListener actAddUser = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
        }

        private void buttonActionPerformed(ActionEvent e) {
            User newUser;
            DefaultMutableTreeNode parent;

            if (currentComponent == null) {
                newUser = Admin.getInstance().createUser(root);
                parent = treeRoot;
            }
            else if (currentComponent instanceof UserGroup) {
                newUser = Admin.getInstance().createUser((UserGroup)currentComponent);
                parent = currentNode;
            }
            else {
                newUser = Admin.getInstance().createUser(((User)currentComponent).getParentGroup());
                parent = (DefaultMutableTreeNode)currentNode.getParent();
            }

//            customTreeCellRenderer.setIsUser(true);

            Icon userIcon = createImageIcon("Images/userIcon.png");
            if (userIcon != null) {
                DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
                renderer.setIcon(userIcon);
            } else {
                System.err.println("Leaf icon missing; using default.");
            }

            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("username");
            nodeMap.put(newNode, newUser);
            treeModel.insertNodeInto(newNode, parent, parent.getChildCount());
            tree.scrollPathToVisible(new TreePath(newNode.getPath()));
        }
    };

    private AdminUI() {
//        customTreeCellRenderer = new CustomTreeCellRenderer(false);
    }

    public static AdminUI getInstance() {
        if (instance == null) {
            synchronized (AdminUI.class) {
                if (instance == null) {
                    instance = new AdminUI();
                }
            }
        }

        return instance;
    }

    static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = AdminUI.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public JTree getTree() {
        return tree;
    }

    public Map<DefaultMutableTreeNode, UserComponent> getNodeMap() {
        return nodeMap;
    }

    public void setRoot(UserGroup root) {
        this.root = root;
    }

    public UserComponent getCurrentComponent() {
        return currentComponent;
    }

    public void createAdminView() {
        /* creating panels */
        JPanel treePanel = new JPanel();
        treePanel.setBackground(Color.blue);
        treePanel.setBounds(0, 0, 400, 750);
        treePanel.setLayout(new BorderLayout());

        JPanel upperSubPanel = new JPanel();
        upperSubPanel.setBackground(Color.red);
        upperSubPanel.setBounds(400, 0, 900, 200);
        upperSubPanel.setLayout(new GridLayout());

        JPanel leftVerticalSubPanel = new JPanel();
        leftVerticalSubPanel.setBackground(Color.LIGHT_GRAY);
        GridBagLayout gridBagLayout = new GridBagLayout();
        leftVerticalSubPanel.setLayout(gridBagLayout);

        JPanel rightVerticalSubPanel = new JPanel();
        rightVerticalSubPanel.setBackground(Color.LIGHT_GRAY);
        rightVerticalSubPanel.setLayout(gridBagLayout);

        JPanel bottomVerticalSubPanel = new JPanel();
        bottomVerticalSubPanel.setBackground(Color.BLUE);
        bottomVerticalSubPanel.setLayout(gridBagLayout);

        JPanel userIDTextPanel = new JPanel();
        userIDTextPanel.setBackground(Color.GRAY);
        userIDTextPanel.setLayout(gridBagLayout);
        userIDTextPanel.setPreferredSize(new Dimension(400, 60));

        JPanel groupIDTextPanel = new JPanel();
        groupIDTextPanel.setBackground(Color.GRAY);
        groupIDTextPanel.setLayout(gridBagLayout);
        groupIDTextPanel.setPreferredSize(new Dimension(400, 60));

        // user button
        GridBagConstraints constraintsUserButton = new GridBagConstraints(100, 200, 300,
                400, 0.8, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE,
                new Insets(10, 0, 0, 20), 0, 0);
        // group button
        GridBagConstraints constraintsGroupButton = new GridBagConstraints(100, 200, 200,
                400, 1, 1, GridBagConstraints.PAGE_END, GridBagConstraints.NONE,
                new Insets(0, 0, 10, 20), 0, 0);

        // user ID
        GridBagConstraints constraintsUserID = new GridBagConstraints(100, 200, 300,
                400, 0.8, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE,
                new Insets(10, 0, 0, 20), 0, 0);
        // group ID
        GridBagConstraints constraintsGroupID = new GridBagConstraints(100, 200, 200,
                400, 1, 1, GridBagConstraints.PAGE_END, GridBagConstraints.NONE,
                new Insets(0, 0, 10, 20), 0, 0);


        JPanel lowerSubPanel = new JPanel();
        lowerSubPanel.setBackground(Color.green);
        lowerSubPanel.setBounds(400, 400, 900, 250);
//        lowerSubPanel.setLayout(new BorderLayout());

        /* add components to the panels */
        // upperPanel labels
        JTextArea userID = new JTextArea();
        userID.setBackground(Color.GRAY);
        userID.setForeground(Color.WHITE);
        userID.setFont(new Font("Arial", Font.PLAIN, 24));
        userID.setText("User ID: (Select a node in the tree)");

        JTextArea groupID = new JTextArea();
        groupID.setBackground(Color.GRAY);
        groupID.setForeground(Color.WHITE);
        groupID.setFont(new Font("Arial", Font.PLAIN, 24));
        groupID.setText("Group ID: (Select a node in the tree)");

        // upperPanel buttons
        JButton addUserButton = new JButton("Add User");
        addUserButton.addActionListener(actAddUser);
        addUserButton.setBorderPainted(false);
        addUserButton.setBackground(Color.gray);
        addUserButton.setFont(new Font("Arial", Font.PLAIN, 30));
        addUserButton.setForeground(Color.WHITE);
        addUserButton.setPreferredSize(new Dimension(400, 60));

        JButton addGroupButton = new JButton("Add Group");
        addGroupButton.addActionListener(actAddGroup);
        addGroupButton.setBorderPainted(false);
        addGroupButton.setBackground(Color.gray);
        addGroupButton.setFont(new Font("Arial", Font.PLAIN, 30));
        addGroupButton.setForeground(Color.WHITE);
        addGroupButton.setPreferredSize(new Dimension(400, 60));

        /* tree view */
        treeRoot = new DefaultMutableTreeNode("Root");
        currentNode = treeRoot;
        treeModel = new DefaultTreeModel(treeRoot);
        treeModel.addTreeModelListener(new AppTreeModelListener());
        tree = new JTree(treeModel);
        tree.setCellRenderer(new DefaultTreeCellRenderer());
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        nodeMap.put(treeRoot, this.root);
        tree.setEditable(true);
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            currentNode = node;

            if (node == null) {
                return;
            }
            currentComponent = AdminUI.nodeMap.get(node);

            if (currentComponent instanceof UserGroup) {
                userID.setText("User ID: N/A");
                groupID.setText("Group ID: " + currentComponent.getID().substring(0, 20));
            }
            else if (currentComponent instanceof User) {
                userID.setText("User ID: " + currentComponent.getID().substring(0, 20));
                groupID.setText("Group ID: N/A");
            }
        });

        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);

        /* adding everything together */
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(null);
        frame.setVisible(true);
        gridBagLayout.setConstraints(addUserButton, constraintsUserButton);
        gridBagLayout.setConstraints(addGroupButton, constraintsGroupButton);
        gridBagLayout.setConstraints(userIDTextPanel, constraintsUserID);
        gridBagLayout.setConstraints(groupIDTextPanel, constraintsGroupID);
        userIDTextPanel.add(userID);
        groupIDTextPanel.add(groupID);
        rightVerticalSubPanel.add(addUserButton);
        rightVerticalSubPanel.add(addGroupButton);
        leftVerticalSubPanel.add(userIDTextPanel);
        leftVerticalSubPanel.add(groupIDTextPanel);
        upperSubPanel.add(leftVerticalSubPanel);
        upperSubPanel.add(rightVerticalSubPanel);
        frame.add(upperSubPanel);
        frame.add(lowerSubPanel);
        frame.add(treePanel);
        treePanel.add(tree);
    }


}
