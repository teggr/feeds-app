CREATE TABLE podcasts
(
    id       int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    feed_url varchar(255) NULL,
    CONSTRAINT podcasts_pkey PRIMARY KEY (id)
);