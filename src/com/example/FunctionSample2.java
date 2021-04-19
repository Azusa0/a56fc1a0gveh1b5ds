package com.example;

import java.util.List;

public class FunctionSample2 {

    @FunctionalInterface
    public interface FunctionSample2Processor {
        void accept();
    }

    public static void functionProcess(FunctionSample2Processor processor) {
        processor.accept();
    }

    public void print(Book book) {
        System.out.println(book.toString());
    }

    public String getBookInfo(Book book) {
        return "BookId: " + book.getBookId() + ",BookName: " + book.getBookName();
    }

    public List<String> toList(Book book) {
        return List.of(book.getBookId().toString(), book.getBookName());
    }
}
