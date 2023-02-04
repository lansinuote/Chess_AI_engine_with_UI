package lee;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Node {

    public Board game;
    public Float score;
    public List<Node> children = new ArrayList<>();

    public Node(Board game) {
        this.game = game;
    }

    public String get_result() {
        if (game.isMated()) {
            if (game.getSideToMove() == Data.color) {
                return "loss";
            }
            return "win";
        }

        if (game.isDraw()) {
            return "draw";
        }

        return null;
    }

    public String get_check() {
        if (game.squareAttackedBy(game.getKingSquare(Data.color.flip()), Data.color) > 0L) {
            return "check";
        }

        if (game.squareAttackedBy(game.getKingSquare(Data.color), Data.color.flip()) > 0L) {
            return "be_check";
        }
        return null;
    }

    public void set_score() {
        if (!children.isEmpty()) {
            List<Float> children_score = children.stream().map(i -> i.score).collect(Collectors.toList());

            if (game.getSideToMove() == Data.color) {
                score = children_score.stream().max(Float::compare).get();
            } else {
                score = children_score.stream().min(Float::compare).get();
            }

            return;
        }

        score = 0.0F;

        for (int i = 0; i < 64; i++) {
            Piece piece = game.getPiece(Square.squareAt(i));
            if (piece == Piece.NONE) {
                continue;
            }

            float piece_score = Data.piece_score.get(piece.getPieceType());
            float cell_score = Data.cell_score.get(piece.getPieceSide()).get(piece.getPieceType()).get(i);

            if (piece.getPieceSide() == Data.color) {
                score += piece_score + cell_score;
            } else {
                score -= piece_score + cell_score;
            }
        }

        String result = get_result();

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

        String check = get_check();

        if ("check".equals(check)) {
            score += 5.00F;
        }

        if ("be_check".equals(check)) {
            score -= 5.00F;
        }

        /*if (game.getSideToMove() == Data.color) {
            score += game.legalMoves().size();
        } else {
            score -= game.legalMoves().size();
        }*/
    }
}