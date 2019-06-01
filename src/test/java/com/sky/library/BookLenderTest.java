package com.sky.library;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BookLenderTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private BookLender bookLender = new BookLender(new BookRepositoryStub());

    @Test(expected = BookMissingPrefixException.class)
    public void retrieveBook_whenReferenceInvalid_returnsException() throws BookNotFoundException {
        bookLender.retrieveBook("INVALID-TEXT");
    }

    @Test(expected = BookNotFoundException.class)
    public void retrieveBook_whenBookDoesntExist_throwsException() throws BookNotFoundException {
        bookLender.retrieveBook("BOOK-999");
    }

    @Test
    public void retrieveBook_whenBookExists_returnsBook() throws BookNotFoundException {
        Book book = bookLender.retrieveBook("BOOK-GRUFF472");
        assertEquals("The Gruffalo",book.getTitle());
    }

    @Test(expected = BookMissingPrefixException.class)
    public void getBookSummary_whenReferenceInvalid_returnsException() throws BookNotFoundException {
        bookLender.getBookSummary("INVALID-TEXT");
    }

    @Test(expected = BookNotFoundException.class)
    public void getBookSummary_whenBookDoesntExist_throwsException() throws BookNotFoundException {
        bookLender.getBookSummary("BOOK-999");
    }

    @Test
    public void getBookSummary_whenBookExists_returnsSummary_GRUFF472() throws BookNotFoundException {
        String summary = bookLender.getBookSummary("BOOK-GRUFF472");
        assertEquals("[BOOK-GRUFF472] The Gruffalo - A mouse taking a walk in the woods",summary);
    }

    @Test
    public void getBookSummary_whenBookExists_returnsSummary_WILL987() throws BookNotFoundException {
        String summary = bookLender.getBookSummary("BOOK-WILL987");
        assertEquals("[BOOK-WILL987] The Wind In The Willows - With the arrival of spring and fine weather outside...",summary);
    }

}