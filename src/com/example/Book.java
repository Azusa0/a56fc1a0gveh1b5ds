package com.example;

import java.util.StringJoiner;

public class Book {

    private Integer bookId;

    private String bookName;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Book.class.getSimpleName() + "[", "]")
                .add("bookId=" + bookId)
                .add("bookName='" + bookName + "'")
                .toString();
    }

    public static final class Builder {
        private Integer bookId;
        private String bookName;

        private Builder() {
        }

        public Builder bookId(Integer bookId) {
            this.bookId = bookId;
            return this;
        }

        public Builder bookName(String bookName) {
            this.bookName = bookName;
            return this;
        }

        public Book build() {
            Book book = new Book();
            book.setBookId(bookId);
            book.setBookName(bookName);
            return book;
        }
    }
}
