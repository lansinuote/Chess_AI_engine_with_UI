package lee;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;

import java.util.Collections;
import java.util.stream.Collectors;

public class TreeUtil {

    public static void build_tree(Node node) {
        if (node.get_result() != null) {
            return;
        }

        if (!node.children.isEmpty()) {
            for (Node i : node.children) {
                build_tree(i);
            }
            return;
        }

        for (Move i : node.game.legalMoves()) {
            Board child = node.game.clone();
            child.doMove(i);
            node.children.add(new Node(child));
        }
    }

    public static void print_tree(Node node, String prefix) {
        System.out.println(prefix + " " + node.score + " " + (node.game.getSideToMove() != Data.color ? "me" : "op"));
        prefix += "-";
        for (Node i : node.children) {
            print_tree(i, prefix);
        }
    }

    public static void set_tree_score(Node node) {
        for (Node i : node.children) {
            set_tree_score(i);
        }

        node.set_score();
    }

    public static void sort_tree(Node node) {
        if (node.children.isEmpty()) {
            return;
        }

        node.children = node.children.stream().sorted((i1, i2) -> Float.compare(i1.score, i2.score)).collect(Collectors.toList());

        for (Node i : node.children) {
            sort_tree(i);
        }
    }

    public static void cut_children(Node node, int depth, int width) {
        if (depth < 0 || node.children.isEmpty()) {
            return;
        }

        node.children = node.children.stream().sorted((i1, i2) -> Float.compare(i1.score, i2.score)).collect(Collectors.toList());

        if (node.game.getSideToMove() == Data.color) {
            Collections.reverse(node.children);
        }

        node.children = node.children.subList(0, Math.min(width, node.children.size()));

        width = width * 2;
        width = Math.min(width, 16);
        for (Node i : node.children) {
            cut_children(i, depth - 1, width);
        }
    }

    public static void cut_layer(Node node, int layer, int cut_layer, int width) {
        if (layer != cut_layer) {
            for (Node i : node.children) {
                cut_layer(i, layer + 1, cut_layer, width);
            }
        }

        if (node.children.isEmpty()) {
            return;
        }

        node.children = node.children.stream().sorted((i1, i2) -> Float.compare(i1.score, i2.score)).collect(Collectors.toList());

        if (node.game.getSideToMove() == Data.color) {
            Collections.reverse(node.children);
        }

        node.children = node.children.subList(0, Math.min(width, node.children.size()));
    }

    public static void print_children_size(Node node, int depth) {
        System.out.println(depth + " - " + node.children.size());
        if (!node.children.isEmpty()) {
            print_children_size(node.children.get(0), depth + 1);
        }
    }
}