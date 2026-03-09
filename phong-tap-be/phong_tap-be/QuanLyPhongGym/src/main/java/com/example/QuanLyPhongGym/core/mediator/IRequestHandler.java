package com.example.QuanLyPhongGym.core.mediator;

public interface IRequestHandler<T, R> {

    R handle(T request);
}
