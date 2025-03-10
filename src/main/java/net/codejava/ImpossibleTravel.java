package net.codejava;

import java.sql.*;

import java.util.*;

import java.time.*;

import java.time.format.*;

import java.time.temporal.ChronoUnit;

public class ImpossibleTravel {
	public static double getRisk(String uname)
	{
		try
    	{
    		Connection c;
            Statement s;
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/capstone?zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false","root","Lalitha@1");
            s = c.createStatement();
            
            String x="select * from UserLoginInfo where Username='" +uname +"';";
            
            ResultSet rs = s.executeQuery(x);
            
            ArrayList<ArrayList<Object>> UserList = new ArrayList<ArrayList<Object>>();
            
            while(rs.next())
            {
            	ArrayList<Object>UserInfo=new ArrayList<Object>();
            	UserInfo.add(rs.getString("Username"));
            	UserInfo.add(rs.getString("CreateDateTime"));
            	UserInfo.add(rs.getString("LocationCity"));
            	UserInfo.add(rs.getString("LocationCountryorRegion"));
            	UserInfo.add(rs.getFloat("LocationLatitude"));
            	UserInfo.add(rs.getFloat("LocationLongitude"));
            	UserList.add(UserInfo);
            }
            
            Collections.sort(UserList, new Comparator<ArrayList<Object>> () {
                @Override
                public int compare(ArrayList<Object> a, ArrayList<Object> b) {
                    return (a.get(1).toString()).compareToIgnoreCase(b.get(1).toString());
                }
            });
            
            for(int i=0;i<UserList.size();i++)
            {
            	for(int j=0;j<UserList.get(i).size();j++)
            	{
            		System.out.print(UserList.get(i).get(j) + " ");
            	}
            	
            	System.out.print("\n");
            }
            
            int n=UserList.size();
        	ArrayList<Object> tempi=UserList.get(n-2);
        	ArrayList<Object> tempi1=UserList.get(n-1);
        	
        	//System.out.print(tempi.get(0) + " ");
    		//System.out.print(tempi1.get(0) + " ");
        	
    		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
    		LocalDateTime dateTime1 = LocalDateTime.parse(tempi.get(1).toString(), formatter);
    		LocalDateTime dateTime2 = LocalDateTime.parse(tempi1.get(1).toString(), formatter);

    		//System.out.print(dateTime1 + " ");
    		//System.out.print(dateTime2 + " ");
    		
    		long seconds = dateTime1.until( dateTime2, ChronoUnit.SECONDS );
    		
    		 double lon1 = Math.toRadians((Float)tempi.get(5));
    	     double lon2 = Math.toRadians((Float)tempi1.get(5));
    	     double lat1 = Math.toRadians((Float)tempi.get(4));
    	     double lat2 = Math.toRadians((Float)tempi1.get(4));
    	     
    	     double dlon = Math.abs(lon2 - lon1);
    	        double dlat = Math.abs(lat2 - lat1);
    	        double a = Math.pow(Math.sin(dlat / 2), 2)
    	                 + Math.cos(lat1) * Math.cos(lat2)
    	                 * Math.pow(Math.sin(dlon / 2),2);
    	             
    	        double c_ = 2 * Math.asin(Math.sqrt(a));

    	        double r = 6371;
    	        
    	      double dist=c_*r;
    	      
    	      double speed=dist/seconds;
    	      
    	      /*if(speed>0.244) --flight=0.244km/s
    	      return 10;
    	      
    	      if(speed>0.083) --train=0.083km/s
    	      return 7.5;
    	      
    	      if(speed>0.02) --bus=0.02km/s
    	      return 5;*/
    	      
    	      // 5* v/(R*Vm)
    	      
    	      /*if(speed>0.244)
    	      {
    	    	  return 5;
    	      }*/
    	      
    	      double RF;
    	      double RT;
    	      double RB;
    	      
    	      
    	      //System.out.println(speed);
    	      
    	      if(tempi.get(3).toString().equals(tempi1.get(3).toString()))
    	      {
    	    	  RF=5;
    	    	  RT=10;
    	    	  RB=10;
    	      }
    	      
    	      else
    	      {
    	    	  RF=10;
    	    	  RT=5;
    	    	  RB=2;
    	      }
    	      
    	      double riskFlight=50*(speed/(RF*0.244));
    	      double riskTrain=50*(speed/(RT*0.083));
    	      double riskBus=50*(speed/(RB*0.02));
    	      
    	      double ans;
    	      
    	      if(riskFlight<riskTrain)
    	      {
    	    	  if(riskBus<riskFlight)
    	    		  ans=riskBus;
    	    	  
    	    	  else
    	    		  ans=riskFlight;
    	    	  
    	      }
    	      
    	      else
    	      {
    	    	  if(riskBus<riskTrain)
    	    		  ans=riskBus;
    	    	  
    	    	  else 
    	    		  ans=riskTrain;
    	      }
    	      
    	      if(ans>5)
        	      return 5;
    	      
    	      return ans;
            
     
    	}
    	
    	catch(Exception e){
            System.out.println(e);
        }
		
		return 0;
	}
	
	public static void main(String[] args)
	{
		double k=getRisk("dishitha123");
		System.out.println("\n"+k+" ");
	}
}
