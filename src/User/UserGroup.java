package User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserGroup implements UserComponent{
    private UserGroup parentGroup;
    private String groupName;
    private final String ID;
    List<UserComponent> groupMembers;

    public UserGroup(String groupName, UserGroup parentGroup) {
        ID = UUID.randomUUID().toString();
        this.groupMembers = new ArrayList<>();
        this.groupName = groupName;
        this.parentGroup = parentGroup;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    // composite pattern
    public String getID() {
        return ID;
    }

    public void addMember(UserComponent userComponent) {
        groupMembers.add(userComponent);
    }

    public void removeMember(UserComponent userComponent) {
        groupMembers.remove(userComponent);
    }
    // composite pattern end
}
