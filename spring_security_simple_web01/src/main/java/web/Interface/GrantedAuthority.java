package web.Interface;

import java.io.Serializable;

/**
 * @author dkw
 */
public interface GrantedAuthority extends Serializable {


    String getAuthority();
}
