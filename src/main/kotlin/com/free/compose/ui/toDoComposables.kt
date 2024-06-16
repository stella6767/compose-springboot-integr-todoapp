package com.free.compose.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.free.compose.entity.Todo
import com.free.compose.service.TodoService
import com.free.compose.util.LocalApplicationContext
import org.springframework.data.domain.PageRequest

@Composable
fun TodoList(
    toDos: List<Todo>,
    onCreateItem: (String) -> Unit,
    onCheckItem: (Todo) -> Unit,
    onRemoveItem: (Todo) -> Unit
) = TodoTheme {
    Surface {
        Column(Modifier.fillMaxSize()) {
            TodoInput(onCreateItem = onCreateItem)
            LazyColumn {
                items(toDos.size) { index ->
                    val toDo = toDos[index]
                    TodoItem(
                        content = toDo.content,
                        checked = toDo.status,
                        onCheckedClick = { onCheckItem(toDo) },
                        onDeleteClick = { onRemoveItem(toDo) }
                    )
                }
            }
        }
    }
}

@Composable
fun TodoInput(
    onCreateItem: (String) -> Unit
) = Column {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val input = remember { mutableStateOf(TextFieldValue()) }

        fun createTodo() {
            if (input.value.text.isBlank()) return
            onCreateItem(input.value.text)
            input.value = input.value.copy(text = "")
        }

        OutlinedTextField(
            value = input.value,
            onValueChange = { input.value = it },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { createTodo() }),
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
        )
        OutlinedButton(
            onClick = { createTodo() },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Create")
        }
    }
    Divider()
}

@Composable
fun TodoItem(
    content: String,
    checked: Boolean,
    onCheckedClick: () -> Unit,
    onDeleteClick: () -> Unit
) = Column {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { onCheckedClick() },
            modifier = Modifier.padding(8.dp)
        )
        Text(content, Modifier.weight(1f))
        IconButton(
            onClick = { onDeleteClick() }
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = MaterialTheme.colors.error
            )
        }
    }
    Divider()
}

@Composable
fun TodoTheme(content: @Composable () -> Unit) = MaterialTheme(
    colors = lightColors(
        primary = Color(0xFF3759DF),
        secondary = Color(0xFF375A86)
    ),
    typography = MaterialTheme.typography,
    shapes = MaterialTheme.shapes,
    content = content
)

@Preview
@Composable
fun TodoListPreview() {

    val applicationContext = LocalApplicationContext.current
    val todoService = applicationContext.getBean(TodoService::class.java)

    var toDos =
        remember { mutableStateOf(todoService.findTodosByPage().content.toMutableList()) }


//    val toDos = remember {
//        mutableStateOf(
//            listOf(
//                Todo(0, "Make a list", true),
//                Todo(1, "Check it twice", false),
//            )
//        )
//    }






    TodoList(
        toDos = toDos.value,
        onCreateItem = {
            toDos.value += Todo(toDos.value.lastOrNull()?.id?.plus(1) ?: 0, it, false)
        },
        onCheckItem = {
//            toDos.value = toDos.value.toMutableList().apply {
//                //set(toDos.value.indexOf(it), Todo(!it.status))
//            }.toList()
        },
        onRemoveItem = {
            toDos.value -= it
        }
    )
}