package cn.swm.common.test;

import cn.swm.pojo.TbMember;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args){
        Integer[] integers = {2,3,4,5};
        List<Integer> list = new ArrayList<>();
        list.addAll(Arrays.asList(integers));
        System.out.println(list.contains(8));
    }
}
