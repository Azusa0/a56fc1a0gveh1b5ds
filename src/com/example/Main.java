package com.example;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

        // ----------------------------元素不可变集合----------------------------
        // List.of
        var list = List.of(1, 2, 3, 4, 5, 6);
        System.out.println("元素不可变集合 -> List.of():" + list);

        // Set.of
        var set = Set.of(1, 2, 3, 4, 5, 6, 7);
        System.out.println("元素不可变集合 -> Set.of():" + set);

        // Map.of
        var map = Map.of(1, "BookName1", 2, "BookName2", 3, "BookName3", 4, "BookName4", 5, "BookName5");
        System.out.println("元素不可变集合 -> Map.of():" + map);

        // ----------------------------集合与对象转换----------------------------
        // List转对象
        var bookList1 = list.stream()
                .map(id -> Book.newBuilder()
                        .bookId(id)
                        .build())
                .collect(Collectors.toList());
        System.out.println("集合与对象转换 -> List转对象 -> bookList1:" + bookList1);

        // Map转对象
        var bookList2 = map.entrySet().stream()
                .map(entry -> Book.newBuilder()
                        .bookId(entry.getKey())
                        .bookName(entry.getValue())
                        .build())
                .collect(Collectors.toList());
        System.out.println("集合与对象转换 -> Map转对象 -> bookList2:" + bookList2);

        // 对象集合转Map
        var bookMap1 = bookList2.stream()
                .collect(Collectors.toMap(Book::getBookId, Book::getBookName));
        System.out.println("集合与对象转换 -> 对象集合转Map -> bookMap1:" + bookMap1);

        // 对象集合转Map，并对key和value进行修改
        var bookMap2 = bookList2.stream()
                .collect(Collectors.toMap(book -> "*" + book.getBookId(), book -> "*" + book.getBookName()));
        System.out.println("集合与对象转换 -> 对象集合转Map，并对key和value进行修改 -> bookMap2:" + bookMap2);

        // 对象集合转Map，可能会有重复key的处理
        bookList2.add(Book.newBuilder().bookId(1).bookName("重复key测试").build());
        var bookMap3 = bookList2.stream()
                .collect(Collectors.toMap(Book::getBookId, Book::getBookName, (v1, v2) -> v2));
        System.out.println("集合与对象转换 -> 对象集合转Map，可能会有重复key的处理 -> bookMap3:" + bookMap3);

        // 对象集合转Map，可能会有重复key时特殊处理
        var bookMap4 = bookList2.stream()
                .collect(Collectors.toMap(Book::getBookId, Book::getBookName, (v1, v2) -> "出现重复key，第一个value：" + v1 + "，第二个value：" + v2));
        System.out.println("集合与对象转换 -> 对象集合转Map，可能会有重复key时特殊处理 -> bookMap4:" + bookMap4);

        // 对象集合的某个属性转List
        var bookIdList = bookList2.stream()
                .map(Book::getBookId)
                .collect(Collectors.toList());
        System.out.println("集合与对象转换 -> 对象集合的某个属性转List -> bookIdList:" + bookIdList);

        // ----------------------------数值运算----------------------------
        // 数值类型List全部元素求和
        var sum = bookIdList.stream().reduce(0, Integer::sum);
        System.out.println("数值运算 -> 数值类型List全部元素求和 -> sum:" + sum);

        // ----------------------------排序处理----------------------------
        // 按照给定顺序排序
        var sequenceList = List.of(2, 4, 5, 1, 3);
        var sortedBookList1 = bookList2.stream()
                .sorted(Comparator.comparingInt(book -> sequenceList.indexOf(book.getBookId())))
                .collect(Collectors.toList());
        System.out.println("排序处理 -> 按照给定顺序排序 -> sortedBookList1:" + sortedBookList1);

        // 按照bookId降序
        var sortedBookList2 = sortedBookList1.stream()
                .sorted(Comparator.comparingInt(Book::getBookId).reversed())
                .collect(Collectors.toList());
        System.out.println("排序处理 -> 按照bookId降序 -> sortedBookList2:" + sortedBookList2);

        // ----------------------------通过条件从集合中检索数据----------------------------
        // 筛选出bookId是1的数据
        var resultList1 = bookList2.stream()
                .filter(book -> book.getBookId() == 1)
                .collect(Collectors.toList());
        System.out.println("通过条件从集合中检索数据 -> 筛选出bookId是1的数据 -> resultList1:" + resultList1);

        // 筛选出bookName包含“3”的数据
        var resultList2 = bookList2.stream()
                .filter(book -> book.getBookName().contains("3"))
                .collect(Collectors.toList());
        System.out.println("通过条件从集合中检索数据 -> 筛选出bookName包含“3”的数据 -> resultList2:" + resultList2);

        // ----------------------------List、Optional、Object的转换----------------------------
        // 筛选出bookId是1的数据转换成Optional对象
        var resultOptional1 = bookList2.stream()
                .filter(book -> book.getBookId() == 1)
                .findAny();
        System.out.println("List、Optional、Object的转换 -> 筛选出bookId是1的数据转换成Optional对象 -> resultOptional1:" + resultOptional1);

        // 筛选出bookName包含“bookName”的第一条数据
        var resultOptional2 = bookList2.stream()
                .filter(book -> book.getBookName().contains("BookName"))
                .findFirst();
        System.out.println("List、Optional、Object的转换 -> 筛选出bookName包含“bookName”的第一条数据 -> resultOptional2:" + resultOptional2);

        // Optional转Book，当Optional为empty时，返回空的Book对象
        var result1 = resultOptional1.orElse(Book.newBuilder().build());
        System.out.println("List、Optional、Object的转换 -> Optional转Book，当Optional为empty时，返回空的Book对象 -> result1:" + result1);

        // Optional转Book，当Optional为empty时，抛出异常
        var result2 = resultOptional2.orElseThrow(() -> new RuntimeException("404"));
        System.out.println("List、Optional、Object的转换 -> Optional转Book，当Optional为empty时，抛出异常 -> result2:" + result2);

        // 判断Optional对象是否存在，存在则输出对象，不存在则输出未找到bookName
        System.out.println("List、Optional、Object的转换 -> 判断Optional对象是否存在，存在则输出对象，不存在则输出未找到bookName");
        resultOptional2.ifPresentOrElse(System.out::println, () -> System.out.println("未找到bookName"));

        // ----------------------------多线程----------------------------

        var functionSample = new FunctionSample1Impl();

        // parallel
        System.out.println("parallel");
        IntStream.range(0, 5).parallel().forEach(System.out::println);

        // runAsync
        System.out.println("runAsync");
        CompletableFuture.runAsync(() -> functionSample.print(Book.newBuilder().bookId(30).bookName("runAsync").build()));

        // supplyAsync
        System.out.println("supplyAsync");
        var futureList1 = bookList2.stream()
                .map(book -> CompletableFuture.supplyAsync(() -> functionSample.getBookInfo(book)))
                .collect(Collectors.toList());
        var strList1 = futureList1.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        System.out.println("多线程 -> supplyAsync -> strList1:" + strList1);

        // supplyAsync flat map
        System.out.println("supplyAsync flat map");
        var futureList2 = bookList2.stream()
                .map(book -> CompletableFuture.supplyAsync(() -> functionSample.toList(book)))
                .collect(Collectors.toList());
        var strList2 = futureList2.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        System.out.println("多线程 -> supplyAsync flat map -> strList2:" + strList2);
    }
}
