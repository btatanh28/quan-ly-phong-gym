package com.example.QuanLyPhongGym.core.mediator;

import org.springframework.http.ResponseEntity;

public interface IMediator {
    
    ResponseEntity<Object> send(Object request);

    ResponseEntity<Object> export(Object request);

    void publish(Object message);
}
