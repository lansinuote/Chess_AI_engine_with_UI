import lee.Data;
import lee.Main;
import lee.Node;
import org.junit.Test;

public class TT {

    @Test
    public void aa() {
        Main.main(new String[]{"rn3kn1/pp2bq2/2p1r3/4Qp1p/3PP1b1/8/PPPB1PPP/R3KB1R w KQ - 1 17"});
    }

    @Test
    public void aab() {
        Data.init("4r1bk/3B3p/8/4p1p1/2p5/8/P4PPP/2R3K1 b - - 0 34");
        Node node = new Node(Data.game);

        node.set_score();

        System.out.println(node.score);
    }
}