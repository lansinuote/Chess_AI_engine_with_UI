package lee;

public class Main {

    public static void main(String[] args) {
        String fen = args[0];

        System.out.println("fen=" + fen);

        DataUtil.init(fen);

        System.out.println(DataUtil.board);
        System.out.println("depth=" + DataUtil.depth);
        System.out.println("pool_size=" + DataUtil.pool_size);

        long s = System.currentTimeMillis();
        ThreadUtil.batch_run();
        System.out.println("time cost = " + (System.currentTimeMillis() - s));

        ThreadUtil.print_best_move();
    }
}