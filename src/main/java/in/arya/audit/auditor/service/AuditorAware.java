package in.arya.audit.auditor.service;

/**
 * Created by dev on 07/04/18.
 */
public interface AuditorAware<T> {

    /**
     * Returns the current auditor of the application.
     *
     * @return the current auditor
     */
    T getCurrentAuditor();
}
