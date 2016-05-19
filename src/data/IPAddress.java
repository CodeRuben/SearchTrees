package data;

import searchtrees.*;

/**
 * Class that stores an ip address along with it's associated country,
 * latitude and longitude values.
 * Class implements the Comparable interface. 
 * 
 * @author Ruben Ramirez 
 */

public class IPAddress implements Comparable<IPAddress>{
  
	public String ip_address;
	public String country;
	public double latitude;
	public double longitude;
	
    // Default constructor
	public IPAddress() {
		this.ip_address = null;
		this.country = null;
		this.latitude = 0;
		this.longitude = 0;
	}
	
	public IPAddress(String ip, String country, double lat, double lon) {
		this.ip_address = ip;
		this.country = country;
		this.latitude = lat;
		this.longitude = lon;
	}

	/**
	 * Compares two IPAddress objects by evaluating the String 
	 * value associated with the ip address. Returns 0 if the
	 * objects are equal. 
	 * @param obj, the IPAddress object to be compared.
	 * @return an integer value indicating whether one object
	 * is greater than, less than, or equal two the the other.
	 */
	public int compareTo(IPAddress ip) {
		String one = "", two = "";
        int i = 0, j = 0;
        
		for(int k = 0; k < 4; k++) {
			while(i < ip_address.length() && ip_address.charAt(i) != '.')
				one = one + ip_address.charAt(i++);
			while(j < ip.ip_address.length() && ip.ip_address.charAt(j) != '.')
				two = two + ip.ip_address.charAt(j++);
			
            if(one.length() == two.length() && one.compareTo(two) != 0)
            	return one.compareTo(two);
            else if(one.length() > two.length())
            	return 1;
            else if(one.length() < two.length())
            	return -1;
            i++;
			j++;
        }
		return 0;
	}
	
	/**
	 * Generates and returns a hash code value using the string 
	 * ip_address instance variable.
	 * @return hash, the calculated integer hash value
	 */
	public int hashCode() {
		int hash = 7;
		
		for(int i = 0; i < ip_address.length(); i++)
			hash = (hash << 5) + ip_address.charAt(i);
		return hash;
	}
	
	/**
	 * Returns a hash code calculated using multiplication
	 * @return hash, the calculated integer hash value
	 */
	public int hashOne() {
      long hash = 3;
      
      for(int i = 0; i < ip_address.length(); i++) 
          hash = hash * ip_address.charAt(i);
      return (int) hash;
	}
	
	/**
	 * Implementation of Bob Jenkins' hash value function
	 * @return hash, the calculated integer hash value
	 */
	public int hashTwo() {
      int hash = 1;
      
      for(int i = 0; i < ip_address.length(); ++i) {
          hash = hash + ip_address.charAt(i);
          hash = hash + (hash << 10);
          hash = hash ^ (hash >> 6);
      } 
      hash = hash + (hash << 3);
      hash = hash ^ (hash >> 11);
      hash = hash + (hash << 15);
      
      return hash;
	}
	
	/**
	 * Implementation of the Fowler-Noll-Vo hash value function
	 * created by Glenn Fowler, Landon Curt Noll and Kiem-Phong Vo.
	 * @return hash, the calculated integer hash value
	 */
	public int hashThree() {
        int hash = 0;
        int prime = 0x01000193;
        long offset = 0x811c9dc5;
        
        for(int i = 0; i < ip_address.length(); ++i) {
            hash = hash * prime;
            hash = hash ^ ip_address.charAt(i);
        }
        return hash;
	}

	/**
	 * Compares the objects by using the ip_address instance variable
	 * @param IPAddress, the object to be compared
	 * @return true if the ip_addresses are the same, false otherwise
	 */
	public boolean equals(IPAddress obj) {
		return obj.equals(ip_address);
	}
	
	/**
	 * Returns a string of the ip address stored in the object.
	 * @return the string containing the ip address of the object.
	 */
	public String toString() {
		return "IPAddress [ip_address = " + ip_address + ", country = " + country + 
				", latitude = " + latitude + ", longitude = " + longitude + "]";
	}
}
   