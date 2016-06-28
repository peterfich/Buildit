# Buildit

The project uses Maven

To build and run the application against http://wiprodigital.com/ do a:
mvn clean test exec:java

The url to crawl is set in the pom.

There is no input validation. The url is expected to be well formed, with schema.

All checked exceptions are court and rethrown as WebCrawlerException, a RuntimeException.

Parsing HTML is hard, as it can be non well formed. Also, connecting resources can be many things, including links, images, javascript file, style sheets, AJAX call.
I only deal with 2 things; link tags and image tags. But it can easily be extended.
 
For both I use regex to find the url.

For image tags I ignore links to other domains and schema and expand url to absolute urls.

For link tags I ignore links to other domains and schema. I also remove anchors (the bit after a '#') and expand urls to absolute urls.

# Things to improve
- Input validation
- Error handling
- Download in parallel
- The output is not formated as a tree
