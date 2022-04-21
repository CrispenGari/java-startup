package com.pagination.pagination.posts;
public enum Status {
    LIKED("LIKED"),
    UNLIKED("UNLIKED");
    private final String status;
    Status(String status){
        this.status =status;
    }
    public String getStatus() {
        return this.status;
    }
}
