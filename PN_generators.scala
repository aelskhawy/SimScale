import util.control.Breaks._
import scala.math._
import scala.collection._
import scala.collection.mutable.ListBuffer

//package testPackage {

class prime_1st(){
	
   def FindPrime(lb:Long, mynum: Long) {
	   var prime_list = new ListBuffer[Long]()
	   var x: Long =0
	   if (lb <=2) {prime_list+=2}
	   if (lb <=3) {prime_list+=3; x=4}
	   else{ x=lb }
	   while (x <= mynum){
		   breakable{
			   if ((x %2 ==0)||(x %3 ==0)){x+=1; break} // x is not prime, break from the breakable to continue the loop
				var i =5 
				// no need to check after (sqrt(number)) because divisors repeat
				while(i*i <= x){
				if ((x %i ==0)||(x %(i+2) ==0)) {x+=1; break} // x is not prime, break
				i+=6 //skipping all multiples of 2 and 3 which we already checked
				}
				// loop finished then x is prime
				prime_list+=x
				x+=1
			}
	   }
	   println("Prime Numbers are :" + prime_list)
	   println("# of prime numbers" + prime_list.length)
   }
}

/* still to be done:
replace the filtering with just marking the indecis with true or false , this might be faster 

*/

class Sieve() {
	
	def prime_sieve(lb:Long, n:Long): Int ={
		val sqrt_n= math.round(math.sqrt(n))
		var loop_end= sqrt_n 
		var offset=0
		if( lb %2 == 0) offset=1
		// popuating a list up to the sqrt(n) with odd numbers only, because after it the composition repeats
		var num_list= List.range(lb+offset,loop_end,2) 
		var buff= num_list.to[ListBuffer]
		 // removing multiples of 3. 1 is also not a prime
		buff = buff.filter(_ %3 != 0)
		buff = buff.filter(_ != 1) 
		var i=5
		var j=0
		// Loop for finding the Prime numbers up to sqrt(n)
		while (i < loop_end){
			j= i*i
			// if j= i^2 > = n, then we passed the sqrt(n) value 
			  if(j < n ){
				  // removing the items that are divisable by i, but not i itself
				 buff = buff.filter(x => ((x %i != 0) || (x == i)))
			}
			i+=2 // looping only over mutliples of odd numbers as we have not even generated the even numbers
		} 

		//populate the rest of the array and remove the multiples of the previously found prime numbers
		
		/* OLd approach:
		var rest= List.range(loop_end+ loop_end %3, n+1, 2) // to make sure to start at odd number 
		var rest_buff =rest.to[ListBuffer]
		rest_buff = rest_buff.filter(_ %3 != 0)  
		for (i <- buff){
			rest_buff=rest_buff.filter(_ %i !=0)
		}*/
		
		/* new approach to divide the rest to chunks and proccess sequentially (later on multi cores*/
		i=2
		var off=0
		if (sqrt_n %2 ==0) off=1 // to generate odd numbers only 
		while ( i*i <= n){	
			var chunk_size=sqrt_n
			var chunk_sqrt= math.round(math.sqrt(i*chunk_size))
			var chunk= List.range( (i-1)*sqrt_n +off, i*chunk_size,2)
			var chunk_buff= chunk.to[ListBuffer]
			chunk_buff = chunk_buff.filter(_ %3 != 0)
			for (i <- buff){
				if(i <= chunk_sqrt){
					chunk_buff=chunk_buff.filter(_ %i !=0)					
				}
			}
			i+=1
			buff= buff ++ chunk_buff // appending the new prime numbers in the current chunk 
		}
		
		if(lb <= 3) 3 +=: buff
		if(lb <= 2) 2 +=: buff
		// removing, if any, prime numbers < lower_bound
		buff= buff.filter( _ >= lb)
		println("Prime Numbers: " + buff)
		return buff.length
	}
}



object Demo {
   def main(args: Array[String]) {
	  
	   println("Please enter the lower bound:")
			
	   val lower_bound=scala.io.StdIn.readLong()
	   if(lower_bound < 0) println("Wrong input, please enter positive integers")
	   println("Please enter the UPPER bound:")
       val upper_bound=scala.io.StdIn.readLong()
	   
	   println("Please choose the method: \n \n" + "1. Trial Division\n" + "2. Sieve of Eratosthenes (segmented)\n")
	   val option=scala.io.StdIn.readInt()
	  
		
	   println("lower_bound: "+ lower_bound )
	   println("Upper_bound: "+ upper_bound )
	   println("Choosen Option: "+ option +"\n")
	   
	   if(option ==1){
		   time{
		   val prime_numbers= new prime_1st()
		   prime_numbers.FindPrime(lower_bound, upper_bound)
		   }
       }
	   
	   else{
		time{
		  val nd_app= new Sieve(); 
		  var x= 0
		  x = nd_app.prime_sieve(lower_bound, upper_bound) 
		  
		  println("number of prime Numbers between " + lower_bound + " and " + upper_bound + " is " + x)
		}
	   }
   }
   
   def time[R](block: => R): R = {  
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0)/ 1000000000.0 + "sec")
    result
}
}

//}