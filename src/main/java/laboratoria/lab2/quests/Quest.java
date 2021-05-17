package laboratoria.lab2.quests;

import java.util.LinkedList;
import java.util.List;

public class Quest{

    /**
     * number to divide
     */
    private int number;

    /**
     * number store to which divisor was counted the number is prime
     */
    private int soFarCounted;

    /**
     * list storing divisors of number
     */
    private List<Long> divisors;


    /**
     * initialize the Quest
     * @param number what number has to have quest
     */
    public Quest( int number ){
        this.number = number;
        this.divisors = new LinkedList<Long>();
        this.soFarCounted = 0;
    }


    /**
     * @return number
     */
    public int get_number(){
        return this.number;
    }


    /**
     * @return so far counted value
     */
    public int get_so_far_counted(){
        return this.soFarCounted;
    }


    /**
     * @return list of divisors
     */
    public List<Long> get_divisors(){
        return this.divisors;
    }


    /**
     * set number variable as newNumber
     * @param newNumber new int value number define quest's number
     */
    public void set_number( int newNumber ){
        this.number = newNumber;
    }

    /**
     * set count for so far counted
     * @param newCount new value for so far counted
     */
    public void set_so_far_counted( int newCount ){
        this.soFarCounted = newCount;
    }


    /**
     * add new divider of number
     * @param newDividor divider of the number
     */
    public void add_divider( long newDividor ){

        synchronized (this){
            this.divisors.add( newDividor );
        }
    }
}
