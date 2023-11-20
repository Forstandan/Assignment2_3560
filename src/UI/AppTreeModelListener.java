package UI;

import Admin.Admin;
import User.User;
import User.UserComponent;
import User.UserGroup;

import javax.swing.event.TreeModelEvent;
import javax.swing.tree.DefaultMutableTreeNode;

public class AppTreeModelListener implements javax.swing.event.TreeModelListener {
    @Override
    public void treeNodesChanged(TreeModelEvent e) {
        DefaultMutableTreeNode node;
        AdminUI AdminUIInstance = AdminUI.getInstance();
        Admin AdminInstance = Admin.getInstance();
        node = (DefaultMutableTreeNode)(AdminUIInstance.getTree().getLastSelectedPathComponent());

        UserComponent userComponent = AdminUIFields.getNodeMap().get(node);
        String userComponentName = node.getUserObject().toString();
        if (!userComponentName.equals("groupName") && userComponent instanceof UserGroup) {
            AdminInstance.setGroupName((UserGroup) AdminUI.getCurrentComponent(), userComponentName);
        }
        else if (!userComponentName.equals("username") && userComponent instanceof User) {
            AdminInstance.setUsername((User) AdminUI.getCurrentComponent(), userComponentName);
        }
    }

    @Override
    public void treeNodesInserted(TreeModelEvent e) {

    }

    @Override
    public void treeNodesRemoved(TreeModelEvent e) {

    }

    @Override
    public void treeStructureChanged(TreeModelEvent e) {

    }
}
