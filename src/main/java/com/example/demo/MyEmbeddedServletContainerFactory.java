package com.example.demo;

import org.springframework.boot.context.embedded.EmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;

@Component()
public class MyEmbeddedServletContainerFactory extends TomcatEmbeddedServletContainerFactory {
    public EmbeddedServletContainer getEmbeddedServletContainer(ServletContextInitializer... initializers) {
        this.setProtocol("com.example.demo.MyNioProtocol");
        return super.getEmbeddedServletContainer(initializers);
    }

}  