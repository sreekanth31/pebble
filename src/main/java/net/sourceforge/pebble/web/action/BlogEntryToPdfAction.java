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

import net.sourceforge.pebble.util.SecurityUtils;

import net.sourceforge.pebble.domain.Blog;
import net.sourceforge.pebble.domain.BlogEntry;
import net.sourceforge.pebble.domain.BlogService;
import net.sourceforge.pebble.domain.BlogServiceException;

import net.sourceforge.pebble.web.view.NotFoundView;
import net.sourceforge.pebble.web.view.PdfView;
import net.sourceforge.pebble.web.view.View;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Allows the user to export current blog entry as a PDF file.
 *
 * @author    Alexander Zagniotov
 */
public class BlogEntryToPdfAction extends Action {

  private static final Log log = LogFactory.getLog(BlogEntryToPdfAction.class);

  /**
   * Peforms the processing associated with this action.
   *
   * @param request  the HttpServletRequest instance
   * @param response the HttpServletResponse instance
   * @return the name of the next view
   */
  public View process(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    Blog blog = (Blog)getModel().get(Constants.BLOG_KEY);
    String entryId = request.getParameter("entry");

    BlogEntry blogEntry = null;
    if (entryId != null) {
      BlogService service = new BlogService();
      try {
        blogEntry = service.getBlogEntry(blog, entryId);
      } catch (BlogServiceException e) {
        throw new ServletException(e);
      }
    }

	if (blogEntry == null) {
      // the entry cannot be found - it may have been removed or the
      // requesting URL was wrong
      return new NotFoundView();

    } else {
      
	  String filename = this.buildPermalink(blogEntry);
	  return new PdfView(blogEntry, filename + ".pdf");

	}
  }

  private String buildPermalink(BlogEntry blogEntry) {
    String title = blogEntry.getTitle();
    if (title == null || title.length() == 0) {
      title = "" + blogEntry.getId();
    } else {
      title = title.toLowerCase();
      title = title.replaceAll("[\\. ,;/\\\\-]", "_");
      title = title.replaceAll("[^a-z0-9_]", "");
      title = title.replaceAll("_+", "_");
      title = title.replaceAll("^_*", "");
      title = title.replaceAll("_*$", "");
    }

    // if the title has been blanked out, use the blog entry instead
    if (title == null || title.length() == 0) {
      title = "" + blogEntry.getId();
    }
    return title;
  }
}