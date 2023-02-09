<img src="https://raw.githubusercontent.com/lansinuote/Chess_AI_engine_with_UI/main/demonstration.gif">

<h2>A chess AI engine with UI.</h2>

This project includes two java projects. The 1st part is the computing engine and the 2nd part is UI.

The engine part please see <a href="https://github.com/lansinuote/Chess_AI_engine_with_UI/tree/main/engine">engine</link>

The UI part please see <a href="https://github.com/lansinuote/Chess_AI_engine_with_UI/tree/main/ui">ui</link>

At depth of 4 and width of 16, it is about 1800 points of chess.com

<b>Please don't use it for cheating. Please respect your opponent and play fair!</b>

<h2>Quick start</h2>
<code>mvn install -f engine/pom.xml
mvn package -f ui/pom.xml
java -jar -Xmx30000m ui/target/ui-0.0.1-SNAPSHOT.jar
</code>

Open your browser and visit:http://127.0.0.1:8080
<br>
Fill in your fen, then click submit to search best move.
<br>
You can modify depth, width and pool size to suit your own CPU and RAM.

<h2>Why Java not Python?</h2>
Better multithreading support.
<br>
SpringBoot is better than Flask.
<br>
I like Jupyter Notebook, But Maven is more suitable for complex projects.
