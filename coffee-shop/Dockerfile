#FROM sdaschner/wildfly:javaee8-b1
FROM sdaschner/open-liberty:javaee8-tracing-jdk11-b3

COPY openliberty/server.xml $CONFIG_DIR

COPY target/coffee-shop.war $DEPLOYMENT_DIR
