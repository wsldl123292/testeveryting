package common.service;

import common.Book;

import java.util.List;

/**
 * User: Bill Bejeck
 * Date: 4/5/13
 * Time: 1:29 PM
 */
public interface BookService {

    List<Book> findBooksByAuthor(String author);
    Book findBookByIsbn(String isbn);
    List<Book> get();


}