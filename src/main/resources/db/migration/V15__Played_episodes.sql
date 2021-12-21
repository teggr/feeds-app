ALTER TABLE inbox_podcast_episodes ADD COLUMN played boolean NOT NULL DEFAULT false;

UPDATE inbox_podcast_episodes SET played = true WHERE status = 'NOT_INTERESTED';

