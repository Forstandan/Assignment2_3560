package UI.Listeners;

import Admin.Admin;
import UI.AdminUI;
import User.User;
import User.UserComponent;
import User.UserGroup;

import javax.swing.event.TreeModelEvent;
import javax.swing.tree.DefaultMutableTreeNode;

public class AppTreeModelListener implements javax.swing.event.TreeModelListener {
    @Override
    public void treeNodesChanged(TreeModelEvent e) {
        DefaultMutableTreeNode node;
        node = (DefaultMutableTreeNode)(AdminUI.getInstance().getTree().getLastSelectedPathComponent());

        UserComponent userComponent = AdminUI.getInstance().getNodeMap().get(node);
        String userComponentName = node.getUserObject().toString();
        if (!userComponentName.equals("groupName") && userComponent instanceof UserGroup) {
            Admin.getInstance().setGroupName((UserGroup) AdminUI.getInstance().getCurrentComponent(), userComponentName);
        }
        else if (!userComponentName.equals("username") && userComponent instanceof User) {
            Admin.getInstance().setUsername((User) AdminUI.getInstance().getCurrentComponent(), userComponentName);
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
