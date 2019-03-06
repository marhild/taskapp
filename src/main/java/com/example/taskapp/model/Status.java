package com.example.taskapp.model;

import java.util.stream.Stream;

/**
 * @author platoiscoding.com
 */
public enum Status {
    OPEN("open"),
    CLOSED("closed"),
    REOPENED("reopened");

    private String typeOfStatus;

    Status(String typeOfStatus){
        this.typeOfStatus = typeOfStatus;
    }

    public String getTypeOfStatus() {
        return typeOfStatus;
    }

    public static Stream<Status> stream() {
        return Stream.of(Status.values());
    }
}
