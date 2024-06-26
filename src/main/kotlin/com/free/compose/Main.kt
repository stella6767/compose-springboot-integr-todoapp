package com.free.compose

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.free.compose.view.TodoListPreview
import com.free.compose.util.LocalApplicationContext
import org.springframework.boot.SpringApplication
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
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
        CompositionLocalProvider(LocalApplicationContext provides applicationContext){
            App(applicationContext)
        }
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

