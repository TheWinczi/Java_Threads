package laboratoria.lab2.collectioner;

import laboratoria.lab2.quests.Quest;
import laboratoria.lab2.results.Results;

public class Collectioner implements Runnable{

    private Results results;

    /**
     * initialize the object
     * @param results results storing done quests
     */
    public Collectioner(Results results){
        this.results = results;
    }


    @Override
    public void run() {

        while(!Thread.interrupted()){
            try{
                Quest result = this.results.get();
                this.results.save_to_file( result );

            }catch( InterruptedException ex ) {
                break;
            }
        }
    }
}
