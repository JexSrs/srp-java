import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class test {

    private static final int TIMEOUT = 15 * 1000;

    public static void main(String[] args) throws Exception {
        new test().test_exceptions();
    }

    @Test
    public void test_exceptions() throws Exception {
        System.out.println("Starting test file.");



        new Thread(() -> {
            Assertions.assertDoesNotThrow(() -> Thread.sleep(TIMEOUT));
            System.out.println("Exiting test file");
            System.exit(0);
        }).start();
    }
}
