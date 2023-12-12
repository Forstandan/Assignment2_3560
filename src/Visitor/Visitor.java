package Visitor;

import User.*;
 public interface Visitor {
    void visitUser(User user);

    void visitUserComponent(UserComponent userComponent);
}
