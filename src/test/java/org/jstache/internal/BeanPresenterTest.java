package org.jstache.internal;

import org.jstache.provider.BeanProvider;

import java.util.ArrayList;
import java.util.List;
import org.jstache.Template;
import org.junit.Assert;
import org.junit.Test;

public class BeanPresenterTest{

    public class User{
        private final String name;

        public User(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }

        public List<User> getFriends(){
            List<User> friends = new ArrayList<User>();
            friends.add(new User("Riley"));
            friends.add(new User("Pepper"));
            friends.add(new User("Molly"));
            return friends;
        }
    }

    @Test
    public void testBean(){
        BeanProvider presenter = new BeanProvider(new User("Sophie"));
        String name = presenter.get("name").toString();
        Assert.assertEquals("Sophie", name);
    }

    @Test
    public void testIterable(){
        Template template = Template.parse("{{#friends}}{{name}}{{/friends}}");
        String output = template.render(new User("Sophie"));
        Assert.assertEquals("RileyPepperMolly", output);
    }
}

