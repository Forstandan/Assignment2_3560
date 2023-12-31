package User;

import Visitor.Element;
import Visitor.Visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserGroup implements UserComponent, Element {
    private UserGroup parentGroup;
    private String groupName;
    private final String ID;
    private long creationTime;
    private long updateTime;
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

    public long getCreationTime() { return creationTime; }

    public void setCreationTime(long creationTime) { this.creationTime = creationTime; }

    public long getUpdateTime() { return updateTime; }

    public void setUpdateTime(long updateTime) { this.updateTime = updateTime; }

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

    // visitor pattern
    public void accept(Visitor visitor) {
        visitor.visitUserComponent(this);
    }
    // visitor pattern end
}
