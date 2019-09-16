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

package net.sourceforge.pebble.web.security;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author James Roper
 */
public class SecurityTokenValidatorTest extends TestCase {

  private SecurityTokenValidator validator;

  protected void setUp() {
    validator = new SecurityTokenValidatorImpl();
  }

  public void testGenerateHash() {
    Map<String, String[]> map1 = new HashMap<String, String[]>();
    map1.put("key", new String[] {"value"});
    map1.put("zed", new String[] {"value2"});
    String hash1 = validator.hashRequest("path", map1, "salt");
    Map<String, String[]> map2 = new HashMap<String, String[]>();
    map2.put("zed", new String[] {"value2"});
    map2.put("key", new String[] {"value"});
    String hash2 = validator.hashRequest("path", map2, "salt");
    assertEquals(hash1, hash2);
    String hash3 = validator.hashRequest("path", map1, "badsalt");
    assertFalse(hash1.equals(hash3));
  }

}
