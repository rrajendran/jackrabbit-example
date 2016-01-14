package com.capella.jackrabbit;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by ramesh on 14/01/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class JackRabbitOperationsTest {

    @Autowired
    private JackRabbitOperations jackRabbitOperations;

    @org.junit.Test
    public void testAddText() throws Exception {
        jackRabbitOperations.addText("Test message");
        String text = jackRabbitOperations.retrieveText();
        assertThat(text, is("Test message"));
    }


}