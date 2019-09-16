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
package net.sourceforge.pebble.web.view.impl;

import net.sourceforge.pebble.Constants;
import net.sourceforge.pebble.api.decorator.ContentDecoratorContext;
import net.sourceforge.pebble.decorator.ContentDecoratorChain;
import net.sourceforge.pebble.domain.AbstractBlog;
import net.sourceforge.pebble.web.view.XmlView;

import java.util.List;

/**
 * Base class for newsfeed views.
 *
 * @author    Simon Brown
 */
public abstract class AbstractFeedView extends XmlView {

  /**
   * Gets the content type of this view.
   *
   * @return the content type as a String
   */
  public String getContentType() {
    AbstractBlog blog = (AbstractBlog)getModel().get(Constants.BLOG_KEY);
    return "application/xml; charset=" + blog.getCharacterEncoding();
  }

  /**
   * Prepares the view for presentation.
   */
  public void prepare() {
    ContentDecoratorContext context = new ContentDecoratorContext();
    context.setView(ContentDecoratorContext.SUMMARY_VIEW);
    context.setMedia(ContentDecoratorContext.NEWS_FEED);

    List blogEntries = (List)getModel().get(Constants.BLOG_ENTRIES);
    ContentDecoratorChain.decorate(context, blogEntries);
    getModel().put(Constants.BLOG_ENTRIES, blogEntries);
  }

}