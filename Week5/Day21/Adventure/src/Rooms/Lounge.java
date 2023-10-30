//Aiden Pratt

package Rooms;

import Game.Adventure;
import Rooms.Room;
import Items.Item;

public class Lounge extends Room {

    private boolean locked_ = true;

    public Lounge() {
        super("Lounge", "A small, dark room.");

        Item dave = new Item("Dave", "A petrified professor. To use type 'release Dave'");
        items_.add(dave);
    }
    @Override
    public Room goThroughDoor(int doorNum) {

        if( locked_ ) {
            System.out.println( "The door is locked!" );
            return null;
        }
        else {
            return super.goThroughDoor( doorNum );
        }
    }

    @Override
    public void playerEntered() {
        System.out.println("It is dark, you hear a faint voice reciting pseudo code for a web server.");
    }

    @Override
    public boolean handleCommand( String[] subcommands ) {

        if( subcommands.length <= 1 ) {
            return false;
        }
        String cmd  = subcommands[0];
        String attr = subcommands[1];

        // unlock, use
        if( cmd.equals( "use" ) && attr.equals( "Key-Card") ) {

            boolean hasKeyCard = false;
            for( Item item : Adventure.inventory ) {
                if( item.getName().equals( "Key-Card" ) ) {
                    hasKeyCard = true;
                    break;
                }
            }
            if( hasKeyCard ) {
                System.out.println( "The lounge is unlocked.");
                locked_ = false;
            }
            else {
                System.out.println( "You don't have a key card. you are stuck in the lounge." );
            }
            return true;
        }
        return false;
    }




}