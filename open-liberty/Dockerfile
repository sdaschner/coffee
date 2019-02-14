# taken from https://github.com/OpenLiberty/ci.docker
# this is already available on Docker Hub: sdaschner/open-liberty:javaee8-tracing-b1
FROM ibmjava:8-sfj-alpine

LABEL maintainer="Sebastian Daschner"

RUN apk add curl

COPY docker-server /opt/ol/docker/

# Install Open Liberty
ENV LIBERTY_VERSION=19.0.0.1-cl190220190131-0300 \
    LIBERTY_RELEASE=2019-01-31_0451 \
    LIBERTY_SHA=22eb00a3e9b29dfb6b6698ed603cd524b3511a1c \
    INSTALL_DIR=/opt/ol/wlp \
    CONFIG_DIR=/opt/ol/wlp/usr/servers/defaultServer \
    DEPLOYMENT_DIR=/opt/ol/wlp/usr/servers/defaultServer/dropins \
    PATH=/opt/ol/wlp/bin:/opt/ol/docker/:$PATH \
    LOG_DIR=/logs \
    WLP_OUTPUT_DIR=/opt/ol/wlp/output \
    WLP_SKIP_MAXPERMSIZE=true

RUN curl https://public.dhe.ibm.com/ibmdl/export/pub/software/openliberty/runtime/nightly/${LIBERTY_RELEASE}/openliberty-all-${LIBERTY_VERSION}.zip -L -o /tmp/wlp.zip \
 && echo "$LIBERTY_SHA  /tmp/wlp.zip" > /tmp/wlp.zip.sha1 \
 && sha1sum -c /tmp/wlp.zip.sha1 \
 && unzip -q /tmp/wlp.zip -d /opt/ol \
 && rm /tmp/wlp.zip \
 && rm /tmp/wlp.zip.sha1

RUN mkdir /logs \
    && mkdir -p $WLP_OUTPUT_DIR/defaultServer \
    && ln -s $WLP_OUTPUT_DIR/defaultServer /output \
    && ln -s $CONFIG_DIR /config \
    && ln -s /logs $WLP_OUTPUT_DIR/defaultServer/logs

RUN /opt/ol/wlp/bin/server create \
    && rm /config/server.env \
    && rm -rf $WLP_OUTPUT_DIR/.classCache /output/workarea \
    && mkdir /config/configDropins \
    && mkdir /config/configDropins/defaults \
    && echo "<server description=\"Default Server\"><httpEndpoint id=\"defaultHttpEndpoint\" host=\"*\" /></server>" > /config/configDropins/defaults/open-default-port.xml

ADD https://github.com/WASdev/sample.opentracing.zipkintracer/releases/download/1.2/liberty-opentracing-zipkintracer-1.2-sample.zip /
RUN unzip liberty-opentracing-zipkintracer-1.2-sample.zip -d $INSTALL_DIR/usr/

EXPOSE 9080 9443

ENTRYPOINT ["/opt/ol/docker/docker-server"]

CMD ["/opt/ol/wlp/bin/server", "run", "defaultServer"]