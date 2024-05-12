class Lexer(private val input: String) {
    
    private var pos: Int = 0
    private val cur: Char get() = input.elementAtOrElse(pos) { Char.MIN_VALUE }

    fun hasNext(): Boolean = pos < input.length

    fun next(): Token {
        skip_space()

        return when(cur) {
            '{' -> create(Type.L_BRACE) 
            '}' -> create(Type.R_BRACE)
            '(' -> create(Type.L_PAREN)
            ')' -> create(Type.R_PAREN)
            ',' -> create(Type.COMMA)
            '+' -> create(Type.PLUS)
            '=' -> create(Type.ASSIGN)
            '-' -> create(Type.MINUS)
            '*' -> create(Type.STAR)
            '/' -> create(Type.SLASH)
            ';' -> create(Type.SEMICOLON)
            ':' -> create(Type.COLON)
            '!' -> create(Type.BANG)
            '<' -> create(Type.LT)
            '>' -> create(Type.GT)
            '\n', '\r' -> next()
            in 'a'..'z', in 'A'..'Z', '_' -> readIdentifier()
            in '0'..'9' -> readNum()
            Char.MAX_VALUE -> create(Type.EOF)
            else -> create(Type.ILLEGAL)
        }
    }

    fun create(type: Type): Token {
        pos++
        return Token(type)
    }
    
    fun readIdentifier(): Token {
        val start = pos
        while (cur.isLetter() || cur == '_') pos++
        return when(val identifier = input.substring(start, pos)) {
            "val" -> Token(Type.VAL)
            "var" -> Token(Type.VAR)
            "fun" -> Token(Type.FUN)
            "class" -> Token(Type.CLASS)
            "enum" -> Token(Type.ENUM)
            "data" -> Token(Type.DATA)
            "return" -> Token(Type.RETURN)
            "while" -> Token(Type.WHILE)
            "when" -> Token(Type.WHEN)
            "if" -> Token(Type.IF)
            "else" -> Token(Type.ELSE)
            "let" -> Token(Type.LET)
            "also" -> Token(Type.ALSO)
            "apply" -> Token(Type.APPLY)
            "use" -> Token(Type.USE)
            "run" -> Token(Type.RUN)
            "with" -> Token(Type.WITH)
            "true" -> Token(Type.TRUE)
            "false" -> Token(Type.FALSE)
            "in" -> Token(Type.IN)
            ".." -> Token(Type.RANGE)
            "!=" -> Token(Type.NEQ)
            "==" -> Token(Type.EQ)
            "<=" -> Token(Type.LE)
            ">=" -> Token(Type.GE)
            "?:" -> Token(Type.ELVIS)
            "//" -> Token(Type.COMMENT)
            else -> Token(Type.IDENTIFIER, identifier)
        }
    }

    fun readNum(): Token {
        val start = pos       
        while (cur.isDigit()) pos++
        return Token(Type.NUMBER, input.substring(start, pos))
    }

    fun skip_space() {
        while (cur.isWhitespace()) pos++
    }

}

data class Token(
    val type: Type, 
    val literal: String = "",
) {
    private val green = "\u001B[32m"
    private val reset = "\u001B[0m"

    override fun toString(): String {
        if (type in literals) {
            return "$green$type$reset"
        } else {
            return "$type \t $literal"
        }
    } 
}

private val literals = listOf(
    Type.L_BRACE,
    Type.R_BRACE,
    Type.L_PAREN,
    Type.R_PAREN,
    Type.COMMA,
    Type.SEMICOLON,
    Type.PLUS,
    Type.ASSIGN,
)

enum class Type {
    L_BRACE,
    R_BRACE,
    L_PAREN,
    R_PAREN,
    COMMA,
    SEMICOLON,
    PLUS,
    ASSIGN,

    RANGE,
    SINGLE_QUOTE,
    DOUBLE_QUOTE,
    COLON,
    MINUS,
    SLASH,
    STAR,
    IDENTIFIER,
    BANG,
    LT,
    GT,

    STRING,
    NUMBER,

    NEQ,
    EQ,
    LE,
    GE,

    FUN,

    CLASS,
    ENUM,
    DATA,

    RETURN,
    TRUE,
    FALSE,
    IF,
    ELSE,
    LET,
    ALSO,
    APPLY,
    USE,
    RUN,
    WITH,
    WHILE,
    WHEN,
    IN,

    VAL,
    VAR,

    ELVIS,
    COMMENT,

    EOF,
    ILLEGAL,
}

