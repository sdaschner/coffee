FROM sdaschner/open-liberty:javaee8-tracing-jdk11-b5

COPY openliberty/default-config.xml $CONFIG_DIR/configDropins/defaults/
COPY openliberty/server.xml $CONFIG_DIR

COPY target/coffee-shop.war $DEPLOYMENT_DIR
