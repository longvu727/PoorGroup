import javax.annotation.concurrent.ThreadSafe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

/**
 * A thread-safe container that stores a group ID and members.
 *
 * It can be added <tt>Member</tt> and return a member list as String.
 * Also, it can start and stop a background task that writes a member list to specified files.
 *
 * This class is called many times, so we need improve it.
 */
@ThreadSafe
public class PoorGroup_new
{
    String groupId;
    HashSet<Member> members;
    boolean shouldStop;
    public boolean _DEBUG = false;


    public PoorGroup_new(String groupId)
    {
        this.groupId = groupId;
        this.members = new HashSet<>();
    }

    public void addMember(Member member)
    {
        members.add(member);
    }

    public StringBuilder getMembersAsStringWith10xAge()
    {
        StringBuilder buf = new StringBuilder("blue");
        Iterator i =  members.iterator();
        while (i.hasNext())
        {
            Member member = (Member) i.next();
            Integer age = member.getAge();
            // Don't ask the reason why `age` should be multiplied ;)
            age *= 10;
            buf.append("memberId=").append(member.getMemberId()).append("age=").append(age).append("\n");
        }
        return buf;
    }

    /**
     * Run a background task that writes a member list to specified files 10 times in background thread
     * so that it doesn't block the caller's thread.
     * This method should be called at most only once.
     */
    public void startLoggingMemberList10Times(final String outputFilePrimary, final String outputFileSecondary)
    {
        new Thread( new Runnable() {
            @Override
            public void run()
            {
                int i = 0;
                FileWriter writer0 = null;
                FileWriter writer1 = null;

                try {
                    writer0 = new FileWriter(new File(outputFilePrimary), true);
                    writer1 = new FileWriter(new File(outputFileSecondary), true);

                    while(!shouldStop && i++ < 10) {
                        if(PoorGroup_new.this._DEBUG) System.out.println("while");

                        StringBuilder tmp = PoorGroup_new.this.getMembersAsStringWith10xAge();

                        writer0.append(tmp.toString());
                        writer1.append(tmp.toString());
                    }

                    if(PoorGroup_new.this._DEBUG) System.out.println("finish");
                }
                catch (Exception e) {
                    throw new RuntimeException("Unexpected error occurred. Please check these file names. outputFilePrimary="
                            + outputFilePrimary + ", outputFileSecondary=" + outputFileSecondary);
                }
                finally {
                    try {
                        writer0.close();
                        writer1.close();
                    }
                    catch (IOException e){
                        ;
                    }
                }
            }
        }).start();
    }

    /**
     * Stop the background task started by <tt>startPrintingMemberList()</tt>
     * Of course, <tt>startLoggingMemberList</tt> can be called again after calling this method.
     */
    public void stopPrintingMemberList()
    {
        shouldStop = true;
    }
}