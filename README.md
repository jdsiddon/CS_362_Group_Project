Assignment
====

* Go through instructor video of explaining URL Validation testing.

* Explain testIsValid Function of UrlValidator test code. It is availabe under christia/UrlValidator folder.
  * Give how many total number of urls it is testing. Also, explain how it is building all the urls.
  * Give an example of valid url being tested and an invalid url being tested by testIsValid() method.  


* UrlValidator code is a direct copy paste of apache commons url validator code. The test file/code is also direct copy paste of apache commons test code.

* Do you think that a real world test (URL Validator's testIsValid() test in this case) is very different than the unit tests and card tests that we wrote (in terms of concepts & complexity)?

* Explain in few lines. Submit a file called ProjectPartA.txt with your writeup. You can submit the file under the folder URLValidator within on ONID directory. (How to setup this folder will be explained soon.)


testIsValid()
======

The testIsValid method that is called in main() accepts no parameters. This method calls another testIsValid() method that accepts two parameters, an array of url parts, and a long value.

This method grabs a piece of a potential url (5 parts, scheme, authority, port, path, query) from each's respective arrays and creates a url to test.

Since each piece of a url is stored with a valid/invalid flag once invalid flag should make the entire url invalid. This method loops over each url 'part' and checks if the flag is set to valid/invalid. One invalid will set the expected result = to invalid, this is done on the last line of the for loop "expected &= part[index].valid;".

It then compares this valid/invalid value with the value that is actually returned by the isValid method of the UrlValidator class. If these match then the test passes. If they don't match, then the flags attached to each url part is incorrect or there is a bug with the isValid method.
