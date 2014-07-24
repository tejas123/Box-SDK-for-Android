package com.box.boxjavalibv2.resourcemanagers;

import java.util.ArrayList;
import java.util.List;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.dao.BoxCollection;
import com.box.boxjavalibv2.dao.BoxEvent;
import com.box.boxjavalibv2.dao.BoxEventCollection;
import com.box.boxjavalibv2.dao.BoxResourceType;
import com.box.boxjavalibv2.dao.BoxTypedObject;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxServerException;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.jsonparsing.IBoxResourceHub;
import com.box.boxjavalibv2.requests.EventOptionsRequest;
import com.box.boxjavalibv2.requests.GetEventsRequest;
import com.box.boxjavalibv2.requests.requestobjects.BoxEventRequestObject;
import com.box.restclientv2.IBoxRESTClient;
import com.box.restclientv2.authorization.IBoxRequestAuth;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;

/**
 * Use this class to execute requests <b>synchronously</b> against the Box REST API(V2). Full details about the Box API can be found at {@see <a
 * href="http://developers.box.com/docs">http://developers.box.com/docs</a>} . You must have an OpenBox application with a valid API key to use the Box API. All
 * methods in this class are executed in the invoking thread, and therefore are NOT safe to execute in the UI thread of your application. You should only use
 * this class if you already have worker threads or AsyncTasks that you want to incorporate the Box API into.
 */
public class BoxEventsManagerImpl extends AbstractBoxResourceManager implements IBoxEventsManager {

    /**
     * Constructor.
     * 
     * @param config
     *            BoxConfig
     * @param resourceHub
     *            IResourceHub
     * @param parser
     *            json parser
     * @param auth
     *            auth for api calls
     * @param restClient
     *            REST client to make api calls.
     */
    public BoxEventsManagerImpl(IBoxConfig config, IBoxResourceHub resourceHub, final IBoxJSONParser parser, final IBoxRequestAuth auth,
        final IBoxRESTClient restClient) {
        super(config, resourceHub, parser, auth, restClient);
    }

    @Override
    public BoxEventCollection getEvents(BoxEventRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException {
        GetEventsRequest request = new GetEventsRequest(getConfig(), getJSONParser(), requestObject);
        return (BoxEventCollection) getResponseAndParseAndTryCast(request, BoxResourceType.EVENTS, getJSONParser());
    }

    @Override
    public BoxCollection getEventOptions(BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException {
        EventOptionsRequest request = new EventOptionsRequest(getConfig(), getJSONParser(), requestObject);
        return (BoxCollection) getResponseAndParseAndTryCast(request, BoxResourceType.ITEMS, getJSONParser());
    }

    /**
     * Get events from a collection. Deprecated, use Utils.getTypedObjects instead.
     * 
     * @param collection
     *            collection
     * @return comments
     */
    @Deprecated
    public static List<BoxEvent> getEvents(BoxCollection collection) {
        List<BoxEvent> events = new ArrayList<BoxEvent>();
        List<BoxTypedObject> list = collection.getEntries();
        for (BoxTypedObject object : list) {
            if (object instanceof BoxEvent) {
                events.add((BoxEvent) object);
            }
        }
        return events;
    }
}
