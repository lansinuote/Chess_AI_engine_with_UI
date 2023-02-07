package lee;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.PieceType;
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

    public void set_score() {
        //set score by children
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

        //piece and cell score
        {
            for (int i = 0; i < 64; i++) {
                Square square = Square.squareAt(i);
                Piece piece = game.getPiece(square);

                //cell under control score.
                if (piece == Piece.NONE) {
                    if (game.squareAttackedBy(square, Data.color) > 0L) {
                        score += 0.2F;
                    }

                    if (game.squareAttackedBy(square, Data.color.flip()) > 0L) {
                        score -= 0.2F;
                    }
                    continue;
                }

                float piece_score = Data.piece_score.get(piece.getPieceType());
                float cell_score = Data.cell_score.get(piece.getPieceSide()).get(piece.getPieceType()).get(i);

                if (piece.getPieceSide() == Data.color) {
                    score += piece_score + cell_score;
                } else {
                    score -= piece_score + cell_score;
                }

                //piece has root or attacking score.
                if (piece.getPieceType() == PieceType.KING) {
                    continue;
                }
                if (game.squareAttackedBy(square, Data.color) > 0L) {
                    score += piece_score * 0.05F;
                }

                if (game.squareAttackedBy(square, Data.color.flip()) > 0L) {
                    score -= piece_score * 0.05F;
                }
            }
        }

        //score by result.
        {
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
        }

        //if check + or - score.
        {
            if (game.squareAttackedBy(game.getKingSquare(Data.color.flip()), Data.color) > 0L) {
                score += 5.00F;
            }

            if (game.squareAttackedBy(game.getKingSquare(Data.color), Data.color.flip()) > 0L) {
                score -= 5.00F;
            }
        }

        //freedom score.
        {
            int move_score_curr = game.legalMoves().size();
            game.setSideToMove(game.getSideToMove().flip());
            int move_score_next = game.legalMoves().size();
            game.setSideToMove(game.getSideToMove().flip());

            if (game.getSideToMove() == Data.color) {
                score += (move_score_curr - move_score_next) * 0.2F;
            } else {
                score -= (move_score_curr - move_score_next) * 0.2F;
            }
        }
    }
}