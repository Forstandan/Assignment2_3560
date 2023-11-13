package User;

import Follow.Observer;
import Follow.Subject;
import Visitor.Visitor;
import Visitor.Element;
import java.util.ArrayList;
import java.util.List;

import java.util.UUID;

public class User implements UserComponent, Subject, Observer, Element {
    private UserGroup group;
    private final String ID;
    private String username;
    private List<Observer> followers;
    private List<String> tweets;
    private List<String> feed;

    public User(String username, UserGroup parentGroup) {
        ID = UUID.randomUUID().toString();
        this.followers = new ArrayList<>();
        this.tweets = new ArrayList<>();
        this.username = username;
        follow(this);
        group = parentGroup;
    }

    // general methods
    public List<String> getTweets() {
        return tweets;
    }

    public UserGroup getParentGroup() {
        return group;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void getFeed() {
        for (int i = feed.size() - 1; i >= 0; i--) {
            System.out.println(feed.get(i));
        }
    }

    public void tweet(String string) {
        tweets.add(string);
        notifyObservers();
    }
    // general methods end

    // composite pattern
    public String getID() {
        return ID;
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
        }
    }

    public void update(Subject subject) {
        if (subject instanceof User) {
            List<String> tweets = ((User) subject).getTweets();

            // get latest tweet
            feed.add(tweets.get(tweets.size()-1));
        }
    }

    public void follow(User user) {
        user.attachObserver(this);
    }

    public void unfollow(User user) {
        user.detachObserver(this);
    }
    // observer pattern end

    // visitor pattern
    public void accept(Visitor visitor) {
        visitor.visitUser(this);
    }
}
