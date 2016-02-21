/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import junit.framework.TestCase;





/**
 * Performs Validation Test for url validations.
 *
 * @version $Revision: 1128446 $ $Date: 2011-05-27 13:29:27 -0700 (Fri, 27 May 2011) $
 */
public class UrlValidatorTest extends TestCase {

   private boolean printStatus = false;
   private boolean printIndex = false;//print index that indicates current scheme,host,port,path, query test were using.

   public UrlValidatorTest(String testName) {
      super(testName);
   }



   public void testManualTest()
   {
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   System.out.println(urlVal.isValid("http://www.amazon.com"));


   }


   public void testYourFirstPartition()
   {

   }

   public void testYourSecondPartition(){

   }


   public void testIsValid()
   {
     int length = 10000;        // loop counter
     int bugs = 0;              // bug counter
     int r = 0;                 // second loop counter

     /* Failed Urls */
     String[] failed = new String[length]; // will hold the failed urls.

     /* Url Pieces */
     String[] schemes    = { "http://", "", "ftp://", "h3t://" }; // all valid schemes
     String[] authority  = { "www.google.com", "google.com", "go.com", "go.au", "0.0.0.0", "255.255.255.255", "255.com", "go.cc" }; // all valid authorities
     String[] ports      = { ":80", ":90", ":100", ":3000", ":8888" }; // all valid ports
     String[] path       = { "/test", "/t123", "/$23", "/test/", "/test/next" }; // all valid paths
     String[] options    = { "/test1", "/t123", "/$23", "/test/", "/$23/file" }; // all valid path options
     String[] queries    = { "?action=view", "?action=edit&mode=down", "", "?set=true&bob=dylan&you=true" }; // all valid queries

	   for(int i = 0; i<length; i++)
	   {
      int scheme_index    = (int) (Math.random() * 3);
      int authority_index = (int) (Math.random() * 7);
      int ports_index     = (int) (Math.random() * 4);
      int path_index      = (int) (Math.random() * 4);
      int options_index   = (int) (Math.random() * 4);
      int queries_index   = (int) (Math.random() * 3);

      /* Create a random string made up of valid url pieces */
      String url = schemes[scheme_index] + authority[authority_index] + ports[ports_index] + path[path_index] + queries[queries_index];
      UrlValidator validator =  new UrlValidator();
      boolean test = validator.isValid(url);

      if ( test == false ) {
        bugs++;           // increase number of bugs
        failed[r] = url;  // save the failed url
        r++;              // increment the counter
      }
     } //end for loop

     /* Print out number of bugs and failed urls */
     System.out.println("Tests found " + bugs + " bugs. \n");
     System.out.println("Failed urls: \n");
     for ( int i=0; i< failed.length; i++) {
       if ( failed[i] != null ) System.out.println( i + " : " + failed[i] + "\n");
     }

   } // end test is valid

   public void testAnyOtherUnitTest()
   {

   }
   /**
    * Create set of tests by taking the testUrlXXX arrays and
    * running through all possible permutations of their combinations.
    *
    * @param testObjects Used to create a url.
    */


}
