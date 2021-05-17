package laboratoria.lab2.resources;

import laboratoria.lab2.quests.Quest;
import laboratoria.lab2.results.Results;

import java.util.concurrent.SynchronousQueue;

public class Resource {

    int numberToDivide;
    Results results;
    Quest questToDo;

    /**
     * initialize the object
     */
    public Resource( Results results ) {
        this.numberToDivide = 0;
        this.results = results;
        this.questToDo = null;
    }

    /**
     * put new quest to do
     * @param newQuest new quest to do
     * @throws InterruptedException
     */
    public synchronized void putQuest( Quest newQuest ) throws InterruptedException{

        while( this.questToDo != null )
            wait();

        if( newQuest.get_so_far_counted() < newQuest.get_number() ){
            //System.out.println("Quest nie zostal jeszcze zakonczony");
            this.questToDo = newQuest; // add quest
        }
        else if( newQuest.get_so_far_counted() >= newQuest.get_number() ){
            // System.out.println("Wiadomo juz ile dzielnikow ma liczba " + newQuest.get_number());
            this.results.put( newQuest );
        }

        notifyAll(); // notify all threads
    }


    /**
     * take element from resource
     */
    public synchronized Quest takeQuest() throws InterruptedException{

        while( this.questToDo == null )
            wait();

        // System.out.println("Number " + this.numberToDivide + " have been taken");

        Quest ret = this.questToDo;
        this.questToDo = null;
        return ret;
    }


    /**
     * clear quest to do
     * @throws InterruptedException
     */
    public synchronized void clearQueue() throws InterruptedException {

        this.questToDo = null;
    }
}

