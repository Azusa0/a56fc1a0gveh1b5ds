package com.example;

import java.util.List;

@FunctionalInterface
interface FunctionSample1 {

    List<String> toList(Book book);

    default void print(Book book) {
        System.out.println(book.toString());
    }

    default String getBookInfo(Book book) {
        return "BookId: " + book.getBookId() + "BookName: " + book.getBookName();
    }
}
