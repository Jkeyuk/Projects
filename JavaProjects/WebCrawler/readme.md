# Web Crawler

**Example only do not use in production or on a regular basis**

This application is an example of a basic web crawler. This application crawls the websites of a given url and never visits websites outside of the given urls host. When finished the pages visited are saved to a text file in the current working directory under the name of the urls host. 

For politeness sake, the crawler will not request a page more then once every half second. This causes large websites to take a long time if the maximum number of pages to visit is set high.

## General Usage

After building the source code, the application can be started with:

> java webCrawler.Main

Just follow the prompts in the console to get started.

Uses an Html parsing library written [here](https://github.com/Jkeyuk/Projects/tree/master/JavaProjects/HtmlParsingLibrary/).