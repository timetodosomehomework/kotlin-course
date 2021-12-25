package lab5

import java.rmi.NoSuchObjectException
import java.time.LocalDate

enum class Genre {
    STORY,
    SHORTSTORY,
    NOVEL,
    NOVELLA,
    EPICNOVEL
}

const val maxNumberOfBooks = 3

data class Book(
    val name: String,
    val author: List<Author>,
    val genre: Genre,
    val year: Int
) {
    init {
        if (name.isEmpty())
            throw IllegalArgumentException("Book must have a title")
        if (author.isEmpty())
            throw IllegalArgumentException("Book must have at least one author")
        if (year > LocalDate.now().year || year < 868)
            throw IllegalArgumentException("Wrong publishing year")
    }
}

data class Author(
    val firstName: String,
    val lastName: String
) {
    init {
        if (lastName.isEmpty())
            throw IllegalArgumentException("Author must have last name or pseudonym")
    }
}

data class User(
    val firstName: String,
    val lastName: String
) {
    init {
        if (firstName.isEmpty() || lastName.isEmpty())
            throw IllegalArgumentException("User must have both first name and last name")
    }
}

sealed class Status {
    object Available : Status()
    data class UsedBy(val user: User) : Status()
    object ComingSoon : Status()
    object Restoration : Status()
}

interface LibraryService {
    fun findBooks(substring: String): List<Book>
    fun findBooks(author: List<Author>): List<Book>
    fun findBooks(year: Int): List<Book>
    fun findBooks(genre: Genre): List<Book>

    fun getAllBooks(): List<Book>
    fun getAllAvailableBooks(): List<Book>

    fun getBookStatus(book: Book): Status
    fun getAllBookStatuses(): Map<Book, Status>

    fun setBookStatus(book: Book, status: Status)
    fun addBook(book: Book, status: Status = Status.Available)

    fun registerUser(firstName: String, lastName: String)
    fun unregisterUser(user: User)
    fun getUser(firstName: String, lastName: String): User

    fun takeBook(user: User, book: Book)
    fun returnBook(book: Book)
    fun restorationBook(book: Book)
    fun bookIsComingSoon(book: Book)
}

class Library(
    booksOriginal: Map<Book, Status> = emptyMap(),
    usersOriginal: List<User> = emptyList()
) : LibraryService {
    private val books: MutableMap<Book, Status> = mutableMapOf()
    private val users: MutableList<User> = mutableListOf()

    init {
        for (pair in booksOriginal)
            books.plus(pair.toPair())
        for (i in usersOriginal.indices)
            users.add(i, usersOriginal[i])
    }

    override fun findBooks(substring: String): List<Book> {
        var matching: List<Book> = mutableListOf()
        for ((k) in books)
            if (k.name.contains(substring))
                matching = matching.plus(k)
        return matching
    }

    override fun findBooks(author: List<Author>): List<Book> {
        var matching: List<Book> = mutableListOf()
        for ((k) in books) {
            if (k.author == author)
                matching = matching.plus(k)
        }
        return matching
    }

    override fun findBooks(year: Int): List<Book> {
        var matching: List<Book> = mutableListOf()
        for ((k) in books)
            if (k.year == year)
                matching = matching.plus(k)
        return matching
    }

    override fun findBooks(genre: Genre): List<Book> {
        var matching: List<Book> = mutableListOf()
        for ((k) in books)
            if (k.genre == genre)
                matching = matching.plus(k)
        return matching
    }

    override fun getAllBooks(): List<Book> {
        var matching: List<Book> = mutableListOf()
        for ((k) in books)
            matching = matching.plus(k)
        return matching
    }

    override fun getAllAvailableBooks(): List<Book> {
        var matching: List<Book> = mutableListOf()
        for ((k, v) in books)
            if (v == Status.Available)
                matching = matching.plus(k)
        return matching
    }

    override fun getBookStatus(book: Book): Status {
        for ((k, v) in books)
            if (k == book)
                return v
        throw NoSuchObjectException("There is no such book in the library")
    }

    override fun getAllBookStatuses(): Map<Book, Status> {
        var statuses: Map<Book, Status> = mutableMapOf()
        for (pair in books)
            statuses = statuses.plus(pair.toPair())
        return statuses
    }

    override fun setBookStatus(book: Book, status: Status) {
        for ((k) in books)
            if (k == book) {
                books[k] = status
            }
    }

    override fun addBook(book: Book, status: Status) {
        books += Pair(book, status)
    }

    override fun registerUser(firstName: String, lastName: String) {
        users += User(firstName, lastName)
    }

    override fun unregisterUser(user: User) {
        users.remove(user)
    }

    override fun getUser(firstName: String, lastName: String): User {
        val user = User(firstName, lastName)
        for (i in users.indices)
            if (users[i] == user)
                return users[i]
        throw NoSuchObjectException("There is no such user")
    }

    override fun takeBook(user: User, book: Book) {
        if (user !in users)
            throw NoSuchObjectException("User isn't registered")
        if (getBookStatus(book) == Status.Available && books.values.filter { status -> status is Status.UsedBy && status.user == user }.size < maxNumberOfBooks) {
            books[book] = Status.UsedBy(user)
        } else
            throw IllegalArgumentException("User has max number of books or book is unavailable")
    }

    override fun returnBook(book: Book) {
        books[book] = Status.Available
    }

    override fun restorationBook(book: Book) {
        books[book] = Status.Restoration
    }

    override fun bookIsComingSoon(book: Book) {
        books[book] = Status.ComingSoon
    }
}