## Local postgres

Run postgres locally:

```shell
docker run -e POSTGRES_DB=oldschool -e POSTGRES_PASSWORD=postgres -v ~/.config/oldschool-pg-data:/var/lib/postgresql/data -p 5432:5432 postgres:13.3-alpine3.14
```

Cleanup data dir:

```shell
rm -rf ~/.config/oldschool-pg-data
```
