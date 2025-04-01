package Commands;


/**
 * Interface for all game commands.
 * Each command must have the execute method, which handles the action logic,
 * and the exit method, which indicates whether the command ends the game.
 */

public interface Command {

 String execute();
 boolean exit();


}
