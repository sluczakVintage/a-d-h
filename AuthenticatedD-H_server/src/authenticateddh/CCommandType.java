/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

import java.io.Serializable;

/**
 *
 * @author Sebastian
 */
public enum CCommandType implements Serializable {

    /**
     *
     */
    CT_REGISTER,
    /**
     *
     */
    CT_LOGIN,
    /**
     *
     */
    CT_HELLO,
    /**
     *
     */
    CT_LIST,
    /**
     *
     */
    CT_MESSAGE,
    /**
     *
     */
    CT_NONE,

    CT_ERROR;

    @Override public String toString() {
   //only capitalize the first letter
   String s = super.toString();
   return s.substring(0, 1) + s.substring(1).toLowerCase();
 }
}
