FROM mysql:8

# Environment variables
ENV MYSQL_DATABASE url_shortener
ENV MYSQL_ROOT_PASSWORD supersecretpassword

COPY ./sql/create.sql /docker-entrypoint-initdb.d/