# kotlinc
# -d 				: output {file | directory}
# -include-runtime	: include kotlin runtime

out/MainKt.class:
	kotlinc Main.kt Lexer.kt -d out/

run: out/MainKt.class 
	kotlin -cp out/ MainKt

clean:
	rm -rf out/

