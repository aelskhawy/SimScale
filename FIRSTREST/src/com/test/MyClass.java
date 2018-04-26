package com.test;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import testPackage.*; 



@Path("/status") 

public class MyClass {

	 static int x=100;
	Sieve mysieve= new Sieve();
	

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public int returnTitle(){
		x= mysieve.prime_sieve(0,100);	
		return x ;
	}
}