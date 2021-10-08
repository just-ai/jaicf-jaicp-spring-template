# JAICF Spring Bot template

Here is a ready to use [JAICF](https://github.com/just-ai/jaicf-kotlin) bot template that uses [Spring](https://spring.io) and [MongoDB](https://mongodb.com).

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

To overwrite default bot configuration, create `application.yml` file somewhere on the disk with required properties:

```yaml
bot:
  accessToken: <your production JAICP token>
```

## Build and run

To build and run Docker image:

1. Build project with `stage` gradle task
2. Build Docker image with `docker build -t jaicf-jaicp-spring-template .`
3. Run Docker image with `docker run -p 8080:8080 -v <directory with production application.yml>:/opt/jaicf/conf jaicf-jaicp-spring-template`

Note that this builds a webhook version of your JAICF bot.
Thus, you have to propagate your local 8080 port to the global web (through some gateway like ngrok) and configure your JAICP project with direct public URL of your machine.

## Options

You can override default running options in `scripts/run.sh`.
