package com.block.papagoapp.model;

import java.io.Serializable;

public class History implements Serializable {

    public String sentence;
    public String result;
    public String target;

    public History(String sentence, String result, String target) {
        this.sentence = sentence;
        this.result = result;
        this.target = target;
    }
}
