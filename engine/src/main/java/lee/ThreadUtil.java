package lee;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadUtil {

    public static class MyRunnable implements Runnable {
        private Node node;

        public MyRunnable(Node node) {
            this.node = node;
        }

        @Override
        public void run() {
            TreeUtil.build_tree(node);
            TreeUtil.set_tree_score(node);
        }
    }

    public static void batch_run(List<Node> children) {
        if (children.size() < Data.pool_size) {
            for (Node i : children) {
                batch_run(i.children);
                i.set_score();
            }

            return;
        }

        LogUtil.debug("build thread pool size=" + children.size());

        ExecutorService pool = Executors.newFixedThreadPool(children.size());

        for (Node i : children) {
            pool.submit(new MyRunnable(i));
        }

        pool.shutdown();
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
