package com.box.boxjavalibv2.responseparsers;

import java.io.InputStream;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;

import com.box.boxjavalibv2.dao.BoxPreview;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.httpclientsupport.HttpClientURIBuilder;
import com.box.restclientv2.responseparsers.DefaultFileResponseParser;
import com.box.restclientv2.responses.DefaultBoxResponse;
import com.box.restclientv2.responses.IBoxResponse;

/**
 * Parser to parse {@link com.box.restclientv2.responses.DefaultBoxResponse} into {@link com.box.boxjavalibv2.dao.BoxPreview} objects.
 */
public class PreviewResponseParser extends DefaultFileResponseParser {

    private static final String DELIM_LINKS = ",";
    private static final String DELIM_LINK_PARAM = ";";
    private static final String HEADER_LINK = "Link";
    private static final String FIRST = "first";
    private static final String LAST = "last";
    private static final String REL = "rel";
    private static final String PAGE = "page";

    @Override
    public BoxPreview parse(IBoxResponse response) throws BoxRestException {
        InputStream is = (InputStream) super.parse(response);
        BoxPreview preview = new BoxPreview();
        preview.setContent(is);
        preview.setContentLength(response.getContentLength());
        parseLinks(preview, (DefaultBoxResponse) response);
        return preview;
    }

    private void parseLinks(BoxPreview preview, DefaultBoxResponse response) throws BoxRestException {
        Header header = response.getHttpResponse().getFirstHeader(HEADER_LINK);
        if (header == null) {
            return;
        }
        String linkHeader = header.getValue();
        if (linkHeader != null) {
            String[] links = linkHeader.split(DELIM_LINKS);
            for (String link : links) {
                String[] segments = link.split(DELIM_LINK_PARAM);
                if (segments.length < 2)
                    continue;

                String linkPart = segments[0].trim();
                if (!linkPart.startsWith("<") || !linkPart.endsWith(">")) //$NON-NLS-1$ //$NON-NLS-2$
                    continue;
                linkPart = linkPart.substring(1, linkPart.length() - 1);

                for (int i = 1; i < segments.length; i++) {
                    String[] rel = segments[i].trim().split("="); //$NON-NLS-1$
                    if (rel.length < 2 || !REL.equals(rel[0]))
                        continue;

                    String relValue = rel[1];
                    if (relValue.startsWith("\"") && relValue.endsWith("\"")) //$NON-NLS-1$ //$NON-NLS-2$
                        relValue = relValue.substring(1, relValue.length() - 1);

                    try {
                        if (FIRST.equals(relValue)) {
                            int fPage = getQueryIntValue(linkPart, PAGE);
                            if (fPage < 1) {
                                fPage = 1;
                            }
                            preview.setFirstPage(fPage);
                        }
                        else if (LAST.equals(relValue)) {
                            int lPage = getQueryIntValue(linkPart, PAGE);
                            if (lPage < 1) {
                                lPage = 1;
                            }
                            preview.setLastPage(lPage);
                        }
                    }
                    catch (NumberFormatException e) {
                        throw new BoxRestException(e);
                    }
                }
            }
        }
    }

    private int getQueryIntValue(String str, String name) {
        int result = -1;
        HttpClientURIBuilder ub;
        try {
            ub = new HttpClientURIBuilder(str);
            List<NameValuePair> queries = ub.getQueryParams();
            for (NameValuePair pair : queries) {
                if (name.equals(pair.getName())) {
                    result = Integer.parseInt(pair.getValue());
                    break;
                }
            }
        }
        catch (Exception e) {
            // swallow all exceptions.
        }
        return result;
    }
}
