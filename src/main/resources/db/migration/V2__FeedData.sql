ALTER TABLE podcasts
    ADD COLUMN feed_data text NULL;
ALTER TABLE podcasts
    ADD COLUMN feed_title text NULL;
ALTER TABLE podcasts
    ADD COLUMN feed_link_url varchar(255) NULL;