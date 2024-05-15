import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HelloGoodbye {
    public static void main(String[] args) {
        List<String> words = Arrays.asList(args);
        System.out.print("Hello " + args[0] + " and " + args[1] + ".");

        System.out.println();
        System.out.print("Goodbye " + args[1] + " and " + args[0] + ".");
    }
}
