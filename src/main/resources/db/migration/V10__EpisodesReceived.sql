ALTER TABLE subscription_episodes
    ADD COLUMN received_date_time timestamp with time zone default now();

UPDATE subscription_episodes SET received_date_time = now() WHERE received_date_time is null;