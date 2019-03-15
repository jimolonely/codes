package jimo.io;

import org.junit.Test;

import static org.junit.Assert.*;

public class ChannelDemoTest {

    private ChannelDemo demo = new ChannelDemo();

    @Test
    public void streamRead() {
        demo.streamRead("java-basic.iml");
    }
}