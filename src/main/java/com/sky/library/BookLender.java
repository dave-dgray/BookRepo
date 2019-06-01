package com.sky.library;

/*
 * Copyright © 2015 Sky plc All Rights reserved.
 * Please do not make your solution publicly available in any way e.g. post in forums or commit to GitHub.
 */

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BookLender implements BookService {

    private static final int REVIEW_WORD_LIMIT = 9;
    private static final String BOOK_REFERENCE_PREFIX = "BOOK-";
    private static final String ELLIPSIS = "...";

    private BookRepository bookRepository;

    BookLender(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public Book retrieveBook(String bookReference) throws BookNotFoundException {
        if (!bookReference.startsWith(BOOK_REFERENCE_PREFIX)) {
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
        if (!bookReference.startsWith(BOOK_REFERENCE_PREFIX)) {
            throw new BookMissingPrefixException();
        }
        Book book = bookRepository.retrieveBook(bookReference);
        if (Objects.isNull(book)) {
            throw new BookNotFoundException();
        }
        String review = book.getReview();
        List<String> reviewInWords = splitReviewIntoWords(review);
        if (reviewInWords.size() > REVIEW_WORD_LIMIT) {
            review = createAbbreviatedReview(reviewInWords.subList(0, REVIEW_WORD_LIMIT));
        }
        return "["+book.getReference()+"] " +
                book.getTitle() + " - " +
                review;
    }

    private String createAbbreviatedReview(List<String> reviewInWords) {
        StringBuilder stringBuilder = new StringBuilder();
        reviewInWords.forEach(word -> stringBuilder.append(word).append(" "));
        removeTrailingSpace(stringBuilder);
        stringBuilder.append(ELLIPSIS);
        return stringBuilder.toString();
    }

    private List<String> splitReviewIntoWords(String review) {
        return Arrays.asList(review.split("\\P{Alpha}+"));
    }

    private void removeTrailingSpace(StringBuilder stringBuilder) {
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    }
}
