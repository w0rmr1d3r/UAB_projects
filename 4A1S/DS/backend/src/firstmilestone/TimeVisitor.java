package firstmilestone;

import java.util.Observable;
import java.util.Observer;

/**
 * TimeVisitor - Implements Visitor.
 * Used to show the time in a table.
 *
 * @author Ramon 1400214, David 1391968
 *
 */
public class TimeVisitor implements Visitor, Observer {

    /**
     * Root project.
     */
    private Project rootProject;

    /**
     * Constructor of TimeVisitor.
     *
     * @param newRootProject root project of the time tracker
     *
     * @throws WrongParamsException precondition of params
     */
    public TimeVisitor(final Project newRootProject)
            throws WrongParamsException {
        if (newRootProject == null) {
            throw new WrongParamsException("Root project cannot be null");
        }
        this.rootProject = newRootProject;
        //MyLogger.info("TimeVisitor created");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Task taskVisited) throws WrongParamsException {
        if (taskVisited == null) {
            throw new WrongParamsException("Task cannot be null");
        }
        //MyLogger.info("TimeVisitor visiting Task");
        TimePrinter tp = TimePrinter.getInstance();
        System.out.println(
                taskVisited.getName()
                + "   " + tp.printDate(taskVisited.getCreationDate())
                + "   " + tp.printDate(taskVisited.getStartingDate())
                + "   " + tp.printDate(taskVisited.getEndDate())
                + "   " + tp.printTotalTime(taskVisited.getTotalTime())
            );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Project projectVisited)
            throws WrongParamsException {
        if (projectVisited == null) {
            throw new WrongParamsException("Project cannot be null");
        }
        //MyLogger.info("TimeVisitor visiting Project");
        if (projectVisited.getParentProject() != null) {
            TimePrinter tp = TimePrinter.getInstance();
            System.out.println(
                    projectVisited.getName()
                    + "   " + tp.printDate(projectVisited.getCreationDate())
                    + "   " + tp.printDate(projectVisited.getStartingDate())
                    + "   " + tp.printDate(projectVisited.getEndDate())
                    + "   " + tp.printTotalTime(projectVisited.getTotalTime())
                );
        }
        for (Work subWork : projectVisited.getSubWorkList()) {
            try {
                subWork.accept(this);
            } catch (WrongParamsException e) {
                MyLogger.severe(e.getMessage());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Period periodVisited) {
        MyLogger.severe("TIMEVISITOR VISITED PERIOD --- NOT IMPLEMENTED");
    }

    /**
     * Shows the table of times.
     *
     * @throws WrongParamsException precondition of params
     */
    public void showTable() throws WrongParamsException {
        System.out.println("");
        System.out.println("Nom  Data creacio            Data inici              Data final             Durada");
        System.out.println("----+-----------------------+----------------------+-----------------------+------");
        this.rootProject.accept(this);
        System.out.println("");
    }

    /**
     * Update function from Observer.
     *
     * @param clock clock from where the period is notified
     * @param time new time must be casted to long
     */
    @Override
    public final void update(final Observable clock, final Object time) {
        try {
            if (clock == null || time == null || (long) time < 0) {
                throw new WrongParamsException();
            }
            //MyLogger.info("TimeVisitor update");
            this.showTable();
        } catch (WrongParamsException e) {
           MyLogger.severe(e.getMessage());
        }
    }
}
