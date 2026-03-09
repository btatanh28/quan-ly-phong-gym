package com.example.QuanLyPhongGym.core.mediator;

public interface IMessageHandler<T> {
    
    void handle(T message);
}
