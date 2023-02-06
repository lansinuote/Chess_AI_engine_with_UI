package lee;

import com.github.bhlangonijr.chesslib.PieceType;
import com.github.bhlangonijr.chesslib.move.Move;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static void best_moves(List<Node> children) {
        for (Node node : children.stream().filter(i -> "win".equals(i.get_result())).collect(Collectors.toList())) {
            Move move = node.game.undoMove();
            node.game.doMove(move);
            PieceType pieceType = Data.game.getPiece(move.getFrom()).getPieceType();
            LogUtil.info("checkmate!! --> " + pieceType + " " + move);
        }

        children = children.stream().sorted((i1, i2) -> Float.compare(i1.score, i2.score)).collect(Collectors.toList());
        Collections.reverse(children);

        for (int i = 0; i < Math.min(children.size(), 3); i++) {
            Node node = children.get(i);
            Move move = node.game.undoMove();
            node.game.doMove(move);
            PieceType pieceType = Data.game.getPiece(move.getFrom()).getPieceType();
            LogUtil.info(pieceType + " " + move + " " + node.score);
        }
    }

    public static void main(String[] args) {
        String fen = args[0];
        Data.init(fen);

        System.out.println(Data.game);

        Node root = new Node(Data.game);
        TreeUtil.build_tree(root);

        for (int depth = 1; depth <= Data.depth; depth++) {
            ThreadUtil.batch_run(root.children);

            LogUtil.info("batch_run depth=" + depth);

            int cut_depth = depth - 2;
            if (cut_depth >= 0) {
                int cut_width = (int) Math.ceil(Data.width / Math.pow(2, cut_depth));
                cut_width = Math.max(cut_width, 3);
                TreeUtil.cut_children(root, cut_depth, cut_width);

                LogUtil.debug("cut_children  cut_depth=" + cut_depth + " cut_width=" + cut_width);
            }

            TreeUtil.print_children_size(root, 0);

            best_moves(root.children);
        }
    }
}