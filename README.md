# feeds-app
Feed reader for Podcasts

## Development

Run the local database. Schema loaded using flyway

```shell
docker-compose up
```

## Deployment

```shell
git push heroku main
```

## Features

Home
* list of episodes interested in
* not interested

New Releases
* see latest episodes
* interested / not interested

Subscriptions
* list of subscribed podcasts
* unsubscribe

Podcasts
* all available podcasts
* add podcasts
* refresh podcasts
* subscribe to podcasts

Media player
* attach player to each episode

## Architecture

Podcasts - 