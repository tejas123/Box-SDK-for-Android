package com.box.boxjavalibv2;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

@SuppressWarnings("deprecation")
public class BoxConnectionManagerBuilder {

    private int maxConnectionPerRoute = 50;
    private int maxConnection = 1000;
    private long timePeriodCleanUpIdleConnection = 300000;
    private long idleTimeThreshold = 60000;

    public BoxConnectionManager build() {
        return new BoxConnectionManager(this);
    }

    /**
     * @param maxConnectionPerRoute
     *            maximum connection allowed per route.
     */
    public void setMaxConnectionPerRoute(int maxConnectionPerRoute) {
        this.maxConnectionPerRoute = maxConnectionPerRoute;
    }

    /**
     * @param maxConnection
     *            maximum connection.
     */
    public void setMaxConnection(int maxConnection) {
        this.maxConnection = maxConnection;
    }

    /**
     * @param timePeriodCleanUpIdleConnection
     *            clean up idle connection every such period of time. in miliseconds.
     */
    public void setTimePeriodCleanUpIdleConnection(long timePeriodCleanUpIdleConnection) {
        this.timePeriodCleanUpIdleConnection = timePeriodCleanUpIdleConnection;
    }

    /**
     * @param idleTimeThreshold
     *            an idle connection will be closed if idled above this threshold of time. in miliseconds.
     */
    public void setIdleTimeThreshold(long idleTimeThreshold) {
        this.idleTimeThreshold = idleTimeThreshold;
    }

    public class BoxConnectionManager {

        private ClientConnectionManager connectionManager;
        private final int maxConnectionPerRoute;
        private final int maxConnection;
        private final long timePeriodCleanUpIdleConnection;
        private final long idleTimeThreshold;

        private BoxConnectionManager(BoxConnectionManagerBuilder builder) {
            this.maxConnection = builder.maxConnection;
            this.maxConnectionPerRoute = builder.maxConnectionPerRoute;
            this.timePeriodCleanUpIdleConnection = builder.timePeriodCleanUpIdleConnection;
            this.idleTimeThreshold = builder.idleTimeThreshold;
            createConnectionManager();
        }

        public DefaultHttpClient getMonitoredRestClient() {
            return new DefaultHttpClient(connectionManager, getHttpParams());
        }

        private void createConnectionManager() {
            SchemeRegistry schemeReg = new SchemeRegistry();
            schemeReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            schemeReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
            connectionManager = new ThreadSafeClientConnManager(getHttpParams(), schemeReg);
            monitorConnection(connectionManager);
        }

        private HttpParams getHttpParams() {
            HttpParams params = new BasicHttpParams();
            ConnManagerParams.setMaxTotalConnections(params, maxConnection);
            ConnManagerParams.setMaxConnectionsPerRoute(params, new ConnPerRoute() {

                @Override
                public int getMaxForRoute(HttpRoute httpRoute) {
                    return maxConnectionPerRoute;
                }
            });
            return params;
        }

        private void monitorConnection(ClientConnectionManager connManager) {
            final WeakReference<ClientConnectionManager> ref = new WeakReference<ClientConnectionManager>(connManager);
            connManager = null;
            Thread monitorThread = new Thread() {

                @Override
                public void run() {
                    try {
                        while (true) {
                            synchronized (this) {
                                ClientConnectionManager connMan = ref.get();
                                if (connMan == null) {
                                    return;
                                }

                                wait(timePeriodCleanUpIdleConnection);
                                // Close expired connections
                                connMan.closeExpiredConnections();
                                connMan.closeIdleConnections(idleTimeThreshold, TimeUnit.SECONDS);
                            }
                        }
                    }
                    catch (InterruptedException ex) {
                        // terminate
                    }
                }
            };
            monitorThread.start();
        }

    }
}
