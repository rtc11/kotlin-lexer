import java.io.File

fun main() {
    val content = File("Lexer.kt").readText()

    val lex = Lexer(content)
    val tokens = mutableListOf<Token>()

    while (lex.hasNext()) {
        tokens.add(lex.next())
    }

    println(tokens.joinToString("\n"))
}

