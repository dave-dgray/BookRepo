package com.sky.library;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BookRetrieverTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private BookRetriever bookRetriever = new BookRetriever(new BookRepositoryStub());

    @Test(expected = BookNotFoundException.class)
    public void retrieveBook_whenBookDoesntExist_throwsException() throws Exception {
        bookRetriever.retrieveBook("BOOK-999");
    }

    @Test
    public void retrieveBook_whenReferenceInvalid_returnsException() throws Exception {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Reference Is Missing BOOK Prefix");
        bookRetriever.retrieveBook("INVALID-TEXT");
    }

    @Test
    public void retrieveBook_whenBookExists_returnsBook() throws Exception {
        Book book = bookRetriever.retrieveBook("BOOK-GRUFF472");
        assertEquals("The Gruffalo",book.getTitle());
    }

}