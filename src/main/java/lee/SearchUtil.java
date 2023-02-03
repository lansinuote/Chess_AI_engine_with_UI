package lee;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

public class SearchUtil {

    private static String get_result(Board board) {
        if (board.isMated()) {
            if (board.getSideToMove() == DataUtil.color) {
                return "loss";
            }
            return "win";
        }

        if (board.isDraw()) {
            return "draw";
        }

        return null;
    }

    private static String get_check(Board board) {
        if (board.squareAttackedBy(board.getKingSquare(DataUtil.color.flip()), DataUtil.color) > 0L) {
            return "check";
        }

        if (board.squareAttackedBy(board.getKingSquare(DataUtil.color), DataUtil.color.flip()) > 0L) {
            return "be_check";
        }
        return null;
    }

    public static float get_score(Board board) {
        float score = 0.0F;

        for (int i = 0; i < 64; i++) {
            Piece piece = board.getPiece(Square.squareAt(i));
            if (piece == Piece.NONE) {
                continue;
            }

            float piece_score = DataUtil.piece_score.get(piece.getPieceType());
            float cell_score = DataUtil.cell_score.get(piece.getPieceSide()).get(piece.getPieceType()).get(i);

            if (piece.getPieceSide() == DataUtil.color) {
                score += piece_score + cell_score;
            } else {
                score -= piece_score + cell_score;
            }
        }

        String result = get_result(board);

        if ("win".equals(result)) {
            score += 10000.0F;
        }

        if ("loss".equals(result)) {
            score -= 10000.0F;
        }

        if ("draw".equals(result)) {
            if (score > 0.0F) {
                score -= 10000.0F;
            } else {
                score += 10000.0F;
            }
        }

        String check = get_check(board);

        if ("check".equals(check)) {
            score += 5.00F;
        }

        if ("be_check".equals(check)) {
            score -= 5.00F;
        }

        return score;
    }

    public static float search(Board board, int depth) {
        if (depth == 0) {
            return get_score(board);
        }

        boolean me = board.getSideToMove() == DataUtil.color;
        float best_score = Float.MAX_VALUE;
        if (me) {
            best_score = -best_score;
        }

        for (Move move : board.legalMoves()) {
            board.doMove(move);
            float score = search(board, depth - 1);
            board.undoMove();

            if (me && score > best_score) {
                best_score = score;
            }

            if (!me && score < best_score) {
                best_score = score;
            }
        }

        return best_score;
    }
}
