# webdev-rethinkdb

I followed Eric Normand's excellent
[Web Development in Clojure](http://www.purelyfunctional.tv/web-dev-in-clojure)
LispCast, with some alterations:

* RethinkDB instead of Postgres (expects rethinkdb to be running locally on default port)
* Not set up for Heroku
* No Bootstrap
* Uses the [-> macro](https://clojuredocs.org/clojure.core/-%3E) for middleware

Run with `lein run 5000`
