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
package net.sourceforge.pebble.web.action;

import net.sourceforge.pebble.Constants;
import net.sourceforge.pebble.comparator.CountedUrlByCountComparator;
import net.sourceforge.pebble.domain.Blog;
import net.sourceforge.pebble.logging.CountedUrl;
import net.sourceforge.pebble.logging.Log;
import net.sourceforge.pebble.web.view.View;
import net.sourceforge.pebble.web.view.impl.ReferersView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Gets the referers for the specified time period. This differs from the
 * unsecure version in that it allows referers to be seen month by month
 * and referer filtering can be toggled on/off.
 *
 * @author    Simon Brown
 */
public class ViewReferersAction extends AbstractLogAction {

  /**
   * Peforms the processing associated with this action.
   *
   * @param request  the HttpServletRequest instance
   * @param response the HttpServletResponse instance
   * @return the name of the next view
   */
  public View process(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    Blog blog = (Blog)getModel().get(Constants.BLOG_KEY);
    Log log = getLog(request, response);

    String filter = request.getParameter("filter");
    List referers = new ArrayList(log.getReferers());
    if (filter == null || filter.equalsIgnoreCase("true")) {
      referers = blog.getRefererFilterManager().filter(referers);
    }
    Collections.sort(referers, new CountedUrlByCountComparator());

    // now calculate the total number of referers, after filtering spam
    int totalReferers = 0;
    Iterator it = referers.iterator();
    CountedUrl url;
    while (it.hasNext()) {
      url = (CountedUrl)it.next();
      totalReferers += url.getCount();
    }

    getModel().put("logAction", "viewReferers");
    getModel().put("referers", referers);
    getModel().put("totalReferers", new Integer(totalReferers));

    return new ReferersView();
  }

}