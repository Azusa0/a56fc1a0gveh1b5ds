package com.example;

import java.util.List;

public class FunctionSample1Impl implements FunctionSample1 {

    @Override
    public List<String> toList(Book book) {
        return List.of(book.getBookId().toString(), book.getBookName());
    }
}
