package com.habibi.CafeManagement.controller;

import java.util.Scanner;

public class Sample {
    public static void main(String[] args) {
        int [] mark = new int[4];
        Scanner scanner = new Scanner(System.in);

        for (int i =0; i < mark.length; i++){
            System.out.println("Enter the mark "+i+": ");
            mark[i] = scanner.nextInt();
        }

    }

}
