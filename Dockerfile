FROM openjdk:8

ENV IDC zeus
ENV MODULE bk-pic-color
ENV ENVTYPE test
ENV DEBUGPORT 9008
ENV JMXPORT 9009
ENV MATRIX_CODE_DIR /opt/bk-pic-color/htdocs
ENV MATRIX_APPLOGS_DIR /opt/bk-pic-color/applogs
ENV MATRIX_ACCESSLOGS_DIR /opt/bk-pic-color/logs
ENV MATRIX_LOGS_DIR /opt/bk-pic-color/logs
ENV MATRIX_CACHE_DIR /opt/bk-pic-color/cache
ENV MATRIX_PRIVDATA_DIR /opt/bk-pic-color/privdata

COPY release/ /opt/bk-pic-color/htdocs/
RUN chmod +x /opt/bk-pic-color/htdocs/bin/*.sh

EXPOSE 8080 9008 9009
WORKDIR /opt/bk-pic-color/htdocs
VOLUME ["/opt/bk-pic-color/applogs", "/opt/bk-pic-color/logs", "/opt/bk-pic-color/cache", "/opt/bk-pic-color/privdata"]
CMD ["/bin/bash", "-x", "/opt/bk-pic-color/htdocs/bin/run.sh"]
