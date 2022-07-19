var notes: MutableList<Note> = mutableListOf()
var comments: MutableList<Comment> = mutableListOf()
var idNote = 0
var idComment = 0


class NoteService<Note, Comment>(var notes: Note, var comments: Comment) {
}

open class Note(
    open val noteId: Int,
    open val noteTitle: String,
    open val noteText: String,
    open var commentId: Int,
    open val count: Int,
    open val deleted: Boolean = false
)

class Comment(
    noteId: Int,
    noteTitle: String,
    noteText: String,
    commentId: Int,
    count: Int,
    val commentMessage: String,
    val commentIsDeleted: Boolean = false
) : Note(noteId, noteTitle, noteText, commentId, count)

fun add(title: String, text: String): Int {
    idNote++
    val note = Note(
        noteId = idNote,
        noteTitle = title,
        noteText = text,
        commentId = 0,
        count = 0
    )
    notes.add(note)
    return note.noteId
}

fun createComment(note: Note, message: String): Int {
    idComment++
    val comment = Comment(
        noteId = note.noteId,
        noteTitle = note.noteTitle,
        noteText = note.noteText,
        commentId = idComment,
        count = note.count + 1,
        commentMessage = message,
        commentIsDeleted = false
    )
    comments.add(comment)

    val noteWithComment = Note(
        noteId = note.noteId,
        noteTitle = note.noteTitle,
        noteText = note.noteText,
        commentId = comment.commentId,
        count = comment.commentId
    )
    notes[notes.indexOf(note)] = noteWithComment
    return comment.commentId
}

fun delete(noteId: Int): Boolean {
    val indexElementNote = notes.indexOf(notes.find { it.noteId == noteId })

    if (indexElementNote == -1) {
        return false
    } else {
        if (notes[indexElementNote].deleted == true) {
            throw NoteHasDeletedException("Заметка с id $noteId уже удалена!")
        } else {
            val deletedNote = Note(
                noteId = noteId,
                noteTitle = notes[indexElementNote].noteTitle,
                noteText = notes[indexElementNote].noteText,
                commentId = notes[indexElementNote].commentId,
                count = notes[indexElementNote].count,
                deleted = true
            )
            notes[indexElementNote] = deletedNote
        }
    }
    return true
}

fun deleteComment(commentId: Int): Boolean {
    val indexElementComment = comments.indexOf(comments.find { it.commentId == commentId })

    if (indexElementComment == -1) {
        return false
    } else {
        if (comments[indexElementComment].commentIsDeleted == true) {
            throw CommentHasDeletedException("Комментарий с id $commentId удален")
        }
        val deletedComment = Comment(
            noteId = comments[indexElementComment].noteId,
            noteTitle = comments[indexElementComment].noteTitle,
            noteText = comments[indexElementComment].noteText,
            commentId = comments[indexElementComment].commentId,
            count = comments[indexElementComment].count,
            commentMessage = comments[indexElementComment].commentMessage,
            commentIsDeleted = true
        )
        comments[indexElementComment] = deletedComment
    }
    return true
}

fun edit(noteId: Int, newTitle: String, newText: String): Boolean {
    val indexElementNote = notes.indexOf(notes.find { it.noteId == noteId })
    var targetNote = notes.find { it.noteId == noteId } ?: return false

    if (!targetNote.deleted) {
        targetNote = Note(
            noteId = noteId,
            noteTitle = newTitle,
            noteText = newText,
            deleted = false,
            commentId = targetNote.commentId,
            count = targetNote.count
        )
        notes[indexElementNote] = targetNote
    } else {
        throw Exception("Заметка с id $noteId удалена")
    }
    return true
}

fun editComment(commentId: Int, newMessage: String): Boolean {
    val indexElementComment = comments.indexOf(comments.find { it.commentId == commentId })
    var targetComment = comments.find { it.commentId == commentId } ?: return false

    if (!targetComment.commentIsDeleted) {
        targetComment = Comment(
            noteId = targetComment.noteId,
            noteTitle = targetComment.noteTitle,
            noteText = targetComment.noteText,
            commentId = commentId,
            count = targetComment.count,
            commentMessage = newMessage,
            commentIsDeleted = targetComment.commentIsDeleted,
        )
        comments[indexElementComment] = targetComment
    } else {
        throw CommentHasDeletedException("Комментарий с id $commentId удален")
    }
    return true
}

fun get(): List<Note> {
    val listNote: MutableList<Note> = mutableListOf()
    for ((index, note) in notes.withIndex()) {
        if (note.deleted == false) {
            listNote += notes[index]
        }
    }
    return listNote
}

fun getComments(noteId: Int): MutableList<Comment> {
    val listComments: MutableList<Comment> = mutableListOf()
    val indexElementNote = notes.indexOf(notes.find { it.noteId == noteId })

    for ((index, comment) in comments.withIndex()) {
        if (comment.noteId == noteId && !notes[indexElementNote].deleted) {
            if (!comment.commentIsDeleted) listComments += comment
        }
    }
    return listComments
}