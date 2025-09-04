package com.krr.demo;

import java.util.BitSet;
import java.util.Random;

/**
 * 一亿数据（数字）去重
 * 使用Bitmap位图形式，java中由BitSet实现
 */
public class BitSetTest {

    private static final int MAX_NUM = 100_000_000;

    /**
     * 数据
     */
    private final int[] DATA = new int[MAX_NUM];

    /**
     * 位图，最大数据量+1
     */
    private final BitSet BITSET = new BitSet(MAX_NUM + 1);

    public BitSetTest(){}

    public static void main(String[] args) {
        BitSetTest app = new BitSetTest();
        app.initData();
        System.out.println(app.duplicate());
    }

    private void initData(){
        long t1 = System.currentTimeMillis();
        Random random = new Random();
        for(int i = 0; i < MAX_NUM; i++){
            DATA[i] = random.nextInt(MAX_NUM + 1);
        }
        long t2 = System.currentTimeMillis();
        System.out.printf("Finished init data %s，spent%sms%n", MAX_NUM , t2 - t1);
    }

    private Pair duplicate(){
        long t1 = System.currentTimeMillis();
        int duplicate = 0 , unDuplicate = 0;
        for (int datum : DATA) {
            if (datum <= MAX_NUM) {
                if (BITSET.get(datum)) {
                    duplicate++;
                    continue;
                }

                BITSET.set(datum);
                unDuplicate++;
            } else {
                System.out.printf("Data num out of range %s%n", datum);
            }
        }
        long t2 = System.currentTimeMillis();
        System.out.printf("Finished duplicate data , spent%sms%n" , t2 - t1);
        return new Pair(duplicate , unDuplicate);
    }
}

class Pair{
    int duplicate;
    int unDuplicate;

    Pair(int duplicate , int unDuplicate){
        this.duplicate = duplicate;
        this.unDuplicate = unDuplicate;
    }

    public String toString(){
        return String.format("duplicate : %s , unDuplicate : %s" , duplicate , unDuplicate);
    }
}
