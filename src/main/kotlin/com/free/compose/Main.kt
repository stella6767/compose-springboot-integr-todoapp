package com.free.compose

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.free.compose.ui.TodoList
import com.free.compose.ui.TodoListPreview
import org.springframework.boot.SpringApplication
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
@AutoConfiguration
class Main(

)


fun main(args: Array<String>) {

    val applicationContext = SpringApplicationBuilder(Main::class.java)
        .web(WebApplicationType.NONE) // 웹 서버 비활성화
        .headless(false)
        .run()

    application {
        App(applicationContext)

//        Window(
//            onCloseRequest = ::exitApplication,
//            title = "todo app"
//
//        ) {
//            TodoListPreview()
//        }
    }


}



@Composable
@Preview
fun ApplicationScope.App(
    applicationContext: ApplicationContext,
) {

    val globalWindowState = rememberWindowState(
        position = WindowPosition(Alignment.Center),
        width = 1400.dp,
        height = 800.dp,
    )

    Window(
        onCloseRequest = {
            SpringApplication.exit(applicationContext)
            exitApplication()
        },
        state = globalWindowState,
        //undecorated = true,
        title = "todo app"
        //icon = painterResource("app-icon.svg")
    ) {
        TodoListPreview()
    }




}

//val LocalApplicationContext = staticCompositionLocalOf<ApplicationContext> {
//    error("ApplicationContext not provided")
//}
