<h2>environment</h2>
java:8

<h2>build</h2>
step 1:Install engine to your local maven repostory:<code>mvn install -f engine/pom.xml</code>
<br>
step 2:Build UI:<code>mvn package -f ui/pom.xml</code>

<h2>run</h2>
<code>java -jar -Xmx30000m ui/target/ui-0.0.1-SNAPSHOT.jar</code>

<h2>visit</h2>
Open your browser and visit:<code>http://127.0.0.1:8080</code>
<br>
Fill in your fen, then click submit to search best move.
<br>
You can modify depth, width and pool size to suit your own CPU and RAM.

<h2>quote</h2>
The webpage of this project quotes some contents of <link>https://github.com/lhartikk/simple-chess-ai</link>, thank you!
