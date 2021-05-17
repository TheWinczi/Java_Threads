package laboratoria.lab2;

import laboratoria.lab2.quests.Quest;
import laboratoria.lab2.resources.Resource;
import laboratoria.lab2.results.Results;
import laboratoria.lab2.collectioner.Collectioner;
import laboratoria.lab2.worker.Worker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ThreadsApp {

    /**
     * main function
     * @param args stores arguments for application
     *             there is only one -f=_ where "_" is integer
     *             defines how many threads have to be used
     */
    public static void main(String[] args){

        ThreadsApp app = new ThreadsApp();
        try {
            app.start( args[0] );
        }
        catch (InterruptedException | FileNotFoundException ex) {
            ;
        }
    }


    /**
     * start application
     * @param args argument define how many threads have to be use
     * @throws InterruptedException
     */
    private void start(String args) throws InterruptedException, FileNotFoundException {

        int threadsNb = Integer.parseInt(args.substring(3));    // take number of threads

        File inputFile = new File("C:\\Users\\Maciej WINczewski\\Desktop\\PT_Lab2\\src\\main\\resources\\input.txt");
        String outputFilePath = "C:\\Users\\Maciej WINczewski\\Desktop\\PT_Lab2\\src\\main\\resources\\output.txt";
        Scanner in = new Scanner( inputFile );

        Results results = new Results( outputFilePath );        // create Results
        Resource resource = new Resource( results );            // create Resource

        List<Thread> workers = new LinkedList<>();              // create list of threads named Workers

        // create workers
        inform_user("Creating " + threadsNb + " threads", 400); // inform user
        for( int i = 0; i < threadsNb; i++ ) {
            Thread t = new Thread( new Worker(resource, i) );
            workers.add(t);
        }

        // Start all dividers
        inform_user("Starting " + threadsNb + " threads", 400); // inform user
        for( Thread t : workers )
            t.start();


        // Start collectioner
        inform_user("Starting 1 collectioner", 400);
        Thread t = new Thread( new Collectioner(results) );
        t.start();
        workers.add( t ); // add collectioner to workers


        System.out.println("Initializating start numbers form file");
        while( in.hasNext() )
            resource.putQuest( new Quest( in.nextInt() ) );

        System.out.println("\nAPPLICATION STARTED\n");

        listen_to_user( workers, resource, results );
        inform_user("Kończę dzialanie", 0);
    }


    /**
     * listen to user and doing quests
     * @param workers list of threads
     * @param resource resource with quests
     * @throws InterruptedException
     */
    void listen_to_user( List<Thread> workers, Resource resource, Results results ) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);   // create Scanner

        int number;                     // number to quest from user
        while( true )
        {
            // show_menu();                        // show menu
            String choice = scanner.nextLine();     // take choice from user

            if( "exit".equals(choice) ) {
                close_threads( workers );
                clear_queue( resource, results );
                break;
            }
            else if( "add".equals(choice) ){
                System.out.println("Number to add: ");    // take number from user
                number = scanner.nextInt();                         // read number
                if( number < 0 ) {
                    System.out.println("you cannot add negative numbers");
                    continue;
                }

                Quest newQuest = new Quest(number);                 // create new quest
                resource.putQuest( newQuest );                      // add quest to resource to do
                System.out.println("Number added");
            }
        }
    }


    /**
     * close threads in list threads
     * @param threads list storing threads to close
     * @throws InterruptedException
     */
    private void close_threads( List<Thread> threads ) throws InterruptedException{

        for (Thread thread : threads) {
            thread.interrupt();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                ;
            }
        }
    }

    /**
     * clear queue of quests to do
     * @param resource queue of quest
     * @throws InterruptedException
     */
    private void clear_queue( Resource resource, Results results ) throws InterruptedException {
        try{
            results.close_file();
            resource.clearQueue();
        } catch (InterruptedException ex){
            ;
        }
    }


    /**
     * show menu for user
     */
    private void show_menu(){

        System.out.println("------ OPTIONS ------");
        System.out.println("| \"add\" add number  |");
        System.out.println("| \"exit\" exit       |");
        System.out.println("---------------------");

    }


    /**
     * Show message in console for user
     * @param message message to show
     * @param delay how much time has to wait
     */
    private void inform_user(String message, int delay) throws InterruptedException{
        System.out.println( message );

        if( delay >= 0 )
            Thread.sleep( delay );
    }
}

