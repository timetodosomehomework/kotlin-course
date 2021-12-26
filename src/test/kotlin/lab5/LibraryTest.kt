package lab5

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


internal class LibraryTest {

    private val library = Library()
    private val book = Book("Властелин колец", listOf(Author("Джордж Р.М.", "Мартин")), Genre.EPICNOVEL, 1955)

    @Test
    fun constructor() {
        val map = mapOf<Book, Status>(book to Status.UsedBy(User("Максим", "Ковалевский")))
        val users = listOf(User("Вася", "Пупкин"))
        val usersCorrect = listOf(User("Максим", "Ковалевский"))
        assertDoesNotThrow {
            val library2 = Library(map, usersCorrect)
        }
        assertThrows(Exception::class.java) {
            val library2 = Library(map, users)
        }
    }

    @Test
    fun addBook() {
        assertNotEquals(listOf(book), library.findBooks(1955))
        library.addBook(book, Status.Available)
        assertEquals(listOf(book), library.findBooks(1955))
    }

    @Test
    fun findBooksByName() {
        assertNotEquals(listOf(book), library.findBooks("ин ко"))
        library.addBook(book, Status.Available)
        assertEquals(listOf(book), library.findBooks("ин ко"))
    }

    @Test
    fun findBooksByAuthors() {
        assertNotEquals(listOf(book), library.findBooks(listOf(Author("Джордж Р.М.", "Мартин"))))
        library.addBook(book, Status.Available)
        assertEquals(listOf(book), library.findBooks(listOf(Author("Джордж Р.М.", "Мартин"))))
    }

    @Test
    fun findBooksByYear() {
        assertNotEquals(listOf(book), library.findBooks(1955))
        library.addBook(book, Status.Available)
        assertEquals(listOf(book), library.findBooks(1955))
    }

    @Test
    fun findBooksByGenre() {
        assertNotEquals(listOf(book), library.findBooks(Genre.EPICNOVEL))
        library.addBook(book, Status.Available)
        assertEquals(listOf(book), library.findBooks(Genre.EPICNOVEL))
    }

    @Test
    fun getAllBooks() {
        assertNotEquals(1, library.getAllBooks().size)
        library.addBook(book, Status.Available)
        assertEquals(1, library.getAllBooks().size)
    }

    @Test
    fun getAllAvailableBooks() {
        val book2 = Book("Автостопом по галактике", listOf(Author("Дуглас", "Адамс")), Genre.NOVEL, 1979)
        library.addBook(book, Status.Available)
        library.addBook(book2, Status.ComingSoon)
        assertEquals(2, library.getAllBooks().size)
        assertEquals(1, library.getAllAvailableBooks().size)
    }

    @Test
    fun getBookStatus() {
        val book2 = Book("Превращение", listOf(Author("Франц", "Кафка")), Genre.NOVELLA, 1912)
        library.addBook(book2, Status.Available)
        assertEquals(Status.Available, library.getBookStatus(book2))
    }

    @Test
    fun getAllBookStatuses() {
        val book2 = Book("Повесть временных лет", listOf(Author("Нестор", "Летописец")), Genre.STORY, 1117)
        library.addBook(book, Status.Available)
        library.addBook(book2, Status.ComingSoon)
        val map = mapOf(book to Status.Available, book2 to Status.ComingSoon)
        assertEquals(map, library.getAllBookStatuses())
    }

    @Test
    fun setBookStatus() {
        val book2 = Book("Морфий", listOf(Author("Михаил", "Булгаков")), Genre.SHORTSTORY, 1926)
        library.addBook(book2, Status.Available)
        assertEquals(Status.Available, library.getBookStatus(book2))
        library.setBookStatus(book2, Status.Restoration)
        assertEquals(Status.Restoration, library.getBookStatus(book2))
    }

    @Test
    fun registerUser() {
        library.registerUser("Максим", "Ковалевский")
        library.addBook(book, Status.UsedBy(library.getUser("Максим", "Ковалевский")))
        assertEquals(Status.UsedBy(library.getUser("Максим", "Ковалевский")), library.getBookStatus(book))
    }

    @Test
    fun unregisterUser() {
        val user = User("Максим", "Ковалевский")
        library.registerUser("Максим", "Ковалевский")
        assertEquals(user, library.getUser("Максим", "Ковалевский"))
        library.unregisterUser(user)
        assertThrows(Exception::class.java) {
            library.getUser("Макисм", "Ковалевский")
        }
    }

    @Test
    fun takeBook() {
        val user = User("Максим", "Ковалевский")
        library.registerUser("Максим", "Ковалевский")
        library.addBook(book, Status.Available)
        assertEquals(Status.Available, library.getBookStatus(book))
        library.takeBook(user, book)
        assertEquals(Status.UsedBy(user), library.getBookStatus(book))
    }

    @Test
    fun returnBook() {
        val user = User("Максим", "Ковалевский")
        library.registerUser("Максим", "Ковалевский")
        library.addBook(book, Status.Available)
        assertEquals(Status.Available, library.getBookStatus(book))
        library.takeBook(user, book)
        assertEquals(Status.UsedBy(user), library.getBookStatus(book))
        library.returnBook(book)
        assertEquals(Status.Available, library.getBookStatus(book))
    }

    @Test
    fun restorationBook() {
        library.addBook(book, Status.Available)
        assertEquals(Status.Available, library.getBookStatus(book))
        library.restorationBook(book)
        assertEquals(Status.Restoration, library.getBookStatus(book))
    }

    @Test
    fun bookIsComingSoon() {
        library.addBook(book, Status.Available)
        assertEquals(Status.Available, library.getBookStatus(book))
        library.bookIsComingSoon(book)
        assertEquals(Status.ComingSoon, library.getBookStatus(book))
    }
}