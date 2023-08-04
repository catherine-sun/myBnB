import java.util.ArrayList;
import java.util.Properties;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.PropertiesUtils;
import edu.stanford.nlp.ling.IndexedWord;

public class NpParser {

    public static ArrayList<NPhrase> parseNounPhrase(String text) {
        // set list of annotators to run
        Properties prop = PropertiesUtils.asProperties("annotators", "tokenize, ssplit, pos, lemma, parse");
        // build pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP(prop);
        // create document object
        CoreDocument document = new CoreDocument(text);
        //annotate the document
        pipeline.annotate(document);

        ArrayList<NPhrase> nPhrases = new ArrayList<>();

        // iterate through sentences
        for (CoreSentence sentence : document.sentences()) {

            SemanticGraph dependencyParse = sentence.dependencyParse();
            Tree tree = sentence.constituencyParse();

            for (IndexedWord i : dependencyParse.vertexListSorted()) {
                // found a noun
                if (i.tag().startsWith("NN")) {

                    // get leaf for the noun
                    Tree npTree = tree.getLeaves().get(i.index() - 1);
                    String nPhrase = getNounPhrase(npTree, tree);

                    NPhrase np = new NPhrase(nPhrase, 1);
                    if (nPhrases.contains(np)) {
                        nPhrases.get(nPhrases.indexOf(np)).incrementCount();
                    } else {
                        nPhrases.add(np);
                    }
                }
            }
        }

        return nPhrases;
    }


    private static String getNounPhrase(Tree npTree, Tree tree) {
        if (npTree == null) {
            return "";
        }
        if (npTree.label().value().startsWith("NP")) {
            return npTree.spanString();
        }
        return getNounPhrase(npTree.parent(tree), tree);
    }

}
