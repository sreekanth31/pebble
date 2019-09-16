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
package net.sourceforge.pebble.plugins;

import org.apache.commons.lang.StringEscapeUtils;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * @author James Roper
 */
public class PlainTextPluginConfigType implements PluginConfigType {

  public static final PlainTextPluginConfigType INSTANCE = new PlainTextPluginConfigType();

  public void render(JspWriter writer, PluginConfig pluginConfig, String value) throws IOException {
    writer.print("<input type=\"text\" name=\"");
    writer.print(PLUGIN_PROPERTY_NAME_PREFIX);
    writer.print(pluginConfig.getKey());
    writer.print("\" value=\"");
    if (value != null) {
      writer.print(StringEscapeUtils.escapeHtml(value));
    }
    writer.print("\"/>");
  }

  public String validate(PluginConfig pluginConfig, String value) {
    String regex = pluginConfig.getProperties().getProperty("regex");
    if (regex != null && value != null)
    {
      if (!value.matches(regex))
      {
        return "Property " + pluginConfig.getName() + " must match regular expression " + regex;
      }
    }
    // Success
    return null;
  }
}
