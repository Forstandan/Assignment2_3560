package UI;

import UI.Listeners.AdminUIListener;
import User.UserComponent;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AdminUI extends AdminUIListener {
    private static UserComponent currentComponent;
    private static DefaultMutableTreeNode currentNode;
    private static AdminUI instance;

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

    public static UserComponent getCurrentComponent() {
        return currentComponent;
    }

    public static void setCurrentComponent(UserComponent currentComponent) {
        AdminUI.currentComponent = currentComponent;
    }

    public static DefaultMutableTreeNode getCurrentNode() {
        return currentNode;
    }

    public static void setCurrentNode(DefaultMutableTreeNode currentNode) {
        AdminUI.currentNode = currentNode;
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

        JPanel groupIDTextPanel = new JPanel();
        groupIDTextPanel.setBackground(Color.lightGray);
        groupIDTextPanel.setLayout(gridBagLayout);

        /* add components to the panels */
        // upperPanel labels
        JTextField userID = new JTextField();
        userID.setBackground(Color.WHITE);
        userID.setForeground(Color.BLACK);
        userID.setEditable(true);
        setUserID(userID);

        JTextField groupID = new JTextField();
        groupID.setBackground(Color.WHITE);
        groupID.setForeground(Color.BLACK);
        groupID.setEditable(true);
        setGroupID(groupID);

        AdminUIListener adminUIListenerInstance = new AdminUIListener();

        // upperPanel buttons
        JButton addUserButton = new JButton("Add User");
        addUserButton.addActionListener(actAddUser);
        addUserButton.setBorderPainted(false);
        addUserButton.setBackground(Color.gray);
        addUserButton.setForeground(Color.WHITE);
        addUserButton.setPreferredSize(new Dimension(100, 24));

        JButton addGroupButton = new JButton("Add Group");
        addGroupButton.addActionListener(adminUIListenerInstance.actAddGroup);
        addGroupButton.setBorderPainted(false);
        addGroupButton.setBackground(Color.gray);
        addGroupButton.setForeground(Color.WHITE);
        addGroupButton.setPreferredSize(new Dimension(100, 24));

        // mid panel buttons
        JButton userViewButton = new JButton("Open User View");
        userViewButton.addActionListener(adminUIListenerInstance.actOpenUserView);
        userViewButton.setBorderPainted(false);
        userViewButton.setBackground(Color.gray);
        userViewButton.setForeground(Color.WHITE);

        // lower panel buttons
        JButton userTotalButton = new JButton("Show User Total");
        userTotalButton.addActionListener(adminUIListenerInstance.actShowUserTotal);
        userTotalButton.setBorderPainted(false);
        userTotalButton.setBackground(Color.gray);
        userTotalButton.setForeground(Color.WHITE);
        userTotalButton.setPreferredSize(new Dimension(400, 30));

        JButton totalMessagesButton = new JButton("Show Message Total");
        totalMessagesButton.addActionListener(adminUIListenerInstance.actShowTotalMessages);
        totalMessagesButton.setBorderPainted(false);
        totalMessagesButton.setBackground(Color.gray);
        totalMessagesButton.setForeground(Color.WHITE);
        totalMessagesButton.setPreferredSize(new Dimension(400, 30));

        JButton groupTotalButton = new JButton("Show Group Total");
        groupTotalButton.addActionListener(adminUIListenerInstance.actShowGroupTotal);
        groupTotalButton.setBorderPainted(false);
        groupTotalButton.setBackground(Color.gray);
        groupTotalButton.setForeground(Color.WHITE);
        groupTotalButton.setPreferredSize(new Dimension(400, 30));

        JButton positiveMessagePercentButton = new JButton("Positive Message Percentage");
        positiveMessagePercentButton.addActionListener(adminUIListenerInstance.actShowPositiveMessagePercentage);
        positiveMessagePercentButton.setBorderPainted(false);
        positiveMessagePercentButton.setBackground(Color.gray);
        positiveMessagePercentButton.setForeground(Color.WHITE);
        positiveMessagePercentButton.setPreferredSize(new Dimension(400, 30));

        /* tree view */
        DefaultMutableTreeNode treeRoot = new DefaultMutableTreeNode("Root");
        setTreeRoot(treeRoot);

        setCurrentNode(treeRoot);
        DefaultTreeModel treeModel = new DefaultTreeModel(treeRoot);
        treeModel.addTreeModelListener(new AppTreeModelListener());
        setTreeModel(treeModel);

        JTree tree = new JTree(treeModel);
        tree.setCellRenderer(new CustomTreeCellRenderer());
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        setTree(tree);

        Map<DefaultMutableTreeNode, UserComponent> nodeMap = new HashMap<>();
        nodeMap.put(treeRoot, getRoot());
        setNodeMap(nodeMap);

        tree.setEditable(true);
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
             setCurrentNode(node);

            if (node == null) {
                return;
            }
            setCurrentComponent(AdminUI.getNodeMap().get(node));
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
