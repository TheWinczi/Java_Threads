package laboratoria.lab2.worker;

import laboratoria.lab2.quests.Quest;
import laboratoria.lab2.resources.Resource;

public class Worker implements Runnable{

    private Resource resource;
    private int ID;

    /**
     * Initialize the object
     * @param resource resorce using by Divider
     * @param ID individual number for all threads
     */
    public Worker( Resource resource, int ID ) {
        this.resource = resource;
        this.ID = ID;
    }


    @Override
    public void run() {

        while(!Thread.interrupted()) {
            try {
                //System.out.println("Rozpoczynam liczenie ");

                Quest toDo = this.resource.takeQuest();
                int number = toDo.get_number();
                int soFar = toDo.get_so_far_counted();
                int i;

                for( i = soFar+1 ; i < soFar + 15 ; i++ ){
                    if( number % i == 0 ){ // i is divider of number
                        toDo.add_divider( i );
                    }
                    if( i >= number ){
                        break;
                    }
                }

                toDo.set_so_far_counted(i);
                this.resource.putQuest( toDo );
            }
            catch (InterruptedException ex){
                break;
            }
        }
    }
}
