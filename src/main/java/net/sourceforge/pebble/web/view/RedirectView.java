/*
 * Copyright (c) 2003-2011, Simon Brown
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in
 *     the documentation and/or other materials provided with the
 *     distribution.
 *
 *   - Neither the name of Pebble nor the names of its contributors may
 *     be used to endorse or promote products derived from this software
 *     without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package net.sourceforge.pebble.web.view;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.pebble.util.Utilities;

import java.io.IOException;

/**
 * Represents a JSP view component and prepares the model for display.
 *
 * @author    Simon Brown
 */
public class RedirectView extends View {

  private String uri;

  /**
   * Creates a new instance with the specified URI.
   *
   * @param uri   the URI to redirect to
   */
  public RedirectView(String uri) {
    this.uri = uri;
  }

  /**
   * Gets the URI that this view represents.
   *
   * @return  the URI as a String
   */
  public String getUri() {
    return this.uri;
  }

  /**
   * Gets the title of this view.
   *
   * @return the title as a String
   */
  public String getContentType() {
    return null;
  }

  /**
   * Dispatches this view.
   *
   * @param request  the HttpServletRequest instance
   * @param response the HttpServletResponse instance
   * @param context
   */
  public void dispatch(HttpServletRequest request, HttpServletResponse response, ServletContext context) throws ServletException {
    try {
      String target = Utilities.calcBaseUrl(request.getScheme(), getUri());
      response.sendRedirect(target);
    } catch (IOException ioe) {
      throw new ServletException(ioe);
    }
  }

}
