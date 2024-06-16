package com.free.compose.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.free.compose.entity.Todo
import com.free.compose.service.TodoService
import org.springframework.stereotype.Component


@Component
class TodoViewModel(
    private val todoService: TodoService,
) {

    // Todo 리스트 상태를 관리
    var todos = mutableStateOf<List<Todo>>(emptyList())

    // 초기화 메서드, 데이터를 로드합니다.
    init {
        loadTodos()
    }

    // 모든 Todo를 불러오는 메서드
    fun loadTodos() {
        todos.value = todoService.findTodosByPage().content
    }

    // 새로운 Todo를 추가하는 메서드
    fun addTodo(todo: String) {
        val newTodo = todoService.save(todo)
        todos.value = todos.value + newTodo
    }

    // Todo를 업데이트하는 메서드
    fun updateTodo(todoId: Long) {
        val updated = todoService.updateStatusTodo(todoId)
        if (updated != null) {
            todos.value = todos.value.map {
                if (it.id == todoId) updated else it
            }
        }
    }

    // Todo를 삭제하는 메서드
    fun deleteTodo(todoId: Long) {
        todoService.deleteTodoById(todoId)
        todos.value = todos.value.filter { it.id != todoId }
    }


}