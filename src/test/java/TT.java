import lee.DataUtil;
import lee.SearchUtil;
import org.junit.Test;

public class TT {

    @Test
    public void aaaa() {
        DataUtil.init("7k/8/8/8/8/8/rr6/7K b - - 4 80");
        System.out.println(SearchUtil.get_score(DataUtil.board));
    }
}