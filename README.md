# Play Slick with PostgreSQL database

An example how to access database from Scala Play application using Slick library.

## Get started

### Step 1

Run PostgreSQL and pgAdmin containers. There is docker-compose.yml file included
```
docker-compose up -d
```
<ol>
    <li>Open <a href="http://localhost:5050">pgAdmin</a> in browser</li>
    <li>Add new server 
        <ul>
            <li>Host name/address = postgres_container (name of docker container)</li>
            <li>Port = 5432 (exposed port)</li>
        </ul>
</ol>



### Step 2

Run Scala Play application. 9000 is default port, you can change it.

```
sbt "run 9000"
```

<p>SQL scripts from conf/evolutions folder will be executed when application is started.</p>

<p>Open application <a href="http://localhost:9000">index page</a>. It will render swagger documentation.</p>

<p>Enjoy!</p>




