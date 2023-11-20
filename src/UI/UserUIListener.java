package UI;

import Admin.Admin;
import User.User;
import User.UserComponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class UserUIListener extends UserUIFields {
    protected final ActionListener actFollowUser = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
        }

        private void buttonActionPerformed(ActionEvent e) {

            if (getUserID().getText().equals("")) {
                UserComponent currentComponent = AdminUI.currentComponent;
                if (currentComponent instanceof User && currentComponent != getCurrentUser()) {
                    getCurrentUser().follow((User) currentComponent);
                    getFollowerListModel().insertElementAt(currentComponent.getID(), getFollowerList().getSelectedIndex() + 1);
                    getUserID().setText("");
                }
            } else {
                Set<User> userSet = Admin.getInstance().getUserSet();

                for (User user : userSet) {
                    if (Objects.equals(user.getID(), getUserID().getText()) && !getCurrentUser().getFollowing().contains(user)) {
                        getCurrentUser().follow(user);
                        getFollowerListModel().insertElementAt(user.getID(), getFollowerList().getSelectedIndex() + 1);
                        getUserID().setText("");
                        break;
                    }
                }
            }
        }
    };
    protected final ActionListener actTweet = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
        }

        private void buttonActionPerformed(ActionEvent e) {
            if (!getTweet().getText().equals("")) {
                List<String> tweets = getCurrentUser().getTweets();
                tweets.add(getTweet().getText());
                getCurrentUser().setTweets(tweets);
                getCurrentUser().notifyObservers();
                getTweet().setText("");
            }
        }
    };
}
