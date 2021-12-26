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
const val yearOfFirstPublishedBook = 868

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
        if (year > LocalDate.now().year || year < yearOfFirstPublishedBook)
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
        for ((_, v) in booksOriginal) {
            if (v != Status.Available && v != Status.ComingSoon && v != Status.Restoration) {
                var flag = true
                for (i in usersOriginal)
                    if (v == Status.UsedBy(i)) {
                        flag = false
                        break
                    }
                if (flag)
                    throw IllegalArgumentException("Book is used by unregistered user")
            }
        }
        books.putAll(booksOriginal)
        users.addAll(usersOriginal)
    }

    override fun findBooks(substring: String): List<Book> {
        return books.keys.filter { book -> book.name.contains(substring) }
    }

    override fun findBooks(author: List<Author>): List<Book> {
        return books.keys.filter { book -> book.author == author }
    }

    override fun findBooks(year: Int): List<Book> {
        return books.keys.filter { book -> book.year == year }
    }

    override fun findBooks(genre: Genre): List<Book> {
        return books.keys.filter { book -> book.genre == genre }
    }

    override fun getAllBooks(): List<Book> {
        return books.keys.toList()
    }

    override fun getAllAvailableBooks(): List<Book> {
        return books.filter { (_, v) -> v == Status.Available }.keys.toList()
    }

    override fun getBookStatus(book: Book): Status {
        return books[book] ?: throw NoSuchObjectException("There is no such book in the library") // throwed if we tried to invoke method (set) of non-existing object (entity of map)
    }

    override fun getAllBookStatuses(): Map<Book, Status> {
        return books
    }

    override fun setBookStatus(book: Book, status: Status) {
        books[book] ?: throw NoSuchObjectException("There is no such book in the library") // throwed if we tried to invoke method (set) of non-existing object (entity of map)
        books[book] = status
    }

    override fun addBook(book: Book, status: Status) {
        books[book] = status
    }

    override fun registerUser(firstName: String, lastName: String) {
        if (!users.contains(User(firstName, lastName)))
            users.add(User(firstName, lastName))
        else
            throw IllegalArgumentException("User is already registered")
    }

    override fun unregisterUser(user: User) {
        if (users.contains(user))
            users.remove(user)
        else
            throw IllegalArgumentException("User has never been registered")
    }

    override fun getUser(firstName: String, lastName: String): User {
        val user = User(firstName, lastName)
        for (i in users.indices)
            if (users[i] == user)
                return users[i]
        throw IllegalArgumentException("There is no such user")
    }

    override fun takeBook(user: User, book: Book) {
        if (user !in users)
            throw IllegalArgumentException("User isn't registered")
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