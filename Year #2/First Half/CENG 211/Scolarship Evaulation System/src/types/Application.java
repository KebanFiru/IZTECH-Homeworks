package types;

import evaluator.ScholarshipEvaluator;
import model.Applicant;

/**
 * Abstract base class representing a scholarship application.
 * Uses composition by containing an Applicant object and provides
 * a common framework for subclasses (Merit, Need, Research) to build upon.
 * 
 * This class follows the Single Responsibility Principle (SRP):
 * - Application classes handle data management (applicant info, state)
 * - Evaluator classes handle business logic (validation, scoring)
 * 
 * Each Application subclass uses its own ScholarshipEvaluator to
 * evaluate the application (Polymorphism).
 * 
 * Design Pattern: Strategy Pattern - each application type delegates
 * evaluation logic to its specific evaluator strategy.
 */
public abstract class Application {

    protected Applicant applicant;
    protected ScholarshipEvaluator evaluator;

    /**
     * Constructs an Application with the given applicant.
     * Creates a defensive copy of the applicant to ensure encapsulation.
     * 
     * @param applicant the applicant for this application
     * @throws IllegalArgumentException if applicant is null
     */
    public Application(Applicant applicant) {
        if (applicant == null) {
            throw new IllegalArgumentException("Applicant cannot be null.");
        }

        this.applicant = new Applicant(applicant);
    }

    /**
     * Returns a copy of the applicant associated with this application.
     * Uses defensive copying to protect internal state.
     * 
     * @return a copy of the applicant
     */
    public Applicant getApplicant() {
        return new Applicant(applicant);
    }

    /**
     * Returns the category of this scholarship application.
     * Must be implemented by each concrete subclass.
     * 
     * @return the scholarship category ("Merit", "Need", or "Research")
     */
    public abstract String getScholarshipCategory();

    /**
     * Evaluates this application using the appropriate evaluator.
     * Each subclass provides its own evaluator implementation.
     * 
     * @return Formatted evaluation result string
     */
    public String evaluate() {
        if (evaluator == null) {
            throw new IllegalStateException("Evaluator not initialized for " + getScholarshipCategory());
        }
        return evaluator.evaluate(this);
    }

    /**
     * Checks if the application was accepted.
     * Used for summary statistics.
     * 
     * @return true if accepted, false if rejected
     */
    public boolean isAccepted() {
        if (evaluator == null) {
            return false;
        }
        return evaluator.getRejectionReason(this) == RejectionReason.NONE;
    }

    /**
     * Gets the scholarship type (FULL, HALF, NONE).
     * Used for summary statistics.
     * 
     * @return The scholarship result type
     */
    public ScholarshipResultType getType() {
        if (evaluator == null) {
            return ScholarshipResultType.NONE;
        }
        return evaluator.getScholarshipType(this);
    }

    @Override
    public String toString() {
        return String.format("Application: " + applicant.toString());
    }


}