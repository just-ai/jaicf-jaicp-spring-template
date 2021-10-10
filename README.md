# JAICF Spring Bot template

Here is a ready to use [JAICF](https://github.com/just-ai/jaicf-kotlin) bot template that utilises 

- [Spring](https://spring.io)
- [MongoDB](https://mongodb.com)
- [Docker](https://docker.com)
- [Prometheus](https://prometheus.io)
- [Grafana](https://grafana.com)
- [Graylog](https://graylog.org)

# How to use

Please refer to the detailed [Quick Start](https://help.jaicf.com/Quick-Start) that shows how to use this template with [JAICP](https://help.jaicf.com/JAICP) and [CAILA NLU](https://help.jaicf.com/Caila) services.

# Development

Run `Dev.kt` file in your IDE while developing the project.
To make it work just create `local.application.yml` in `resources` folder with required options:

```yaml
bot:
  accessToken: <your production JAICP token>
```

_Note that this file is ignored by git and won't be populated to your repository._

# Docker

## Configuration

To overwrite default bot configuration, create `application.yml` file in `docker/app/conf` with required properties:

```yaml
bot:
  accessToken: <your production JAICP token>
```

## Build and run

1. Build project with `stage` gradle task
2. Run `docker-compose up`

This build script runs:

- `http://localhost:3000` Grafana monitoring dashboards
- `http://localhost:9000` Graylog logging system
- `http://localhost:8080` Your JAICF bot endpoint

>Note that this builds a webhook version of your JAICF bot.
Thus, you have to propagate your local 8080 port to the global web (through some gateway like ngrok) and configure your JAICP project with direct public URL of your machine.

3. Define your public JAICF bot webhook in the webhook settings of your JAICP project using `/webhook` (e.g. `https://somedomain.io/webhook`)

## Options

You can override default running options for every service in the `docker` folder.
