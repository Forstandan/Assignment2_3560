package User;

import Follow.Observer;
import Follow.Subject;
import UI.UserUI;
import Visitor.Visitor;
import Visitor.Element;
import java.util.ArrayList;
import java.util.List;
import Admin.Admin;

public class User implements UserComponent, Subject, Observer, Element {
    private UserUI UIInstance;
    private boolean isOpen;
    private UserGroup group;
    private String ID;
    private List<Observer> followers;
    private List<User> following;
    private List<String> tweets;
    private List<String> feed;
    private long creationTime;
    private long updateTime;

    public User(String username, UserGroup parentGroup) {
        isOpen = false;
        ID = username;
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.tweets = new ArrayList<>();
        this.feed = new ArrayList<>();
        follow(this);
        group = parentGroup;
    }

    // general methods
    public List<String> getTweets() {
        return tweets;
    }

    public void setTweets(List<String> tweets) { this.tweets = tweets; }

    public UserGroup getParentGroup() {
        return group;
    }

    public List<String> getFeed() {
        return feed;
    }

    public List<User> getFollowing() {
        return following;
    }

    public long getCreationTime() { return creationTime; }

    public void setCreationTime(long creationTime) { this.creationTime = creationTime; }

    public long getUpdateTime() { return updateTime; }

    public void setUpdateTime(long updateTime) { this.updateTime = updateTime; }

    public void setIsOpen(boolean isOpen, UserUI UIInstance) {
        this.isOpen = isOpen;
        this.UIInstance = UIInstance;
    }

    public UserUI getUIInstance() {
        return UIInstance;
    }

    public boolean isOpen() {
        return isOpen;
    }
    // general methods end

    // composite pattern
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    // composite pattern end

    // observer pattern
    public void attachObserver(Observer observer) {
        followers.add(observer);
    }

    public void detachObserver(Observer observer) {
        followers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : followers) {
            observer.update(this);

            if (observer instanceof User && ((User) observer).isOpen()) {
                ((User) observer).getUIInstance().update((User) observer);
            }
        }
    }

    public void update(Subject subject) {
        if (subject instanceof User) {
            List<String> tweets = ((User) subject).getTweets();

            // get latest tweet
            feed.add(tweets.get(tweets.size()-1));
            setUpdateTime(System.currentTimeMillis());
        }
    }

    public void follow(User user) {
        user.attachObserver(this);
        following.add(user);
    }
    // observer pattern end

    // visitor pattern
    public void accept(Visitor visitor) {
        if (visitor instanceof Admin && !Admin.getInstance().isCheckingValidity()) {
            visitor.visitUser(this);
        }
        else {
            visitor.visitUserComponent(this);
        }
    }
}
