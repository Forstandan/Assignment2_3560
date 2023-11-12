package UI;

import Admin.Admin;
import UI.Listeners.AppTreeModelListener;
import User.User;
import User.UserComponent;
import User.UserGroup;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class AdminUI {
    private UserGroup root;
    private static AdminUI instance;
    private static Map<DefaultMutableTreeNode, UserComponent> nodeMap = new HashMap<>();
    private UserComponent currentComponent;

    private ActionListener actAddGroup = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
        }

        private void buttonActionPerformed(ActionEvent e) {
            if (currentComponent == null) {
                Admin.getInstance().createGroup(root);
            }
            else if (currentComponent instanceof UserGroup) {
                Admin.getInstance().createGroup((UserGroup)currentComponent);
            }
            else {
                Admin.getInstance().createGroup(((User)currentComponent).getParentGroup());
            }
        }
    };

    private AdminUI(UserGroup root) {
        this.root = root;
        createAdminView();
    }

    public static AdminUI getInstance(UserGroup userGroup) {
        if (instance == null) {
            synchronized (AdminUI.class) {
                if (instance == null) {
                    instance = new AdminUI(userGroup);
                }
            }
        }

        return instance;
    }

    public void setRoot(UserGroup userGroup) {
        root = userGroup;
    }

    public UserComponent getCurrentComponent() {
        return currentComponent;
    }

    public void setCurrentComponent(UserGroup userGroup) {
        currentComponent = userGroup;
    }

    public void createAdminView() {
        // create main panels
        JPanel treePanel = new JPanel();
        treePanel.setBackground(Color.blue);
        treePanel.setBounds(0, 0, 400, 750);
        treePanel.setLayout(new BorderLayout());

        JPanel upperSubPanel = new JPanel();
        upperSubPanel.setBackground(Color.red);
        upperSubPanel.setBounds(400, 0, 900, 300);
        upperSubPanel.setLayout(new GridLayout());

        JPanel leftVerticalSubPanel = new JPanel();
        leftVerticalSubPanel.setBackground(Color.gray);
        leftVerticalSubPanel.setLayout(new BoxLayout(leftVerticalSubPanel, BoxLayout.Y_AXIS));

        JPanel rightVerticalSubPanel = new JPanel();
        rightVerticalSubPanel.setBackground(Color.BLACK);
        GridBagLayout gridBagLayout = new GridBagLayout();
        rightVerticalSubPanel.setLayout(gridBagLayout);

        JPanel bottomVerticalSubPanel = new JPanel();
        bottomVerticalSubPanel.setBackground(Color.BLUE);
        bottomVerticalSubPanel.setLayout(new GridBagLayout());

        // user button
        GridBagConstraints constraintsUserButton = new GridBagConstraints(100, 200, 300,
                400, 0.8, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0);
        // group button
        GridBagConstraints constraintsGroupButton = new GridBagConstraints(100, 200, 200,
                400, 1, 1, GridBagConstraints.PAGE_END, GridBagConstraints.NONE,
                new Insets(0, 0, 100, 20), 0, 0);

        JPanel lowerSubPanel = new JPanel();
        lowerSubPanel.setBackground(Color.green);
        lowerSubPanel.setBounds(400, 400, 900, 250);
//        lowerSubPanel.setLayout(new BorderLayout());

        // add components to the panels

        // upperPanel labels
        JLabel userID = new JLabel();
        userID.setText("User ID: ");

        JLabel groupID = new JLabel();
        groupID.setText("Group ID: ");

        // upperPanel buttons
        JButton addUserButton = new JButton("Add User");

        JButton addGroupButton = new JButton("Add Group");
        addGroupButton.addActionListener(actAddGroup);
        addGroupButton.setBorderPainted(false);
        addGroupButton.setBackground(Color.gray);
        addGroupButton.setFont(new Font("Arial", Font.PLAIN, 30));
        addGroupButton.setForeground(Color.WHITE);
        addGroupButton.setPreferredSize(new Dimension(400, 60));

        // tree component
        DefaultMutableTreeNode treeRoot = new DefaultMutableTreeNode("Root");
        DefaultTreeModel treeModel = new DefaultTreeModel(treeRoot);
        treeModel.addTreeModelListener(new AppTreeModelListener());

        JTree tree = new JTree(treeModel);
        nodeMap.put(treeRoot, this.root);
        tree.setEditable(true);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                        tree.getLastSelectedPathComponent();

                if (node == null) {
                    return;
                }
                Object nodeInfo = node.getUserObject();
                if (AdminUI.nodeMap.get(node) instanceof UserGroup) {
                    currentComponent = AdminUI.nodeMap.get(node);
                }
            }
        });

        Border blackline = BorderFactory.createLineBorder(Color.gray);
        tree.setBorder(blackline);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(null);
        frame.setVisible(true);
        gridBagLayout.setConstraints(addUserButton, constraintsUserButton);
        gridBagLayout.setConstraints(addGroupButton, constraintsGroupButton);
        rightVerticalSubPanel.add(addUserButton);
        rightVerticalSubPanel.add(addGroupButton);
        upperSubPanel.add(leftVerticalSubPanel);
        upperSubPanel.add(rightVerticalSubPanel);
        frame.add(upperSubPanel);
        frame.add(lowerSubPanel);
        frame.add(treePanel);
        treePanel.add(tree);
    }


}
