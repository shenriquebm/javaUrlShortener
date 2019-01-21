CREATE DATABASE IF NOT EXISTS url_shortener;
CREATE USER IF NOT EXISTS 'url_shortener'@'%' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON url_shortener.* TO 'url_shortener'@'%';