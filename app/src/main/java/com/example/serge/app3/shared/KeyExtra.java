package com.example.serge.app3.shared;

public enum KeyExtra {

    EDIT_MODE("EDIT_MODE")
    ;

    private String key;

    KeyExtra(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
