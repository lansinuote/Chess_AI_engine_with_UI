<h2>environment</h2>
java:8

<h2>build</h2>
Without UI:<code>mvn package -f engine/pom.xml</code>
<br>
If you want to use UI:<code>mvn install -f engine/pom.xml</code>
<br>
The UI part please see <a href="https://github.com/lansinuote/Chess_AI_engine_with_UI/tree/main/ui">ui</a>

<h2>run</h2>
<code>java -jar -Xmx30000m engine/target/engine-1.0-SNAPSHOT.jar "r1bqkbnr/pppp1ppp/2n1p3/8/3PP3/5N2/PPP2PPP/RNBQKB1R b KQkq - 1 3"</code>
<br>

The fen means the game borad like follow:

<code>
r  .  b  q  k  b  n  r 
p  p  p  p  .  p  p  p 
.  .  n  .  p  .  .  . 
.  .  .  .  .  .  .  . 
.  .  .  P  P  .  .  . 
.  .  .  .  .  N  .  . 
P  P  P  .  .  P  P  P 
R  N  B  Q  K  B  .  R 
</code>

We are playing black side.

<h2>output</h2>
output maybe like follow:
<code>
11:35:28.151 - info - batch_run depth=1
11:35:28.155 - info - KNIGHT g8f6 -6.0
11:35:28.155 - info - KNIGHT g8e7 -6.5
11:35:28.155 - info - BISHOP f8d6 -9.0
11:35:28.862 - info - batch_run depth=2
11:35:28.862 - info - BISHOP f8b4 11.5
11:35:28.863 - info - QUEEN d8f6 11.0
11:35:28.863 - info - KNIGHT g8f6 7.0
11:35:35.671 - info - batch_run depth=3
11:35:35.671 - info - KNIGHT g8f6 -6.0
11:35:35.671 - info - BISHOP f8b4 -10.0
11:35:35.671 - info - QUEEN d8f6 -10.5
11:36:28.222 - info - batch_run depth=4
11:36:28.223 - info - QUEEN d8f6 15.5
11:36:28.223 - info - KNIGHT g8f6 11.0
11:36:28.223 - info - BISHOP f8b4 6.5
</code>

<h2>output explain</h2>

The program is to search from shallow to deep.

The <code>batch_run depth=1</code> means now search to the 1st layer.

The <code>KNIGHT g8f6 -6.0</code> in output means KNIGHT from g8 to f6 is the best move found in the current layer. And it's score is -6.0.

The program will show 3 "best moves". So you will see 3 recommended moves, then go to next layer, until <code>lee.Data.depth</code>

Start from the 3rd floor program will do cut, means remove some obviously bad moves for save CPU and RAM resources.

You can modify depth, width and pool size in <code>lee.Data</code> to suit your own CPU and RAM.

The program does not always perform well. It is just a simple small program, with performance equivalent to 1800 points of chess.com.
