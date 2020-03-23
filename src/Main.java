import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        String strTest1 = "Мама мыла-мыла-мыла раму!";

        String strTest2 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed sodales consectetur purus at faucibus. Donec mi quam, tempor vel ipsum non, faucibus suscipit massa. Morbi lacinia velit blandit tincidunt efficitur. Vestibulum eget metus imperdiet sapien laoreet faucibus. Nunc eget vehicula mauris, ac auctor lorem. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer vel odio nec mi tempor dignissim.";

        BufferedReader bf = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(strTest2.getBytes()), StandardCharsets.UTF_8));

        Stream <String> myStream = bf.lines();
        Map <String,Long> finalRes = new LinkedHashMap<>();
        Map <String,Long> res =
                myStream.map(str ->str.split("[^\\p{L}\\p{Digit}_]+")).flatMap(Arrays::stream).map(String::toLowerCase).sorted().collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));

                res.entrySet().stream().sorted(Map.Entry.<String,Long>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey())).forEachOrdered(x->finalRes.put(x.getKey(),x.getValue()));

                finalRes.entrySet().stream().limit(10).forEachOrdered(x->System.out.println(x.getKey()));

        }

    class MyComparator implements Comparator<String> {
        Map<String, Long> base;

        public MyComparator(Map<String, Long> base) {
            this.base = base;
        }

        // Note: this comparator imposes orderings that are inconsistent with
        // equals.
        public int compare(String a, String b) {
            if (base.get(a) >= base.get(b)) {
                return -1;
            } else {
                return 1;
            } // returning 0 would merge keys
        }
    }
}