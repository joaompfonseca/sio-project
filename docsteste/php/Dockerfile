FROM php:8.2-fpm
RUN apt-get update && \
    apt-get install -y libxml2-dev && \
    docker-php-ext-install soap
RUN docker-php-ext-install opcache
COPY ./php/opcache.ini /usr/local/etc/php/conf.d/opcache.ini
COPY ./php/index.php /var/www/html/index.php