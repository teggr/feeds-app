ALTER TABLE inbox_podcast_episodes ADD COLUMN episode_title varchar(255) NULL;
ALTER TABLE inbox_podcast_episodes ADD COLUMN episode_link_url varchar(255) NULL;
ALTER TABLE inbox_podcast_episodes ADD COLUMN episode_published_date timestamptz NULL;
ALTER TABLE inbox_podcast_episodes ADD COLUMN episode_image_url varchar(255) NULL;
ALTER TABLE inbox_podcast_episodes ADD COLUMN episode_image_title varchar(255) NULL;
ALTER TABLE inbox_podcast_episodes ADD COLUMN episode_audio_url varchar(255) NULL;
ALTER TABLE inbox_podcast_episodes ADD COLUMN episode_audio_type varchar(255) NULL;

UPDATE inbox_podcast_episodes i
SET 
	episode_title = p.title,
	episode_link_url  = p.link_url,
	episode_published_date  = p.published_date,
	episode_image_url = p.image_url,
	episode_image_title = p.image_title,
	episode_audio_url  = p.audio_url 
FROM podcast_episodes p
WHERE p.episode_id = i.episode_id;