ALTER TABLE subscription_episodes RENAME TO inbox_podcast_episodes;

ALTER TABLE inbox_podcast_episodes ADD COLUMN username varchar(50);