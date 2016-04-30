package org.hitzmann;

import org.hitzmann.typotree.Tree;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

/**
 * Created by arne on 10.04.16.
 */
public class SpeedTest {

    private final static int LENGTH = 100000;
    private final static int MAX_DISTANCE = 2;


    @Test
    public void testSpeedTypoTree() throws Exception {
        System.out.println("Using TypoTree");
        ArrayList<String> list = readWords();
        Tree ttree = new Tree();
        ttree.addWordList(list);
        ArrayList<String> typos;

        for (int i = 1; i <= MAX_DISTANCE; i++) {
            TimeMeasurement timer = new TimeMeasurement();
            timer.start();
            typos = generateTypos(i, list, LENGTH);
            timer.stop();
            System.out.println("Typos with distance " + i + " generated in " + timer.getMeanDurationInMillis() + " milliseconds");
            timer.reset();

            for (int j = 0; j < typos.size(); j++) {
                timer.start();
                try {
                    assertTrue(ttree.checkWord(typos.get(j), i));
                }catch(AssertionError e){
                    System.out.println("I was wrong with " + typos.get(j) + " being in distance " + i);
                    throw new AssertionError();
                }
                timer.stop();
            }

            System.out.println("Checking " + LENGTH + " words in a dictionary of " + list.size() + " words with a distance of " + i + "\ntook " + timer.getTotalTimeInMillis() +
                    " milliseconds in total with a meantime of " + timer.getMeanDurationInNanos() + " nano seconds per word");

        }

    }

    @Test
    public void testSpeedRosettaCode() throws Exception {
        System.out.println("Using Rosetta Levenshtein Distance Algorithm");
        ArrayList<String> list = readWords();
        ArrayList<String> typos;

        for (int i = 1; i <= MAX_DISTANCE; i++) {
            TimeMeasurement timer = new TimeMeasurement();
            timer.start();
            typos = generateTypos(i, list, LENGTH <= 100 ? LENGTH : 100);
            timer.stop();
            System.out.println("Typos with distance " + i + " generated in " + timer.getMeanDurationInMillis() + " milliseconds");
            timer.reset();

            for (int j = 0; j < typos.size(); j++) {
                timer.start();
                assertTrue(LevenshteinRosetta.checkWord(typos.get(j), list, i));
                timer.stop();
            }

            int amount = LENGTH <= 100 ? LENGTH : 100;
            System.out.println("Checking " + amount + " words in a dictionary of " + list.size() + " words with a distance of " + i + "\ntook " + timer.getTotalTimeInMillis() +
                    " milliseconds in total with a meantime of " + timer.getMeanDurationInNanos() + " nano seconds per word");

        }

    }

    private ArrayList<String> readWords() {

        ArrayList<String> ret = new ArrayList<String>();

        try (Stream<String> stream = Files.lines(Paths.get("/usr/share/dict/ngerman"))) {

            stream.forEach(line -> ret.add(line));

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Something is wrong with the ngerman wordlist. Please install wngerman package");
        }

        return ret;
    }

    private ArrayList<String> generateTypos(int distance, ArrayList<String> list, int amount) {
        ArrayList<String> ret = new ArrayList<String>();
        StringBuilder sb;
        Random rand = new Random(0);

        for (int a = 0; a < amount;) {

            String word;
            do {
                word = list.get(rand.nextInt(list.size()));
            } while (word.length() < distance);

            sb = new StringBuilder(word);

            if (sb.length() > 1) {
                for (int i = 0; i < distance; i++) {

                    int r = rand.nextInt(3);
                    int pos;
                    if (sb.length() > 1) {
                        pos = rand.nextInt(sb.length() - 1);
                    } else {
                        pos = sb.length() - 1;
                    }

                    switch (r) {
                        case 0:
                            sb.deleteCharAt(pos);
                            break;
                        case 1:
                            sb.insert(pos, (char) (48 + rand.nextInt(47)));
                            break;
                        case 2:
                            sb.replace(pos, pos, Character.toString((char) (48 + rand.nextInt(47))));
                            break;
                    }
                }
            }

            if(LevenshteinRosetta.distance(sb.toString(), word) == distance && !sb.toString().isEmpty()){
                ret.add(sb.toString());
                a++;
            }

        }

        return ret;
    }
}
