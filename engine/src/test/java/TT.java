import lee.Data;
import lee.Main;
import lee.Node;
import org.junit.Test;

public class TT {

    @Test
    public void aa() {
        //7k/8/8/8/8/8/rr6/6K1 b - - 4 79
        Main.main(new String[]{"7k/8/8/8/8/8/rr6/6K1 b - - 4 79"});
    }

    @Test
    public void bb(){
        Data.init("1rbr2k1/1pppnpp1/p1n1p2p/4Pq2/2PP4/PQ2BN1P/2P1BPP1/R4RK1 b - - 1 14");
        Node node = new Node(Data.game);
        node.set_score();
    }
}