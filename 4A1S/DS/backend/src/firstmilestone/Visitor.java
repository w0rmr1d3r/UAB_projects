package firstmilestone;

/**
 * Visitor interface for TimeTracker.
 *
 * @author Ramon 1400214, David 1391968
 *
 */
public interface Visitor {

    /**
     * Visits a Task.
     *
     * @param taskVisited task to visit
     *
     * @throws WrongParamsException precondition of params
     */
    void visit(Task taskVisited) throws WrongParamsException;

    /**
     * Visits a Project.
     *
     * @param projectVisited project to visit
     *
     * @throws WrongParamsException precondition of params
     */
    void visit(Project projectVisited) throws WrongParamsException;

    /**
     * Visits a Period.
     *
     * @param periodVisited period to visit
     */
    void visit(Period periodVisited);
}
