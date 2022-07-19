fun main() {

    add("Заголовок 1", "Текст заметки 1")
    add("Заголовок 2", "Текст заметки 2")
    add("Заголовок 3", "Текст заметки 3")
    add("Заголовок 4", "Текст заметки 4")
    add("Заголовок 5", "Текст заметки 5")

    createComment(notes[0], "Первый комментарий к заметке 1")
    createComment(notes[1], "Первый комментарий к заметке 2")
    createComment(notes[1], "Второй комментарий к заметке 2")
    createComment(notes[1], "Третий комментарий к заметке 2")

    val noteService = NoteService(notes, comments)

    println(getComments(1))
//    deleteComment(1)
    delete(1)
    println(getComments(1))
    println()
    println(getComments(2))
//    delete(2)
    deleteComment(2)
    deleteComment(3)
//    deleteComment(4)
    println(getComments(2))
    println(comments)
    println(noteService)

}