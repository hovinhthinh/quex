package quex;

import edu.illinois.cs.cogcomp.annotation.AnnotatorServiceConfigurator;
import edu.illinois.cs.cogcomp.quant.driver.QuantSpan;
import edu.illinois.cs.cogcomp.quant.driver.Quantifier;
import quex.model.Quantity;
import quex.util.NLP;

import java.util.Scanner;

// Now using IllinoisQuantifier.
// TODO: Problem: "Google pays $ 17 million compensation over privacy breach .": compensation is detected in the
// quantity span.
// TODO: Problem: "Paris still has more than 2,000 troops deployed in Mali .": troops are in both quantity and context.
// TODO: Completed in 2010 , the Zifeng Tower in Nanjing has an architectural height of 1,476 feet ( 450 meters ) and is occupied to a height of 1,039 feet ( 316.6 meters ) .
// TODO: In 2013 , SBB Cargo had 3,061 employees and achieved consolidated sales of CHF 953 million .
public class Main {
    public static void main(String[] args) throws Exception {
        Quantifier ILLINOIS_QUANTIFIER  = new Quantifier();
        ILLINOIS_QUANTIFIER.initialize(null);
        AnnotatorServiceConfigurator.DISABLE_CACHE.value = "true";

        Scanner sc = new Scanner(System.in);
        String input;
        do {
            System.out.print("INPUT >> ");
            input = sc.nextLine();
            try {
                input = String.join(" ", NLP.tokenize(input));
                for (QuantSpan span : ILLINOIS_QUANTIFIER.getSpans(input, true, null)) {
                    if (span.object instanceof edu.illinois.cs.cogcomp.quant.standardize.Quantity) {
                        if (!Quantity.fixQuantityFromIllinois(span, input)) {
                            continue;
                        }
                        edu.illinois.cs.cogcomp.quant.standardize.Quantity q = (edu.illinois.cs.cogcomp.quant.standardize.Quantity) span.object;

                        System.out.println("================");

                        System.out.println(q.phrase);
                        Quantity qt = new Quantity(q.value, q.units, q.bound);
                        System.out.println(qt.resolution + " " + qt.value + " " + qt.unit + "(" + qt.getKgUnit().entity + ") | " + qt.getKgUnit().getDomain());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (true);

    }

}
