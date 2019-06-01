package com.sky.library;

/*
 * Copyright © 2015 Sky plc All Rights reserved.
 * Please do not make your solution publicly available in any way e.g. post in forums or commit to GitHub.
 */

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BookLender implements BookService {

    public static final int MAX_NUMBER_OF_WORDS = 9;
    private BookRepository bookRepository;

    BookLender(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public Book retrieveBook(String bookReference) throws BookNotFoundException {
        if (!bookReference.startsWith("BOOK-")) {
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
        if (!bookReference.startsWith("BOOK-")) {
            throw new BookMissingPrefixException();
        }
        Book book = bookRepository.retrieveBook(bookReference);
        if (Objects.isNull(book)) {
            throw new BookNotFoundException();
        }
        String review = book.getReview();
        List<String> reviewInWords = splitReviewIntoWords(review);
        if (reviewInWords.size() > MAX_NUMBER_OF_WORDS) {
            review = getAbbreviatedReview(reviewInWords.subList(0,MAX_NUMBER_OF_WORDS));
        }
        return "["+book.getReference()+"] " +
                book.getTitle() + " - " +
                review;
    }

    private String getAbbreviatedReview(List<String> reviewAsWords) {
        StringBuilder stringBuilder = new StringBuilder();
        reviewAsWords.forEach(word -> stringBuilder.append(word).append(" "));
        removeTrailingSpace(stringBuilder);
        stringBuilder.append("...");
        return stringBuilder.toString();
    }

    private List<String> splitReviewIntoWords(String review) {
        return Arrays.asList(review.split("\\P{Alpha}+"));
    }

    private void removeTrailingSpace(StringBuilder stringBuilder) {
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    }
}
