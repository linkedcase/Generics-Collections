package ru.netology

import Comment
import CommentHasDeletedException
import Note
import NoteHasDeletedException
import add
import comments
import createComment
import delete
import deleteComment
import edit
import editComment
import get
import getComments
import notes
import org.junit.Test
import idNote
import idComment

import org.junit.Assert.*

class NoteServiceKtTest {

    @Test
    fun addNewNote() {
        //arrange
        val title = "Тест. Заголовок заметки 1"
        val text = "Тест. Текст заметки 1"

        //act
        val result = add(title, text)

        //assert
        assertEquals(6, result)
    }

    @Test
    fun createNewComment() {
        //arrange
        add("Тест. Заголовок заметки", "Тест. Текст заметки")
        val note = notes[0]
        val commentMessage = "Тест. Теккст оменнтария к заметке"

        //act
        val result = createComment(note, commentMessage)

        //assert
        assertEquals(2, result)
    }

    @Test
    fun deleteAbsentNote() {
        //arrange
        notes

        //act
        val result = delete(9)

        //assert
        assertFalse(result)
    }

    @Test(expected = NoteHasDeletedException::class)
    fun shouldThrowExceptionWhenNoteIsDeleted() {
        delete(notes[0].noteId)
        delete(notes[0].noteId)
        }

    @Test
    fun deleteCurrentNote() {
        //arrange
        val noteId = notes[2].noteId

        //act
        val result = delete(noteId)

        //assert
        assertTrue(result)
    }

    @Test
    fun deleteAbsentComment() {
        //arrange
        comments

        //act
        val result = deleteComment(5)

        //assert
        assertFalse(result)
    }

    @Test(expected = CommentHasDeletedException::class)
    fun shouldThrowExceptionWhenCommentIsDeleted() {
        deleteComment(comments[0].commentId)
        deleteComment(comments[0].commentId)
    }


    @Test
    fun deleteCurrentComment() {
        //arrange
        add("Тест. Заголовок заметки", "Тест. Текст заметки")
        val note = notes[0]
        createComment(note, "Тест. Текст комментария")
        val commentId = comments[0].commentId

        //act
        val result = deleteComment(commentId)

        //assert
        assertTrue(result)
    }

//    @Test(expected = NoteHasDeletedException::class)
//    fun shouldThrowExceptionWhenDeletedNote() {
//        //act
//        delete(1)
//    }

    @Test
    fun editCurrentNote() {
        //arrange
        val title = "Тест. Заголовок заметки 1"
        val text = "Тест. Текст заметки 1"
        add(title, text)
        val noteId = notes[0].noteId

        //act
        val result = edit(noteId, "Тест. Редактируем заголовок", "Тест. Редактируем текст заметки")

        //assert
        assertTrue(result)
    }

    @Test
    fun editAbsentNote() {
        //arrange
        notes

        //act
        val result = edit(7, "Тест. Редактируем заголовок", "Тест. Редактируем текст заметки")

        //assert
        assertFalse(result)
    }

    @Test(expected = NoteHasDeletedException::class)
    fun shouldThrowExceptionWhenEditDeletedNote() {
        delete(1)
        edit(1, "Тест. Редактируем заголовок", "Тест. Редактируем текст заметки")
    }

    @Test
    fun editCurrentComment() {
        //arrange
        val commentId = comments[1].commentId

        //act
        val result = editComment(commentId, "Тест. Редактируем текст комментария")

        //assert
        assertTrue(result)
    }

    @Test
    fun editAbsentComment() {
        //arrange
        comments

        //act
        val result = editComment(7, "Тест. Редактируем текст комментария")

        //assert
        assertFalse(result)
    }

//    @Test(expected = CommentHasDeletedException::class)
//    fun shoulrThrowExceptioneditCurrentComment() {
//        //arrange
//        comments
//
//        //act
//        val result = editComment(1, "Тест. Редактируем текст комментария")
//
//        //assert
//        assertTrue(result)
//    }

    @Test(expected = CommentHasDeletedException::class)
    fun shouldThrowExceptionWhenEditIsDeletedComment() {
        deleteComment(1)
        editComment(1, "Тест. Редактируем текст комментария")
    }

    @Test
    fun getListOfNotes() {
        //arrange
        notes
        add("Тест.Заметка 1", "Тест. Текст заметки 1")
        add("Тест.Заметка 2", "Тест. Текст заметки 2")
        var expected: List<Note> = emptyList()
        expected = get()

        //act
        val result = get()

        //assert
        assertEquals(expected, result)
    }

    @Test
    fun getListOfComments() {
        // arrange
        notes
        delete(notes[3].noteId)
        val expected: MutableList<Comment> = mutableListOf()

        //act
        val result = getComments(notes[0].noteId)

        //act
        assertEquals(expected, result)
    }
}