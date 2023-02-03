package lee;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ThreadUtil {

    private static HashMap<Move, Float> resultMap = new HashMap<>();

    private static class MyRunnable implements Runnable {
        private Board board;

        public MyRunnable(Board board) {
            this.board = board;
        }

        @Override
        public void run() {
            float score = SearchUtil.search(board, DataUtil.depth);
            Move move = board.undoMove();
            System.out.println(Thread.currentThread().getName() + " " + move + " " + score);
            resultMap.put(move, score);
        }
    }

    public static void batch_run() {
        ExecutorService pool = Executors.newFixedThreadPool(DataUtil.pool_size);

        for (Move move : DataUtil.board.legalMoves()) {
            Board clone = DataUtil.board.clone();
            clone.doMove(move);
            pool.submit(new MyRunnable(clone));
        }

        pool.shutdown();
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void print_best_move() {
        List<Map.Entry<Move, Float>> collect = resultMap.entrySet().stream().sorted((i1, i2) -> Float.compare(i1.getValue(), i2.getValue())).collect(Collectors.toList());
        Collections.reverse(collect);

        for (int i = 0; i < Math.min(collect.size(), 3); i++) {
            Map.Entry<Move, Float> entry = collect.get(i);
            System.out.println(DataUtil.board.getPiece(entry.getKey().getFrom()).getPieceType() + " " + entry.getKey() + " " + entry.getValue());
        }
    }
}
