/*
 * This code is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License version 2 only, as published by
 * the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this work; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package ai.util;

/**
 * Signals that an attempt to access a {@code Node} in a {@code Graph}
 * with the specified data has failed.
 *
 * @author  <a href="mailto:barkle36@gmail.com">Andrew Allen Barkley</a>
 * @version 2012-10-10
 */
public class GraphNodeNotFoundException extends GraphException {

    /**
     * Construct a {@code GraphNodeNotFoundException} with {@code null} as its
     * error detail message.
     */
    public GraphNodeNotFoundException() {
        super();
    }

    /**
     * Construct a {@code GraphNodeNotFoundException} with the specified
     * detail message.
     *
     * @param message
     *        The detail message (which is saved for later retrieval by the
     *        {@link #getMessage()} method)
     */
    public GraphNodeNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a {@code GraphNodeNotFoundException} with the specified
     * detail message and cause.
     *
     * <p> Note that the detail message associated with {@code cause} is
     * <i>not</i> automatically incorporated into this exception's detail
     * message.
     *
     * @param message
     *        The detail message (which is saved for later retrieval by the
     *        {@link #getMessage()} method)
     *
     * @param cause
     *        The cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A null value is permitted, and
     *        indicates that the cause is nonexistent or unknown.)
     */
    public GraphNodeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a {@code GraphNodeNotFoundException} with the specified
     * cause and a detail message of
     * {@code (cause==null ? null : cause.toString())} (which typically
     * contains the class and detail message of {@code cause}).
     *
     * @param cause
     *        The cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A null value is permitted, and
     *        indicates that the cause is nonexistent or unknown.)
     */
    public GraphNodeNotFoundException(Throwable cause) {
        super(cause);
    }
}


