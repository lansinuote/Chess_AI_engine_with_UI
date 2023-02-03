environment:<code>java:8</code>

<hr>

build:
<code>mvn package</code>

<hr>

run:
<code>java -jar target/search_chess_next_move-1.0-SNAPSHOT.jar "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1"</code>

<hr>

output:
<code>
...
KNIGHT b8c6 9.0
PAWN e7e5 8.0
PAWN d7d5 7.5
</code>

<hr>

The <code>KNIGHT b8c6 9.0</code> in output means KNIGHT from b8 to c6 is the best move by program found. And it's score is 9.0.

The program will show 3 "best moves". So you will see 3 recommended moves.

You can modify depth and thred pool size in <code>lee.DataUtil</code> to suit your own CPU.

The program does not always perform well. It is just a simple small program, with performance equivalent to 1400 points of chess.com.
