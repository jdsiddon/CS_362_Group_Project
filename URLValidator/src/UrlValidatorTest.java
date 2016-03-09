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



   /*********
    * Scheme partitions
    *********/
   public String[] validSchemePartition = { "http://", "https://", "ftp://" };
   public String[] invalidSchemePartition = { "htt://", "http//", " " };
   public String[][] schemePartition = { validSchemePartition, invalidSchemePartition };


   /*********
    * Authority partitions
    *********/
   public String[] validAuthorityPartition = { "google.com", "256.255.255.255", "www.google.ru" };
   public String[] invalidAuthorityPartition = { " ", "255.255.255.255", "0.0.0" };
   public String[][] authorityPartition = { validAuthorityPartition, invalidAuthorityPartition };


   /*********
    * Path partitions
    *********/
   public String[] validPathPartition = { "/test", "/testing-123/" };
   public String[] invalidPathPartition = { " ", "e/" };
   public String[][] pathPartition = { validPathPartition, invalidPathPartition };


   /*********
    * Query partitions
    *********/
   public String[] validQueryPartition = { " ", "?test=123" };
   public String[] invalidQueryPartition = { "?test[]", "test=12" };
   public String[][] queryPartition = { validQueryPartition, invalidQueryPartition };


   // Combine partitions into a single array.
   public String[][][] domainPartitions = { schemePartition, authorityPartition, pathPartition, queryPartition };

   public void testDomainPartitions()
   {
	   String[] partitionUnderTest;
	   String[] urlToTest = new String[4];							// [ scheme, domain, path, query ];
	   String[] passed = new String[1000];		// Passed urls.
	   String[] failed = new String[1000];		// Failed urls.
	   int numPass = 0, numFail = 0;			// Iterators on pass/fails.
	   int numLoopsThroughPart = 10;
	   
	   for(int z = 0; z < 100; z++) {
	   
		   for(int i = 0; i < domainPartitions.length; i++)			  		// Loop through each domain partition.
		   {
			  for(int j = 0; j < domainPartitions[i][1].length; j++) 		// Loop through invalid partition values.
			  {
				  urlToTest[i] = domainPartitions[i][1][j];				// Place current invalid value into url to test.
	
				  for(int k = 0; k < domainPartitions.length; k++)			// Fill in rest of url with valid values from other partitions.
				  {
					  if(k == i)		// don't override our invalid value we are testing.
						  continue;
	
					  urlToTest[k] = domainPartitions[k][0][(int)(Math.random() * domainPartitions[k][0].length)];		// Pick and random valid value.
	
				  }
	
				  UrlValidator validator = new UrlValidator();
				  String url = "";
	
				  // Convert our array of strings to a string.
				  for(int l = 0; l < urlToTest.length; l++) {
					  url += urlToTest[l];
				  }
	
			      boolean urlValid = validator.isValid(url);
	
			      if ( urlValid ) {
			        passed[numPass] = url;  // save the failed url
			        numPass++;              // increment the counter
			      } else {
			    	failed[numFail] = url;
			    	numFail++;
			      }
	
			  }
		   }
		   
		   numLoopsThroughPart--;
		   
	   }	// Number of times through input partition.
		   
	   // Print out passing and failing urls.
	   System.out.println("--- Passed ---\n");
	   int m = 0;
	   int n = 0;
	   while(passed[m] != null)
	   {
		   System.out.println("Passed: " + passed[m]);
		   m++;
	   }
	   System.out.println("--- Failed ---\n");
	   while(failed[n] != null)
	   {
		   System.out.println("Failed: " + failed[n]);
		   n++;
	   }

   }
   



   public void testIsValid()
   {
     int length = 500;         // loop counter
     int bugs = 0;              // bug counter
     int r = 0;                 // second loop counter

     /* Failed Urls */
     String[] failed = new String[length]; // will hold the failed urls.

     /* Test Valid Urls */
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
     System.out.println("Valid Url Tests found " + bugs + " bugs. \n");
     System.out.println("Failed urls: \n");
     for ( int i=0; i< failed.length; i++) {
       if ( failed[i] != null ) System.out.println( i + " : " + failed[i] + "\n");
     }

     System.out.println("Valid Url Tests Complete");
	 /* Test Invalid Urls */
     bugs = 0;
     r = 0;
     /* Invalid Passed Urls */
     String[] passed = new String[length]; // will hold the invalid urls which pass

     /* Invalid Pieces */
     String[] badSchemes 	= { "3ht://", "http:", "http:/", "://", "http/" };
     String[] badAuthority 	= { "www.google.1com", "256.256.256.256", "16.16.16.16.16", "55.55.55", ".255.255.255.255", "192.68.102.10.", "abc", "abc.123" };
     String[] badPorts 		= { ":-1", ":8888a", ":70000", "22000" };
     String[] badPath 		= { "/..", "/../", "/path/to//file", "//#" };
     String[] badOptions 	= { "/../", "/#", "/test//file" };

     /* Create a random string made up of valid url pieces and one invalid piece */
     for(int i = 0; i < length; i++)
	 {
    	 /* Create Valid Url */
         int scheme_index    = (int) (Math.random() * 3);
         int authority_index = (int) (Math.random() * 7);
         int ports_index     = (int) (Math.random() * 4);
         int path_index      = (int) (Math.random() * 4);
         int options_index   = (int) (Math.random() * 4);
         int queries_index   = (int) (Math.random() * 3);


         /* Replace One Piece with an Invalid Piece */
         /* Reassign Indices to Invalid Pieces */
		 int bad_scheme_index 		= (int) (Math.random() * 4);
		 int bad_authority_index 	= (int) (Math.random() * 7);
		 int bad_ports_index     	= (int) (Math.random() * 3);
		 int bad_path_index      	= (int) (Math.random() * 3);
		 int bad_options_index   	= (int) (Math.random() * 2);
		 int bad_queries_index   	= (int) (Math.random() * 3);

	     /* Choose the Piece to Replace */
	     int replace_piece		= (int) (Math.random() * 3);

	     /* Create a random string made up of valid url pieces and on invalid piece */
	     String url;
	     if ( replace_piece == 0 )		//Replace scheme
	     {
	         url = badSchemes[bad_scheme_index] + authority[authority_index] + ports[ports_index] + path[path_index] + queries[queries_index];
	     }
	     else if( replace_piece == 1 )	//Replace authority
	     {
	    	 url = schemes[scheme_index] + badAuthority[bad_authority_index] + ports[ports_index] + path[path_index] + queries[queries_index];
	     }
	     else if( replace_piece == 2)	//Replace port
	     {
	    	 url = schemes[scheme_index] + authority[authority_index] + badPorts[bad_ports_index] + path[path_index] + queries[queries_index];
	     }
	     else							//Replace path
	     {
	    	 url = schemes[scheme_index] + authority[authority_index] + ports[ports_index] + badPath[bad_path_index] + queries[queries_index];
	     }

	     //System.out.println(i + " : " + url + "\n");

         UrlValidator validator =  new UrlValidator();
         boolean test = validator.isValid(url);
         if ( test == true ) {
             bugs++;           // increase number of bugs
             passed[r] = url;  // save the failed url
             r++;              // increment the counter
         }
	 }	//end for loop

     /* Print out number of bugs and improperly passed urls */
     System.out.println("Invalid Url Tests found " + bugs + " bugs. \n");
     System.out.println("Incorrectly Passed Urls: \n");
     for ( int i=0; i< failed.length; i++) {
       if ( passed[i] != null ) System.out.println( i + " : " + passed[i] + "\n");
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
