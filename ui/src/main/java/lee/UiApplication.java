package lee;

import com.github.bhlangonijr.chesslib.move.Move;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class UiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UiApplication.class, args);
    }

    @GetMapping("get")
    public Move get(String fen, int depth, int width, int pool_size, int leaf_depth) {
        Data.init(fen);
        Data.depth = depth;
        Data.width = width;
        Data.pool_size = pool_size;
        Data.leaf_depth = leaf_depth;

        System.out.println(Data.game);

        Node root = new Node(Data.game);
        TreeUtil.build_tree(root);

        Move best_move = SearchUtil.search(root);

        return best_move;
    }
}