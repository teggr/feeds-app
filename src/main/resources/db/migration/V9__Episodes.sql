CREATE TABLE episodes
(
    id       int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    episode_id varchar(255) NULL,
    title varchar(255) NULL,
    link_url varchar(255) NULL,
    published_date timestamp with time zone NULL,
    image_url varchar(255) NULL,
    image_title varchar(255) NULL,
    audio_url varchar(255) NULL,
    audio_type varchar(255) NULL,
    podcast_id int8 NOT NULL,
    CONSTRAINT episodes_pkey PRIMARY KEY (id)
);