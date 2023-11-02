
//Melanie Prettyman
package Rooms;

import Game.Adventure;
import Items.Item;

public class ClassRoom extends Room{

    public ClassRoom(String name, String description) {
        super(name, description);
        Item keyCard = new Item( "Key-Card", "U of U student ID. To use type 'use Key-Card' ");
        items_.add( keyCard );
        Item plaque = new Item( "Plaque", "MSD-Classroom created by Melanie Prettyman. Classroom interacts with the Lounge, created by Aiden Pratt.\n"
        + "To win pick up Dave from the Lounge and release him into the MSD-Classroom.");
        items_.add( plaque );
    }

    @Override
    public void playerEntered() {
            System.out.println( "As you step into the classroom, an unfamiliar figure stands at the podium, wearing an uncharacteristically stern expression.\n"
                    + "The person, who bears an uncanny resemblance to Professor Dav, turns abruptly toward you. With a sly grin, they mutter, \n"
                 +"'Four hours of struggle? Brace for more. I won't be lending a hand! No extensions either. Your assignment's now due in 10 minutes!'\n"
                    + "It's clear – this must be Dav's mischievous evil twin! Your mission: uncover the real Dav and return him to the classroom to salvage your grades!\n" );

    }

    @Override
    public boolean handleCommand( String[] subcommands ) {

        if( subcommands.length <= 1 ) {
            return false;
        }
        String cmd  = subcommands[0];
        String attr = subcommands[1];

        // has Dave, use
        if( cmd.equals( "release" ) && attr.equals( "Dav") ) {

            boolean hasDave = false;
            for( Item item : Adventure.inventory ) {
                if( item.getName().equals( "Dav" ) ) {
                    hasDave = true;
                    break;
                }
            }
            if( hasDave ) {
                System.out.println( "Amidst the academic standoff, both Dav's, distinct yet identical, observed as you, undeterred by the twin's enigmatic challenge, \n"+
                        "embarked on a quest. With swift wit and collaborative insight, you discern the authentic Dav, and the imposter is vanquished!\n"+
                        "The real Dav resumes his position at the classroom podium, and turns to you and says ‘Well done! Your resourcefulness and cooperation led to the liberation\n"+
                        "of the real me.You have restored tranquility to the classroom. Thanks for untangling the 'web' of mystery surrounding the imposter - quite a 'server'-ing\n"+
                        " achievement in a classroom filled with 'pseudo' personalities!’\n");
            }
            else {
                System.out.println( "Dav’s evil twin erupts with an evil laughter and says to you ‘You seek the authentic Dav? Ha! He's entangled in a web of server confusion.\n"
                        +"Without his insight, your plight shall persist. Find your way if you dare, for Dav's aid won't be found here!'\n" );
            }
            return true;
        }
        return false;
    }
}
