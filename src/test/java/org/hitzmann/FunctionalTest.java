package org.hitzmann;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.hitzmann.typotree.Tree;

/**
 * Unit test for simple Main.
 */
public class FunctionalTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public FunctionalTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( FunctionalTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {

        Tree typo = new Tree();
        typo.addWord("facebook");
        typo.addWord("folder");
        typo.addWord("home");

        assertTrue(typo.checkWord("facebook",1));
        assertTrue(typo.checkWord("folder",1));
        assertTrue(typo.checkWord("home",1));


        assertTrue(typo.checkWord("fxacebook",1));
        assertTrue(typo.checkWord("facebooxk",1));
        assertTrue(typo.checkWord("facbook",1));
        assertTrue(typo.checkWord("facebok",1));

        assertTrue(typo.checkWord("folde",1));
        assertTrue(typo.checkWord("older",1));
        assertTrue(typo.checkWord("fxolder",1));
        assertTrue(typo.checkWord("folderx",1));


        assertTrue(typo.checkWord("ome",1));


        assertFalse(typo.checkWord("fabook",1));
        assertFalse(typo.checkWord("cebook",1));
        assertFalse(typo.checkWord("facebo",1));
        assertFalse(typo.checkWord("fcebok",1));
        assertFalse(typo.checkWord("facexxbook",1));
        assertFalse(typo.checkWord("facebookxx",1));


    }
}
