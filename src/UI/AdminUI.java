package UI;

import Admin.Admin;
import UI.Listeners.AppTreeModelListener;
import User.*;
import User.UserGroup;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class AdminUI {
    private JTextField userID;
    private JTextField groupID;
    private JTree tree;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode treeRoot;
    private UserGroup root;
    private static final Map<DefaultMutableTreeNode, UserComponent> nodeMap = new HashMap<>();
    private UserComponent currentComponent;
    private DefaultMutableTreeNode currentNode;
    private static AdminUI instance;
    // group button
    private final GridBagConstraints constraintsGroupButton = new GridBagConstraints(100, 0, 200,
            0, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE,
            new Insets(60, 0, 0, 20), 0, 0);
    // user button
    private final GridBagConstraints constraintsUserButton = new GridBagConstraints(100, 200, 300,
            400, 0.8, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE,
            new Insets(20, 0, 0, 20), 0, 0);
    // user view button
    private final GridBagConstraints constraintsUserViewButton = new GridBagConstraints(100, 100, 200,
            0, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL,
            new Insets(10, 40, 10, 60), 0, 0);
    // total users button
    private final GridBagConstraints constraintsUserTotalButton = new GridBagConstraints(100, 200, 200,
            100, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE,
            new Insets(10, 0, 10, 10), 0, 0);
    // total messages button
    private final GridBagConstraints constraintsTotalMessagesButton = new GridBagConstraints(100, 200, 200,
            400, 1, 1, GridBagConstraints.PAGE_END, GridBagConstraints.NONE,
            new Insets(10, 0, 10, 10), 0, 0);
    // user view button
    private final GridBagConstraints constraintsGroupTotalButton = new GridBagConstraints(100, 200, 200,
            400, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE,
            new Insets(10, 0, 10, 20), 0, 0);
    // total users button
    private final GridBagConstraints constraintsPositiveMessagePercentButton = new GridBagConstraints(100, 200, 200,
            400, 1, 1, GridBagConstraints.PAGE_END, GridBagConstraints.NONE,
            new Insets(10, 0, 10, 20), 0, 0);
    // user ID
    private final GridBagConstraints constraintsUserID = new GridBagConstraints(100, 200, 300,
            400, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL,
            new Insets(10, 40, 45, 0), 0, 0);
    // group ID
    private final GridBagConstraints constraintsGroupID = new GridBagConstraints(100, 200, 200,
            400, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL,
            new Insets(45, 40, 0, 0), 0, 0);

    private final ActionListener actAddGroup = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
        }

        private void buttonActionPerformed(ActionEvent e) {
            UserGroup newGroup;
            DefaultMutableTreeNode parent;

            String groupname = "";

            if (groupID.getText().equals("")) {
                groupname = "groupname";
            }
            else {
                groupname = groupID.getText();
            }

            if (currentComponent == null) {
                newGroup = Admin.getInstance().createGroup(root, groupname);
                parent = treeRoot;
            }
            else if (currentComponent instanceof UserGroup) {
                newGroup = Admin.getInstance().createGroup(((UserGroup)currentComponent), groupname);
                parent = currentNode;
            }
            else {
                newGroup = Admin.getInstance().createGroup(((User)currentComponent).getParentGroup(), groupname);
                parent = (DefaultMutableTreeNode)currentNode.getParent();
            }

            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(groupname);
            nodeMap.put(newNode, newGroup);
            treeModel.insertNodeInto(newNode, parent, parent.getChildCount());
            tree.scrollPathToVisible(new TreePath(newNode.getPath()));

            groupID.setText("");
        }
    };

    private final ActionListener actAddUser = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
        }

        private void buttonActionPerformed(ActionEvent e) {
            User newUser;
            DefaultMutableTreeNode parent;

            String username;

            if (userID.getText().equals("")) {
                username = "username";
            }
            else {
                username = userID.getText();
            }

            if (currentComponent == null) {
                newUser = Admin.getInstance().createUser(root, username);
                parent = treeRoot;
            }
            else if (currentComponent instanceof UserGroup) {
                newUser = Admin.getInstance().createUser((UserGroup) currentComponent, username);
                parent = currentNode;
            }
            else {
                newUser = Admin.getInstance().createUser(((User) currentComponent).getParentGroup(), username);
                parent = (DefaultMutableTreeNode)currentNode.getParent();
            }

            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(username);
            nodeMap.put(newNode, newUser);
            treeModel.insertNodeInto(newNode, parent, parent.getChildCount());
            tree.scrollPathToVisible(new TreePath(newNode.getPath()));

            userID.setText("");
        }
    };

    private final ActionListener actShowUserTotal = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
        }

        private void buttonActionPerformed(ActionEvent e) {
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

    private final ActionListener actShowGroupTotal = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
        }

        private void buttonActionPerformed(ActionEvent e) {
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

    private final ActionListener actShowTotalMessages = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
        }

        private void buttonActionPerformed(ActionEvent e) {
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

    private final ActionListener actShowPositiveMessagePercentage = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
        }

        private void buttonActionPerformed(ActionEvent e) {
            JFrame frame = new JFrame("popup");
            Admin admin = Admin.getInstance();
            int totalMessages = admin.getTotalTweets();
            JLabel label;

            if (totalMessages != 0) {
                for (User user : admin.getUserSet()) {
                    user.accept(admin);
                }

                label = new JLabel("Percentage of Positive Messages: " +
                        admin.getPositiveTweetTotal()/admin.getTotalTweets() * 100 + "%");
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

    private final ActionListener actOpenUserView = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
        }

        private void buttonActionPerformed(ActionEvent e) {
            if (currentComponent instanceof User) {
                UserUI instance = new UserUI();
                instance.createUserUI();
            }
        }
    };

    private AdminUI() {

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
        treePanel.setBounds(0, 0, 200, 230);
        treePanel.setLayout(new BorderLayout());

        JPanel upperSubPanel = new JPanel();
        upperSubPanel.setBackground(Color.red);
        upperSubPanel.setBounds(200, 0, 400, 100);
        upperSubPanel.setLayout(new GridLayout());

        JPanel leftVerticalSubPanel = new JPanel();
        leftVerticalSubPanel.setBackground(Color.DARK_GRAY);
        GridBagLayout gridBagLayout = new GridBagLayout();
        leftVerticalSubPanel.setLayout(gridBagLayout);

        JPanel rightVerticalSubPanel = new JPanel();
        rightVerticalSubPanel.setBackground(Color.DARK_GRAY);
        rightVerticalSubPanel.setLayout(gridBagLayout);

        JPanel bottomLeftVerticalSubPanel = new JPanel();
        bottomLeftVerticalSubPanel.setBackground(Color.LIGHT_GRAY);
        bottomLeftVerticalSubPanel.setLayout(gridBagLayout);

        JPanel bottomRightVerticalSubPanel = new JPanel();
        bottomRightVerticalSubPanel.setBackground(Color.LIGHT_GRAY);
        bottomRightVerticalSubPanel.setLayout(gridBagLayout);

        JPanel lowerSubPanel = new JPanel();
        lowerSubPanel.setBackground(Color.green);
        lowerSubPanel.setBounds(200, 150, 400, 80);
        lowerSubPanel.setLayout(new GridLayout());

        JPanel midSubPanel = new JPanel();
        midSubPanel.setBackground(Color.LIGHT_GRAY);
        midSubPanel.setBounds(200, 100, 400, 50);
        midSubPanel.setLayout(gridBagLayout);

        JPanel userIDTextPanel = new JPanel();
        userIDTextPanel.setBackground(Color.lightGray);
        userIDTextPanel.setLayout(gridBagLayout);
//        userIDTextPanel.setPreferredSize(new Dimension(300, 30));

        JPanel groupIDTextPanel = new JPanel();
        groupIDTextPanel.setBackground(Color.lightGray);
        groupIDTextPanel.setLayout(gridBagLayout);
//        groupIDTextPanel.setPreferredSize(new Dimension(300, 30));

        /* add components to the panels */
        // upperPanel labels
        userID = new JTextField();
        userID.setBackground(Color.WHITE);
        userID.setForeground(Color.BLACK);
//        userID.setSize(new Dimension(300, 30));
        userID.setEditable(true);

        groupID = new JTextField();
        groupID.setBackground(Color.WHITE);
        groupID.setForeground(Color.BLACK);
//        groupID.setSize(new Dimension(300, 30));
        groupID.setEditable(true);

        // upperPanel buttons
        JButton addUserButton = new JButton("Add User");
        addUserButton.addActionListener(actAddUser);
        addUserButton.setBorderPainted(false);
        addUserButton.setBackground(Color.gray);
        addUserButton.setForeground(Color.WHITE);
        addUserButton.setPreferredSize(new Dimension(100, 24));

        JButton addGroupButton = new JButton("Add Group");
        addGroupButton.addActionListener(actAddGroup);
        addGroupButton.setBorderPainted(false);
        addGroupButton.setBackground(Color.gray);
        addGroupButton.setForeground(Color.WHITE);
        addGroupButton.setPreferredSize(new Dimension(100, 24));

        // mid panel buttons
        JButton userViewButton = new JButton("Open User View");
        userViewButton.addActionListener(actOpenUserView);
        userViewButton.setBorderPainted(false);
        userViewButton.setBackground(Color.gray);
        userViewButton.setForeground(Color.WHITE);

        // lower panel buttons
        JButton userTotalButton = new JButton("Show User Total");
        userTotalButton.addActionListener(actShowUserTotal);
        userTotalButton.setBorderPainted(false);
        userTotalButton.setBackground(Color.gray);
        userTotalButton.setForeground(Color.WHITE);
        userTotalButton.setPreferredSize(new Dimension(400, 30));

        JButton totalMessagesButton = new JButton("Show Message Total");
        totalMessagesButton.addActionListener(actShowTotalMessages);
        totalMessagesButton.setBorderPainted(false);
        totalMessagesButton.setBackground(Color.gray);
        totalMessagesButton.setForeground(Color.WHITE);
        totalMessagesButton.setPreferredSize(new Dimension(400, 30));

        JButton groupTotalButton = new JButton("Show Group Total");
        groupTotalButton.addActionListener(actShowGroupTotal);
        groupTotalButton.setBorderPainted(false);
        groupTotalButton.setBackground(Color.gray);
        groupTotalButton.setForeground(Color.WHITE);
        groupTotalButton.setPreferredSize(new Dimension(400, 30));

        JButton positiveMessagePercentButton = new JButton("Positive Message Percentage");
        positiveMessagePercentButton.addActionListener(actShowPositiveMessagePercentage);
        positiveMessagePercentButton.setBorderPainted(false);
        positiveMessagePercentButton.setBackground(Color.gray);
        positiveMessagePercentButton.setForeground(Color.WHITE);
        positiveMessagePercentButton.setPreferredSize(new Dimension(400, 30));

        /* tree view */
        treeRoot = new DefaultMutableTreeNode("Root");
        currentNode = treeRoot;
        treeModel = new DefaultTreeModel(treeRoot);
        treeModel.addTreeModelListener(new AppTreeModelListener());
        tree = new JTree(treeModel);
        tree.setCellRenderer(new CustomTreeCellRenderer());
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
        });

        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);

        /* adding everything together */
        JFrame frame = new JFrame();
        frame.setBackground(Color.LIGHT_GRAY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(610, 260);
        frame.setLayout(null);
        frame.setVisible(true);

        gridBagLayout.setConstraints(addUserButton, constraintsUserButton);
        gridBagLayout.setConstraints(addGroupButton, constraintsGroupButton);
        gridBagLayout.setConstraints(userViewButton, constraintsUserViewButton);
        gridBagLayout.setConstraints(totalMessagesButton, constraintsTotalMessagesButton);
        gridBagLayout.setConstraints(userTotalButton, constraintsUserTotalButton);
        gridBagLayout.setConstraints(positiveMessagePercentButton, constraintsPositiveMessagePercentButton);
        gridBagLayout.setConstraints(groupTotalButton, constraintsGroupTotalButton);
        gridBagLayout.setConstraints(userID, constraintsUserID);
        gridBagLayout.setConstraints(groupID, constraintsGroupID);

        rightVerticalSubPanel.add(addUserButton);
        rightVerticalSubPanel.add(addGroupButton);
        leftVerticalSubPanel.add(userID);
        leftVerticalSubPanel.add(groupID);
        upperSubPanel.add(leftVerticalSubPanel);
        upperSubPanel.add(rightVerticalSubPanel);
        midSubPanel.add(userViewButton);
        bottomLeftVerticalSubPanel.add(userTotalButton);
        bottomLeftVerticalSubPanel.add(totalMessagesButton);
        bottomRightVerticalSubPanel.add(groupTotalButton);
        bottomRightVerticalSubPanel.add(positiveMessagePercentButton);
        lowerSubPanel.add(bottomLeftVerticalSubPanel);
        lowerSubPanel.add(bottomRightVerticalSubPanel);
        treePanel.add(tree);

        frame.add(upperSubPanel);
        frame.add(midSubPanel);
        frame.add(lowerSubPanel);
        frame.add(treePanel);
    }
}
