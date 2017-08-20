package com.example.demo;

import org.apache.coyote.http11.Http11NioProtocol;

public class MyNioProtocol extends Http11NioProtocol {
    public MyNioProtocol() {
        super();
        this.setSendReasonPhrase(true);
    }
}
