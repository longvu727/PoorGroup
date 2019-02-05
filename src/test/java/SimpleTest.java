import com.intellij.ide.ui.EditorOptionsTopHitProvider;
import org.apache.commons.net.imap.IMAPClient;
import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

public class SimpleTest {

    boolean _DEBUG = true;
    @Test
    public void test() {
        PoorGroup_new poorGroup = new PoorGroup_new("1");
        poorGroup._DEBUG = this._DEBUG;

        poorGroup.addMember(new Member("A", 5));
        poorGroup.addMember(new Member("C", 6));
        poorGroup.addMember(new Member("L", 37));
        poorGroup.addMember(new Member("V", 37));

        poorGroup.startLoggingMemberList10Times("/tmp/test1", "/tmp/test2");

        int test1LineCount = countLineInFile("/tmp/test1");
        int test2LineCount = countLineInFile("/tmp/test2");

        truncateFile("/tmp/test1");
        truncateFile("/tmp/test2");

        Assert.assertTrue( test1LineCount == 40 );
        Assert.assertTrue( test2LineCount == 40 );
    }

    private void truncateFile( String fileName ) {
        try {
            PrintWriter writer = new PrintWriter(fileName);
            writer.print("");
            writer.close();
        }
        catch (Exception e){
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }

    private int countLineInFile( String fileName ) {
        int lines = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String str;
            while ( (str = reader.readLine()) != null){
                lines++;
            }
            reader.close();
        }
        catch (Exception e){
            e.printStackTrace();
            Assert.assertTrue(false);
        }

        if(this._DEBUG) System.out.println(lines);

        return lines;
    }
}