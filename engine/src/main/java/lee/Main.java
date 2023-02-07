package lee;

import com.github.bhlangonijr.chesslib.move.Move;

public class Main {

    public static void main(String[] args) {
        String fen = args[0];
        Data.init(fen);

        System.out.println(Data.game);

        Node root = new Node(Data.game);
        TreeUtil.build_tree(root);

        Move best_move = SearchUtil.search(root);

        LogUtil.debug("best_move -> " + best_move);
    }
}