/*
 * Copyright (c) 2002-2012, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.wurfl.web;

import fr.paris.lutece.plugins.wurfl.service.WurflService;
import fr.paris.lutece.portal.service.content.PageData;
import fr.paris.lutece.portal.service.includes.PageInclude;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.html.HtmlTemplate;

import net.sourceforge.wurfl.core.Device;
import net.sourceforge.wurfl.core.utils.UserAgentUtils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * WurflInclude
 */
public class WurflInclude implements PageInclude
{
    private static final String TEMPLATE_DEVICE_INCLUDE = "skin/plugins/wurfl/include.html";
    private static final String MARK_DEVICE = "device";
    private static final String MARK_DEVICE_INCLUDE = "device_include";
    private static final String MARK_USER_AGENT = "user_agent";
    private static final String MARK_UA_PROFILE = "ua_profile";
    private static final String MARK_IS_MOBILE_BROWSER = "is_mobile_browser";
    private static final String MARK_IS_XHTML_REQUESTER = "is_xhtml_requester";

    public void fillTemplate( Map<String, Object> rootModel, PageData pd, int i, HttpServletRequest request )
    {
        HtmlTemplate t = new HtmlTemplate(  );

        if ( request != null )
        {
            Device device = WurflService.getDevice( request );
            String strProfile = UserAgentUtils.getUaProfile( request );
            Map<String, Object> model = new HashMap<String, Object>(  );
            model.put( MARK_DEVICE, device );
            model.put( MARK_USER_AGENT, UserAgentUtils.getUserAgent( request ) );
            model.put( MARK_UA_PROFILE, ( strProfile != null ) ? strProfile : "Not found" );
            model.put( MARK_IS_MOBILE_BROWSER,
                UserAgentUtils.isMobileBrowser( UserAgentUtils.getUserAgent( request ) ) ? 1 : 0 );
            model.put( MARK_IS_XHTML_REQUESTER, UserAgentUtils.isXhtmlRequester( request ) ? 1 : 0 );
            t = AppTemplateService.getTemplate( TEMPLATE_DEVICE_INCLUDE, request.getLocale(  ), model );
        }

        rootModel.put( MARK_DEVICE_INCLUDE, t.getHtml(  ) );
    }
}
