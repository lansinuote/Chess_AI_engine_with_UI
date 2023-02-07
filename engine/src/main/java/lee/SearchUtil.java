package lee;

import com.github.bhlangonijr.chesslib.PieceType;
import com.github.bhlangonijr.chesslib.move.Move;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SearchUtil {

    private static Move checkmate(List<Node> children) {
        Move checkmate_move = null;

        for (Node node : children.stream().filter(i -> "win".equals(i.get_result())).collect(Collectors.toList())) {
            Move move = node.game.undoMove();
            node.game.doMove(move);
            PieceType pieceType = Data.game.getPiece(move.getFrom()).getPieceType();
            LogUtil.info("checkmate!! --> " + pieceType + " " + move);

            checkmate_move = move;
        }

        return checkmate_move;
    }

    private static Move best_moves(List<Node> children) {
        Move best_move = null;

        children = children.stream().sorted((i1, i2) -> Float.compare(i1.score, i2.score)).collect(Collectors.toList());
        Collections.reverse(children);

        for (int i = 0; i < Math.min(children.size(), 3); i++) {
            Node node = children.get(i);
            Move move = node.game.undoMove();
            node.game.doMove(move);
            PieceType pieceType = Data.game.getPiece(move.getFrom()).getPieceType();
            LogUtil.info(pieceType + " " + move + " " + node.score);

            if (best_move == null) {
                best_move = move;
            }
        }

        return best_move;
    }

    public static Move search(Node root) {

        Move checkmate_move = null;
        Move best_move = null;

        for (int depth = 1; depth <= Data.depth; depth++) {
            ThreadUtil.batch_run(root.children);

            LogUtil.info("batch_run depth=" + depth);

            int cut_depth = depth - Data.leaf_depth;
            if (cut_depth >= 0) {
                int cut_width = (int) Math.ceil(Data.width / Math.pow(2, cut_depth));
                cut_width = Math.max(cut_width, 3);
                TreeUtil.cut_children(root, cut_depth, cut_width);

                LogUtil.debug("cut_children  cut_depth=" + cut_depth + " cut_width=" + cut_width);
            }

            TreeUtil.print_children_size(root, 0);

            if (checkmate_move == null) {
                checkmate_move = checkmate(root.children);
            }
            best_move = best_moves(root.children);
        }

        return checkmate_move != null ? checkmate_move : best_move;
    }
}
