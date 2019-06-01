package com.sky.library;

/*
 * Copyright © 2015 Sky plc All Rights reserved.
 * Please do not make your solution publicly available in any way e.g. post in forums or commit to GitHub.
 */

import java.util.Objects;

public class BookRetriever implements BookService {

    private BookRepository bookRepository;

    BookRetriever(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public Book retrieveBook(String bookReference) throws BookNotFoundException {
        if (!bookReference.startsWith("BOOK")) {
            throw new BookMissingPrefixException();
        }
        Book book = bookRepository.retrieveBook(bookReference);
        if (Objects.isNull(book)) {
            throw new BookNotFoundException();
        }
        return book;
    }

    @Override
    public String getBookSummary(String bookReference) throws BookNotFoundException {
        return null;
    }
}
