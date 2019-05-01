FROM sdaschner/open-liberty:javaee8-tracing-jdk12-b1

COPY liberty/default-config.xml $CONFIG_DIR/configDropins/defaults/server.xml
COPY liberty/server.xml $CONFIG_DIR

COPY target/coffee-shop.war $DEPLOYMENT_DIR
