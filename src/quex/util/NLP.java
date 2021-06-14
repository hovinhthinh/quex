package quex.util;

import edu.knowitall.tool.tokenize.ClearTokenizer;
import edu.knowitall.tool.tokenize.Token;
import scala.collection.JavaConversions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NLP {
    public static String stripSentence(String sentence) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sentence.length(); ++i) {
            char c = sentence.charAt(i);
            if (c == 160 || Character.isWhitespace(c)) {
                continue;
            }
            if (sb.length() != 0) {
                sb.append(' ');
            }
            int j = i;
            do {
                if (j >= sentence.length()) {
                    break;
                }
                c = sentence.charAt(j);
                if (c == 160 || Character.isWhitespace(c)) {
                    break;
                }
                sb.append(c);
                ++j;
            } while (true);
            i = j;
        }
        return sb.toString();
    }

    public static ArrayList<String> splitSentence(String sentence) {
        ArrayList<String> arr = new ArrayList<>();
        for (int i = 0; i < sentence.length(); ++i) {
            char c = sentence.charAt(i);
            if (c == 160 || Character.isWhitespace(c)) {
                continue;
            }
            StringBuilder sb = new StringBuilder();
            int j = i;
            do {
                if (j >= sentence.length()) {
                    break;
                }
                c = sentence.charAt(j);
                if (c == 160 || Character.isWhitespace(c)) {
                    break;
                }
                sb.append(c);
                ++j;
            } while (true);
            i = j;
            arr.add(sb.toString());
        }
        return arr;
    }

    static ClearTokenizer TOKENIZER = new ClearTokenizer();

    public static final ArrayList<String> tokenize(String text) {
        text = NLP.stripSentence(text);
        List<Token> tokens = JavaConversions.seqAsJavaList(TOKENIZER.tokenize(text));
        return tokens.stream().map(o -> o.string()).collect(Collectors.toCollection(ArrayList::new));
    }
}
