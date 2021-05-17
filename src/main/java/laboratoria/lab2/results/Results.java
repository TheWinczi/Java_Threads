package laboratoria.lab2.results;

import laboratoria.lab2.quests.Quest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Results {

    private List<Quest> questsResults;
    private File outputFile;
    private PrintWriter out;
    private Semaphore semaphore;

    /**
     * initialize the object
     */
    public Results(String outFilePath) throws FileNotFoundException {
        this.questsResults = new LinkedList<Quest>();
        this.outputFile = new File(outFilePath);
        this.out = new PrintWriter( this.outputFile );
        this.semaphore = new Semaphore(1);
    }

    /**
     * @return quest from list and remove him
     */
    public Quest get() throws InterruptedException {

        synchronized (this){

            while( this.questsResults.size() <= 0 )
                wait();

            return this.questsResults.remove(0);
        }
    }

    public void close_file(){
        out.close();
    }

    public File get_output_file(){
        return this.outputFile;
    }

    public PrintWriter get_print_writer(){
        return this.out;
    }

    public void save_to_file( Quest quest ) throws InterruptedException {

        this.semaphore.acquire(1);

        if( quest != null ){

            this.out.print(quest.get_number() + ": ");

            for( Long l : quest.get_divisors() ){
                this.out.print(l + ", ");
            }

            out.print('\n');
            out.flush();
        }

        this.semaphore.release(1);
    }

    /**
     * put new quest to list of solved quest
     * @param quest solved quest to add
     */
    public void put( Quest quest ){
        synchronized (this){
            this.questsResults.add( quest );
            // System.out.println("Dodano quest do rezultatow o wyniku: " + quest.get_divisors().size() );
            notifyAll();
        }
    }
}
