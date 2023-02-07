<h2>A chess ai engine with UI.</h2>

This project includes two java projects. The 1st part is the computing engine and the 2nd part is UI.

The engine part please see <link>https://github.com/lansinuote/search_chess_next_move/tree/main/engine</link>

The UI part please see <link>https://github.com/lansinuote/search_chess_next_move/tree/main/ui</link>

At depth of 4 and width of 16, it is about 1800 points of chess.com

<b>Please don't use it for cheating. Please respect your opponent and play fair!</b>

<h2>quick start</h2>
<code>mvn install -f engine/pom.xml
mvn package -f ui/pom.xml
java -jar -Xmx30000m ui/target/ui-0.0.1-SNAPSHOT.jar
</code>

Open your browser and visit:http://127.0.0.1:8080
<br>
Fill in your fen, then click submit to search best move.
<br>
You can modify depth, width and pool size to suit your own CPU and RAM.
