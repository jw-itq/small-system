package com.example.bootdemo.geetestlibboot.pojo;

public class TestOutside{

    private String name = "999";
    class Inside{
        public Inside(){
            System.out.println(name);
        }
    }

    public static void main(String[] args){
        new TestOutside().new Inside();
    }
}
